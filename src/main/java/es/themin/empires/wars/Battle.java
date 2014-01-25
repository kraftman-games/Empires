package es.themin.empires.wars;

import java.util.ArrayList;

import es.themin.empires.enums.BattleType;
import es.themin.empires.util.Empire;

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
	}
	public void end() {
		this.onGoing = false;
		if (team1points > team2points) this.victor = empire1; this.endsinatie = false; war.addWinsToTeam1(1);
		if (team2points > team1points) this.victor = empire2; this.endsinatie = false; war.addWinsToTeam2(1);
		if (team1points == team2points) this.victor = null; this.endsinatie = true;
		this.end = System.currentTimeMillis();
	}
	public boolean endInATie() {
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
	public void addPointsToTeam2(int points) {
		this.team1points = this.team1points + points;
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
}
