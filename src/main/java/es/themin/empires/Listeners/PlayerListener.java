package es.themin.empires.Listeners;


import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
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
			
			Block myBlock = event.getClickedBlock();
			
			UUID myUUID = myBlock.getLocation().getWorld().getUID();
			CoreWorld myCoreWorld = UtilManager.getWorlds().get(myUUID);
			
			ArrayList<Integer> myCores = myCoreWorld.getCoresInGrid(myBlock.getX(), myBlock.getY());
			
			for(Integer i : myCores){
				//faster than global core list since there are less
				Core myCore = myCoreWorld.getCoreByID(i);
				Integer x = myCore.getLocation().getBlockX();
				Integer z = myCore.getLocation().getBlockZ();
				if (x - myCore.getSize() < myBlock.getX() && x + myCore.getSize() > myBlock.getX()){
					if (z - myCore.getSize() < myBlock.getZ() && z + myCore.getSize() > myBlock.getZ()){
						//the player is within the bounds of the core
					}
				}
			}
			
			
			//if its in the players empire and its a normal block, do nothing
			//if its in the players empire and its a core, try and delete it
			//if its an enemy block and its base protection, insta break (if the empire can be attacked)
			
		}
	}
}




