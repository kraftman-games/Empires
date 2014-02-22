package es.themin.empires.cmds.ally;

import org.bukkit.ChatColor;

import es.themin.empires.cmds.EmpireSubCommand;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;

public class AllyRemoveCommand extends EmpireSubCommand{

	private ManagerAPI myApi = null;
	
	public String plprefix;
	public AllyRemoveCommand(ManagerAPI myAPI) {
		myApi = myAPI;
	}

	@Override
	public boolean onCommand(EPlayer myEPlayer, String[] args) {
		
		
		if (myEPlayer == null || myEPlayer.getEmpireUUID() == null) {
			myEPlayer.sendMessage(MsgManager.notinemp);
			return false;
		}
		Empire empire = myApi.getEmpire(myEPlayer);
		if (args.length == 1) {
			myEPlayer.sendMessage(MsgManager.toofewargs);
			return false;
		}
		if (myApi.getEmpire(args[1]) == null) {
			myEPlayer.sendMessage(MsgManager.empirenotfound);
			return false;
		}
		Empire ally = myApi.getEmpire(args[1]);
		if (empire.isAlliedWith(ally)) {
			//when i figure out how to do it i will use the /confirm command here
			empire.removeAlly(ally);
			ally.removeAlly(ally);
			empire.addExAlly(ally);
			empire.broadcastMessage(plprefix + ChatColor.DARK_PURPLE + myEPlayer.getName()  +" has cancelled your alliance with " + ally.getName());
			ally.broadcastMessage(plprefix + ChatColor.RED + empire.getName()  +  " have decided they no longer wish to be your allies");
			return false;
		}if (ally.hasAllyRequestFrom(empire)) {
			ally.removeAllyRequest(empire);
			ally.broadcastMessage(plprefix + ChatColor.RED + empire.getName() + " have withdrawn their alliance request");
			empire.broadcastMessage(plprefix + ChatColor.DARK_PURPLE + myEPlayer.getName() + " has withdrawn your alliance request to "  +ally.getName());
			return false;
		}
		myEPlayer.sendMessage(plprefix + ChatColor.RED + "You are not allied with " + ally.getName() + " nor is there a pending alliance request to them");
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
