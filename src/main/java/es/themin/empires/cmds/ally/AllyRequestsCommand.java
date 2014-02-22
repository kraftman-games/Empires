package es.themin.empires.cmds.ally;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.EmpireSubCommand;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.EmpireManager;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.managers.PlayerManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;

public class AllyRequestsCommand extends EmpireSubCommand{

	private ManagerAPI myApi;
	
	public AllyRequestsCommand(ManagerAPI myAPI) {
		myApi = myAPI;
	}

	@Override
	public boolean onCommand(EPlayer myEPlayer, String[] args) {
		
		if (!myEPlayer.isInEmpire()) {
			myEPlayer.sendMessage(MsgManager.notinemp);
			return false;
		}
		Empire empire = myApi.getEmpire(myEPlayer);
		myEPlayer.sendMessage(MsgManager.createTitle(ChatColor.LIGHT_PURPLE + "Alliance Requests", ChatColor.GOLD));
		int i =0;
		for (Empire ally : empire.getAllianceRequests().keySet()) {
			i++;
			myEPlayer.sendMessage( ChatColor.GREEN + ally.getName() + ChatColor.WHITE + " - " + ChatColor.GOLD + MsgManager.createSmartTimeStamp(empire.getAllianceRequests().get(ally)));
		}
		if (i==0) myEPlayer.sendMessage(ChatColor.RED + "You have not alliance requests :(");
		return false;
	}

	@Override
	public String name() {
		return "requests";
	}

	@Override
	public String info() {
		return "gives a lists of pending alliance requests";
	}

	@Override
	public String[] aliases() {
		return new String[] { "requests"};
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
