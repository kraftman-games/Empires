package es.themin.empires.Listeners;


import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import es.themin.empires.empires;
import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.BlockUtils;
import es.themin.empires.util.CoreWorld;
import es.themin.empires.util.Empire;
import es.themin.empires.util.SettingsManager;
import es.themin.empires.util.UtilManager;

public class PlayerListener implements Listener{
	
	private empires plugin;
	public PlayerListener(empires myPlugin){
		this.plugin = myPlugin;
	}
	
	
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
	
	private void handleBlockClick(PlayerInteractEvent event){
		//if its in the players empire and its a normal block, do nothing
		//if its in the players empire and its a core, try and delete it
		//if its an enemy block and its base protection, insta break (if the empire can be attacked)
		
		
		Block myBlock = event.getClickedBlock();
		Empire myPlayerEmpire = UtilManager.empireplayers.get(event.getPlayer().getName());
		Player myPlayer = event.getPlayer();
		UUID myUUID = myBlock.getLocation().getWorld().getUID();
		CoreWorld myCoreWorld = UtilManager.getWorlds().get(myUUID);
		
		ArrayList<Integer> myCores = myCoreWorld.getCoresInGrid(myBlock.getX(), myBlock.getY());
		
		ArrayList<Core> myMatchingCores = new ArrayList<Core>();
		
		Core selectedCore = null;
		
		//not sure how to deal with overlapping cores for attackers
		//we will need to decide how they destroy
		
		boolean isEnemyEmpire = false;
		boolean isSpecialCore = false;
		Core griefCore = null;
		
		for(Integer i : myCores){
			//faster than global core list since there are less
			Core myCore = myCoreWorld.getCoreByID(i);
			
			if (myCore.isAreaBlock(myBlock)){
				if (myCore.isCoreBlock(myBlock)){
					//the block belongs to the core
				} else {
					//its in the cores area but not a core block
					//add to the list we found
					myMatchingCores.add(myCore);
				}
			}
		}
		
		if (myMatchingCores.size() < 1){
			//its not part of any core, act normal
			//and return
		} else if (myMatchingCores.size() > 1){
			//we need to choose which core this is
			//for now just choose the first.
			selectedCore = myMatchingCores.get(0);
		} else {
			selectedCore = myMatchingCores.get(0);
		}
		
		// we now have the core that contains the block the player is trying to destroy
		if (selectedCore.getEmpire().equals(myPlayerEmpire)){
			//its their empire, let them do what they want
		} else {
			//its an enemy empire, which can either be protected (repairing) at war, or ready for war.
			if (selectedCore.getEmpire().canPlayerAttack(myPlayer)){
				
			}
		}		
		
		//check block meta data for special cases
		if (isEnemyEmpire && !isSpecialCore && griefCore != null){
			griefCore.addGriefedBlock(myBlock);
			myBlock.setType(Material.AIR);
		}		
	}
}


//the player is within the bounds of the core

//if (myCore.getEmpire().equals(myBlockEmpire)){
//	event.getPlayer().sendMessage("You cannot destroy your own cores");
//	break;
//} else {
//	if (myBlockEmpire.isProtected()){
//		event.getPlayer().sendMessage("You cannot attack this empire");
//		break;
//	} else {
//		if (myCore.getCoreType() == CoreType.GRIEF){
//			isEnemyEmpire = true;
//			griefCore = myCore;
//		} else {
//			
//		}
//	}
//}

