package es.themin.empires.managers;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.defaults.SaveCommand;

import com.mysql.jdbc.jmx.LoadBalanceConnectionGroupManager;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.util.EWorld;
import es.themin.empires.util.testing.newemp;

public class WorldManager implements IManager{

	private HashMap<UUID,EWorld> worlds = new HashMap<UUID,EWorld>();
	private EmpiresDAL myEmpiresDAL = null;
	
	public WorldManager(EmpiresDAL empireDAL, HashMap<UUID, EWorld> myWorlds) {
		myEmpiresDAL = empireDAL;
		
		worlds = myWorlds;
		
		
	}
	
	public void load(){
		worlds = myEmpiresDAL.loadWorlds();
		List<World> myWorlds = Bukkit.getServer().getWorlds();
		for(World myWorld : myWorlds){
			if (worlds.get(myWorld.getUID()) == null){
				EWorld myEWorld = new EWorld(myWorld.getUID());
				myEWorld.setWorld(myWorld);
				myEWorld.setName(myWorld.getName());
				myEmpiresDAL.createOrUpdateWorld(myEWorld);
			}
		}
	}
	
	public void save(){
		Bukkit.getServer().getLogger().info("saving world");
	}

	
	public HashMap<UUID, EWorld> getWorlds() {
		return worlds;
	}
	public EWorld loadEWorld(World myWorld) {
		if (this.worlds.containsKey(myWorld.getUID())) {
			return worlds.get(myWorld.getUID());
		}
		EWorld ew = new EWorld(myWorld.getUID());
		ew.setName(myWorld.getName());
		ew.setWorld(myWorld);
		worlds.put(ew.getUUID(), ew);
		return ew;
	}

	public EWorld getWorld(UUID uid) {
		return worlds.get(uid);
	}
}
