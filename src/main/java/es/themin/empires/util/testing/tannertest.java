package es.themin.empires.util.testing;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.themin.empires.cmds.SubCommand;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.Core;

public class tannertest extends SubCommand{

	@Override
	public boolean onCommand(Player player, String[] args) {
		ItemStack myItem = new ItemStack(Material.FLINT);
		player.getInventory().addItem(myItem);
		
		return false;
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
