package es.themin.empires.util.testing;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.SubCommand;
import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;

public class generatebasecore extends SubCommand{

	public String plprefix = empires.plprefix;
	public boolean onCommand(Player player, String[] args) {
		if (!(UtilManager.empireplayers.containsKey(player.getName()))) {
			player.sendMessage(plprefix + ChatColor.RED + "You are not in an empire");
			return false;
		}
		Empire empire = UtilManager.empireplayers.get(player.getName());
		if (empire.hasCoreOfType(CoreType.BASE)) {
			player.sendMessage(plprefix + ChatColor.RED + "You already have a core of this type");
			return false;
		}
		Core myCore = new Core(UtilManager.nextUnusedCoreId(), CoreType.BASE, player.getLocation(), 1, empire);
		empire.addCore(myCore);
		return false;
		
	}

	public String name() {
		return "generatebasecore";
	}

	public String info() {
		return "generates a core";
	}

	public String[] aliases() {
		return new String[] {"genbase" , "gbc" , "genbasecore", "generatebase"};
	}

}
