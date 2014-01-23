package es.themin.empires.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.empire.ChatCommand;

public class GlobalCommand implements CommandExecutor{
	
	private final GlobalCommand gc = this;
	private empires plugin;
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		final Player player = (Player) sender;
		if (commandLabel.equalsIgnoreCase("g")) {
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					CommandExecutor ce = Bukkit.getServer().getPluginCommand("g").getExecutor();
					Bukkit.getServer().getPluginCommand("g").setExecutor(gc);
					if (ChatCommand.empirechatplayers.contains(player)) ChatCommand.empirechatplayers.remove(player);
					Bukkit.getServer().getPluginCommand("g").setExecutor(ce);
				}
					
				
			},1L);
		}if (commandLabel.equalsIgnoreCase("global")) {
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					CommandExecutor ce = Bukkit.getServer().getPluginCommand("global").getExecutor();
					Bukkit.getServer().getPluginCommand("global").setExecutor(gc);
					if (ChatCommand.empirechatplayers.contains(player)) ChatCommand.empirechatplayers.remove(player);
					Bukkit.getServer().getPluginCommand("global").setExecutor(ce);
				}
					
				
			},1L);
		}
		return false;		
	}

}
