package es.themin.empires.util.testing;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import es.themin.empires.empires;
import es.themin.empires.cmds.SubCommand;
import es.themin.empires.managers.EmpireManager;
import es.themin.empires.managers.PlayerManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class addplayer extends SubCommand{
	public String plprefix;
	private empires myPlugin;
	private PlayerManager Players;
	private EmpireManager Empires;
	
	public addplayer(empires empires) {
		myPlugin = empires;
		plprefix = empires.plprefix;
		Players = empires.Players;
		Empires = empires.Empires;
	}

	public boolean onCommand(Player player, String[] args) {
		EPlayer myEPlayer = Players.loadEPlayer(player);
		
		if (myEPlayer == null || myEPlayer.getEmpireUUID() == null) {
			player.sendMessage(plprefix + ChatColor.RED + "you are not in an empire");
			return false;
		}if (args.length == 1) {
			player.sendMessage(plprefix + "Please specify a player");
		}
		Player target = Bukkit.getServer().loadEPlayer(args[1]);
		EPlayer myTarloadEPlayer = Players.loadEPlayer(target);
		
		if (!(target.isOnline())) {
			player.sendMessage(plprefix + ChatColor.RED + "Player is not online"); 
			return false;
		}
		if (myTarloadEPlayer != null && myTarloadEPlayer.getEmpireUUID() != null) {
			player.sendMessage(plprefix + ChatColor.RED + "Player is already in an empire");
			return false;
		}
		Empire empire = Empires.getEmpire(myEPlayer.getEmpireUUID());
		empire.addPlayer(myTarloadEPlayer);
		Players.addPlayer(myTarloadEPlayer);
		player.sendMessage(plprefix + ChatColor.GREEN + "Added " + target.getName() + " To your empire");
		target.sendMessage(plprefix + ChatColor.GREEN + "You were added to " + empire.getName() + " by " + player.getName());
		return false;
	}

	public String name() {
		return "addplayer";
	}

	public String info() {
		return "adds a player to your empire";
	}

	public String[] aliases() {
		return new String[] {"ap"};
	}
	
	

}
