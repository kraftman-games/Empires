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
	
	public String plprefix = empires.plprefix;
	private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();
	private empires myPlugin;
	
	public UtilityTesting(empires empires){
		myPlugin = empires;
		commands.add(new newemp(empires));
		commands.add(new emp(empires));
		commands.add(new emps(empires));
		commands.add(new addplayer(empires));
		commands.add(new tannertest(empires));
		commands.add(new generatebasecore(empires));
		commands.add(new RicTest(empires));
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
