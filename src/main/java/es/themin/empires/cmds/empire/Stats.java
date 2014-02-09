package es.themin.empires.cmds.empire;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import es.themin.empires.empires;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;

public class Stats extends EmpireSubCommand{

	private empires myPlugin;
	public String plprefix = empires.plprefix;
	public Stats(empires empires) {
		myPlugin = empires;
	}

	@Override
	public boolean onCommand(final Player player, String[] args) {
		if (myPlugin.empireplayers.containsKey(player.getName())) {
			Empire empire = myPlugin.empireplayers.get(player.getName());
			final ScoreboardManager sbm = Bukkit.getScoreboardManager();
			Scoreboard sb = sbm.getNewScoreboard();
			
			Objective obj = sb.registerNewObjective(plprefix, "stats");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName(ChatColor.GOLD + "====" + ChatColor.LIGHT_PURPLE + "Empire Stats" + ChatColor.GOLD + "====");
			Score Exp = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Exp: "));
			Exp.setScore(empire.getExp());
			Score ranking = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Ranking /" + myPlugin.empires.size() +" : "));
			ranking.setScore(empire.getRanking());
			Score cores = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Cores: "));
			cores.setScore(empire.numberOfCores());
			Score players = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Players: "));
			players.setScore(empire.numberOfPlayers());
			
			
			player.setScoreboard(sb);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Empires"), new Runnable() {

				@Override
				public void run() {
					player.setScoreboard(sbm.getNewScoreboard());
				}
				
			}, 400L);
		}else {
			player.sendMessage(plprefix + ChatColor.RED + "You are not in an empire");
		}
		return false;
	}

	@Override
	public String name() {
		return "stats";
	}

	@Override
	public String info() {
		return "gives statistics about your empire";
	}

	@Override
	public String[] aliases() {
		return new String[] {"stat"};
	}

	@Override
	public String[] variables() {
		return null;
	}

	@Override
	public EmpirePermission permission() {
		return null;
	}
	
	

}
