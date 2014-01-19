package es.themin.empires.util;

import java.util.ArrayList;

import es.themin.empires.enums.EmpirePermission;

public class Rank {
	
	private int weight;
	private String name;
	private ArrayList<String> players = new ArrayList<String>();
	private ArrayList<EmpirePermission> permissions = new ArrayList<EmpirePermission>();
	public Rank(int weight, String name) {
		this.weight = weight;
		this.name = name;
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
	public ArrayList<String> getPlayers() {
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
}
