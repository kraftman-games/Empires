package es.themin.empires.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.empire.ChatCommand;

public class GlobalCommand implements CommandExecutor{
	
	private final GlobalCommand gc = this;
	private empires plugin;
	public GlobalCommand(empires empires) {
		this.plugin = empires;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		final Player player = (Player) sender;
		if (commandLabel.equalsIgnoreCase("all")) {
			if (ChatCommand.empirechatplayers.contains(player)) ChatCommand.empirechatplayers.remove(player);
			player.performCommand("global");	
			
		}
		return false;
	}

}
