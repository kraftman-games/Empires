package es.themin.empires.util.testing;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.cmds.SubCommand;

public class RicTest extends SubCommand {

	@Override
	public boolean onCommand(Player player, String[] args) {
		if (args.length <= 1) {
			player.setLevel((int) player.getHealth() /2 );
			player.sendMessage("Xp set");
			return false;
		}
		int i = 0;
		try {
			i = Integer.parseInt(args[1]);
		}catch (NumberFormatException e ) {
			player.sendMessage(ChatColor.RED + "That is not an integer");
			return false;
		}
		player.setLevel(i);
		player.sendMessage("Xp set");
		return false;
	}

	@Override
	public String name() {
		return "rictest";
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] aliases() {
		return new String[] {"rt"};
	}
}
