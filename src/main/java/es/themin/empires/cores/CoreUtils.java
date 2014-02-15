package es.themin.empires.cores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import es.themin.empires.CoreManager;
import es.themin.empires.empires;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpireState;
import es.themin.empires.enums.PlaceType;
import es.themin.empires.managers.PlayerManager;
import es.themin.empires.managers.WorldManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.EWorld;
import es.themin.empires.util.Empire;
import es.themin.empires.util.EmpireUtils;
import es.themin.empires.util.PlayerUtils;
import es.themin.empires.util.UtilManager;

public class CoreUtils {
	
	private  empires myPlugin;
	private WorldManager Worlds;
	private CoreManager Cores;
	private PlayerManager Players;
	
	public CoreUtils(empires plugin){
		myPlugin = plugin;	
		Worlds = plugin.Worlds;
		Players = plugin.Players;
	}

	public static CoreType GetCoreType(String coreType){
		
		coreType = coreType.toUpperCase();
		
		return CoreType.valueOf(coreType);
		
	}
	
	public Core placeCore(Player myPlayer, CoreType myCoreType){
		Core myCore = null;
		EPlayer myCorePlayer = Players.getPlayer(myPlayer.getUniqueId());
		
		Empire myEmpire = myCorePlayer.getEmpire();
		
		//check they are in an empire
		if (myEmpire == null){
			myPlayer.sendMessage("You cannot create a core unless you are in an empire");
			return null;
		}
		
		//check the empire is in a state where it can expand
		if (myEmpire.getEmpireState() != EmpireState.BATTLEREADY){
			if (myEmpire.getEmpireState() == EmpireState.ATWAR){
				myPlayer.sendMessage("You cannot expand your empire while you are at war!");
				return null;
			} else if (myEmpire.getEmpireState() == EmpireState.REBUILDING){
				myPlayer.sendMessage("You cannot expand your empire until it has rebuilt!");
				return null;
			} 
		}
		
		//check that they have permission to place this core
		if (!PlayerUtils.PlayerCanPlaceCore(myPlayer, myCoreType)){
			myPlayer.sendMessage("You don't not have permission to place this core");
			return null;
		}
			
		//check if the empire has any cores of this type to create
		if (!EmpireUtils.empireCanAfford(myCoreType)){
			myPlayer.sendMessage("Your empire has no cores of that type to place");	
			return null;
		}
		
		//create the core
		 myCore = new Core(myCorePlayer, myCoreType);
				
		
		//check if the core can physically be placed here
		if (canPlace(myPlayer, myCore) == false){
			return null;
		}
		
		//we're good to go. Give the core an id and add it to the empire
		myCore.setId(Cores.nextUnusedCoreId());
		myEmpire.addCore(myCore);
		
		UUID myUUID = myCore.getLocation().getWorld().getUID();
		EWorld myCoreWorld = Worlds.getWorlds().get(myUUID);
		myCoreWorld.addCore(myCore);
		
		return myCore;
	}
	
	public boolean canPlace(Player myPlayer, Core myCore){
		//needs a complete re write for new system.
		ArrayList<Integer> nearbyCores = new ArrayList<Integer>();
		
		UUID myUUID = myCore.getLocation().getWorld().getUID();
		EWorld myCoreWorld = Worlds.getWorlds().get(myUUID);
		
		// check if its too close to another empire
		if (myCore.getPlaceType() == PlaceType.OUTSIDE || myCore.getPlaceType() == PlaceType.EDGE){
			if (myCoreWorld.isNearEnemyCore(myCore)){
				myPlayer.sendMessage("You cannot expand this near to an enemy empire");
				return false;
			}
		}
		
		if (myCore.getPlaceType() == PlaceType.EDGE){
			if (!myCoreWorld.isEdgeOfEmpire(myCore)){
				myPlayer.sendMessage("Amps must be placed on the edge of your empire");
				return false;
			}
		}
		
		
		if (myCore.getPlaceType() == PlaceType.INSIDE){
			if (!myCoreWorld.isInsideEmpire(myCore)){
				myPlayer.sendMessage("Cannot place outside of empire");
				return false;
			}
		}
		
		//we need to check that the cores themselves dont overlap
		if (coresOverlap(myCoreWorld, myCore )){
			myPlayer.sendMessage("Cores cannot overlap!");
		}
		
		// i think we're done??!
		
		return true;
	}
	
	public static boolean coresOverlap(EWorld myCoreWorld, Core myCore){
		HashMap<Integer, Core> myCores = myCoreWorld.getFriendlyCoresInGrid(myCore.getEmpire(), myCore.getLocation());
		
		if (myCores == null){
			return false;
		}
		
		int coreSize = myCore.getCoreSize();
		int c1x1 = myCore.getLocation().getBlockX()-coreSize;
		int c1x2 = myCore.getLocation().getBlockX()+coreSize;
		int c1z1 = myCore.getLocation().getBlockZ()-coreSize;
		int c1z2 = myCore.getLocation().getBlockZ()+coreSize;
		
		for(Core friendlyCore : myCores.values()){
			
			int coreSize2 = myCore.getCoreSize();
			int c2x1 = myCore.getLocation().getBlockX()-coreSize2;
			int c2x2 = myCore.getLocation().getBlockX()+coreSize2;
			int c2z1 = myCore.getLocation().getBlockZ()-coreSize2;
			int c2z2 = myCore.getLocation().getBlockZ()+coreSize2;
			
			if(!(c1x2 < c2x1 || c1x1 > c2x2 || c1z2 < c2z1 || c1z1 > c2z2)){
				return true;
			}
		}
		return false;
	}
}
































