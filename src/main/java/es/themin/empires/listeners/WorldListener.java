package es.themin.empires.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.WorldLoadEvent;

import es.themin.empires.managers.ManagerBL;

public class WorldListener implements Listener{

	private ManagerBL myApi = null;
	public WorldListener(ManagerBL myAPI){
		myApi = myAPI;
	}
	
	@EventHandler
	public void onChunkPopulate(ChunkPopulateEvent event){
		
		
	}
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent  event){
		
		
	}
}
