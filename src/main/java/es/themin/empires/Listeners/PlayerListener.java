package es.themin.empires.Listeners;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import es.themin.empires.empires;
import es.themin.empires.cores.Core;
import es.themin.empires.enums.BattleType;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.BlockUtils;
import es.themin.empires.util.CoreWorld;
import es.themin.empires.util.Empire;
import es.themin.empires.util.SettingsManager;
import es.themin.empires.util.UtilManager;
import es.themin.empires.wars.Battle;
import es.themin.empires.wars.Battle.BattleTeam;
import es.themin.empires.wars.War;

public class PlayerListener implements Listener{
	
	private empires plugin;
	public PlayerListener(empires myPlugin){
		this.plugin = myPlugin;
	}
	public String warprefix= plugin.warprefix;
	
	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		if (SettingsManager.getInstance().getPlayerData().get(player.getName()) != null && UtilManager.containsEmpireWithId(SettingsManager.getInstance().getPlayerData().getInt(player.getName() + ".empire"))) {
			UtilManager.empireplayers.put(player.getName(), UtilManager.getEmpireWithId(SettingsManager.getInstance().getPlayerData().getInt(player.getName() + ".empire")));
		}
		Bukkit.broadcastMessage("test 1");
		SettingsManager.getInstance().getPlayerData().set("test 1", "true");
		SettingsManager.getInstance().savePlayerData();
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if (UtilManager.empireplayers.containsKey(event.getPlayer().getName())/* && UtilManager.empireplayers.get(player).getEmpire() != null*/) {
			SettingsManager.getInstance().getPlayerData().set(player.getName() + ".empire", UtilManager.empireplayers.get(player.getName()).getId());
			SettingsManager.getInstance().savePlayerData();
		}else {
			SettingsManager.getInstance().getPlayerData().set(player.getName(), null);
			SettingsManager.getInstance().savePlayerData();
		}
	}
		
	@EventHandler
	  public void onPlayerInteractEvent(PlayerInteractEvent event){
		if (event.getAction() == Action.LEFT_CLICK_BLOCK){
			handleBlockClick(event);
		}
	}
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (player.getKiller() instanceof Player) {
			Player killer = (Player) player.getKiller();
			if (UtilManager.empireplayers.containsKey(player.getName()) && UtilManager.empireplayers.containsKey(killer.getName())) {
				Empire attacker = UtilManager.empireplayers.get(killer.getName());
				Empire defender = UtilManager.empireplayers.get(player.getName());
				if (attacker.isAtWarWith(defender)) {
					War war = attacker.getWarAgainst(defender);
					if (!(attacker.isInABattle()) && !(defender.isInABattle())) {
						Empire team1 = null;
						Empire team2 = null;
						BattleTeam attackingteam = null;
						if (war.getAllEmpiresOnTeam1().contains(attacker)) {
							team1 = attacker;
							attackingteam = BattleTeam.team1;
							team2 = defender;
						}else if (war.getAllEmpiresOnTeam1().contains(defender)){
							team1= defender;
							attackingteam = BattleTeam.team2;
							team2 = attacker;
						}
						Battle battle = new Battle(team1, team2, war, BattleType.DEATHMATCH, attackingteam);
						battle.start();
						war.addBattle(battle);
						war.Save();
						for (Empire empire : war.getAllEmpires()) {
							if (empire != attacker && empire != defender) {
								empire.broadcastMessage(warprefix + ChatColor.RED + "A Battle has broken out between " + attacker.getName() + " and "  + defender.getName() + ". Both sides fight to the death");
							}
						}
						attacker.broadcastMessage(warprefix + ChatColor.RED + killer.getName() + " Has begun a battle against " + defender.getName() + " slaughter them to to tip the balance of the war in your favour");
						defender.broadcastMessage(warprefix + ChatColor.RED + player.getName() + " Was killed by a member of " + attacker.getName() + " slaughter them to to tip the balance of the war in your favour");
					}else if (attacker.isInBattleWith(defender)) {
						Battle battle = war.getOnGoingBattle();
						if (battle.getType() == BattleType.DEATHMATCH) {
							Bukkit.broadcastMessage("kill");
							battle.addPointsToTeamWithEmpire(attacker, 1);
						}
					}
					
				}
			}
		}
		
	}
	private void handleBlockClick(PlayerInteractEvent event){
		
		Block myBlock = event.getClickedBlock();
		Empire eventPlayerEmpire = UtilManager.empireplayers.get(event.getPlayer().getName());
		Player myPlayer = event.getPlayer();
		UUID myUUID = myBlock.getLocation().getWorld().getUID();
		CoreWorld myCoreWorld = UtilManager.getWorlds().get(myUUID);
		HashMap<Integer, Core> myCores = myCoreWorld.getCoresInGrid(myBlock.getX(), myBlock.getY());
		ArrayList<Core> myMatchingCores = new ArrayList<Core>();
		
		Core selectedCore = null;
		
		boolean isCoreBlock = false;
		
		for(Core myCore : myCores.values()){
			//faster than global core list since there are less
			
			if (myCore.isAreaBlock(myBlock)){
				if (myCore.isCoreBlock(myBlock)){
					isCoreBlock = true;
				} else {
					myMatchingCores.add(myCore);
				}
			}
		}
		
		if (myMatchingCores == null || myMatchingCores.size() < 1){
			return;
		}
		
		selectedCore = chooseCore(myMatchingCores);
		
		//get block metadata to see if its special.
		
		// if its the players own empire
		if (selectedCore.getEmpire().equals(eventPlayerEmpire)){
			if (isCoreBlock){
				myPlayer.sendMessage("You cannot destroy your own core!");
				return;
			} else {
				//check its not some special block we havnt incented yet
			}
		} else {
			//its an enemy empire, which can either be protected (repairing) at war, or ready for war.
			if (selectedCore.getEmpire().canPlayerAttack(myPlayer)){
				//selectedCore.getEmpire().startWar(eventPlayerEmpire);
			}
		}			
	}
	
	private Core chooseCore(ArrayList<Core> myCores){
		//we need to choose which overlapping core is the one we want
		//eventually it might need to be a bit more complex
		//for now just choose the first.
		Core myCore = null;
		if (myCores.size() > 1){
			
			myCore = myCores.get(0); //maybe a different selection in future
		} else {
			myCore = myCores.get(0);
		}
		return myCore;
	}
	
}


