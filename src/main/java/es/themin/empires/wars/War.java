package es.themin.empires.wars;

import java.util.ArrayList;

import es.themin.empires.util.Empire;

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
	}
	public void start() {
		this.onGoing = true;
		this.start = System.currentTimeMillis();
	}
	public void end() {
		this.onGoing = false;
		this.end = System.currentTimeMillis();
		if (empire1wins > empire2wins) this.victor = empire1; this.endedintie = false;
		if (empire2wins > empire1wins) this.victor = empire1; this.endedintie = false;
		if (empire1wins == empire2wins) this.victor = null; this.endedintie = true;
	}
	public void endWithVictor(Empire empire) {
		this.onGoing = false;
		this.end = System.currentTimeMillis();
		this.victor = empire;
		this.endedintie = false;
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
	}
	public Empire getVictor() {
		return this.victor;
	}
	public long getEnd() {
		return this.end;
	}
	public void setTeam1Wins(int wins) {
		this.empire1wins = wins;
	}
	public void setTeam2Wins(int wins) {
		this.empire2wins = wins;
	}
	public void addWinsToTeam1(int wins) {
		this.empire1wins = this.empire1wins + wins;
	}
	public void addWinsToTeam2(int wins) {
		this.empire2wins = this.empire2wins + wins;
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
	
}
