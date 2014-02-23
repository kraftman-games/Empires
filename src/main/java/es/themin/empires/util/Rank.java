package es.themin.empires.util;

import java.util.ArrayList;

import es.themin.empires.enums.EmpirePermission;

public class Rank {
	
	private int weight;
	private String name;
	private Empire empire;
	private String prefix;
	private ArrayList<String> players = new ArrayList<String>();
	private ArrayList<EmpirePermission> permissions = new ArrayList<EmpirePermission>();
	public Rank(int weight, String name, Empire empire, String prefix) {
		this.weight = weight;
		this.name = name;
		this.empire = empire;
		this.prefix = prefix;
	}
	public int getWeight(){
		return weight;
	}
	public String getName(){
		return name;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public void setName(String name){
		this.name = name;
	}
	public ArrayList<String> loadEPlayers() {
		return players;
	}
	public ArrayList<EmpirePermission> getPermissions() {
		return permissions;
	}
	public void addPlayer(String playername) {
		players.add(playername);
	}
	public void removePlayer(String playername) {
		players.remove(playername);
	}
	public void addPermission(EmpirePermission permission) {
		permissions.add(permission);
	}
	public void removePermissions(EmpirePermission permission) {
		permissions.remove(permission);
	}
	public ArrayList<EmpirePermission> getInheritedPermissions() {
		ArrayList<EmpirePermission> inperms = new ArrayList<EmpirePermission>();
		for (Rank rank : empire.getRanks()) {
			if (rank.getWeight() < this.weight) {
				for (EmpirePermission ep : rank.getPermissions()) {
					if (!(inperms.contains(ep))) {
						inperms.add(ep);
					}
				}
			}
		}
		return inperms;
	}
	public boolean hasPermission(EmpirePermission ep) {
		if (permissions.contains(ep)) return true;
		return false;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix){
		this.prefix = prefix;
	}
}
