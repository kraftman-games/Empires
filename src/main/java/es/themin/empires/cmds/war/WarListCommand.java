package es.themin.empires.cmds.war;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.empire.EmpireSubCommand;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;
import es.themin.empires.wars.War;

public class WarListCommand extends EmpireSubCommand{

	public String warprefix = empires.warprefix;
	public String plprefix = empires.plprefix;
	@Override
	public boolean onCommand(Player player, String[] args) {
		if (!UtilManager.empireplayers.containsKey(player.getName())) {
			player.sendMessage(plprefix + ChatColor.RED + "You are not in an empire");
			return false;
		}
		Empire empire = UtilManager.empireplayers.get(player.getName());
		player.sendMessage(ChatColor.GOLD + "====" + ChatColor.LIGHT_PURPLE + " Wars " + ChatColor.GOLD + " ====");
		int i = 0;
		for (War war : empire.getWars()) {
			i++;
			if (war.getAllEmpiresOnTeam1().contains(empire)) {
				StringBuilder str = new StringBuilder();
				float fpc = war.getTeam1Percent();
				if (fpc < 35) {
					str.append(ChatColor.DARK_RED + "");
					str.append(fpc);
					//str.append("%");
				}else if (fpc < 50) {
					str.append(ChatColor.RED + "");
					str.append(fpc);
					//str.append("%");
				}else if (fpc < 65) {
					str.append(ChatColor.GOLD + "");
					str.append(fpc);
					//str.append("%");
				}else if (fpc < 80) {
					str.append(ChatColor.GREEN + "");
					str.append(fpc);
					//str.append("%");
				}else {
					str.append(ChatColor.DARK_GREEN + "");
					str.append(fpc);
					//str.append("%");
				}
				str.append("%");
				//player.sendMessage(ChatColor.AQUA + "- " + ChatColor.LIGHT_PURPLE + war.getEmpire2().getName() + ChatColor.AQUA + " - " str);
			}if (war.getAllEmpiresOnTeam2().contains(empire)) {
				String pc = null;
				float fpc = 100 - war.getTeam1Percent();
				player.sendMessage(ChatColor.AQUA + "- " + ChatColor.DARK_RED + war.getEmpire1().getName());
			}
		}
		return false;
	}

	@Override
	public String name() {
		return "list";
	}

	@Override
	public String info() {
		return "Lists all the wars your empire is currently in";
	}

	@Override
	public String[] aliases() {
		return new String[] {"list"};
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
