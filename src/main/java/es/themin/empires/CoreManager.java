package es.themin.empires;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.Empire;

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
	
	public boolean hasCore(Core c){
		if (cores.contains(c)) return true;
		else return false;
	}
	public void ac(Core c) {
		cores.add(c);
	}
	
	public void addCore(Core c) {
		this.cores.add(c);
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
	
	public void Delete(Core myCore) {
		cores.remove(myCore);
//		this.destroy();
//		this.getEmpire().removeCore(this);
//		if (Cores.containsCoreWithId(this.Id)) {
//			int i = Cores.getCores().indexOf(this);
//			Cores.getCores().remove(i);
//		}
		
	}
	
	private  void loadEmpireCores(empires plugin, Empire empire){
		
		//foreach empire in plugin get empires. load cores.
//		List<String> list2 = empiredata.getStringList(empire.getName() + ".cores");
//		for (String s2: list2) {
//			String[] words2 = s2.split(":");
//			int coreID  = Integer.parseInt(words2[0]);
//			
//			CoreType coretype = CoreUtils.GetCoreType(words2[1]);
//			
//			World world2 = Bukkit.getServer().getWorld(words2[2]);
//			int x2 = Integer.parseInt(words2[3]); // - 0:BASE:world:-249:78:223:1:0:kraft
//			int y2 = Integer.parseInt(words2[4]);
//			int z2 = Integer.parseInt(words2[5]);
//			Location location = new Location(world2, x2, y2, z2);
//			int level = Integer.parseInt(words2[6]);
//			Core core = new Core(myPlugin, coreID, coretype, location, level, empire);
//		    //core.build();
//		    //Worlds.getWorlds().get(world2.getUID()).addCore(core);
//			plugin.Cores.getCores().add(core);
//			empire.ac(core);
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

}
