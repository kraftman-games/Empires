package es.themin.empires.cmds;

import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.EPlayer;

public abstract class EmpireSubCommand {
	public abstract boolean onCommand(EPlayer player, String[] args);
	
	public abstract String name();
	
	public abstract String info();
	
	public abstract String[] aliases();
	
	public abstract String[] variables();
	
	public abstract EmpirePermission permission();
}
