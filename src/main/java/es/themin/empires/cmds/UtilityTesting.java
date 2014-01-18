package es.themin.empires.cmds;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.Core;
import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;

public class UtilityTesting implements CommandExecutor{
	
	private empires plugin;
	public UtilityTesting(empires plugin){
		this.plugin = plugin;
	}
	private String plprefix = plugin.plprefix;
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (commandLabel.equalsIgnoreCase("utiltest")) {
				if (player.isOp()){
					if (args.length == 0) player.sendMessage(plprefix + ChatColor.RED + "Too few arguments");
					else {
						if (args[0].equalsIgnoreCase("newemp")) {
							CreateNewEmpire(player, args);
						}else if (args[0].equalsIgnoreCase("emps")) {
							SendEmpireList(player);
						}else if (args[0].equalsIgnoreCase("emp")) {
							/*if (args.length == 1) {
								EmpirePlayer ep = UtilManager.empireplayers.get(player.getName());
								if (ep.getEmpire() == null) {
									player.sendMessage(ChatColor.RED + "Not in an empire");
									return false; }
								else {
									SendEmpireDetails(player, ep.getEmpire());
									return false;
								}
							}else {
								if(UtilManager.getEmpireWithName(args[1]) == null) {
									player.sendMessage("Invalid Empire");
								}else {
									SendEmpireDetails(player, UtilManager.getEmpireWithName(args[1]));
									return false;
								}
							}*/
						}else if (args[0].equalsIgnoreCase("ap")) {
							//return  AddPlayerToEmpire(player, args);
						} else if (args[0].equalsIgnoreCase("tantest")) {
							TannerTests(player);
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
	private void TannerTests(Player myPlayer){
		Core myCore = new Core();
		myCore.setType(CoreType.BASE);
		myCore.build(myPlayer);
	}
	
	private void CreateNewEmpire(Player myPlayer, String[] args) {
		myPlayer.sendMessage("jen1");
		if (args.length == 1) myPlayer.sendMessage(plprefix + ChatColor.RED  + "Give a name");
		else {
			myPlayer.sendMessage(plprefix + ChatColor.GREEN + "Created Empire: "  + args[1]);
			//needs check to see if they are in an empire already
			Empire empire = new Empire(UtilManager.nextUnusedEmpireId(), args[1]);
			empire.addPlayer(myPlayer.getName());
			empire.Save();

			
		}
	}
	
	private void SendEmpireList(Player myPlayer){
		myPlayer.sendMessage(ChatColor.GOLD + "==========" + ChatColor.LIGHT_PURPLE + "Empires" + ChatColor.GOLD + "==========");
		int i = 0;
		for (Empire empire : UtilManager.empires) {
			myPlayer.sendMessage(empire.getName());
			i++;
		}
		if (i == 0) {
			myPlayer.sendMessage(ChatColor.RED + "No Empires :(");
		}
		myPlayer.sendMessage(ChatColor.GOLD + "=========================");
	}
	
	private boolean SendEmpireDetails(Player myPlayer, Empire empire){
		myPlayer.sendMessage(ChatColor.GOLD + "==========" + ChatColor.LIGHT_PURPLE + empire.getName() + ChatColor.GOLD + "==========");
		myPlayer.sendMessage(ChatColor.GREEN + "Cores #: " + ChatColor.LIGHT_PURPLE + empire.numberOfCores());
		myPlayer.sendMessage(ChatColor.GREEN + "Player #: " + ChatColor.LIGHT_PURPLE + empire.numberOfPlayers());
		myPlayer.sendMessage(ChatColor.GOLD + "=========================");
		return false;
	}
	
	/*public boolean AddPlayerToEmpire(Player myPlayer, String myArgs[]){
		EmpirePlayer ep2 = UtilManager.empireplayers.get(myPlayer.getName());
		
		if (myArgs.length == 1) {
			myPlayer.sendMessage("Too Few Args"); 
			return false;
			}
		else {
			String name = myArgs[1];
			Player player2 = Bukkit.getServer().getPlayer(name);
			if (ep2.isInEmpire()) {
				ep2.getEmpire().addPlayer(name);
				myPlayer.sendMessage("Added: " + myArgs[1]);
				player2.sendMessage(myPlayer.getName() + " added you to their empire");
			}
		}
		return false;
	}*/
	
	

}
