package es.themin.empires.util.testing;



import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.SubCommand;

public class UtilityTesting implements CommandExecutor{
	
	public String plprefix;
	private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();
	private empires myPlugin;
	
	public UtilityTesting(empires plugin){
		myPlugin = plugin;
		plprefix = plugin.plprefix;
		
		commands.add(new newemp(plugin));
		commands.add(new emp(plugin));
		commands.add(new emps(plugin));
		commands.add(new addplayer(plugin));
		commands.add(new tannertest(plugin));
		commands.add(new generatebasecore(plugin));
		commands.add(new RicTest(plugin));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (commandLabel.equalsIgnoreCase("utiltest")) {
				if (player.isOp()){
					if (args.length == 0) {player.sendMessage(plprefix + ChatColor.RED + "Too few arguments");}
					
					else {
						SubCommand scmd = get(args[0]);
						if (scmd == null) player.sendMessage(plprefix + ChatColor.RED + "Invalid Command");
						else {
							scmd.onCommand(player, args);
						}
					}
				}
				else {
					player.sendMessage(ChatColor.RED + "Must be opped"); return false;
				}
			}
		}
		return false;
	}

	private SubCommand get(String name) {
		for (SubCommand cmd : commands) {
			if (cmd.name().equalsIgnoreCase(name)) return cmd;
			for (String alias : cmd.aliases()) if (name.equalsIgnoreCase(alias)) return cmd;
		}
		return null;
	}
	
	

}
