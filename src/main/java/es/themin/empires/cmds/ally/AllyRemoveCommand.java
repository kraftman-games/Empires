package es.themin.empires.cmds.ally;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.empire.EmpireSubCommand;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;
import es.themin.empires.util.UtilManager;

public class AllyRemoveCommand extends EmpireSubCommand{

	
	public String plprefix = empires.plprefix;
	@Override
	public boolean onCommand(Player player, String[] args) {
		if (!UtilManager.empireplayers.containsKey(player.getName())) {
			player.sendMessage(MsgManager.notinemp);
			return false;
		}
		Empire empire = UtilManager.empireplayers.get(player.getName());
		if (args.length == 1) {
			player.sendMessage(MsgManager.toofewargs);
			return false;
		}
		if (!UtilManager.containsEmpireWithName(args[1])) {
			player.sendMessage(MsgManager.empirenotfound);
			return false;
		}
		Empire ally = UtilManager.getEmpireWithName(args[1]);
		if (empire.isAlliedWith(ally)) {
			//when i figure out how to do it i will use the /confirm command here
			empire.removeAlly(ally);
			ally.removeAlly(ally);
			empire.addExAlly(ally);
			empire.broadcastMessage(plprefix + ChatColor.DARK_PURPLE + player.getName()  +" has cancelled your alliance with " + ally.getName());
			ally.broadcastMessage(plprefix + ChatColor.RED + empire.getName()  +  " have decided they no longer wish to be your allies");
			return false;
		}if (ally.hasAllyRequestFrom(empire)) {
			ally.removeAllyRequest(empire);
			ally.broadcastMessage(plprefix + ChatColor.RED + empire.getName() + " have withdrawn their alliance request");
			empire.broadcastMessage(plprefix + ChatColor.DARK_PURPLE + player.getName() + " has withdrawn your alliance request to "  +ally.getName());
			return false;
		}
		player.sendMessage(plprefix + ChatColor.RED + "You are not allied with " + ally.getName() + " nor is there a pending alliance request to them");
		return false;
		
	}

	@Override
	public String name() {
		return "remove";
	}

	@Override
	public String info() {
		return "remove an ally or cancel an alliance request";
	}

	@Override
	public String[] aliases() {
		return new String[] {"remove"};
	}

	@Override
	public String[] variables() {
		return new String[] {"empire"};
	}

	@Override
	public EmpirePermission permission() {
		return EmpirePermission.ALLY;
	}
	

}
