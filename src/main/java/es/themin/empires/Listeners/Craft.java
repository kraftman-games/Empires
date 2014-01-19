package es.themin.empires.Listeners;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import es.themin.empires.empires;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.UtilManager;
import es.themin.empires.util.testing.Recipes;

public class Craft implements Listener{
	
	private empires plugin;
	public Craft(empires plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerCraft(CraftItemEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getRecipe() == Recipes.amplifierRecipe()) {
			ItemStack myItem = new ItemStack(Material.FLINT);
			ItemMeta myMeta = myItem.getItemMeta();
			myMeta.setDisplayName("Core Shard");
			
			ArrayList<String> myLore = new ArrayList<String>();
			myLore.add(ChatColor.GREEN + "A faint glow eminates from within");
			
			myMeta.setLore(myLore);
			
			myItem.setItemMeta(myMeta);
			if (!(event.getInventory().contains(myItem)) || !(UtilManager.empireplayers.containsKey(player.getName())) || !(UtilManager.empireplayers.get(player.getName()).hasCoreOfType(CoreType.BASE))||getDiffernceBetween(player.getLocation().getBlockX(), UtilManager.empireplayers.get(player.getName()).getCoreOfType(CoreType.BASE).getLocation().getBlockX()) > 2|| getDiffernceBetween(player.getLocation().getBlockZ(), UtilManager.empireplayers.get(player.getName()).getCoreOfType(CoreType.BASE).getLocation().getBlockZ()) > 2) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "To craft an amplifier you must place a true shard in your Base core's crafting table");
			}
		}
	}

	public int getDiffernceBetween(int i1, int i2) {
		if (i1>i2) return i1 - i2;
		if (i1<i2) return i2 - i1;
		if (i1==i2) return 0;
		return 0;
		
	}

}
