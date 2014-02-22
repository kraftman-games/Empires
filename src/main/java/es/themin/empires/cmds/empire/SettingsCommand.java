package es.themin.empires.cmds.empire;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;

public class SettingsCommand extends EmpireSubCommand{

	private ManagerAPI myApi = null;
	
	public SettingsCommand(ManagerAPI api) {
		myApi = api;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		EPlayer myEPlayer = myApi.getEPlayer(player);
		
		
		if (myEPlayer != null && myEPlayer.getEmpireUUID() != null) {
			Empire empire = myApi.getEmpire(myEPlayer);
			if (myEPlayer.isOwner(empire)) {
				if (args.length == 1) {
					info(player);
				}else {
					if (args[1].equalsIgnoreCase("ownerprefix")) {
						if (args.length < 3) {
							myEPlayer.sendMessage( ChatColor.RED + "Specify a prefix");
							return false;
						}else {
							empire.setOwnerPrefix(args[2]);
							String cprefix = MsgManager.colourUp(args[2]);
							myEPlayer.sendMessage( ChatColor.GREEN + "Your prefix in town chat is now '" + cprefix + ChatColor.GREEN + "'");
							return false;
						}
					}else if (args[1].equalsIgnoreCase("defaultprefix")) {
						if (args.length < 3) {
							myEPlayer.sendMessage( ChatColor.RED + "Specify a prefix");
							return false;
						}else {
							empire.setDefaultPrefix(args[2]);
							String cprefix = MsgManager.colourUp(args[2]);
							myEPlayer.sendMessage( ChatColor.GREEN + "The default prefix in town chat is now '" + cprefix + ChatColor.GREEN + "'");
							return false;
						}
					}
				}
			}else {
				player.sendMessage( ChatColor.RED + "Only the owner of am empire can modify the settings");
			}
		}else {
			player.sendMessage( ChatColor.RED + "You are no in an empire");
			return false;
		}
		return false;
 	}

	@Override
	public String name() {
		return "settings";
	}

	@Override
	public String info() {
		return null;
	}

	@Override
	public String[] aliases() {
		return null;
	}

	@Override
	public String[] variables() {
		return null;
	}

	@Override
	public EmpirePermission permission() {
		return null;
	}
	private void info(Player player) {
		player.sendMessage(ChatColor.GOLD + "/empire settings ownerprefix"  + ChatColor.LIGHT_PURPLE  + "(prefix)" + ChatColor.WHITE + " - " + ChatColor.AQUA + "Changes your prefix in town chat");
		player.sendMessage(ChatColor.GOLD + "/empire settings defaultprefix"  + ChatColor.LIGHT_PURPLE  + "(prefix)" + ChatColor.WHITE + " - " + ChatColor.AQUA + "Changes the default prefix in town chat");
	}

}
