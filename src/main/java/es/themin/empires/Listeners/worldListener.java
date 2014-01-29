package es.themin.empires.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;

import es.themin.empires.empires;
import es.themin.empires.util.UtilManager;

public class worldListener implements Listener{

	
	private empires plugin;
	public worldListener(empires myPlugin){
		this.plugin = myPlugin;
	}
	
	@EventHandler
	public void onChunkPopulate(ChunkPopulateEvent event){
		
		
	}
}
