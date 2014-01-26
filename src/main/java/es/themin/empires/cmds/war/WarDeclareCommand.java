package es.themin.empires.cmds.war;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.empire.EmpireSubCommand;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.enums.EmpireState;
import es.themin.empires.util.Empire;
import es.themin.empires.util.Rank;
import es.themin.empires.util.UtilManager;
import es.themin.empires.wars.War;

public class WarDeclareCommand extends EmpireSubCommand{
	
	public String plprefix = empires.plprefix;
	public String warprefix = empires.warprefix;
	@Override
	public boolean onCommand(Player player, String[] args) {
		if (UtilManager.empireplayers.containsKey(player.getName())) {
			Empire empire = UtilManager.empireplayers.get(player.getName());
			if (!(empire.getOwner().equalsIgnoreCase(player.getName()))) {
				if (empire.playerHasARank(player.getName())) {
					Rank rank = empire.getRankOfPlayer(player.getName());
					if (!(rank.hasPermission(EmpirePermission.ATTACK))) player.sendMessage(plprefix + ChatColor.RED +"You do not have permission to do this"); return false;
				}else {
					return false;
				}
			}
			if (args.length == 0) {
				player.sendMessage(plprefix + ChatColor.RED + "Please define an empire");
				return false;
			}
			if (!(UtilManager.containsEmpireWithName(args[1]))) {
				player.sendMessage(plprefix + ChatColor.RED + "That is not an empire");
				return false;
			}
			Empire attacked = UtilManager.getEmpireWithName(args[1]);
			/* will implement this later
			if (!(attacked.getEmpireState() == EmpireState.BATTLEREADY)) {
				player.
			}*/
			War war = new War(empire, attacked);
			war.start();
			empire.broadcastMessage(warprefix + ChatColor.RED + player.getDisplayName() + " declared war on " + attacked.getName());
			attacked.broadcastMessage(warprefix + ChatColor.RED + empire.getName() + " decalred war on you");
			war.Save();
		}else {
			player.sendMessage(plprefix + ChatColor.RED + "You are not in an empire");
		}
		return false;
	}

	@Override
	public String name() {
		return "declare";
	}

	@Override
	public String info() {
		return "Declare war on another empire";
	}

	@Override
	public String[] aliases() {
		return new String[] {"declare"};
	}

	@Override
	public String[] variables() {
		return null;
	}

	@Override
	public EmpirePermission permission() {
		return EmpirePermission.ATTACK;
	}

}
