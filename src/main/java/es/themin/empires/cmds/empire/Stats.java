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

	public String plprefix = empires.plprefix;
	@Override
	public boolean onCommand(Player player, String[] args) {
		if (UtilManager.empireplayers.containsKey(player.getName())) {
			Empire empire = UtilManager.empireplayers.get(player.getName());
			ScoreboardManager sbm = Bukkit.getScoreboardManager();
			Scoreboard sb = sbm.getNewScoreboard();
			Objective obj = sb.registerNewObjective(ChatColor.GOLD + "jen", "Players");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName("Empire Stats");
			Score score = obj.getScore(Bukkit.getOfflinePlayer("Cores: "));
			score.setScore(empire.numberOfCores());
			player.setScoreboard(sb);
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
