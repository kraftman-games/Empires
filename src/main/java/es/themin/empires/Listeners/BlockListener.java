package es.themin.empires.Listeners;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
				Inventory myInventory = player.getInventory();
		          for(ItemStack myStack : myInventory.getContents()){
		              if(myStack!= null && myStack.getType().equals(Material.FLINT)){
		            	  ItemMeta myMeta = myStack.getItemMeta();
		            	  if (myMeta.getDisplayName() != null && myMeta.getDisplayName().equals("Core Shard") && myStack.getAmount() > 1){
		            		  Bukkit.broadcastMessage("deleted core block of type: " + myCoreType);
		            		  myStack.setAmount(myStack.getAmount()-2);
		            		  player.updateInventory();
		      				 myCore.Delete();
		      				 break;
		            	  }
		              }
		          }
				
			}else {
				Bukkit.broadcastMessage("cannot delete core block of type: " + myCoreType);
				event.setCancelled(true);
			}
		}
	}
}




