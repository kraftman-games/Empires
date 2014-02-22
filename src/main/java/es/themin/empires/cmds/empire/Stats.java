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
import es.themin.empires.managers.CoreManager;
import es.themin.empires.managers.EmpireManager;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.managers.PlayerManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class Stats extends EmpireSubCommand{

	private ManagerAPI myApi = null;
	
	public Stats(ManagerAPI api) {
		myApi = api;
	}

	@Override
	public boolean onCommand(final Player player, String[] args) {
		EPlayer myEPlayer = myApi.getEPlayer(player);
		
		if (myEPlayer != null && myEPlayer.getEmpireUUID() != null) {
			Empire empire = myApi.getEmpire(myEPlayer);
			final ScoreboardManager sbm = Bukkit.getScoreboardManager();
			Scoreboard sb = sbm.getNewScoreboard();
			
			Objective obj = sb.registerNewObjective("Empires", "stats");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName(ChatColor.GOLD + "====" + ChatColor.LIGHT_PURPLE + "Empire Stats" + ChatColor.GOLD + "====");
			Score Exp = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Exp: "));
			Exp.setScore(empire.getXP());
			Score ranking = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Ranking /" + myApi.getEmpireCount() +" : "));
			ranking.setScore(myApi.getRank(empire));
			Score cores = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Cores: "));
			cores.setScore(myApi.getCoreCount(empire));
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
			player.sendMessage( ChatColor.RED + "You are not in an empire");
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
