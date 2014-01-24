
package es.themin.empires.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;

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

	
	public Empire getEnemyEmpire() {
		return enemyEmpire;
	}

	public void setEnemyEmpire(Empire enemyEmpire) {
		this.enemyEmpire = enemyEmpire;
	}

	public boolean isAtWar() {
		return atWar;
	}

	public void setAtWar(boolean atWar) {
		this.atWar = atWar;
	}

	public Empire(int Id, String name, String owner){
		this.Id = Id;
		this.name = name;
		this.owner = owner;
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

	public boolean isProtected() {
		return isProtected;
	}

	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
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
				} else {
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
	public void startWar(Empire eventPlayerEmpire) {
		this.setAtWar(true);
		eventPlayerEmpire.setAtWar(true);
		this.setEnemyEmpire(eventPlayerEmpire);
		eventPlayerEmpire.setEnemyEmpire(eventPlayerEmpire);
	}
	
	public void endWar(Empire eventPlayerEmpire){
		this.setAtWar(false);
		eventPlayerEmpire.setAtWar(false);
		this.setEnemyEmpire(null);
		eventPlayerEmpire.setEnemyEmpire(null);
	}
	
	public boolean playerHasARank(String player) {
		for (Rank rank : ranks) {
			if (rank.getPlayers().contains(player)) return true;
		}
		return false;
	}
}





