package es.themin.empires;

import java.util.ArrayList;

import org.bukkit.Location;

import es.themin.empires.cores.Core;

public class CoreManager {

	private empires myPlugin;
	private ArrayList<Core> cores = new ArrayList<Core>();
	
	
	
	public CoreManager(empires plugin) {
		myPlugin = plugin;
	}

	public ArrayList<Core> getCores() {
		return cores;
	}
	
	public  Core getCoreWithId(int Id) {

		for (Core core : this.cores) {
			if (core.getId() == Id) return core;
		}
		return null;
	}
	public  Core getCoreWithLocation(Location l) {
		for (Core core : this.cores) {
			if (core.getLocation() == l) return core;
		}
		return null;
	}
	public  boolean containsCoreWithId(int Id) {
		for (Core core : this.cores) {
			if (core.getId() == Id) return true;
		}
		return false;
	}
	public  int nextUnusedCoreId(){
		int i = 0;
		while (getCoreWithId(i) != null){
			i++;
		}
		return i;
	}

}
