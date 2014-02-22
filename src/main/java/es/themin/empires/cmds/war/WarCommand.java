package es.themin.empires.cmds.war;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.themin.empires.cmds.empire.EmpireSubCommand;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;

public class WarCommand implements CommandExecutor{
	private static ArrayList<EmpireSubCommand> commands = new ArrayList<EmpireSubCommand>();
	
	ManagerAPI myApi = null;
	
	public WarCommand(ManagerAPI myAPI){
		myApi = myAPI;
		commands.add(new WarDeclareCommand(myAPI));
		commands.add(new WarListCommand(myAPI));
		commands.add(new WarInfoCommand(myAPI));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (commandLabel.equals("war")) {
			EPlayer myEPlayer = myApi.getEPlayer((Player)sender); 
			if (args.length == 0) {
				myEPlayer.sendMessage( ChatColor.RED + "Too few arguments");

				for (EmpireSubCommand scmd : commands) {
					StringBuilder str = new StringBuilder();
					if (scmd.variables() != null) {
						for (String variable : scmd.variables()) {
							str.append("(" + variable + ") ");
						}
					}
					myEPlayer.sendMessage(ChatColor.GOLD + "/war " + scmd.name() + ChatColor.LIGHT_PURPLE + " " + str.toString() + ChatColor.WHITE + "- " + ChatColor.AQUA + " " + scmd.info());
				}
			}
			else {
				EmpireSubCommand scmd = get(args[0]);
				if (scmd == null) myEPlayer.sendMessage( ChatColor.RED + "Invalid Command");
				else {
					scmd.onCommand((Player) sender, args);
				}
			}
		}
		return false;
	}
	
	private EmpireSubCommand get(String name) {
		for (EmpireSubCommand cmd : commands) {
			if (cmd.name().equalsIgnoreCase(name)) return cmd;
			for (String alias : cmd.aliases()) if (name.equalsIgnoreCase(alias)) return cmd;
		}
		return null;
	}
}
