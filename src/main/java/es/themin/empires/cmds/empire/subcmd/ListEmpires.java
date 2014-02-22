package es.themin.empires.cmds.empire.subcmd;

import org.bukkit.entity.Player;

import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.ManagerAPI;

public class ListEmpires extends EmpireSubCommand{

	private ManagerAPI myApi;
	
	public ListEmpires(ManagerAPI api) {
		myApi = api;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		myApi.listEmpires(player);
		return true;
	}

	@Override
	public String name() {
		return "list";
	}

	@Override
	public String info() {
		return "Lists all the empires";
	}

	@Override
	public String[] aliases() {
		return new String[] {"l"};
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
