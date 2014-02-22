package es.themin.empires.cmds.empire;

import java.awt.Point;

import org.bukkit.entity.Player;

import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.ManagerAPI;

public class GridLocationCommand extends EmpireSubCommand{

	private ManagerAPI myApi = null;
	public GridLocationCommand(ManagerAPI api) {
		myApi = api;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		Point point = Worlds.getWorlds().get(player.getWorld().getUID()).getCoords(player.getLocation());
		player.sendMessage("X: " + point.getX() + ", Z: " + point.getY());
		return false;
	}

	@Override
	public String name() {
		return "location";
	}

	@Override
	public String info() {
		return "gives you your grid location";
	}

	@Override
	public String[] aliases() {
		return new String[] {"grid"};
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
