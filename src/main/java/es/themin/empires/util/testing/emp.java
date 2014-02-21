package es.themin.empires.util.testing;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.SubCommand;
import es.themin.empires.managers.CoreManager;
import es.themin.empires.managers.EmpireManager;
import es.themin.empires.managers.PlayerManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class emp extends SubCommand{
	private empires myPlugin;
	public String plprefix;
	private EmpireManager Empires;
	private PlayerManager Players;
	private CoreManager Cores;
	
	public emp(empires plugin) {
		myPlugin = plugin;
		plprefix = plugin.plprefix;
		Empires = plugin.Empires;
		Players = plugin.Players;
		Cores = plugin.Cores;
	}

	public boolean onCommand(Player player, String[] args) {
		EPlayer myEPlayer = Players.getPlayer(player.getUniqueId());
		
		if (args.length == 1) {
			if (myEPlayer == null) {
				player.sendMessage(plprefix + ChatColor.RED + "You are not in an empire");
			}else {
				Empire myEmpire = Empires.getEmpire(myEPlayer.getEmpireUUID());
				player.sendMessage(ChatColor.GOLD + "=====" + ChatColor.LIGHT_PURPLE + myEmpire.getName() + ChatColor.GOLD + "=====");
				player.sendMessage(ChatColor.GREEN + "Player #: " + ChatColor.LIGHT_PURPLE + myEmpire.numberOfPlayers());
				player.sendMessage(ChatColor.GREEN + "Core #: " + ChatColor.LIGHT_PURPLE + Cores.getCoreCount(myEmpire));
				StringBuilder str = new StringBuilder();
				str.append(ChatColor.GOLD + "Players: ");
				int i = 0;
				for (EPlayer p : myEmpire.getPlayers().values()) {
					i++;
					str.append(ChatColor.GREEN + p.getName() + ", ");
				}
				if (i == 0) {
					str.append(ChatColor.RED + "No players :(");
				}
				player.sendMessage(str.toString());
				player.sendMessage(ChatColor.GREEN + "Owner: " + ChatColor.LIGHT_PURPLE + myEmpire.getOwner());
			}		
		}else {
			if (Empires.containsEmpireWithName(args[1])) {
				Empire empire = Empires.getEmpireWithName(args[1]);
				player.sendMessage(ChatColor.GOLD + "=====" + ChatColor.LIGHT_PURPLE + empire.getName() + ChatColor.GOLD + "=====");
				player.sendMessage(ChatColor.GREEN + "Player #: " + ChatColor.LIGHT_PURPLE + empire.numberOfPlayers());
				player.sendMessage(ChatColor.GREEN + "Core #: " + ChatColor.LIGHT_PURPLE + Cores.getCoreCount(empire));
				StringBuilder str = new StringBuilder();
				str.append(ChatColor.GOLD + "Players: ");
				int i = 0;
				for (EPlayer p : empire.getPlayers().values()) {
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
