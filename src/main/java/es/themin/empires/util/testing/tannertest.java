package es.themin.empires.util.testing;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import es.themin.empires.cmds.SubCommand;
import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;

public class tannertest extends SubCommand{

	@Override
	public boolean onCommand(Player player, String[] args) {
		Empire empire = UtilManager.empireplayers.get(player.getName());
		Core myCore = new Core(UtilManager.nextUnusedCoreId(), CoreType.GRIEF, player.getLocation(), 1, empire);
		empire.addCore(myCore);
		myCore.Save();
		player.sendMessage("testing grief block");
		return false;
		
		
//		ItemStack myItem = new ItemStack(Material.FLINT);
//		ItemMeta myMeta = myItem.getItemMeta();
//		myMeta.setDisplayName("Core Shard");
//		
//		ArrayList<String> myLore = new ArrayList<String>();
//		myLore.add(ChatColor.GREEN + "A faint glow eminates from within");
//		
//		myMeta.setLore(myLore);
//		
//		myItem.setItemMeta(myMeta);
//		
//		player.getInventory().addItem(myItem);
//		player.sendMessage( ChatColor.RED + "You won teh shardz!");
//		return false;
	}

	@Override
	public String name() {
		return "tannertest";
	}

	@Override
	public String info() {
		return "who knows";
	}

	@Override
	public String[] aliases() {
		return new String[] {"tt"};
	}

}
