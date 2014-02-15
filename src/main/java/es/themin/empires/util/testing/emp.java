package es.themin.empires.util.testing;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.SubCommand;
import es.themin.empires.managers.EmpireManager;
import es.themin.empires.managers.PlayerManager;
import es.themin.empires.util.CorePlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;

public class emp extends SubCommand{
	private empires myPlugin;
	public String plprefix;
	private EmpireManager Empires;
	private PlayerManager Players;
	
	public emp(empires plugin) {
		myPlugin = plugin;
		plprefix = plugin.plprefix;
		Empires = plugin.Empires;
		Players = plugin.Players;
	}

	public boolean onCommand(Player player, String[] args) {
		CorePlayer myCorePlayer = Players.getPlayer(player.getUniqueId());
		
		if (args.length == 1) {
			if (myCorePlayer == null) {
				player.sendMessage(plprefix + ChatColor.RED + "You are not in an empire");
			}else {
				Empire empire = myCorePlayer.getEmpire();
				player.sendMessage(ChatColor.GOLD + "=====" + ChatColor.LIGHT_PURPLE + empire.getName() + ChatColor.GOLD + "=====");
				player.sendMessage(ChatColor.GREEN + "Player #: " + ChatColor.LIGHT_PURPLE + empire.numberOfPlayers());
				player.sendMessage(ChatColor.GREEN + "Core #: " + ChatColor.LIGHT_PURPLE + empire.numberOfCores());
				StringBuilder str = new StringBuilder();
				str.append(ChatColor.GOLD + "Players: ");
				int i = 0;
				for (CorePlayer p : empire.getPlayers().values()) {
					i++;
					str.append(ChatColor.GREEN + p.getName() + ", ");
				}
				if (i == 0) {
					str.append(ChatColor.RED + "No players :(");
				}
				player.sendMessage(str.toString());
				player.sendMessage(ChatColor.GREEN + "Owner: " + ChatColor.LIGHT_PURPLE + empire.getOwner());
			}		
		}else {
			if (Empires.containsEmpireWithName(args[1])) {
				Empire empire = Empires.getEmpireWithName(args[1]);
				player.sendMessage(ChatColor.GOLD + "=====" + ChatColor.LIGHT_PURPLE + empire.getName() + ChatColor.GOLD + "=====");
				player.sendMessage(ChatColor.GREEN + "Player #: " + ChatColor.LIGHT_PURPLE + empire.numberOfPlayers());
				player.sendMessage(ChatColor.GREEN + "Core #: " + ChatColor.LIGHT_PURPLE + empire.numberOfCores());
				StringBuilder str = new StringBuilder();
				str.append(ChatColor.GOLD + "Players: ");
				int i = 0;
				for (CorePlayer p : empire.getPlayers().values()) {
					i++;
					str.append(ChatColor.GREEN + p.getName() + ", ");
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
