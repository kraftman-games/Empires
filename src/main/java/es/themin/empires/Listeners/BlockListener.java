package es.themin.empires.Listeners;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import es.themin.empires.empires;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.Core;
import es.themin.empires.util.UtilManager;

public class BlockListener implements Listener {

	
	private empires plugin;
	public BlockListener(empires myPlugin){
		this.plugin = myPlugin;
	}
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Player player = event.getPlayer();
		Block myBlock = event.getBlock();
		
		CoreType myCoreType = getCoreType(myBlock);
		
		List<MetadataValue> values = myBlock.getMetadata("coreType");
		List<MetadataValue> values2 = myBlock.getMetadata("core");
		for(MetadataValue value : values){
			if (value.getOwningPlugin().equals(plugin)){
				for (MetadataValue value2 : values2) {
					if (value2.getOwningPlugin().equals(plugin)) {
						int id = Integer.parseInt(value2.asString());
						Core core = UtilManager.getCoreWithId(id);
						if (core.getEmpire() == UtilManager.empireplayers.get(player.getName())){
							//String myCoreType = value.asString();
							//Bukkit.broadcastMessage("deleted core block of type: " + myCoreType);
							core.Delete();
						}else {
							//String myCoreType = value.asString();
							//Bukkit.broadcastMessage("cannot delete core block of type: " + myCoreType);
							event.setCancelled(true);
						}
					}
				}
			}
		}
		
	}
	
	private CoreType getCoreType(Block myBlock){
		List<MetadataValue> values = myBlock.getMetadata("coreType");
		for(MetadataValue value : values){
			if (value.getOwningPlugin().equals(plugin)){
				String myCoreType = value.asString();
				switch (myCoreType){
				case "BASE":
					return CoreType.BASE;
				default:
					return null;
				}
			
			}
		
		}
		return null;
	}
}




