package es.themin.empires.util.testing;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.SubCommand;
import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;

public class addplayer extends SubCommand{
	public String plprefix = empires.plprefix;
	public boolean onCommand(Player player, String[] args) {
		if (!(UtilManager.empireplayers.containsKey(player.getName()))) {
			player.sendMessage(plprefix + ChatColor.RED + "you are not in an empire");
			return false;
		}if (args.length == 1) {
			player.sendMessage(plprefix + "Please specify a player");
		}
		Player target = Bukkit.getServer().getPlayer(args[1]);
		if (!(target.isOnline())) {
			player.sendMessage(plprefix + ChatColor.RED + "Player is not online"); 
			return false;
		}
		String targetname = target.getName();
		if (UtilManager.empireplayers.containsKey(targetname)) {
			player.sendMessage(plprefix + ChatColor.RED + "Player is already in an empire");
			return false;
		}
		Empire empire = UtilManager.empireplayers.get(player.getName());
		empire.addPlayer(targetname);
		UtilManager.empireplayers.put(targetname, empire);
		empire.Save();
		player.sendMessage(plprefix + ChatColor.GREEN + "Added " + targetname + " To your empire");
		target.sendMessage(plprefix + ChatColor.GREEN + "You were added to " + empire.getId() + " by " + player.getName());
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
