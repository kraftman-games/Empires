package es.themin.empires.cmds.war;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.EmpireSubCommand;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.wars.War;

public class WarListCommand extends EmpireSubCommand{

	public String warprefix = empires.warprefix;
	private ManagerAPI myApi = null;
	public WarListCommand(ManagerAPI myAPI) {
		myApi = myAPI;
	}

	@Override
	public boolean onCommand(EPlayer myEPlayer, String[] args) {
		if (myEPlayer == null || myEPlayer.getEmpireUUID() == null) {
			myEPlayer.sendMessage( ChatColor.RED + "You are not in an empire");
			return false;
		}
		Empire empire = myApi.getEmpire(myEPlayer.getEmpireUUID());
		myEPlayer.sendMessage(ChatColor.GOLD + "====" + ChatColor.LIGHT_PURPLE + " Wars " + ChatColor.GOLD + " ====");
		int i = 0;
		for (War war : empire.getWars()) {
			i++;
			if (war.getAllEmpiresOnTeam1().contains(empire)) {
				StringBuilder str = new StringBuilder();
				str.append(ChatColor.AQUA + "- " + ChatColor.LIGHT_PURPLE + war.getEmpire2().getName() + ChatColor.AQUA + " - " );
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
				myEPlayer.sendMessage(str.toString());
			}if (war.getAllEmpiresOnTeam2().contains(empire)) {
				//String pc = null;
				StringBuilder str = new StringBuilder();
				float fpc = 100 - war.getTeam1Percent();
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
				myEPlayer.sendMessage(ChatColor.AQUA + "- " + ChatColor.DARK_RED + war.getEmpire1().getName() + ChatColor.AQUA + " - " + str.toString());
			}
		}
		if (i == 0) myEPlayer.sendMessage( ChatColor.RED + "You are not at war");
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
