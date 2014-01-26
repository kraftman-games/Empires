
package es.themin.empires.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpireState;
import es.themin.empires.wars.Battle;
import es.themin.empires.wars.War;

public class Empire {
	
//Important

//For use in this class	
	private int Id;
	private String name;
	private String owner;
	private ArrayList<String> players = new ArrayList<String>();
	private ArrayList<Core> cores = new ArrayList<Core>();
	private ArrayList<Amplifier> amps = new ArrayList<Amplifier>();
	private ArrayList<Rank> ranks = new ArrayList<Rank>();
	private boolean isProtected;
	private String ownerprefix;
	private String defaultprefix;
	private boolean atWar;
	private Empire enemyEmpire;
	private boolean vunerable;
	private EmpireState empireState;
	private int warwins;
	private int warlosses;
	private int battlewins;
	private int battlelosses;
	private ArrayList<War> wars;
	private ArrayList<Empire> allies;

	
	public Empire getEnemyEmpire() {
		return enemyEmpire;
	}

	public void setEnemyEmpire(Empire enemyEmpire) {
		this.enemyEmpire = enemyEmpire;
	}

	

	public Empire(int Id, String empireName, String ownerName){
		if (UtilManager.getCoreWithId(Id) != null){
			throw new IllegalArgumentException("Empire with this ID already exists");
		}
		
		if (empireName == null || empireName.trim().length() < 1){
			throw new IllegalArgumentException("No empire name");
		}
		
		if (ownerName == null || ownerName.trim().length() < 1){
			throw new IllegalArgumentException("No player name provided");
		}
		
		if (UtilManager.empireplayers.containsKey(ownerName)){
			throw new IllegalArgumentException("Player already in empire");
		}
		
		
		this.Id = Id;
		this.name = empireName;
		this.owner = ownerName;
		this.atWar =false;
		this.warwins = 0;
		this.warlosses = 0;
		this.battlelosses = 0;
		this.battlewins = 0;
		this.wars = new ArrayList<War>();
		this.setProtected(true);
		UtilManager.empires.add(this);
		this.addPlayer(ownerName);
		UtilManager.empireplayers.put(ownerName, this);
		this.allies = new ArrayList<Empire>();
	}
	
	public Empire(String empireName, Player myPlayer){
		if (empireName == null || empireName.trim().length() < 1){
			myPlayer.sendMessage("your empire must have a name");
			throw new IllegalArgumentException("No empire name");
		}
		
		if (UtilManager.getEmpireWithName(empireName)!= null){
			myPlayer.sendMessage("Empire name already exists");
			throw new IllegalArgumentException("Empire already exists");
		}
		
		if (UtilManager.empireplayers.containsKey(myPlayer.getName())){
			myPlayer.sendMessage("You are already in an empire");
			throw new IllegalArgumentException("Player already in empire");
		}
		
		UtilManager.empires.add(this);
		this.addPlayer(myPlayer.getName());
		UtilManager.empireplayers.put(myPlayer.getName(), this);
		this.Id = UtilManager.nextUnusedCoreId();
		this.name = empireName;
		this.owner = myPlayer.getName();
		this.setProtected(true);
	}
	
	public int getId(){
		return Id;
	}
	public String getName(){
		return name;
	}
	public String getOwner(){
		return owner;
	}
	public void setOwner(String owner){
		this.owner = owner;
		Save();
	}
	public ArrayList<String> getPlayers(){
		return players;
	}
	public ArrayList<Amplifier> getAmplifiers(){
		return amps;
	}
	public boolean hasPlayer(String p) {
		if (players.contains(p)) return true;
		else return false;
	}
	public void addPlayer(String p){
		for (Empire emp : UtilManager.empires) {
			if (emp.getPlayers().contains(p)) {
				emp.removePlayer(p);
			}
		}

		players.add(p);
		Save();
		
	}
	public void removePlayer(String p) {
		players.remove(p);
	}
	public void setName(String name){
		this.name = name;
	}
	public ArrayList<Core> getCores(){
		return cores;
	}
	public boolean hasCore(Core c){
		if (cores.contains(c)) return true;
		else return false;
	}
	public void ac(Core c) {
		cores.add(c);
		Save();
	}
	public void addCore(Core c) {
		c.setEmpire(this);
	}
	public void removeCore(Core c){
		cores.remove(c);
		Save();
	}
	public int numberOfCores(){
		int i = cores.size();
		return i;
	}
	public int numberOfPlayers(){
		int i = players.size();
		return i;
	}
	public void aa(Amplifier a) {
		amps.add(a);
		Save();
	}
	public void addAmplifier(Amplifier a) {
		a.setEmpire(this);
	}
	public void removeAmplifier(Amplifier a){
		amps.remove(a);
		Save();
	}
	public void Save(){
		if (UtilManager.containsEmpireWithId(this.Id)) {
			int i = UtilManager.empires.indexOf(UtilManager.getEmpireWithId(this.Id));
			UtilManager.empires.remove(i);
		}
		UtilManager.empires.add(this);
	}
	public Core getCoreOfType(CoreType type) {
		for (Core core : cores) {
			if (core.getType() == type) {
				return core;
			}
		}
		return null;
	}
	public boolean hasCoreOfType(CoreType type) {
		for (Core core : cores) {
			if (core.getType() == type) {
				return true;
			}
		}
		return false;
	}
	public ArrayList<Rank> getRanks(){
		return ranks;
	}
	public void addRank(Rank rank) {
		ranks.add(rank);
		Save();
	}
	public void removeRank(Rank rank) {
		ranks.remove(rank);
		Save();
	}
	public boolean hasRankWithName(String name) {
		for (Rank r : ranks) {
			if (r.getName() == name) {
				return true;
			}
		}
		return false;
	}
	public boolean hasRankWithNameAp(String name) {
		for (Rank r : ranks) {
			if (r.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	public boolean hasRankWithNameAp2(String name, Player player) {

		for (Rank r : ranks) {
			player.sendMessage("|" + r.getName() + "|" + name + "|");
			if (r.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	public boolean hasRankWithWeight(int weight) {
		for (Rank r : ranks) {
			if (r.getWeight() == weight) {
				return true;
			}
		}
		return false;
	}
	public Rank getRankOfPlayer(String playername) {
		for (Rank rank : ranks) {
			if (rank.getPlayers().contains(playername)) return rank;
		}
		return null;
	}
	public void setRankOfPlayer(String playername, Rank rank) {
		for (Rank rank2 : ranks) {
			rank2.removePlayer(playername);
		}
		rank.addPlayer(playername);
	}
	public int numberOfAmplifiers() {
		int i = 0;
		for (Amplifier amp : amps) {
			i++;
		}
		return i;
	}
	public int getRanking() {
		int xp = 0;
		int rank = 1;
		int total = 1;
		int pos = 1;
		xp = this.numberOfPlayers() * 5;
		for (Core core : cores) {
			xp = xp + core.getLevel() * 2;
		}
		xp = xp + this.numberOfAmplifiers() * 2;
		for (Empire empire : UtilManager.empires) {
			int xp2;
			xp2 = empire.numberOfPlayers() * 5;
			for (Core core : empire.getCores()) {
				xp2 = xp2 + core.getLevel() * 2;
			}
			xp2 = xp2 + empire.numberOfAmplifiers() * 2;
			if (xp2 > xp) {
				rank  = rank + pos;
				pos = 1;
			}if (xp2 == xp) {
				pos ++;
			}
		}
		return rank;
	}
	public int getExp() {
		int xp = 0;
		xp = this.numberOfPlayers() * 5;
		for (Core core : cores) {
			xp = xp + core.getLevel() * 2;
		}
		xp = xp + this.numberOfAmplifiers() * 2;
		return xp;
	}
	public Rank getRankWithName(String name) {
		for (Rank rank : ranks) {
			if (rank.getName().equalsIgnoreCase(name))return rank;
		}
		return null;
	}
	public void removePlayerFromRank(String p, Rank r) {
		for (Rank rank : ranks) {
			if (rank.getName() == r.getName()) {
				rank.removePlayer(p);
			}
		}
		Save();
	}


	public void broadcastMessage(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (players.contains(player.getName())) player.sendMessage(message);
		}
	}
	public String getOwnerPrefix() {
		return ownerprefix;
	}
	public String getDefaultPrefix() {
		return defaultprefix;
	}
	public void setOwnerPrefix(String s) {
		this.ownerprefix = s;
		Save();
	}
	public void setDefaultPrefix(String s) {
		this.defaultprefix = s;
		Save();
	}

	public boolean canPlayerAttack(Player myPlayer) {
		Empire playerEmpire = UtilManager.empireplayers.get(myPlayer.getName());
		if (!this.isProtected()){
			if (this.isAtWar()){
				if (playerEmpire == this.getEnemyEmpire()){
					return true;
				}
				else {
					myPlayer.sendMessage("This war is not yours to fight!");
					return false;
				}
				
			} else if (playerEmpire.isProtected){
				myPlayer.sendMessage("You cannot attack an empire until yours is rebuilt!");
				return false;
			}
		} else {
			myPlayer.sendMessage("There is no honor in attack this fallen empire");
			return false;
		}
		return false;
	}
	
	/**
	 * sets up a war between two empires
	 * @param eventPlayerEmpire
	 */
	/*public void startWar(Empire eventPlayerEmpire) {
		this.setAtWar(true);
		eventPlayerEmpire.setAtWar(true);
		this.setEnemyEmpire(eventPlayerEmpire);
		eventPlayerEmpire.setEnemyEmpire(eventPlayerEmpire);
		Save();
	}
	
	public void endWar(Empire eventPlayerEmpire){
		this.setAtWar(false);
		eventPlayerEmpire.setAtWar(false);
		this.setEnemyEmpire(null);
		eventPlayerEmpire.setEnemyEmpire(null);
		Save();
	}*/
	
	public boolean playerHasARank(String player) {
		for (Rank rank : ranks) {
			if (rank.getPlayers().contains(player)) return true;
		}
		return false;
	}

	public EmpireState getEmpireState() {
		return empireState;
	}

	public void setEmpireState(EmpireState empireState) {
		this.empireState = empireState;
	}
	public boolean isProtected() {
		return isProtected;
	}

	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}
	public boolean isAtWar() {
		if (wars.isEmpty()) return false;
		return true;
	}
	/*public void setAtWar(boolean atWar) {
		this.atWar = atWar;
	}*/
	public ArrayList<War> getWar() {
		return wars;
	}
	public void addWar(War war) {
		this.atWar = true;
		this.wars.add(war);
	}
	public void removeWar(War war) {
		this.wars.remove(war);
		if (wars.isEmpty()) this.atWar = false;
	}
	public boolean isInABattle() {
		if (isAtWar()) {
			for (War war : this.wars) {
				if (war.hasBattleOnGoing()) return true;
			}
		}
		return false;
	}
	public Battle getCurrentBattle() {
		if (isAtWar()) {
			for (War war : this.wars) {
				if (war.hasBattleOnGoing()) {
					return war.getOnGoingBattle();
				}
			}
		}
		return null;
	}
	public ArrayList<Empire> getAllies() {
		return allies;
	}
	public void addAlly(Empire empire) {
		allies.add(empire);
	}
	public void removeAlly(Empire empire) {
		allies.remove(empire);
	}
	public boolean hasAllies() {
		if (!(allies.isEmpty())) return true;
		return false;
	}
	public boolean isAlliedWith(Empire empire) {
		if (allies.contains(empire)) return true;
		return false;
	}
	public boolean isAtWarWith(Empire empire) {
		if (isAtWar()) {
			for (War war : wars) {
				if (war.getAllEmpiresOnTeam1().contains(this) && war.getAllEmpiresOnTeam2().contains(empire)) return true;
				else if (war.getAllEmpiresOnTeam2().contains(this) && war.getAllEmpiresOnTeam1().contains(empire)) return true;
			}
		}
		return false;
	}
	public War getWarAgainst(Empire empire) {
		if (isAtWar()) {
			for (War war : wars) {
				if (war.getAllEmpiresOnTeam1().contains(this) && war.getAllEmpiresOnTeam2().contains(empire)) return war;
				else if (war.getAllEmpiresOnTeam2().contains(this) && war.getAllEmpiresOnTeam1().contains(empire)) return war;
			}
		}
		return null;
	}
	public boolean isInBattleWith(Empire empire) {
		if (isAtWarWith(empire)) {
			War war = getWarAgainst(empire);
			if (war.hasBattleOnGoing()) {
				Battle battle = war.getOnGoingBattle();
				if (battle.getAllEmpiresOnTeam1().contains(this) && battle.getAllEmpiresOnTeam2().contains(empire)) return true;
				if (battle.getAllEmpiresOnTeam2().contains(this) && battle.getAllEmpiresOnTeam1().contains(empire)) return true;
			}
		}
		return false;
	}
	public int getNumberOfOnlinePlayers(){
		int number = 0;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (players.contains(player.getName())) number++;
		}
		return number;
	}

}





