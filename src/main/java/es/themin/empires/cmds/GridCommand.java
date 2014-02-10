package es.themin.empires.cmds;

import java.awt.Point;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.themin.empires.WorldManager;
import es.themin.empires.empires;
import es.themin.empires.util.UtilManager;

public class GridCommand implements CommandExecutor{

	
	private empires plugin;
	private WorldManager Worlds;
	public GridCommand(empires plugin) {
		this.plugin = plugin;
		Worlds = plugin.Worlds;
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,String[] arg3) {
		if (arg2.equalsIgnoreCase("grid") && arg0 instanceof Player) {
			Player player = (Player) arg0;
			Point point = Worlds.getWorlds().get(player.getWorld().getUID()).getCoords(player.getLocation());
			player.sendMessage("X: " + point.getX() + ", Z: " + point.getY());
			return false;
		}
		return false;
	}
}
