package es.themin.empires.cmds.ally;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.empire.EmpireSubCommand;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.EmpireManager;
import es.themin.empires.managers.PlayerManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;

public class AllyListCommand extends EmpireSubCommand{

	private empires myPlugin;
	private PlayerManager Players;
	private EmpireManager Empires;
	
	public String plprefix;
	
	public AllyListCommand(empires plugin) {
		myPlugin = plugin;
		plprefix = plugin.plprefix;
		Players = plugin.Players;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		EPlayer myEPlayer = Players.loadEPlayer(player);
		
		if (myEPlayer.getEmpireUUID() == null) {
			player.sendMessage(MsgManager.notinemp); 
			return false;
		}
		Empire empire = Empires.getEmpire(myEPlayer.getEmpireUUID());
		player.sendMessage(MsgManager.createTitle(ChatColor.LIGHT_PURPLE + "Your Allies", ChatColor.GOLD));
		if (!empire.hasAllies()) {
			player.sendMessage(ChatColor.RED + "You do not have any allies :(");
			return false;
		}
		
		for (Empire allyEmpire : empire.getAllies()) {
			player.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN +allyEmpire.getName()+ ChatColor.WHITE + " - " + allyEmpire.getXP());
		}
		return false;
	}

	@Override
	public String name() {
		return "list";
	}

	@Override
	public String info() {
		return "lists your current allies";
	}

	@Override
	public String[] aliases() {
		return new String[]{"list"};
	}

	@Override
	public String[] variables() {
		return null;
	}

	@Override
	public EmpirePermission permission() {
		return null;
	}
	

}
