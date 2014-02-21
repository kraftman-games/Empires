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
		
		if (args.length == 1){
			player.sendMessage("You need to specify a player to add");
		} else {
			if (Players.playerCanAdd(player, args[1])){
				EPlayer myEPlayer = Players.loadEPlayer(player);
				EPlayer myTargetPlayer = Players.loadEPlayer(Bukkit.getServer().getPlayer(args[1]));
				Empire empire = Empires.getEmpire(myEPlayer.getEmpireUUID());
				player.sendMessage(plprefix + ChatColor.GREEN + "Added " + myTargetPlayer.getName() + " to your empire");
				empire.addPlayer(myTargetPlayer);
			}
		}
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
