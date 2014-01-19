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
		if (args.length == 1) {
			player.sendMessage(plprefix + ChatColor.RED + "Please give a name");
			return false;
		}if (UtilManager.empireplayers.containsKey(player.getName())) {
			player.sendMessage(plprefix + ChatColor.RED  +"You are already in an empire");
			return false;
		}if (UtilManager.containsEmpireWithName(args[1])) {
			player.sendMessage(plprefix + ChatColor.RED + "Empire already exists");
			return false;
		}
		Empire empire = new Empire(UtilManager.nextUnusedEmpireId(), args[1], player.getName());
		empire.addPlayer(player.getName());
		UtilManager.empireplayers.put(player.getName(), empire);
		empire.Save();
		player.sendMessage(plprefix + ChatColor.GREEN + "Created Empire: " + args[1]);
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
