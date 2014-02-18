package es.themin.empires.cmds.empire;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.EmpireManager;
import es.themin.empires.util.Empire;

public class list extends EmpireSubCommand{

	private empires myPlugin;
	private EmpireManager Empires;
	
	public list(empires empires) {
		myPlugin = empires;
		Empires = empires.Empires;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		player.sendMessage(ChatColor.GOLD + "=====" + ChatColor.LIGHT_PURPLE + "Empires" + ChatColor.GOLD + "=====");
		int i = 0;
		for (Empire empire : Empires.getEmpires().values()) {
			i++;
			player.sendMessage(ChatColor.GREEN + empire.getName());
		}
		if (i == 0) {
			player.sendMessage(ChatColor.RED + "No empires :(");
		}
		return false;
	}

	@Override
	public String name() {
		return "list";
	}

	@Override
	public String info() {
		return "Lists all the empires";
	}

	@Override
	public String[] aliases() {
		return new String[] {"l"};
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
