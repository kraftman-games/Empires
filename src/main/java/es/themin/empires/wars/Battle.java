package es.themin.empires.wars;

import java.util.ArrayList;

import es.themin.empires.enums.BattleType;
import es.themin.empires.util.Empire;
import es.themin.empires.util.SettingsManager;

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
	public Battle(Empire empire1, Empire empire2, War war, BattleType type) {
		this.empire1 = empire1;
		this.empire2 = empire2;
		this.empire1allies = new ArrayList<Empire>();
		this.empire2allies = new ArrayList<Empire>();
		this.war = war;
		this.type = type;
		this.team1points = 0;
		this.team2points = 0;
		this.onGoing = false;
		
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
		}
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
		
	}

	public boolean endedInATie() {
		return endsinatie;
	}
	public void setTeam1Points(int points) {
		this.team1points = points;
	}
	public void setTeam2Points(int points) {
		this.team2points = points;
	}
	public void addPointsToTeam1(int points) {
		this.team1points = this.team1points + points;
	}
	public void addPointsToTeamWithEmpire(Empire empire, int points) {
		if (getAllEmpiresOnTeam1().contains(empire)) {
			this.team1points = this.team1points + points;
		}else if (getAllEmpiresOnTeam2().contains(empire)) {
			this.team2points = this.team2points + points;
		}
	}
	public void addPointsToTeam2(int points) {
		this.team2points = this.team2points + points;
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
	public void CheckForEnd() {
		
	}
}
