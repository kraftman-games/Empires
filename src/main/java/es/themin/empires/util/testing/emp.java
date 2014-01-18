package es.themin.empires.util.testing;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.SubCommand;
import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;

public class emp extends SubCommand{
	private empires plugin;
	public String plprefix = plugin.plprefix;
	public boolean onCommand(Player player, String[] args) {
		if (args.length == 1) {
			if (!(UtilManager.empireplayers.containsKey(player.getName()))) {
				player.sendMessage(plprefix + ChatColor.RED + "You are not in an empire");
			}else {
				Empire empire = UtilManager.empireplayers.get(player.getName());
				player.sendMessage(ChatColor.GOLD + "=====" + ChatColor.LIGHT_PURPLE + empire.getName() + ChatColor.GOLD + "=====");
				player.sendMessage(ChatColor.GREEN + "Player #: " + ChatColor.LIGHT_PURPLE + empire.numberOfPlayers());
				player.sendMessage(ChatColor.GREEN + "Core #: " + ChatColor.LIGHT_PURPLE + empire.numberOfCores());
				StringBuilder str = new StringBuilder();
				str.append(ChatColor.GOLD + "Players: ");
				int i = 0;
				for (String p : empire.getPlayers()) {
					i++;
					str.append(ChatColor.GREEN + p + ", ");
				}
				if (i == 0) {
					str.append(ChatColor.RED + "No players :(");
				}
				player.sendMessage(str.toString());
			}		
		}else {
			if (UtilManager.containsEmpireWithName(args[1])) {
				Empire empire = UtilManager.getEmpireWithName(args[1]);
				player.sendMessage(ChatColor.GOLD + "=====" + ChatColor.LIGHT_PURPLE + empire.getName() + ChatColor.GOLD + "=====");
				player.sendMessage(ChatColor.GREEN + "Player #: " + ChatColor.LIGHT_PURPLE + empire.numberOfPlayers());
				player.sendMessage(ChatColor.GREEN + "Core #: " + ChatColor.LIGHT_PURPLE + empire.numberOfCores());
				StringBuilder str = new StringBuilder();
				str.append(ChatColor.GOLD + "Players: ");
				int i = 0;
				for (String p : empire.getPlayers()) {
					i++;
					str.append(ChatColor.GREEN + p + ", ");
				}
				if (i == 0) {
					str.append(ChatColor.RED + "No players :(");
				}
				player.sendMessage(str.toString());
			}else {
				player.sendMessage(plprefix + ChatColor.RED + "Empire not found"); 
			}
		}
		return false;
	}

	public String name() {
		return "emp";
	}

	public String info() {
		return "View an empires details";
	}

	public String[] aliases() {
		return new String[] { "e" };
	}

}
