package es.themin.empires.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import es.themin.empires.empires;

public class WorldListener implements Listener{

	
	private empires plugin;
	public WorldListener(empires myPlugin){
		this.plugin = myPlugin;
	}
	
	@EventHandler
	public void onChunkPopulate(ChunkPopulateEvent event){
		
		
	}
}
