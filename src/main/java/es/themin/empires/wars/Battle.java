package es.themin.empires.wars;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import es.themin.empires.enums.BattleType;
import es.themin.empires.util.Empire;
import es.themin.empires.util.SettingsManager;
import es.themin.empires.util.UtilManager;

public class Battle {
	
	private War war;
	private long start;
	private long end;
	private ArrayList<Empire> empire1allies;
	private ArrayList<Empire> empire2allies;
	private Empire empire1;
	private Empire empire2;
	private Empire victor;
	private BattleType type;
	private boolean onGoing;
	private int team1points;
	private int team2points;
	private boolean endsinatie;
	private int killsforwin;
	private int damageforwin;
	private BattleTeam attacker;
	public enum BattleTeam {team1, team2};
	public Battle(Empire empire1, Empire empire2, War war, BattleType type, BattleTeam Attacker) {
		this.empire1 = empire1;
		this.empire2 = empire2;
		this.empire1allies = new ArrayList<Empire>();
		this.empire2allies = new ArrayList<Empire>();
		this.war = war;
		this.type = type;
		this.team1points = 0;
		this.team2points = 0;
		this.onGoing = false;
		this.attacker = Attacker;
		
	}
	public void start() {
		this.onGoing = true;
		this.start = System.currentTimeMillis();
		if (type == BattleType.DEATHMATCH) {
			if (SettingsManager.getInstance().getConfig().getString("wars.battles.deathmatch.use_multiplier").equalsIgnoreCase("true")) {
				int multiplier = SettingsManager.getInstance().getConfig().getInt("wars.battles.deathmatch.kills_for_win_mulitplier");
				int team1 = 0;
				int team2 = 0;
				for (Empire empire : getAllEmpiresOnTeam1()) {
					team1 = team1 + empire.getNumberOfOnlinePlayers();
				}for (Empire empire : getAllEmpiresOnTeam2()) {
					team2 = team2 + empire.getNumberOfOnlinePlayers();
				}
				int added = team1 + team2;
				int average = (int) added / 2;
				this.killsforwin = (int) multiplier * average;
			}else {
				this.killsforwin = SettingsManager.getInstance().getConfig().getInt("wars.battles.deathmatch.kills_for_win");
			}
		}else if (type == BattleType.OBLITERATION) {
			//TODO
		}
		upDateScoreBoards();
	}
	public void end() {
		if (type == BattleType.DEATHMATCH) {
			this.onGoing = false;
			if (team1points > team2points) this.victor = empire1; this.endsinatie = false; war.addWinsToTeam1(1);
			if (team2points > team1points) this.victor = empire2; this.endsinatie = false; war.addWinsToTeam2(1);
			if (team1points == team2points) this.victor = null; this.endsinatie = true;
			this.end = System.currentTimeMillis();
		}else if (type == BattleType.OBLITERATION) {
			
		}
		wipeScoreboards();
	}
	public void setAttacker(BattleTeam team) {
		attacker = team;
	}
	public void endWithWinner(BattleTeam team) {
		if (team == BattleTeam.team1) {
			this.onGoing = false;
			war.addWinsToTeam1(1);
			this.victor = empire1;
			this.endsinatie = false;
			this.end = System.currentTimeMillis();		
		}else if (team == BattleTeam.team2) {
			this.onGoing = false;
			war.addWinsToTeam2(1);
			this.victor = empire2;
			this.endsinatie = false;
			this.end = System.currentTimeMillis();		
		}
	}

	public boolean endedInATie() {
		return endsinatie;
	}
	public void setTeam1Points(int points) {
		this.team1points = points;
		upDateScoreBoards();
		checkForEnd();
	}
	public void setTeam2Points(int points) {
		this.team2points = points;
		upDateScoreBoards();
		checkForEnd();
	}
	public void addPointsToTeam1(int points) {
		this.team1points = this.team1points + points;
		upDateScoreBoards();
		checkForEnd();
	}
	public void addPointsToTeamWithEmpire(Empire empire, int points) {
		if (getAllEmpiresOnTeam1().contains(empire)) {
			this.team1points = this.team1points + points;
			upDateScoreBoards();
			checkForEnd();
		}else if (getAllEmpiresOnTeam2().contains(empire)) {
			this.team2points = this.team2points + points;
			upDateScoreBoards();
			checkForEnd();
		}
	}
	public void addPointsToTeam2(int points) {
		this.team2points = this.team2points + points;
		upDateScoreBoards();
		checkForEnd();
	}
	public void addEmpireToTeam1(Empire empire) {
		empire1allies.add(empire);
	}
	public void addEmpireToTeam2(Empire empire) {
		empire2allies.add(empire);
	}
	public void removeEmpireFromTeam1(Empire empire) {
		empire2allies.add(empire);
	}
	public void removeEmpireFromTeam2(Empire empire) {
		empire2allies.remove(empire);
	}
	public BattleType getType() {
		return type;
	}
	public Empire getVictor() {
		return victor;
	}
	public long getCurrentDuration() {
		return System.currentTimeMillis() - start;
	}
	public boolean isOnGoing() {
		return onGoing;
	}
	public long getEnd() {
		return end;
	}
	public War getWar() {
		return war;
	}
	public ArrayList<Empire> getAllEmpires() {
		ArrayList<Empire> list = new ArrayList<Empire>();
		list.add(empire1);
		list.add(empire2);
		for (Empire empire : this.empire1allies) {
			if (!(list.contains(empire))) {
				list.add(empire);
			}
		}for (Empire empire : this.empire2allies) {
			if (!(list.contains(empire))) {
				list.add(empire);
			}
		}
		return list;
	}
	public ArrayList<Empire> getAllEmpiresOnTeam1() {
		ArrayList<Empire> list = new ArrayList<Empire>();
		list.add(empire1);
		for (Empire empire : this.empire1allies) {
			if (!(list.contains(empire))) {
				list.add(empire);
			}
		}
		return list;
	}
	public ArrayList<Empire> getAllEmpiresOnTeam2() {
		ArrayList<Empire> list = new ArrayList<Empire>();
		list.add(empire2);
		for (Empire empire : this.empire2allies) {
			if (!(list.contains(empire))) {
				list.add(empire);
			}
		}
		return list;
	}
	public void checkForEnd() {
		if (type == BattleType.DEATHMATCH) {
			if (team1points >= killsforwin) {
				endWithWinner(BattleTeam.team1);
			}else if (team2points >= killsforwin) {
				endWithWinner(BattleTeam.team2);
			}
		}else if (type == BattleType.OBLITERATION) {
			if (attacker == BattleTeam.team1) {
				if (team1points >= damageforwin) {
					endWithWinner(BattleTeam.team1);
				}else if (team2points >= killsforwin) {
					endWithWinner(BattleTeam.team2);
				}
			}else if (attacker == BattleTeam.team2) {
				if (team1points >= killsforwin) {
					endWithWinner(BattleTeam.team1);
				}else if (team2points >= damageforwin) {
					endWithWinner(BattleTeam.team2);
				}
			}
		}
	}
	public void upDateScoreBoards() {
		Bukkit.getServer().broadcastMessage("DEBUG 1");
		final ScoreboardManager sbm = Bukkit.getScoreboardManager();
		for (Empire empire : getAllEmpiresOnTeam1()) {
			empire.broadcastMessage("DEBUG 2.1");
			Scoreboard sb = sbm.getNewScoreboard();
			
			Objective you = sb.registerNewObjective("you", "stats");
			you.setDisplaySlot(DisplaySlot.SIDEBAR);
			you.setDisplayName(ChatColor.GOLD + "====" + ChatColor.DARK_GREEN + "Allies" + ChatColor.GOLD + "====");
			Score yous;
			if (type == BattleType.DEATHMATCH) {
				yous = you.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD+"Kills / " + killsforwin + ":"));
				yous.setScore(team1points);
				empire.broadcastMessage("DEBUG 3.1");
			}else if (type == BattleType.OBLITERATION) {
				//TODO
			}
			
			
			Objective them = sb.registerNewObjective("them", "stats");
			them.setDisplaySlot(DisplaySlot.SIDEBAR);
			them.setDisplayName(ChatColor.GOLD + "====" + ChatColor.DARK_RED + "Enemies" + ChatColor.GOLD + "====");
			Score thems = you.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Exp: "));
			thems.setScore(empire.getExp());
			if (type == BattleType.DEATHMATCH) {
				thems = you.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD+"Kills / " + killsforwin + ":"));
				thems.setScore(team2points);
			}else if (type == BattleType.OBLITERATION) {
				//TODO
			}
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (empire.hasPlayer(player.getName())) {
					player.sendMessage("DEBUG 4.1");
					player.setScoreboard(sb);
				}
			}
		}
		for (Empire empire : getAllEmpiresOnTeam2()) {
			empire.broadcastMessage("DEBUG 2.2");
			Scoreboard sb = sbm.getNewScoreboard();
			
			Objective you = sb.registerNewObjective("you", "stats");
			you.setDisplaySlot(DisplaySlot.SIDEBAR);
			you.setDisplayName(ChatColor.GOLD + "====" + ChatColor.DARK_GREEN + "Allies" + ChatColor.GOLD + "====");
			Score yous;
			if (type == BattleType.DEATHMATCH) {
				yous = you.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD+"Kills / " + killsforwin + ":"));
				yous.setScore(team2points);
				empire.broadcastMessage("DEBUG 3.2");
			}else if (type == BattleType.OBLITERATION) {
				//TODO
			}
			
			
			Objective them = sb.registerNewObjective("them", "stats");
			them.setDisplaySlot(DisplaySlot.SIDEBAR);
			them.setDisplayName(ChatColor.GOLD + "====" + ChatColor.DARK_RED + "Enemies" + ChatColor.GOLD + "====");
			Score thems = you.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Exp: "));
			thems.setScore(empire.getExp());
			if (type == BattleType.DEATHMATCH) {
				thems = you.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD+"Kills / " + killsforwin + ":"));
				thems.setScore(team1points);
			}else if (type == BattleType.OBLITERATION) {
				//TODO
			}
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (empire.hasPlayer(player.getName())) {
					player.sendMessage("DEBUG 4.2");
					player.setScoreboard(sb);
				}
			}
		}
	}
	public void wipeScoreboards() {
		final ScoreboardManager sbm = Bukkit.getScoreboardManager();
		for (Empire empire : getAllEmpires()) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (empire.hasPlayer(player.getName())) {
					player.setScoreboard(sbm.getNewScoreboard());
				}
			}
		}
	}
}
