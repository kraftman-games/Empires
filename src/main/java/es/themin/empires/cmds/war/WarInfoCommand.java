package es.themin.empires.cmds.war;

import java.sql.Date;

import org.bukkit.ChatColor;

import es.themin.empires.cmds.EmpireSubCommand;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;
import es.themin.empires.wars.War;

public class WarInfoCommand extends EmpireSubCommand{

	
	private ManagerAPI myApi = null;
	
	public WarInfoCommand(ManagerAPI myAPI) {
		myApi = myAPI;
	}

	@Override
	public boolean onCommand(EPlayer myEPlayer, String[] args) {
		if (myEPlayer == null || myEPlayer.getEmpireUUID() == null) {
			myEPlayer.sendMessage( ChatColor.RED +"You are not in an empire therefore cannot be at war");
			return false;
		}
		Empire empire = myApi.getEmpire(myEPlayer.getEmpireUUID());
		if (args.length == 1) {
			myEPlayer.sendMessage(ChatColor.RED+ "Please specify a war");
			return false;
		}
		if (myApi.getEmpire(args[1]) == null) {
			myEPlayer.sendMessage( ChatColor.RED + "That is not an empire");
			return false;
		}
		Empire enemy = myApi.getEmpire(args[1]);
		if (!empire.isAtWarWith(enemy)) {
			myEPlayer.sendMessage( ChatColor.RED +"You are not an war with this empire");
			return false;
		}
		War war = empire.getWarAgainst(enemy);
		myEPlayer.sendMessage(MsgManager.createTitle(ChatColor.GOLD + " [" + ChatColor.LIGHT_PURPLE + "War Against " + enemy.getName() + ChatColor.GOLD + "] ",ChatColor.GOLD));
		Date date = new Date(war.getStart());
		
		if (war.getAllEmpiresOnTeam1().contains(empire)) {
			if (war.getEmpire1Allies().contains(empire)) myEPlayer.sendMessage(ChatColor.AQUA + "You are asisting " + war.getEmpire1().getName() + " in their war against " + war.getEmpire2().getName());
			myEPlayer.sendMessage(ChatColor.GREEN + "Start " + ChatColor.AQUA + "- " + ChatColor.LIGHT_PURPLE + date.toString());
			myEPlayer.sendMessage(ChatColor.GREEN + "Allied Wins " + ChatColor.AQUA + "- " + ChatColor.LIGHT_PURPLE + war.getEmpire1Wins());
			myEPlayer.sendMessage(ChatColor.GREEN + "Enemy Wins " + ChatColor.AQUA + "- " + ChatColor.LIGHT_PURPLE + war.getEmpire2Wins());
			StringBuilder str = new StringBuilder();
			String tits = ChatColor.GREEN + "Allied Empires " + ChatColor.DARK_PURPLE + "[" + war.getNumberOfEmpire1Allies() + "]" + ChatColor.GREEN + " : " + ChatColor.WHITE;
			str.append(tits);
			int run = 0;
			str.append(war.getEmpire1().getName());
			for (Empire ally : war.getEmpire1Allies()) {
				if (run == 0) {
					str.append(", ");
				}
				run++;
				str.append(ally.getName());
				if (run != war.getNumberOfEmpire1Allies()) str.append(", ");
			}
			myEPlayer.sendMessage(str.toString());
			
			StringBuilder str2 = new StringBuilder();
			String tits2 = ChatColor.GREEN + "Enemy Empires " + ChatColor.DARK_PURPLE + "[" + war.getNumberOfEmpire2Allies() + "]" + ChatColor.GREEN + " : " + ChatColor.WHITE;
			str2.append(tits2);
			int run2 = 0;
			str2.append(war.getEmpire2().getName());
			for (Empire ally : war.getEmpire2Allies()) {
				if (run2 == 0) {
					str2.append(", ");
				}
				run2++;
				str2.append(ally.getName());
				if (run2 != war.getNumberOfEmpire2Allies()) str2.append(", ");
			}
			myEPlayer.sendMessage(str2.toString());
			myEPlayer.sendMessage(ChatColor.GREEN + "Do '" + ChatColor.GOLD + "/war timeline " + war.getEmpire1().getName() + ChatColor.GREEN + "' for a timeline of this war");
		}if (war.getAllEmpiresOnTeam2().contains(empire)) {
			if (war.getEmpire2Allies().contains(empire)) myEPlayer.sendMessage(ChatColor.AQUA + "You are asisting " + war.getEmpire2().getName() + " in their war against " + war.getEmpire1().getName());
			myEPlayer.sendMessage(ChatColor.GREEN + "Start " + ChatColor.AQUA + "- " + ChatColor.LIGHT_PURPLE + date.toString());
			myEPlayer.sendMessage(ChatColor.GREEN + "Allied Wins " + ChatColor.AQUA + "- " + ChatColor.LIGHT_PURPLE + war.getEmpire2Wins());
			myEPlayer.sendMessage(ChatColor.GREEN + "Enemy Wins " + ChatColor.AQUA + "- " + ChatColor.LIGHT_PURPLE + war.getEmpire1Wins());
			StringBuilder str = new StringBuilder();
			
			StringBuilder str2 = new StringBuilder();
			String tits2 = ChatColor.GREEN + "Allied Empires " + ChatColor.DARK_PURPLE + "[" + war.getNumberOfEmpire2Allies() + "]" + ChatColor.GREEN + " : " + ChatColor.WHITE;
			str2.append(tits2);
			int run2 = 0;
			str2.append(war.getEmpire2().getName());
			for (Empire ally : war.getEmpire2Allies()) {
				if (run2 == 0) {
					str2.append(", ");
				}
				run2++;
				str2.append(ally.getName());
				if (run2 != war.getNumberOfEmpire2Allies()) str2.append(", ");
			}
			myEPlayer.sendMessage(str2.toString());
			
			String tits = ChatColor.GREEN + "Enemy Empires " + ChatColor.DARK_PURPLE + "[" + war.getNumberOfEmpire1Allies() + "]" + ChatColor.GREEN + " : " + ChatColor.WHITE;
			str.append(tits);
			int run = 0;
			str.append(war.getEmpire1().getName());
			for (Empire ally : war.getEmpire1Allies()) {
				if (run == 0) {
					str.append(", ");
				}
				run++;
				str.append(ally.getName());
				if (run != war.getNumberOfEmpire1Allies()) str.append(", ");
			}
			myEPlayer.sendMessage(str.toString());
			myEPlayer.sendMessage(ChatColor.GREEN + "Do '" + ChatColor.GOLD + "/war timeline " + war.getEmpire1().getName() + ChatColor.GREEN + "' for a timeline of this war");
			
		}
		return false;
		
	}

	@Override
	public String name() {
		return "info";
	}

	@Override
	public String info() {
		return "view information about a particular war";
	}

	@Override
	public String[] aliases() {
		return new String[] {"info"};
	}

	@Override
	public String[] variables() {
		return new String[] {"war"};
	}

	@Override
	public EmpirePermission permission() {
		return null;
	}

}
