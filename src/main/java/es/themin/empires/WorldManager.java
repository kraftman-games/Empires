package es.themin.empires;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import es.themin.empires.util.CoreWorld;

public class WorldManager {

	private empires myPlugin;
	private HashMap<UUID,CoreWorld> worlds = new HashMap<UUID,CoreWorld>();
	
	public WorldManager(empires plugin) {
		myPlugin = plugin;
		
		List<World> myWorlds = Bukkit.getServer().getWorlds();
		
		for(World myWorld : myWorlds){
			worlds.put(myWorld.getUID(), new CoreWorld());
		}
	}

	
	public HashMap<UUID, CoreWorld> getWorlds() {
		return worlds;
	}
}
