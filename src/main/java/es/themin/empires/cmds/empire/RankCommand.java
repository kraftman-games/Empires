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

	@Override
	public boolean onCommand(Player player, String[] args) {
		if (UtilManager.empireplayers.containsKey(player.getName())) {
			Empire empire = UtilManager.empireplayers.get(player.getName());
			if (empire.getOwner().equalsIgnoreCase(player.getName())) {
				if (args.length == 1) {
					player.sendMessage(ChatColor.GOLD + "/empire rank " + ChatColor.LIGHT_PURPLE + "(rank) " + ChatColor.GOLD + "create" + ChatColor.LIGHT_PURPLE + " (weight) "+ ChatColor.WHITE + " - " + ChatColor.AQUA + "creates a new rank");
					return false;
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
					}if (args[2].equalsIgnoreCase("create")) {
						if (empire.hasRankWithName(args[1])) {
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
						Rank rank = new Rank(weight, args[1]);
						empire.addRank(rank);
						empire.Save();
						player.sendMessage(plprefix + ChatColor.GREEN + "Rank '" + args[1] + "' with weight '" + weight + "' was succesfuly created");
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
		return "Allows you to alter the permissions of players in your empire";
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
	

}
