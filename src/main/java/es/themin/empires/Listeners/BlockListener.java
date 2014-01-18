package es.themin.empires.Listeners;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import es.themin.empires.empires;

public class BlockListener implements Listener {

	
	private empires plugin;
	public BlockListener(empires myPlugin){
		this.plugin = myPlugin;
	}
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Block myBlock = event.getBlock();
		List<MetadataValue> values = myBlock.getMetadata("coreType");
		for(MetadataValue value : values){
			if (value.getOwningPlugin().equals(plugin)){
				String myCoreType = value.asString();
				Bukkit.broadcastMessage("cannot delete core block of type: " + myCoreType);
				event.setCancelled(true);
			}
		}
		
	}
}
