package es.themin.empires.cmds.empire;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.themin.empires.PlayerManager;
import es.themin.empires.empires;
import es.themin.empires.util.CorePlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;
import es.themin.empires.util.Rank;
import es.themin.empires.util.UtilManager;


public class EmpireCommand implements CommandExecutor{
	public String plprefix;
	private static ArrayList<EmpireSubCommand> commands = new ArrayList<EmpireSubCommand>();
	private empires myPlugin;
	private PlayerManager Players;
	
	public EmpireCommand(empires plugin){
		myPlugin = plugin;
		plprefix = plugin.plprefix;
		Players = plugin.Players;
		commands.add(new list(plugin));
		commands.add(new RankCommand(plugin));
		commands.add(new Stats(plugin));
		commands.add(new ChatCommand(plugin));
		commands.add(new SettingsCommand(plugin));
		commands.add(new GridLocationCommand(plugin));
		commands.add(new EmpireInviteCommand(plugin));
	}
	
	

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (commandLabel.equals("empire") || commandLabel.equalsIgnoreCase("emp") || commandLabel.equalsIgnoreCase("e")) {
			Player player = (Player) sender;
			CorePlayer myCorePlayer = Players.getPlayer(player.getUniqueId());
			
			
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
					if (myCorePlayer != null || myCorePlayer.getEmpire() != null) {
						Empire empire = myCorePlayer.getEmpire();
						if ((empire.getOwner() != myCorePlayer.getUUID())) {
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
