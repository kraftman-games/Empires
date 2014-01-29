package es.themin.empires.cmds.war;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.empire.ChatCommand;
import es.themin.empires.cmds.empire.EmpireSubCommand;
import es.themin.empires.cmds.empire.GridLocationCommand;
import es.themin.empires.cmds.empire.RankCommand;
import es.themin.empires.cmds.empire.SettingsCommand;
import es.themin.empires.cmds.empire.Stats;
import es.themin.empires.cmds.empire.list;

public class WarCommand implements CommandExecutor{
	public String plprefix = empires.plprefix;
	private static ArrayList<EmpireSubCommand> commands = new ArrayList<EmpireSubCommand>();
	public static void setUp(){
		commands.add(new WarDeclareCommand());
		commands.add(new list());
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (commandLabel.equals("war")) {
			Player player = (Player) sender;
			if (args.length == 0) {
				player.sendMessage(plprefix + ChatColor.RED + "Too few arguments");

				for (EmpireSubCommand scmd : commands) {
					StringBuilder str = new StringBuilder();
					if (scmd.variables() != null) {
						for (String variable : scmd.variables()) {
							str.append("(" + variable + ") ");
						}
					}
					player.sendMessage(ChatColor.GOLD + "/war " + scmd.name() + ChatColor.LIGHT_PURPLE + " " + str.toString() + ChatColor.WHITE + "- " + ChatColor.AQUA + " " + scmd.info());
				}
			}
			else {
				EmpireSubCommand scmd = get(args[0]);
				if (scmd == null) player.sendMessage(plprefix + ChatColor.RED + "Invalid Command");
				else {
					scmd.onCommand(player, args);
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