package es.themin.empires.listeners;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import es.themin.empires.cores.Core;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.EWorld;
import es.themin.empires.util.Empire;

public class PlayerListener implements Listener{
	
	private ManagerAPI myApi = null;
	
	public PlayerListener(ManagerAPI myAPI){
		myApi = myAPI;
	}
	
	
	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		
		myApi.getEPlayer(player);
		
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		EPlayer myEPlayer = myApi.getEPlayer(event.getPlayer());
		myApi.removePlayer(myEPlayer);
		
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
			
			EPlayer defender = myApi.getEPlayer(player);
			EPlayer attacker = myApi.getEPlayer(killer);
			
			
			if (defender != null && attacker != null) {
				
				
//				if (attacker.isAtWarWith(defender)) {
//					War war = attacker.getEmpire().getWarAgainst(defender.getEmpire());
//					if (!(attacker.getEmpire().isInABattle()) && !(defender.getEmpire().isInABattle())) {
//						Empire team1 = null;
//						Empire team2 = null;
//						BattleTeam attackingteam = null;
//						if (war.getAllEmpiresOnTeam1().contains(attacker)) {
//							team1 = attacker.getEmpire();
//							attackingteam = BattleTeam.team1;
//							team2 = defender.getEmpire();
//						}else if (war.getAllEmpiresOnTeam1().contains(defender)){
//							team1= defender.getEmpire();
//							attackingteam = BattleTeam.team2;
//							team2 = attacker.getEmpire();
//						}
//						Battle battle = new Battle(team1, team2, war, BattleType.DEATHMATCH, attackingteam);
//						battle.start();
//						war.addBattle(battle);
//						war.Save();
//						for (Empire empire : war.getAllEmpires()) {
//							if (empire != attacker.getEmpire() && empire != defender.getEmpire()) {
//								empire.broadcastMessage(warprefix + ChatColor.RED + "A Battle has broken out between " + attacker.getEmpire().getName() + " and "  + defender.getEmpire().getName() + ". Both sides fight to the death");
//							}
//						}
//						attacker.getEmpire().broadcastMessage(warprefix + ChatColor.RED + killer.getName() + " Has begun a battle against " + defender.getEmpire().getName() + " slaughter them to to tip the balance of the war in your favour");
//						defender.getEmpire().broadcastMessage(warprefix + ChatColor.RED + player.getName() + " Was killed by a member of " + attacker.getEmpire().getName() + " slaughter them to to tip the balance of the war in your favour");
//						battle.addPointsToTeamWithEmpire(attacker.getEmpire(), 1);
//					}else if (attacker.getEmpire().isInBattleWith(defender.getEmpire())) {
//						Battle battle = war.getOnGoingBattle();
//						if (battle.getType() == BattleType.DEATHMATCH) {
//							//Bukkit.broadcastMessage("kill");
//							battle.addPointsToTeamWithEmpire(attacker.getEmpire(), 1);
//						}
//					}
//					
//				}
			}
		}
		
	}
	private void handleBlockClick(PlayerInteractEvent event){
		
		Block myBlock = event.getClickedBlock();
		
		Player myPlayer = event.getPlayer();
		
		EPlayer myEPlayer = myApi.getEPlayer(myPlayer);
		Empire eventPlayerEmpire = myApi.getEmpire(myEPlayer.getEmpireUUID());
		UUID worldUUID = myBlock.getLocation().getWorld().getUID();
		EWorld myCoreWorld = myApi.getEWorld(worldUUID);
		HashMap<UUID, Core> myCores = myCoreWorld.getCoresInGrid(myBlock.getX(), myBlock.getY());
		ArrayList<Core> myMatchingCores = new ArrayList<Core>();
		
		Core selectedCore = null;
		
		boolean isCoreBlock = false;
		if (myCores != null){
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
		}
		
		if (myMatchingCores == null || myMatchingCores.size() < 1){
			return;
		}
		
		selectedCore = chooseCore(myMatchingCores);
		
		//get block metadata to see if its special.
		
		// if its the players own empire
		if (selectedCore.getEmpireUUID().equals(eventPlayerEmpire.getUUID())){
			if (isCoreBlock){
				myPlayer.sendMessage("You cannot destroy your own core!");
				return;
			} else {
				//check its not some special block we havnt incented yet
			}
		} else {
			//its an enemy empire, which can either be protected (repairing) at war, or ready for war.
//			if (selectedCore.getEmpire().canPlayerAttack(myEPlayer)){
//				//selectedCore.getEmpire().startWar(eventPlayerEmpire);
//			}
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



//this is the code to move them to a random location

//Block myBlock = myPlayer.getWorld().getHighestBlockAt(x,z);
//myBlock.getChunk().load();
//Chunk myChunk = myBlock.getChunk();
////myChunk.load(true);
//
//if (myBlock.getType() != Material.AIR){
//	if (myBlock.getType() == Material.GRASS){
//		
//		
//		myPlayer.getWorld().loadChunk(myChunk);
//		myPlayer.getWorld().refreshChunk(myChunk.getX(), myChunk.getZ());
//		myPlayer.sendMessage("X: "+x+"Z: "+z);
//		myPlayer.setFallDistance(0.0F);
//		myPlayer.teleport(new Location(myBlock.getWorld(), myBlock.getX(), myBlock.getY()+1, myBlock.getZ()));
//		
//		
////		myPlayer.setAllowFlight(true);
////		myPlayer.setFlying(true);
////		
////		UtilManager.tannerTemp = myBlock.getLocation();
////		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
////		Plugin thisPlugin = Bukkit.getServer().getPluginManager().getPlugin("Empires");
////		scheduler.scheduleSyncDelayedTask(thisPlugin, new Runnable() {
////			@Override
////            public void run() {
////				Bukkit.getServer().loadEPlayer("kraftman").teleport(UtilManager.tannerTemp);
////				Bukkit.getServer().loadEPlayer("kraftman").setFlying(false);
////
////				Bukkit.getServer().loadEPlayer("kraftman").setAllowFlight(false);
////				Bukkit.getServer().loadEPlayer("kraftman").sendMessage("moving you");
////            }
////        }, 400L);
//		
//		
//		return true;
//	} else {
//		return false;
//	}
//}
