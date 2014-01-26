package es.themin.empires.util.testing;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.SubCommand;
import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;

public class newemp extends SubCommand{
	private empires plugin;
	public String plprefix = plugin.plprefix;
	public boolean onCommand(Player player, String[] args) {
		try {
			//validation moved to constructor
			Empire empire = new Empire(args[1], player);
			empire.Save();
			player.sendMessage(plprefix + ChatColor.GREEN + "Created Empire: " + args[1]);
		} catch (Exception e) {
			
		}
		return false;
	}

	public String name() {
		return "newemp";
	}
	public String info() {
		return "creates an empire";
	}
	public String[] aliases() {
		return new String[] { "ne" };
	}
	
}
