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
		try {
			player.sendMessage("debug 1 ");
			EPlayer myCorePlayer = Players.getPlayer(player.getUniqueId());
			if (!Empires.isValidName(args[1])){
				player.sendMessage("Empire name is invalid");
			}
			player.sendMessage("debug 2");
			if (myCorePlayer.getEmpire() != null){
				myCorePlayer.sendMessage("You are already in an empire");
			}
			player.sendMessage("debug 3");
			Empire empire = new Empire(myPlugin, args[1], myCorePlayer.getUUID());
			empire.addPlayer(myCorePlayer);
			myCorePlayer.setEmpire(empire);
			player.sendMessage("debug 4");
			empire.Save();
			player.sendMessage(plprefix + ChatColor.GREEN + "Created Empire: " + args[1]);
		} catch (Exception e) {
			
		}
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
