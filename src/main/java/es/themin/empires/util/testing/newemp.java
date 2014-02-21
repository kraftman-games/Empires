package es.themin.empires.util.testing;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.SubCommand;
import es.themin.empires.managers.EmpireManager;
import es.themin.empires.managers.PlayerManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class newemp extends SubCommand{
	private empires myPlugin;
	private String plprefix;
	private EmpireManager Empires;
	private PlayerManager Players;
	
	public newemp(empires plugin) {
		myPlugin = plugin;
		plprefix = plugin.plprefix;
		Empires = plugin.Empires;
		Players = plugin.Players;
	}

	public boolean onCommand(Player player, String[] args) {
		
		EPlayer myEplayer = Players.loadEPlayer(player);
		
		
		
		Empires.createEmpire(args[1], myEplayer);
		
		player.sendMessage(plprefix + ChatColor.GREEN + "Created Empire: " + args[1]);
			
		return false;
	}

	public String name() {
		return "newemp";
	}
	public String info() {
		return "creates an empire";
	}
	public String[] aliases() {
		return new String[] { "ne" };
	}
	
}
