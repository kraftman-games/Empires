package es.themin.empires;

import java.util.ArrayList;

import org.bukkit.Location;

import es.themin.empires.cores.Core;
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

}
