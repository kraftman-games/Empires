package es.themin.empires.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.util.Empire;
import es.themin.empires.util.EmpirePlayer;
import es.themin.empires.util.UtilityHashMaps;

public class UtilityTesting implements CommandExecutor{
	
	private empires plugin;
	public UtilityTesting(empires plugin){
		this.plugin = plugin;
	}
	private String plprefix = plugin.plprefix;
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			EmpirePlayer ep2 = UtilityHashMaps.empireplayers.get(player.getName());
			if (commandLabel.equalsIgnoreCase("utiltest")) {
				if (player.isOp()){
					if (args.length == 0) player.sendMessage(plprefix + ChatColor.RED + "Too few arguments");
					else {
						if (args[0].equalsIgnoreCase("newemp")) {
							if (args.length == 1) player.sendMessage(plprefix + ChatColor.RED  + "Give a name");
							else {
								Empire empire = new Empire(UtilityHashMaps.empires.size() + 1, args[1]);
								empire.addPlayer(player.getName());
								UtilityHashMaps.empires.add(empire);

								player.sendMessage(plprefix + ChatColor.GREEN + "Created Empire: "  + args[1]);
							}
						}else if (args[0].equalsIgnoreCase("emps")) {
							player.sendMessage(ChatColor.GOLD + "==========" + ChatColor.LIGHT_PURPLE + "Empires" + ChatColor.GOLD + "==========");
							int i = 0;
							for (Empire empire : UtilityHashMaps.empires) {
								player.sendMessage(empire.getName());
								i++;
							}
							if (i == 0) {
								player.sendMessage(ChatColor.RED + "No Empires :(");
							}
							player.sendMessage(ChatColor.GOLD + "=========================");
						}else if (args[0].equalsIgnoreCase("emp")) {
							if (UtilityHashMaps.empireplayers.containsKey(player.getName())) {
								EmpirePlayer ep = UtilityHashMaps.empireplayers.get(player.getName());
								if (ep.getEmpire() == null) {
									player.sendMessage(ChatColor.RED + "Not in an empire"); return false; }
								else {
									Empire empire = ep.getEmpire();
									player.sendMessage(ChatColor.GOLD + "==========" + ChatColor.LIGHT_PURPLE + empire.getName() + ChatColor.GOLD + "==========");
									player.sendMessage(ChatColor.GREEN + "Cores #: " + ChatColor.LIGHT_PURPLE + empire.numberOfCores());
									player.sendMessage(ChatColor.GREEN + "Player #: " + ChatColor.LIGHT_PURPLE + empire.numberOfPlayers());
									player.sendMessage(ChatColor.GOLD + "=========================");
								}
							}else player.sendMessage(ChatColor.RED + "You weren't found :/");return false;
							
						}else if (args[0].equalsIgnoreCase("ap")) {
							if (args.length == 1) {player.sendMessage("Too Few Args"); return false;}
							else {
								String name = args[1];
								Player player2 = Bukkit.getServer().getPlayer(name);
								if (ep2.isInEmpire()) {
									ep2.getEmpire().addPlayer(name);
									player.sendMessage("Added: " + args[1]);
									player2.sendMessage(player.getName() + " added you to their empire");
								}
							}
						}
					}
				}else player.sendMessage(ChatColor.RED + "Must be opped"); return false;
			}
		}
		return false;
	}

}
