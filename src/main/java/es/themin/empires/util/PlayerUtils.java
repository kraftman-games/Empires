package es.themin.empires.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerUtils {

	
	/**
	 * returns true if the player had the shards and was deducted
	 * false if they couldnt afford it or it failed
	 */
	public static boolean deductShards(Player myPlayer, int numShards){
		Inventory myInventory = myPlayer.getInventory();
        for(ItemStack myStack : myInventory.getContents()){
            if(myStack!= null && myStack.getType().equals(Material.FLINT)){
          	  ItemMeta myMeta = myStack.getItemMeta();
          	  if (myMeta.getDisplayName() != null && myMeta.getDisplayName().equals("Core Shard") && myStack.getAmount() > 1){
          		  
          		  if(myStack.getAmount() == 2) {
          			  myInventory.remove(myStack);
          		  } else {
          			  myStack.setAmount(myStack.getAmount()-2);
          		  }
          		myPlayer.updateInventory();
          		  return true;
          	  }
            }
        }
        return false;
	}
}
