
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
	public void addCore(Core c) {
		cores.add(c);
		Save();
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
	public void Save(){
		if (UtilityHashMaps.containsEmpireWithId(this.Id)) {
			int i = UtilityHashMaps.empires.indexOf(UtilityHashMaps.getEmpireWithId(this.Id));
			UtilityHashMaps.empires.remove(i);
		}
		UtilityHashMaps.empires.add(this);
	}
}





