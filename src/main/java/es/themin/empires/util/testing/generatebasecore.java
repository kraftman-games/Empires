package es.themin.empires.util.testing;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import es.themin.empires.WorldManager;
import es.themin.empires.empires;
import es.themin.empires.cmds.SubCommand;
import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.CoreWorld;
import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;

public class generatebasecore extends SubCommand{

	public String plprefix;
	private empires myPlugin;
	private WorldManager Worlds;
	
	public generatebasecore(empires plugin) {
		myPlugin = plugin;
		plprefix = plugin.plprefix;
	}

	public boolean onCommand(Player player, String[] args) {
		if (!(myPlugin.getEmpireplayers().containsKey(player.getName()))) {
			player.sendMessage(plprefix + ChatColor.RED + "You are not in an empire");
			return false;
		}
		Empire empire = myPlugin.getEmpireplayers().get(player.getName());
		if (empire.hasCoreOfType(CoreType.BASE)) {
			player.sendMessage(plprefix + ChatColor.RED + "You already have a core of this type");
			return false;
		}
		Core myCore = new Core(myPlugin, myPlugin.Cores.nextUnusedCoreId(), CoreType.BASE, player.getLocation(), 1, empire);
		empire.addCore(myCore);
		World world = player.getWorld();
		UUID uuid = world.getUID();
		CoreWorld cw = Worlds.getWorlds().get(uuid);
		cw.addCore(myCore);
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
