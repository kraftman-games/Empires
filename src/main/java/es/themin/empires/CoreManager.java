package es.themin.empires;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;
import es.themin.empires.managers.IManager;
import es.themin.empires.util.Empire;

public class CoreManager implements IManager{

	private HashMap<UUID,Core> cores = new HashMap<UUID,Core>();
	private EmpiresDAL empiresDAL = null;
	
	
	public CoreManager(EmpiresDAL myEmpiresDAL, HashMap<UUID, Core> myCores) {
		empiresDAL = myEmpiresDAL;
		cores = myCores;
	}

	public HashMap<UUID,Core> getCores() {
		return cores;
	}
	
	public  Core getCoreWithId(UUID ID) {
		return cores.get(ID);
	}
	public  Core getCoreWithLocation(Location l) {
		for (Core core : cores.values()) {
			if (core.getLocation() == l) return core;
		}
		return null;
	}
	public  boolean containsCoreWithID(UUID ID) {
		return cores.get(ID) != null ? true : false;
	}
	
	
	
	public void addCore(Core c) {
		this.cores.put(c.getUUID(), c);
		//myempiresDAL save
	}
	
	public void removeCore(Core c){
		cores.remove(c);
	}
	public int numberOfCores(){
		int i = cores.size();
		return i;
	}
	
	public int getExp() {
		int xp = 0;
//		xp = this.numberOfPlayers() * 5;
//		for (Core core : cores) {
//			xp = xp + core.getLevel() * 2;
//		}
		//xp = xp + this.numberOfAmplifiers() * 2;
		return xp;
	}	
	
	public Core getCoreOfType(CoreType type) {
		for (Core core : cores.values()) {
			if (core.getType() == type) {
				return core;
			}
		}
		return null;
	}
	public boolean hasCoreOfType(CoreType type) {
		for (Core core : cores.values()) {
			if (core.getType() == type) {
				return true;
			}
		}
		return false;
	}
	
	public void Delete(Core myCore) {
		cores.remove(myCore);
//		this.destroy();
//		this.getEmpire().removeCore(this);
//		if (Cores.containsCoreWithId(this.Id)) {
//			int i = Cores.getCores().indexOf(this);
//			Cores.getCores().remove(i);
//		}
		
	}
	


	public ArrayList<Core> getEmpireCores(Empire empire) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<Core> getEmpireCores(UUID empireUUID) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getCoreCount(Empire empire) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean empireHasCoreOfType(Empire empire, CoreType base) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object empireGetCoreOfType(Empire empire, CoreType base) {
		// TODO Auto-generated method stub
		return null;
	}

	public ChatColor numberOfCores(Empire empire) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save() {
		empiresDAL.createOrUpdateCores(cores);
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		
	}

}
