package es.themin.empires.cmds.war;

import org.bukkit.entity.Player;

import es.themin.empires.cmds.empire.EmpireSubCommand;
import es.themin.empires.enums.EmpirePermission;

public class WarDeclareCommand extends EmpireSubCommand{

	@Override
	public boolean onCommand(Player player, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String name() {
		return "declare";
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] aliases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] variables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmpirePermission permission() {
		// TODO Auto-generated method stub
		return null;
	}

}
