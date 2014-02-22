package es.themin.empires.cmds.empire.subcmd;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;

public class ToggleChat extends EmpireSubCommand{
	
	private ManagerAPI myApi = null;
	
	public ToggleChat(ManagerAPI api) {
		myApi = api;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		EPlayer myEPlayer = myApi.getEPlayer(player);
		if (myEPlayer.isInEmpire()) {
			
			if (myEPlayer.isInEmpireChat()){
				player.sendMessage(ChatColor.GREEN + "Toggled empire chat on, only players in your empire can see you talk");
			} else {
				player.sendMessage(ChatColor.GREEN + "Toggled empire chat off, all players can see you talk");
			}
			
			// toggle
			myEPlayer.setInEmpireChat(!myEPlayer.isInEmpireChat());
			
			return true;
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
