package es.themin.empires.util.testing;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.SubCommand;
import es.themin.empires.managers.PlayerManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class addplayer extends SubCommand{
	public String plprefix;
	private empires myPlugin;
	private PlayerManager Players;
	
	public addplayer(empires empires) {
		myPlugin = empires;
		plprefix = empires.plprefix;
		Players = empires.Players;
	}

	public boolean onCommand(Player player, String[] args) {
		EPlayer myCorePlayer = Players.getPlayer(player.getUniqueId());
		
		if (myCorePlayer == null || myCorePlayer.getEmpire() == null) {
			player.sendMessage(plprefix + ChatColor.RED + "you are not in an empire");
			return false;
		}if (args.length == 1) {
			player.sendMessage(plprefix + "Please specify a player");
		}
		Player target = Bukkit.getServer().getPlayer(args[1]);
		EPlayer myTargetPlayer = Players.getPlayer(target.getUniqueId());
		
		if (!(target.isOnline())) {
			player.sendMessage(plprefix + ChatColor.RED + "Player is not online"); 
			return false;
		}
		if (myTargetPlayer != null && myTargetPlayer.getEmpire() != null) {
			player.sendMessage(plprefix + ChatColor.RED + "Player is already in an empire");
			return false;
		}
		Empire empire = myCorePlayer.getEmpire();
		empire.addPlayer(myTargetPlayer);
		Players.addPlayer(myTargetPlayer);
		player.sendMessage(plprefix + ChatColor.GREEN + "Added " + target.getName() + " To your empire");
		target.sendMessage(plprefix + ChatColor.GREEN + "You were added to " + empire.getID() + " by " + player.getName());
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
