package es.themin.empires;

import java.util.HashMap;
import java.util.UUID;

import es.themin.empires.util.CoreWorld;

public class WorldManager {

	private empires myPlugin;
	private HashMap<UUID,CoreWorld> worlds = new HashMap<UUID,CoreWorld>();
	
	public WorldManager(empires plugin) {
		myPlugin = plugin;
	}

	
	public HashMap<UUID, CoreWorld> getWorlds() {
		return worlds;
	}
}
