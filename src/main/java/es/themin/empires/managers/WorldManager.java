package es.themin.empires.managers;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import es.themin.empires.empires;
import es.themin.empires.util.EWorld;

public class WorldManager {

	private empires myPlugin;
	private HashMap<UUID,EWorld> worlds = new HashMap<UUID,EWorld>();
	
	public WorldManager(empires plugin) {
		myPlugin = plugin;
		
		List<World> myWorlds = Bukkit.getServer().getWorlds();
		
		for(World myWorld : myWorlds){
			worlds.put(myWorld.getUID(), new EWorld());
		}
	}

	
	public HashMap<UUID, EWorld> getWorlds() {
		return worlds;
	}
}
