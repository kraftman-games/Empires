package es.themin.empires.wars;

import java.util.ArrayList;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;

public class War {

	private ArrayList<Empire> empire1allies;
	private ArrayList<Empire> empire2allies;
	private Empire empire1;
	private Empire empire2;
	private Empire victor;
	private long start;
	private long end;
	private int empire1wins;
	private int empire2wins;
	public boolean endedintie;
	private boolean onGoing;
	private Long team1percent;
	private ArrayList<Battle> battles;
	public War(Empire team1, Empire team2) {
		this.empire1 = team1;
		this.empire2 = team2;
		this.empire1allies = new ArrayList<Empire>();
		this.empire2allies = new ArrayList<Empire>();
		this.empire1wins = 0;
		this.empire2wins = 0;
		this.battles = new ArrayList<Battle>();
		this.onGoing = false;
		this.team1percent = (long) 50;
	}
	public void start() {
		this.onGoing = true;
		this.start = System.currentTimeMillis();
		for (Empire empire : getAllEmpires()) {
			empire.addWar(this);
		}
		Save();
	}
	public void end() {
		this.onGoing = false;
		this.end = System.currentTimeMillis();
		if (empire1wins > empire2wins) this.victor = empire1; this.endedintie = false;
		if (empire2wins > empire1wins) this.victor = empire1; this.endedintie = false;
		if (empire1wins == empire2wins) this.victor = null; this.endedintie = true;
		for (Empire empire : getAllEmpires()) {
			empire.removeWar(this);
		}
		Save();
	}
	public void endWithVictor(Empire empire) {
		this.onGoing = false;
		this.end = System.currentTimeMillis();
		this.victor = empire;
		this.endedintie = false;
		Save();
	}
	public Empire getEmpire1(){
		return empire1;
	}
	public Empire getEmpire2(){
		return empire2;
	}
	public long getStart(){
		return start;
	}
	public boolean isOnGoing() {
		return onGoing;
	}
	public boolean endedInTie() {
		return endedintie;
	}
	public void addBattle(Battle battle) {
		if (!(this.battles.contains(battle))) this.battles.add(battle);
		Save();
	}
	public Empire getVictor() {
		return this.victor;
	}
	public long getEnd() {
		return this.end;
	}
	public void setTeam1Wins(int wins) {
		this.empire1wins = wins;
		Save();
	}
	public void setTeam2Wins(int wins) {
		this.empire2wins = wins;
		Save();
	}
	public void addWinsToTeam1(int wins) {
		this.empire1wins = this.empire1wins + wins;
		Save();
	}
	public void addWinsToTeam2(int wins) {
		this.empire2wins = this.empire2wins + wins;
		Save();
	}
	public void addEmpireToTeam1(Empire empire) {
		empire1allies.add(empire);
		Save();
	}
	public void addEmpireToTeam2(Empire empire) {
		empire2allies.add(empire);
		Save();
	}
	public void removeEmpireFromTeam1(Empire empire) {
		empire2allies.add(empire);
		Save();
	}
	public void removeEmpireFromTeam2(Empire empire) {
		empire2allies.remove(empire);
		Save();
	}
	public boolean hasBattleOnGoing() {
		if (hasHadABattle()) {
			for (Battle battle : battles) {
				if (battle.isOnGoing()) return true;
			}
		}
		return false;
	}
	public Battle getOnGoingBattle() {
		if (hasHadABattle()) {
			for (Battle battle : battles) {
				if (battle.isOnGoing()) return battle;
			}
		}
		return null;
	}
	public Battle getLastBattle() {
		if (hasHadABattle()) {
			Battle myBattle = null;
			for (Battle battle : battles) {
				if (myBattle == null) myBattle = battle;
				else if (battle.getEnd() > myBattle.getEnd()) myBattle = battle;
			}
			return myBattle;
		}
		return null;
	}
	public boolean hasHadABattle() {
		if (!(battles.isEmpty())) return true;
		return false;
	}
	public ArrayList<Battle> getBattle(){
		return battles;
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
	public void Save() {
		if (UtilManager.wars.contains(this)) {
			UtilManager.wars.remove(this);
		}
		UtilManager.wars.add(this);
	}
	public void upDateEmpires() {
		for (Empire empire : getAllEmpires()) {
			empire.addWar(this);
		}
	}
	public Long getTeam1Percent() {
		return team1percent;
	}
	public void addTeam1Percent(Long l) {
		this.team1percent = team1percent + l;
		Save();
	}
	public void displayStatistic() {
		for (Empire empire : getAllEmpiresOnTeam1()){
			StringBuilder str = new StringBuilder();
			float fpc = getTeam1Percent();
			if (fpc < 35) {
				str.append(ChatColor.DARK_RED + "");
				str.append(fpc);
				//str.append("%");
			}else if (fpc < 50) {
				str.append(ChatColor.RED + "");
				str.append(fpc);
				//str.append("%");
			}else if (fpc < 65) {
				str.append(ChatColor.GOLD + "");
				str.append(fpc);
				//str.append("%");
			}else if (fpc < 80) {
				str.append(ChatColor.GREEN + "");
				str.append(fpc);
				//str.append("%");
			}else {
				str.append(ChatColor.DARK_GREEN + "");
				str.append(fpc);
				//str.append("%");
			}
			str.append("%");
			for (Player player : empire.getOnlinePlayers()) {
				BarAPI.setMessage(player, ChatColor.DARK_GREEN + "You        " + str.toString() + ChatColor.DARK_RED + empire2.getName(), fpc);
			}
		}for (Empire empire : getAllEmpiresOnTeam2()) {
			StringBuilder str = new StringBuilder();
			float fpc = 100 - getTeam1Percent();
			if (fpc < 35) {
				str.append(ChatColor.DARK_RED + "");
				str.append(fpc);
				//str.append("%");
			}else if (fpc < 50) {
				str.append(ChatColor.RED + "");
				str.append(fpc);
				//str.append("%");
			}else if (fpc < 65) {
				str.append(ChatColor.GOLD + "");
				str.append(fpc);
				//str.append("%");
			}else if (fpc < 80) {
				str.append(ChatColor.GREEN + "");
				str.append(fpc);
				//str.append("%");
			}else {
				str.append(ChatColor.DARK_GREEN + "");
				str.append(fpc);
				//str.append("%");
			}
			str.append("%");
			for (Player player : empire.getOnlinePlayers()) {
				BarAPI.setMessage(player, ChatColor.DARK_GREEN + "You        " + str.toString() + ChatColor.DARK_RED + empire1.getName(), fpc);
			}
		}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getServer().getPluginManager().getPlugin("Empires"), new Runnable() {

			@Override
			public void run() {
				for (Empire empire : getAllEmpires()) {
					for (Player player : empire.getOnlinePlayers()) {
						BarAPI.removeBar(player);
					}
				}
			}
		}, 400L);
	}
}
