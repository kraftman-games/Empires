package es.themin.empires.cmds.ally;

import org.bukkit.ChatColor;

import es.themin.empires.cmds.EmpireSubCommand;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;

public class AllyListCommand extends EmpireSubCommand{

	private ManagerAPI myApi;
	
	public AllyListCommand(ManagerAPI myAPI) {
		myApi = myAPI;
	}

	@Override
	public boolean onCommand(EPlayer myEPlayer, String[] args) {
		
		if (myEPlayer.getEmpireUUID() == null) {
			myEPlayer.sendMessage(MsgManager.notinemp); 
			return false;
		}
		Empire empire = myApi.getEmpire(myEPlayer);
		myEPlayer.sendMessage(MsgManager.createTitle(ChatColor.LIGHT_PURPLE + "Your Allies", ChatColor.GOLD));
		if (!empire.hasAllies()) {
			myEPlayer.sendMessage(ChatColor.RED + "You do not have any allies :(");
			return false;
		}
		
		for (Empire allyEmpire : empire.getAllies()) {
			myEPlayer.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN +allyEmpire.getName()+ ChatColor.WHITE + " - " + allyEmpire.getXP());
		}
		return false;
	}

	@Override
	public String name() {
		return "list";
	}

	@Override
	public String info() {
		return "lists your current allies";
	}

	@Override
	public String[] aliases() {
		return new String[]{"list"};
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
