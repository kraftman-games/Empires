
package es.themin.empires.util;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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

	public Empire(int Id, String name, String owner){
		this.Id = Id;
		this.name = name;
		this.owner = owner;
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
	public ArrayList<Amplifier> geAmplifiers(){
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
	public boolean containsRankWithName(String name) {
		for (Rank r : ranks) {
			if (r.getName() == name) {
				return true;
			}
		}
		return false;
	}
	public boolean containsRankWithWeight(int weight) {
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
	
}





