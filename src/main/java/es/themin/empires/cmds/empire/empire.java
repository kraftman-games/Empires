package es.themin.empires.cmds.empire;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;
import es.themin.empires.util.Rank;
import es.themin.empires.util.UtilManager;


public class empire implements CommandExecutor{
	public String plprefix = empires.plprefix;
	private static ArrayList<EmpireSubCommand> commands = new ArrayList<EmpireSubCommand>();
	
	public empire(){
		commands.add(new list());
		commands.add(new RankCommand());
		commands.add(new Stats());
		commands.add(new ChatCommand());
		commands.add(new SettingsCommand());
		commands.add(new GridLocationCommand());
		commands.add(new EmpireInviteCommand());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (commandLabel.equals("empire") || commandLabel.equalsIgnoreCase("emp") || commandLabel.equalsIgnoreCase("e")) {
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
					player.sendMessage(ChatColor.GOLD + "/empire " + scmd.name() + ChatColor.LIGHT_PURPLE + " " + str.toString() + ChatColor.WHITE + "- " + ChatColor.AQUA + " " + scmd.info());
				}
			}
			else {
				EmpireSubCommand scmd = get(args[0]);
				if (scmd == null) {
					player.sendMessage(plprefix + ChatColor.RED + "Invalid Command"); return false;
				}
				if (scmd.permission() != null){
					if (UtilManager.empireplayers.containsKey(player.getName())) {
						Empire empire = UtilManager.empireplayers.get(player.getName());
						if (!empire.getOwner().equalsIgnoreCase(player.getName())) {
							if (empire.playerHasARank(player.getName())) {
								Rank rank = empire.getRankOfPlayer(player.getName());
								if (!(rank.hasPermission(scmd.permission()))) {
									player.sendMessage(MsgManager.noempperm);
									return false;
									
								}
							}else {
								player.sendMessage(MsgManager.noempperm);
								return false;
							}
						}
					}
				}
				scmd.onCommand(player, args);
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
