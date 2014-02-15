package es.themin.empires.wars;

import java.util.ArrayList;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
//import org.bukkit.scoreboard.Team;




import es.themin.empires.empires;
import es.themin.empires.enums.BattleType;
import es.themin.empires.managers.SettingsManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class Battle {
	
	//private empires plugin;
	public String warprefix = empires.warprefix;
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
	private long time = SettingsManager.getConfig().getLong("wars.battles.length") * 60 * 1000;
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
		this.start = 0;
		this.end = 0;
		this.endsinatie =false;
	}
	public void start() {
		this.onGoing = true;
		this.start = System.currentTimeMillis();
		if (type == BattleType.DEATHMATCH) {

			if (SettingsManager.getConfig().getString("wars.battles.deathmatch.use_multiplier").equalsIgnoreCase("true")) {
				int multiplier = SettingsManager.getConfig().getInt("wars.battles.deathmatch.kills_for_win_mulitplier");
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
				this.killsforwin = SettingsManager.getConfig().getInt("wars.battles.deathmatch.kills_for_win");
			}
		}else if (type == BattleType.OBLITERATION) {
			//TODO
		}
		upDateScoreBoards();
		scheduleTimer();
		//Bukkit.broadcastMessage("Time: " + time);
	}
	public void end() {
		Bukkit.getServer().broadcastMessage("Percentage: " + SettingsManager.getConfig().getLong("wars.battles.percentage_gain_per_win"));
		if (type == BattleType.DEATHMATCH) {
			this.onGoing = false;
			if (team1points > team2points)  {
				this.victor = empire1; 
				this.endsinatie = false;
				war.addWinsToTeam1(1);
				war.addTeam1Percent(SettingsManager.getConfig().getLong("wars.battles.percentage_gain_per_win"));
			}
			if (team2points > team1points){
				this.victor = empire2; 
				this.endsinatie = false;
				war.addWinsToTeam2(1);
				war.addTeam1Percent(-SettingsManager.getConfig().getLong("wars.battles.percentage_gain_per_win"));
			}
			if (team1points == team2points) this.victor = null; this.endsinatie = true;
			this.end = System.currentTimeMillis();
		}else if (type == BattleType.OBLITERATION) {
			//TODO
		}
		wipeScoreboards();
		for (Empire empire : getAllEmpiresOnTeam1()) {
			if (victor == empire1){
				empire.addBattleWins(1); 
				empire.broadcastMessage(warprefix + ChatColor.GREEN + "You have won this battle, victory get's closer");
				empire.setLastBattleWin(System.currentTimeMillis());
			}
			else if (victor == empire2) {
				empire.addBattleLosses(1);
				empire.broadcastMessage(warprefix + ChatColor.RED + "The battle is lost, regroup ready to fight again");
				empire.setLastBattleLoss(System.currentTimeMillis());
			}
		}for (Empire empire : getAllEmpiresOnTeam2()) {
			if (victor == empire2) {
				empire.addBattleWins(1);
				empire.broadcastMessage(warprefix + ChatColor.GREEN + "You have won this battle, victory get's closer");
				empire.setLastBattleWin(System.currentTimeMillis());
			}
			else if (victor == empire1){ 
				empire.addBattleLosses(1);
				empire.broadcastMessage(warprefix + ChatColor.RED + "The battle is lost, regroup ready to fight again");
				empire.setLastBattleLoss(System.currentTimeMillis());
			}
			
		}
		war.displayStatistic();
		if (!endedInATie()) {
			war.setPercentageOfEmpire(getLooser(), war.getPercentageOfEmpire(getLooser()) - (float) SettingsManager.getConfig().getLong("wars.battles.percentage_change_for_allied_on_battle_end"));
			if (war.getPercentageOfEmpire(victor) != 100) {
				if (war.getPercentageOfEmpire(victor) >= 100 - (float) SettingsManager.getConfig().getLong("wars.battles.percentage_change_for_allied_on_battle_end")) {
					war.setPercentageOfEmpire(victor, (float)100);
				}else {
					war.setPercentageOfEmpire(victor, war.getPercentageOfEmpire(victor) + SettingsManager.getConfig().getLong("wars.battles.percentage_change_for_allied_on_battle_end"));					
				}
			}
		}
		war.checkForKnockOut();
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
			war.addTeam1Percent(SettingsManager.getConfig().getLong("wars.battles.percentage_gain_per_win"));
			for (Empire empire : getAllEmpiresOnTeam1()) {
				empire.addBattleWins(1); 
				empire.broadcastMessage(warprefix + ChatColor.GREEN + "You have won this battle, victory get's closer");
				empire.setLastBattleWin(System.currentTimeMillis());
			}
			for (Empire empire : getAllEmpiresOnTeam2()) {
				empire.addBattleLosses(1);
				empire.broadcastMessage(warprefix + ChatColor.RED + "The battle is lost, regroup ready to fight again");	
				empire.setLastBattleLoss(System.currentTimeMillis());
			}
			this.end = System.currentTimeMillis();		
		}else if (team == BattleTeam.team2) {
			war.addTeam1Percent(-SettingsManager.getConfig().getLong("wars.battles.percentage_gain_per_win"));
			this.onGoing = false;
			war.addWinsToTeam2(1);
			this.victor = empire2;
			this.endsinatie = false;
			this.end = System.currentTimeMillis();
			for (Empire empire : getAllEmpiresOnTeam2()) {
				empire.addBattleWins(1); 
				empire.broadcastMessage(warprefix + ChatColor.GREEN + "You have won this battle, victory get's closer");
				empire.setLastBattleWin(System.currentTimeMillis());
			}
			for (Empire empire : getAllEmpiresOnTeam1()) {
				empire.addBattleLosses(1);
				empire.broadcastMessage(warprefix + ChatColor.RED + "The battle is lost, regroup ready to fight again");	
				empire.setLastBattleLoss(System.currentTimeMillis());
			}
		}
		wipeScoreboards();
		war.displayStatistic();
	}
	public Empire getEmpire1(){
		return empire1;
	}
	public Empire getEmpire2(){
		return empire2;
	}
	public boolean endedInATie() {
		return endsinatie;
	}
	public long getStart(){
		return start;
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
		//Bukkit.getServer().broadcastMessage("DEBUG 1");
		final ScoreboardManager sbm = Bukkit.getScoreboardManager();
		int team1numbers = 0;
		for (Empire empire : getAllEmpiresOnTeam1()) {
			team1numbers = team1numbers + empire.getNumberOfOnlinePlayers();
		}
		int team2numbers = 0;
		for (Empire empire : getAllEmpiresOnTeam2()) {
			team2numbers = team2numbers + empire.getNumberOfOnlinePlayers();
		}
		for (Empire empire : getAllEmpiresOnTeam1()) {
			empire.broadcastMessage("" + killsforwin);
			//empire.broadcastMessage("DEBUG 2.1");
			Scoreboard sb = sbm.getNewScoreboard();
			
			Objective you = sb.registerNewObjective("you", "stats");
			you.setDisplaySlot(DisplaySlot.SIDEBAR);
			//you.setDisplayName(ChatColor.GOLD + "====" + ChatColor.DARK_GREEN + "Allies" + ChatColor.GOLD + "====");
			
			Score yous;
			if (type == BattleType.DEATHMATCH) {
				you.setDisplayName(ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "DeathMatch" + ChatColor.GOLD + "]");
				//Score space = you.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "0=================="));
				//space.setScore(1);
				Score title1 = you.getScore(Bukkit.getOfflinePlayer(/*ChatColor.RED + "0" +*/ ChatColor.DARK_GREEN  + "== Allies =="));
				title1.setScore(team1numbers);
				yous = you.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN +"Kills / " + killsforwin + ":"));
				yous.setScore(team1points);
				//Score space2 = you.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "0=================="));
				//space2.setScore(1);
				Score title2 = you.getScore(Bukkit.getOfflinePlayer((/*ChatColor.RED + "0" +*/ChatColor.DARK_RED + "== Enemies ==")));
				title2.setScore(team2numbers);
				Score thems = you.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_RED +"Kills / " + killsforwin + ":"));
				thems.setScore(team2points);
				//empire.broadcastMessage("DEBUG 3.1");
				//Team team = sb.registerNewTeam("allies"); 
			}else if (type == BattleType.OBLITERATION) {
				//TODO
			}
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				
				if (empire.hasPlayer(player)) {
					you.setDisplaySlot(DisplaySlot.SIDEBAR);
					//player.sendMessage("DEBUG 4.1");
					player.setScoreboard(sb);
				}
			}
		}
		for (Empire empire : getAllEmpiresOnTeam2()) {
			empire.broadcastMessage("" + killsforwin);
			//empire.broadcastMessage("DEBUG 2.2");
			Scoreboard sb = sbm.getNewScoreboard();
			
			Objective you = sb.registerNewObjective("you", "stats");
			you.setDisplaySlot(DisplaySlot.SIDEBAR);
			//you.setDisplayName(ChatColor.GOLD + "====" + ChatColor.DARK_GREEN + "Allies" + ChatColor.GOLD + "====");

			Score yous;
			if (type == BattleType.DEATHMATCH) {
				you.setDisplayName(ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "DeathMatch" + ChatColor.GOLD + "]");
				//Score space = you.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "0=================="));
				//space.setScore(1);
				Score title1 = you.getScore(Bukkit.getOfflinePlayer(/*ChatColor.RED + "0" +*/ ChatColor.DARK_GREEN  + "== Allies =="));
				title1.setScore(team2numbers);
				yous = you.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN +"Kills / " + killsforwin + ":"));
				yous.setScore(team2points);
				//Score space2 = you.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "0=================="));
				//space2.setScore(1);
				Score title2 = you.getScore(Bukkit.getOfflinePlayer((/*ChatColor.RED + "0" +*/ChatColor.DARK_RED + "== Enemies ==")));
				title2.setScore(team1numbers);
				Score thems = you.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_RED +"Kills / " + killsforwin + ":"));
				thems.setScore(team1points);
				//empire.broadcastMessage("DEBUG 3.1");
				//Team team = sb.registerNewTeam("allies"); 
			}else if (type == BattleType.OBLITERATION) {
				//TODO
			}
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (empire.hasPlayer(player)) {
					//player.sendMessage("DEBUG 4.1");
					player.setScoreboard(sb);
				}
			}
		}
	}
	public void wipeScoreboards() {
		final ScoreboardManager sbm = Bukkit.getScoreboardManager();
		for (Empire empire : getAllEmpires()) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (empire.hasPlayer(player)) {
					player.setScoreboard(sbm.getNewScoreboard());
					BarAPI.removeBar(player);
				}
			}
		}
	}
	public void upDateTimer(Player player) {
		long l1  = start + time;
		long l2 = l1 - System.currentTimeMillis();
		double d1 = l2 * 100;
		double d2 = d1 / time;
		float f1 = (float) d2;
		/*player.sendMessage("Start: " + start);
		player.sendMessage("Curernt: " + System.currentTimeMillis());
		player.sendMessage("Time Total: " + time );
		player.sendMessage("l1:"+ l1);
		player.sendMessage("l2: " + l2);
		player.sendMessage("d1: "+ d1);
		player.sendMessage("d2: " + d2);
		player.sendMessage("f1: "+ f1);*/
		if (f1 >= 0 )  {
			if (type == BattleType.DEATHMATCH) {
				BarAPI.setMessage(player, ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "DeathMatch Timer" + ChatColor.GOLD + "]", f1);
			}else if (type == BattleType.OBLITERATION) {
				BarAPI.setMessage(player, ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "Obliteration Timer" + ChatColor.GOLD + "]", f1);
			}
		}
	}
	public void scheduleTimer() {
		Plugin plugin2 = Bukkit.getServer().getPluginManager().getPlugin("Empires");
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin2, new Runnable() {

			@Override
			public void run() {
				if (isOnGoing()) {
					long l1  = start + time;
					long l2 = l1 - System.currentTimeMillis();
					if (l2 >= 0) {
						for (Empire empire : getAllEmpires()) {
							for (EPlayer player : empire.getOnlinePlayers().values()) {
								upDateTimer(player.getPlayer());
							}
						}
					}else {
						//Bukkit.broadcastMessage("String 1");
						end();
					}
				}
				
			}
			
		}, 0L, 200L);
	}
	public int getTeam1Points() {
		return team1points;
	}
	public int getTeam2Points() {
		return team2points;
	}
	public int getKillsForWin() {
		return killsforwin;
	}
	public int getDamageForWin() {
		return damageforwin;
	}
	public BattleTeam getAttackingTeam(){
		return attacker;
	}
	private Empire getLooser() {
		if (victor == empire1) return empire2;
		if (victor == empire2) return empire1;
		return null;
	}
}
