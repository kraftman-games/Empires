package es.themin.empires.cmds.empire;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.managers.PlayerManager;
import es.themin.empires.util.EPlayer;

public class ChatCommand extends EmpireSubCommand{
	public static ArrayList<Player> empirechatplayers = new ArrayList<Player>();
	private ManagerAPI myApi = null;
	
	public ChatCommand(ManagerAPI api) {
		myApi = api;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		EPlayer myEPlayer = myApi.getEPlayer(player);
		if (myEPlayer.isInEmpire()) {
			if (!(empirechatplayers.contains(player))) {
				empirechatplayers.add(player);
				player.sendMessage(ChatColor.GREEN + "Speaking in empire chat do '/g' to talk globally");
				player.performCommand("local");
				return false;
			}else {
				player.sendMessage( ChatColor.RED + "You are already talking in this channel");
				return false;
			}
			
		}else {
			player.sendMessage(ChatColor.RED + "You are not in an empire");
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
