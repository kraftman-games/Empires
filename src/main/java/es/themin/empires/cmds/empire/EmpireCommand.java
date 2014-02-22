package es.themin.empires.cmds.empire;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;


public class EmpireCommand implements CommandExecutor{
	private static ArrayList<EmpireSubCommand> commands = new ArrayList<EmpireSubCommand>();
	
	private ManagerAPI myApi = null;
	
	public EmpireCommand(ManagerAPI api){
		myApi = api;
		commands.add(new list(api));
		commands.add(new RankCommand(api));
		commands.add(new Stats(api));
		commands.add(new ChatCommand(api));
		commands.add(new SettingsCommand(api));
		commands.add(new EmpireInviteCommand(api));
	}
	
	

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (commandLabel.equals("empire") || commandLabel.equalsIgnoreCase("emp") || commandLabel.equalsIgnoreCase("e")) {
			Player player = (Player) sender;
			EPlayer myEPlayer = myApi.getEPlayer(player);
			
			
			if (args.length == 0) {
				sendCommandHelp(player);
				return false;
			}
			else {
				EmpireSubCommand scmd = get(args[0]);
				if (scmd == null) {
					player.sendMessage( ChatColor.RED + "Invalid Command"); 
					return false;
				} 
				
				if (scmd.permission() != null){
					if (!myApi.playerHasPermission(myEPlayer, scmd.permission())){
						return false;
					}
				}
				scmd.onCommand(player, args);
				return true;
			}
		}
		return false;
	}
	
	private void sendCommandHelp(Player myPlayer){
		myPlayer.sendMessage(ChatColor.RED + "Too few arguments");

		for (EmpireSubCommand scmd : commands) {
			StringBuilder str = new StringBuilder();
			if (scmd.variables() != null) {
				for (String variable : scmd.variables()) {
					str.append("(" + variable + ") ");
				}
			}
			myPlayer.sendMessage(ChatColor.GOLD + "/empire " + scmd.name() + ChatColor.LIGHT_PURPLE + " " + str.toString() + ChatColor.WHITE + "- " + ChatColor.AQUA + " " + scmd.info());
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









