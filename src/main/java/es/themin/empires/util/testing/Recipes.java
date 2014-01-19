package es.themin.empires.util.testing;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipes {
	
	public static void setupamplifierRecipe(){
		ItemStack amp = new ItemStack(Material.BRICK, 1);
		ItemMeta meta2 = amp.getItemMeta();
	    meta2.setDisplayName(ChatColor.ITALIC + "Amplifier");
	    meta2.setLore(Arrays.asList(ChatColor.GREEN + "This", "is very special", "Use it wisely."));
	    amp.setItemMeta(meta2);

	    ShapelessRecipe precipe = new ShapelessRecipe(amp);
	    precipe.addIngredient(1, Material.FLINT);
	    Bukkit.getServer().addRecipe(precipe);
	}
	public static Recipe amplifierRecipe(){
		ItemStack amp = new ItemStack(Material.BRICK, 1);
		ItemMeta meta2 = amp.getItemMeta();
	    meta2.setDisplayName(ChatColor.ITALIC + "Amplifier");
	    meta2.setLore(Arrays.asList(ChatColor.GREEN + "This", "is very special", "Use it wisely."));
	    amp.setItemMeta(meta2);

	    ShapelessRecipe precipe = new ShapelessRecipe(amp);
	    return precipe;
	    
	}
}