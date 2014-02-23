package es.themin.empires.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.WorldLoadEvent;

import es.themin.empires.managers.ManagerAPI;

public class WorldListener implements Listener{

	private ManagerAPI myApi = null;
	public WorldListener(ManagerAPI myAPI){
		myApi = myAPI;
	}
	
	@EventHandler
	public void onChunkPopulate(ChunkPopulateEvent event){
		
		
	}
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent  event){
		
		
	}
}
