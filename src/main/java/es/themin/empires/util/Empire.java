
package es.themin.empires.util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Empire {
	
//Important

//For use in this class	
	private int Id;
	private String name;
	private ArrayList<String> players = new ArrayList<String>();
	private ArrayList<Core> cores = new ArrayList<Core>();

	public Empire(int Id, String name){
		this.Id = Id;
		this.name = name;
	}
	
	public int getId(){
		return Id;
	}
	public void setId(int Id){
		if (UtilityHashMaps.empires.contains(this)) UtilityHashMaps.empires.remove(this);
		this.Id = Id;
		UtilityHashMaps.empires.add(this);
	}
	public String getName(){
		return name;
	}
	public ArrayList<String> getPlayers(){
		return players;
	}
	public boolean hasPlayer(Player p) {
		if (players.contains(p)) return true;
		else return false;
	}
	public void addPlayer(String p){
		if (UtilityHashMaps.empires.contains(this)) UtilityHashMaps.empires.remove(this);
		for (Empire emp : UtilityHashMaps.empires) {
			if (emp.getPlayers().contains(p)) {
				emp.removePlayer(p);
			}
		}
		players.add(p);
		EmpirePlayer ep = UtilityHashMaps.empireplayers.get(p);
		ep.setEmpire(this);
		Bukkit.getServer().broadcastMessage("set");
		UtilityHashMaps.empireplayers.put(p, ep);
		UtilityHashMaps.empires.add(this);
	}
	public void removePlayer(String p) {
		if (UtilityHashMaps.empires.contains(this)) UtilityHashMaps.empires.remove(this);
		players.remove(p);
		UtilityHashMaps.empires.add(this);
	}
	public void setName(String name){
		if (UtilityHashMaps.empires.contains(this)) UtilityHashMaps.empires.remove(this);
		this.name = name;
		UtilityHashMaps.empires.add(this);
	}
	public ArrayList<Core> getCores(){
		return cores;
	}
	public boolean hasCore(Core c){
		if (cores.contains(c)) return true;
		else return false;
	}
	public void addCore(Core c) {
		if (UtilityHashMaps.empires.contains(this)) UtilityHashMaps.empires.remove(this);
		cores.add(c);
		UtilityHashMaps.empires.add(this);
	}
	public void removeCore(Core c){
		if (UtilityHashMaps.empires.contains(this)) UtilityHashMaps.empires.remove(this);
		cores.remove(c);
		UtilityHashMaps.empires.add(this);
	}
	public int numberOfCores(){
		int i = cores.size();
		return i;
	}
	public int numberOfPlayers(){
		int i = players.size();
		return i;
	}
}





