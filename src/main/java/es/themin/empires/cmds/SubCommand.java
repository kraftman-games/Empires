package es.themin.empires.cmds;

import org.bukkit.entity.Player;

public abstract class SubCommand {
	
	public abstract boolean onCommand(Player player, String[] args);
	
	public abstract String name();
	
	public abstract String info();
	
	public abstract String[] aliases();
}
