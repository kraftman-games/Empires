package es.themin.empires.cmds.empire;

import org.bukkit.entity.Player;

import es.themin.empires.enums.EmpirePermission;

public abstract class EmpireSubCommand {
	public abstract boolean onCommand(Player player, String[] args);
	
	public abstract String name();
	
	public abstract String info();
	
	public abstract String[] aliases();
	
	public abstract String[] variables();
	
	public abstract EmpirePermission permission();
}
