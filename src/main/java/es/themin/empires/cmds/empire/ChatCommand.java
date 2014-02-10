package es.themin.empires.cmds.empire;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;

public class ChatCommand extends EmpireSubCommand{
	public static ArrayList<Player> empirechatplayers = new ArrayList<Player>();
	public String plprefix;
	private empires myPlugin;
	public ChatCommand(empires plugin) {
		myPlugin = plugin;
		plprefix = plugin.plprefix;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if (myPlugin.getEmpireplayers().containsKey(player.getName())) {
			if (!(empirechatplayers.contains(player))) {
				empirechatplayers.add(player);
				player.sendMessage(plprefix + ChatColor.GREEN + "Speaking in empire chat do '/g' to talk globally");
				player.performCommand("local");
				return false;
			}else {
				player.sendMessage(plprefix + ChatColor.RED + "You are already talking in this channel");
				return false;
			}
			
		}else {
			player.sendMessage(plprefix + ChatColor.RED + "You are not in an empire");
		}
		return false;
	}

	@Override
	public String name() {
		return "chat";
	}

	@Override
	public String info() {
		return "talk to your empire";
	}

	@Override
	public String[] aliases() {
		return new String[] { "c" };
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
