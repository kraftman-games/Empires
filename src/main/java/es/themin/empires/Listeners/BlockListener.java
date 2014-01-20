package es.themin.empires.Listeners;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import es.themin.empires.empires;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.Core;
import es.themin.empires.util.CoreUtils;
import es.themin.empires.util.SettingsManager;
import es.themin.empires.util.UtilManager;

public class BlockListener implements Listener {

	
	private empires plugin;
	public BlockListener(empires myPlugin){
		this.plugin = myPlugin;
	}
	private static HashMap<Block, Material> burnt = new HashMap<Block, Material>();
	
//	@EventHandler
//	public void onBlockBreak(BlockBreakEvent event){
//		we need to think about the regeneration mechanic
//		one option is to store every break and place an enemy makes
//      and then slowly revert depending on rate
//      will need to add a property to the empire for blocks placed and blocks broken by enemies
//	}
	
	@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {
		if (SettingsManager.getInstance().getConfig().getString("regeneration.enable").equalsIgnoreCase("true") && SettingsManager.getInstance().getConfig().getString("regeneration.burn.enable").equalsIgnoreCase("true")) {
			Random r = new Random();
			final Block b = event.getBlock();
			final Material m = b.getType();
			burnt.put(b,m);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					if (b.getType() == Material.AIR) {
						b.setType(m);
						burnt.remove(b);
						Bukkit.broadcastMessage("Burn fixed");
					}
					
				}
				
			}, SettingsManager.getInstance().getConfig().getInt("regeneration.burn.delay") * 20 + r.nextInt(600));
		}
	}
	//for disable
	public static void fixBurns(){
		for (Block b : burnt.keySet()) {
			if (b.getType() == Material.AIR) {
				b.setType(burnt.get(b));
				burnt.remove(b);
			}
		}
	}
}




