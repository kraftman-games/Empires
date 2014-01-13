package es.themin.empires.util;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Empire {
	
//Important
	public ArrayList<Empire> empires = new ArrayList<Empire>();
//For use in this class	
	private int Id;
	private String name;
	private ArrayList<Player> players = new ArrayList<Player>();

	
	
	public Empire(int Id){
		this.Id = Id;
	}
	
	public int getId(){
		return Id;
	}
	
	public String getName(){
		return name;
	}
	public ArrayList<Player> getPlayers(){
		return players;
	}
	public boolean containsPlayer(Player p) {
		if (players.contains(p)) return true;
		else return false;
	}
	public void addPlayer(Player p){
		players.add(p);
	}
	public void removePlayer(Player p) {
		players.remove(p);
	}
}
