
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
	private ArrayList<String> players = new ArrayList<String>();
	private ArrayList<Core> cores = new ArrayList<Core>();
	private ArrayList<Amplifier> amps = new ArrayList<Amplifier>();

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
		EmpirePlayer ep = UtilManager.empireplayers.get(p);
		Bukkit.getServer().broadcastMessage("set");
		UtilManager.empireplayers.put(p, ep);
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
	
}





