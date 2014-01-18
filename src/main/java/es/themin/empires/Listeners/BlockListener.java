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
import es.themin.empires.util.CoreUtils;
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
		
		CoreType myCoreType = CoreUtils.getCoreTypeFromBlock(myBlock, plugin);
		Core myCore = CoreUtils.getCoreFromBlock(myBlock, plugin);
		
		if (myCore != null){
			if (myCore.getEmpire() == UtilManager.empireplayers.get(player.getName())){
				Bukkit.broadcastMessage("deleted core block of type: " + myCoreType);
				myCore.Delete();
			}else {
				Bukkit.broadcastMessage("cannot delete core block of type: " + myCoreType);
				event.setCancelled(true);
			}
		}
	}
}




