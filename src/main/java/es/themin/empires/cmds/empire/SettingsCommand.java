package es.themin.empires.cmds.empire;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;
import es.themin.empires.util.UtilManager;

public class SettingsCommand extends EmpireSubCommand{

	private empires myPlugin;
	public String plprefix;
	public SettingsCommand(empires plugin) {
		myPlugin = plugin;
		plprefix = plugin.plprefix;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if (myPlugin.getEmpireplayers().containsKey(player.getName())) {
			Empire empire = myPlugin.getEmpireplayers().get(player.getName());
			if (empire.getOwner().equalsIgnoreCase(player.getName())) {
				if (args.length == 1) {
					info(player);
				}else {
					if (args[1].equalsIgnoreCase("ownerprefix")) {
						if (args.length < 3) {
							player.sendMessage(plprefix + ChatColor.RED + "Specify a prefix");
							return false;
						}else {
							empire.setOwnerPrefix(args[2]);
							String cprefix = MsgManager.colourUp(args[2]);
							player.sendMessage(plprefix + ChatColor.GREEN + "Your prefix in town chat is now '" + cprefix + ChatColor.GREEN + "'");
							return false;
						}
					}else if (args[1].equalsIgnoreCase("defaultprefix")) {
						if (args.length < 3) {
							player.sendMessage(plprefix + ChatColor.RED + "Specify a prefix");
							return false;
						}else {
							empire.setDefaultPrefix(args[2]);
							String cprefix = MsgManager.colourUp(args[2]);
							player.sendMessage(plprefix + ChatColor.GREEN + "The default prefix in town chat is now '" + cprefix + ChatColor.GREEN + "'");
							return false;
						}
					}
				}
			}else {
				player.sendMessage(plprefix + ChatColor.RED + "Only the owner of am empire can modify the settings");
			}
		}else {
			player.sendMessage(plprefix + ChatColor.RED + "You are no in an empire");
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
