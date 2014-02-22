package es.themin.empires.cmds.ally;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.themin.empires.cmds.EmpireSubCommand;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;

public class AllyCommandStem implements CommandExecutor{
	
	
	private  ArrayList<EmpireSubCommand> commands = new ArrayList<EmpireSubCommand>();
	private ManagerAPI myApi = null;
	
	public AllyCommandStem(ManagerAPI myAPI) {
		myApi = myAPI;
		
		commands.add(new AllyAddCommand(myAPI));
		commands.add(new AllyListCommand(myAPI));
		commands.add(new AllyRequestsCommand(myAPI));
		commands.add(new AllyRemoveCommand(myAPI));
	}

	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (commandLabel.equals("ally")) {
			
			EPlayer myEPlayer = myApi.getEPlayer( (Player) sender);
			
			if (args.length == 0) {
				sendHelp(myEPlayer);
			}
			else {
				EmpireSubCommand scmd = get(args[0]);
				if (scmd == null) {
					myEPlayer.sendMessage(ChatColor.RED + "Invalid Command");
					sendHelp(myEPlayer);
					return false;
				}
				if (scmd.permission() != null && myApi.playerHasPermission(myEPlayer, scmd.permission())){
					return false;
				}
				scmd.onCommand(myEPlayer, args);
			}
		}
		return false;
	}
	
	private void sendHelp(EPlayer myEPlayer){
		for (EmpireSubCommand scmd : commands) {
			StringBuilder str = new StringBuilder();
			if (scmd.variables() != null) {
				for (String variable : scmd.variables()) {
					str.append("(" + variable + ") ");
				}
			}
			myEPlayer.sendMessage(ChatColor.GOLD + "/ally " + scmd.name() + ChatColor.LIGHT_PURPLE + " " + str.toString() + ChatColor.WHITE + "- " + ChatColor.AQUA + " " + scmd.info());
		}
	}
	
	private EmpireSubCommand get(String name) {
		for (EmpireSubCommand cmd : commands) {
			if (cmd.name().equalsIgnoreCase(name)) return cmd;
			for (String alias : cmd.aliases()) if (name.equalsIgnoreCase(alias)) return cmd;
		}
		return null;
	}
}
