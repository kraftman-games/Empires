package es.themin.empires.cmds.empire;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.Empire;
import es.themin.empires.util.Rank;
import es.themin.empires.util.UtilManager;

public class RankCommand extends EmpireSubCommand{
	
	public String plprefix = empires.plprefix;
	public EmpirePermission[] eps = {EmpirePermission.INVITE, EmpirePermission.ALLY, EmpirePermission.ATTACK, EmpirePermission.KICK_PLAYER, EmpirePermission.PLACE_ALTER, EmpirePermission.PLACE_AMPLIFIER, EmpirePermission.SET_FLAG, EmpirePermission.UPGRADE_CORE };
	private empires myPlugin;
	
	
	public RankCommand(empires empires) {
		myPlugin = empires;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if (myPlugin.empireplayers.containsKey(player.getName())) {
			Empire empire = myPlugin.empireplayers.get(player.getName());
			if (empire.getOwner().equalsIgnoreCase(player.getName())) {
				if (args.length == 1) {
					info(player); return false;
				} else {
					if (args[1].equalsIgnoreCase("list")) {
						int i = 0;
						player.sendMessage(ChatColor.GOLD + "====" + ChatColor.LIGHT_PURPLE + "Custom Ranks" + ChatColor.GOLD + "====");
						for (Rank rank : empire.getRanks()) {
							player.sendMessage("- " + ChatColor.GREEN + rank.getName() + ChatColor.AQUA + " : " + ChatColor.LIGHT_PURPLE + rank.getWeight());
							i++;
						}
						if ( i == 0 ) {
							player.sendMessage(ChatColor.RED + "No custom ranks");
						}
						return false;
					}else if (args[1].equalsIgnoreCase("perms")) {
						perms(player);
						return false;
					}else if (args[2].equalsIgnoreCase("perms")) {
						if (!(empire.hasRankWithNameAp(args[1]))) {
							player.sendMessage(plprefix + ChatColor.RED + "rank not found");
							return false;
						}
						Rank rank = empire.getRankWithName(args[1]);
						player.sendMessage(ChatColor.GOLD + "==== " + ChatColor.LIGHT_PURPLE + rank.getName() + ChatColor.GOLD + " ====");
						for (EmpirePermission ep : eps) {
							if (rank.hasPermission(ep)) {
								player.sendMessage("- " + ChatColor.LIGHT_PURPLE + ep + ChatColor.AQUA + " : " + ChatColor.DARK_GREEN + "true");	
							}else {
								player.sendMessage("- " + ChatColor.LIGHT_PURPLE + ep + ChatColor.AQUA + " : " + ChatColor.DARK_RED + "false");
							}
						}
						return false;
					}else if (args[2].equalsIgnoreCase("create")) {
						if (empire.hasRankWithNameAp(args[1])) {
							player.sendMessage(plprefix + ChatColor.RED + "This rank already exists");
							return false;
						}if (args.length < 4) {
							player.sendMessage(plprefix + "Please specify a weight from 1 to 10 do '/empire rank weight' for more details"); 
							return false;
						}
						int weight = 0;
						try {
							weight = Integer.parseInt(args[3]);
						}catch(NumberFormatException e) {
							player.sendMessage(plprefix + ChatColor.RED  +"'" + args[3] + "' is not a valid weight do '/empire rank weight' for more details");
							return false;
						}
						Rank rank = new Rank(weight, args[1], empire, args[1]);
						empire.addRank(rank);
						empire.Save();
						player.sendMessage(plprefix + ChatColor.GREEN + "Rank '" + args[1] + "' with weight '" + weight + "' was succesfuly created");
						return false;
					}else if (args[2].equalsIgnoreCase("delete") || args[2].equalsIgnoreCase("del")) {
						if (empire.hasRankWithNameAp(args[1])) {
							empire.removeRank(empire.getRankWithName(args[1]));
							empire.Save();
							player.sendMessage(plprefix + ChatColor.GREEN + "Succesfuly removed rank '" +args[1] + "'");
							return false;
						}else {
							player.sendMessage(plprefix + ChatColor.RED + "'" + args[1] + "' is not a rank"); 
							return false;
						}
					}else if (args[2].equalsIgnoreCase("add")) {
						if (!(empire.hasRankWithNameAp(args[1]))) {
							player.sendMessage(plprefix + ChatColor.RED + "rank not found");
							return false;
						}
						Rank rank = empire.getRankWithName(args[1]);
						if (args.length < 4) {
							player.sendMessage(plprefix + ChatColor.RED + "Please specify a player"); 
							return false;
						}else {
							if (empire.hasPlayer(args[3])) {
								empire.setRankOfPlayer(args[3], rank);
								empire.Save();
								player.sendMessage(plprefix + ChatColor.GREEN + "'" + args[3] + "' now has rank of " + args[1]);
								return false;
							}else {
								player.sendMessage(plprefix + ChatColor.RED + "'" + args[3] + "' is not in your empire, use full names");
								return false;
							}
						}
					}else if (args[2].equalsIgnoreCase("remove")) {
						if (!(empire.hasRankWithNameAp(args[1]))) {
							player.sendMessage(plprefix + ChatColor.RED + "rank not found");
							return false;
						}
						Rank rank = empire.getRankWithName(args[1]);
						if (args.length < 4) {
							player.sendMessage(plprefix + ChatColor.RED + "Please specify a player");
							return false;
						}if (!(empire.hasPlayer(args[3]))) {
							player.sendMessage(plprefix + ChatColor.RED + "'" + args[3] + "' is not your empire");
							return false;
						}if (empire.getRankOfPlayer(args[3]) != rank) {
							player.sendMessage(plprefix + ChatColor.RED + "'" + args[3] + "' does not hold the rank of " + rank.getName());
							return false;
						}
						empire.removePlayerFromRank(args[3], rank);
						empire.Save();
						player.sendMessage(plprefix + ChatColor.GREEN + "'" + args[3] + "' no longer holds rank of "  + args[1]);
						return false;
					}else if (args[2].equalsIgnoreCase("set")) {
						if (args.length == 3) {
							player.sendMessage(plprefix + ChatColor.RED + "please specifiy a permission");
							return false;
						}if (args.length == 4) {
							player.sendMessage(plprefix + ChatColor.RED + "please specifiy a value (true/false)");
							return false;
						}if (!(empire.hasRankWithNameAp(args[1]))) {
							player.sendMessage(plprefix + ChatColor.RED + "rank not found");
							return false;
						}
						Rank rank = empire.getRankWithName(args[1]);
						int i = 0;
						for (EmpirePermission ep : eps) {
							if (ep.toString().equalsIgnoreCase(args[3])) {
								i++;
								if (args[4].equalsIgnoreCase("true")) {
									rank.addPermission(ep);
									empire.Save();
									player.sendMessage(plprefix + ChatColor.GREEN + "'"  + args[1] + " now have the permission '" + args[3] + "'");
									return false;
								}else if (args[4].equalsIgnoreCase("false")) {
									rank.removePermissions(ep);
									empire.Save();
									player.sendMessage(plprefix + ChatColor.GREEN + args[1] + " now do not have the permission " + args[3]);
									return false;
								}else {
									player.sendMessage(plprefix + ChatColor.RED + "Please give a valid value (true/false)");
									return false;
								}
							}
						}
						if (i == 0) {
							player.sendMessage(plprefix + ChatColor.RED + "'" + args[3] + "' is not a valid permission do '/empire rank perms'");
						}
						return false;
					}
					else {
						info(player);
						return false;
					}
				}
			}else {
				player.sendMessage(plprefix + ChatColor.RED + "You have to be the king of your empire to modify the ranks");
			}
		}
		return false;
	}

	@Override
	public String name() {
		return "rank";
	}

	@Override
	public String info() {
		return "Alter permissions of players";
	}

	@Override
	public String[] aliases() {
		return new String[] {"r"};
	}

	@Override
	public String[] variables() {
		return new String[] {"rank"};
	}

	@Override
	public EmpirePermission permission() {
		return null;
	}
	private void info(Player player) {
		player.sendMessage(ChatColor.GOLD + "/empire rank list " +  ChatColor.WHITE + " - " + ChatColor.AQUA + "ist's the ranks in your town");
		player.sendMessage(ChatColor.GOLD + "/empire rank perms " +  ChatColor.WHITE + " - " + ChatColor.AQUA + "list's available permissions");
		player.sendMessage(ChatColor.GOLD + "/empire rank " + ChatColor.LIGHT_PURPLE + "(rank) " + ChatColor.GOLD + "create" + ChatColor.LIGHT_PURPLE + " (weight) "+ ChatColor.WHITE + " - " + ChatColor.AQUA + "creates a new rank");
		player.sendMessage(ChatColor.GOLD + "/empire rank " + ChatColor.LIGHT_PURPLE + "(rank) " + ChatColor.GOLD + "delete" + ChatColor.WHITE + " - " + ChatColor.AQUA + "deletes this rank");
		player.sendMessage(ChatColor.GOLD + "/empire rank " + ChatColor.LIGHT_PURPLE + "(rank) " + ChatColor.GOLD + "add" + ChatColor.LIGHT_PURPLE + " (player) "+ ChatColor.WHITE + " - " + ChatColor.AQUA + "add a player to the rank");
		player.sendMessage(ChatColor.GOLD + "/empire rank " + ChatColor.LIGHT_PURPLE + "(rank) " + ChatColor.GOLD + "remove" + ChatColor.LIGHT_PURPLE + " (player) "+ ChatColor.WHITE + " - " + ChatColor.AQUA + "removes a player from the rank");
		player.sendMessage(ChatColor.GOLD + "/empire rank " + ChatColor.LIGHT_PURPLE + "(rank) " + ChatColor.GOLD + "set" + ChatColor.LIGHT_PURPLE + " (permission) (true/false)"+ ChatColor.WHITE + " - " + ChatColor.AQUA + "gives / removes a permission to / from the rank");
		player.sendMessage(ChatColor.GOLD + "/empire rank " + ChatColor.LIGHT_PURPLE + "(rank) " + ChatColor.GOLD + "perms" + ChatColor.WHITE + " - " + ChatColor.AQUA + "list's the permissions of a rank");

	}
	private void perms(Player player) {
		player.sendMessage(ChatColor.GOLD + "====" + ChatColor.LIGHT_PURPLE + " Empire Permissions " + ChatColor.GOLD + "====");
		player.sendMessage("- " + ChatColor.GREEN + "PLACE_AMPLIFIER " + ChatColor.AQUA + ": " + ChatColor.LIGHT_PURPLE + "allows a group to place amplifiers");
		player.sendMessage("- " + ChatColor.GREEN + "ADD_PLAYER " + ChatColor.AQUA + ": " + ChatColor.LIGHT_PURPLE + "allows a group to invite others to your empire");
		player.sendMessage("- " + ChatColor.GREEN + "PLACE_ALTER " + ChatColor.AQUA + ": " + ChatColor.LIGHT_PURPLE + "allows a group to place alters and cores");
		player.sendMessage("- " + ChatColor.GREEN + "KICK_PLAYER " + ChatColor.AQUA + ": " + ChatColor.LIGHT_PURPLE + "allows a group to kick players of lower rank");
		player.sendMessage("- " + ChatColor.GREEN + "UPGRADE_CORE " + ChatColor.AQUA + ": " + ChatColor.LIGHT_PURPLE + "allows a group to upgrade cores and alters");
		player.sendMessage("- " + ChatColor.GREEN + "SET_FLAG " + ChatColor.AQUA + ": " + ChatColor.LIGHT_PURPLE + "allows a group to set the flag of your empire");
		player.sendMessage("- " + ChatColor.GREEN + "ALLY " + ChatColor.AQUA + ": " + ChatColor.LIGHT_PURPLE + "allows a group to ally other empires");
		player.sendMessage("- " + ChatColor.GREEN + "ATTACK " + ChatColor.AQUA + ": " + ChatColor.LIGHT_PURPLE + "allows a group to declare war on other empires");
	}

}
