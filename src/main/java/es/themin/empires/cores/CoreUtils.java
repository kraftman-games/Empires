package es.themin.empires.cores;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpireState;
import es.themin.empires.enums.PlaceType;
import es.themin.empires.util.CoreWorld;
import es.themin.empires.util.Empire;
import es.themin.empires.util.EmpireUtils;
import es.themin.empires.util.PlayerUtils;
import es.themin.empires.util.UtilManager;

public class CoreUtils {

	
	public static Core placeCore(Player myPlayer, CoreType myCoreType){
		Core myCore = null;
		
		Empire myEmpire = UtilManager.getEmpireWithPlayer(myPlayer);
		
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
		 myCore = new Core(myPlayer, myCoreType);
				
		
		//check if the core can physically be placed here
		if (!canPlace(myPlayer, myCore)){
			
		}
		
		
		
		return myCore;
	}
	
	public static boolean canPlace(Player myPlayer, Core myCore){
		//needs a complete re write for new system.
		Location myLocation = myCore.getLocation();
		empires myPlugin = (empires) Bukkit.getPluginManager().getPlugin("Empires");
		
		ArrayList<Integer> nearbyCores = new ArrayList<Integer>();
		
		UUID myUUID = myCore.getLocation().getWorld().getUID();
		CoreWorld myCoreWorld = UtilManager.getWorlds().get(myUUID);
		
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
		
		
		//we need to check that the cores themselves dont overlap
		//and that PlaceType.INSIDE cores are fully inside of the empire
		
		if (myCore.getPlaceType() == PlaceType.INSIDE){
//			if (!myCoreWorld.isInsideEmpire(this)){
//				return false;
//			}
		}
		
				
//		if (this.schematic != null){
//			for (CoreBlock myBlock : this.schematic){
//				Location newLocation = new Location(myLocation.getWorld(), myLocation.getX() + myBlock.getOffsetX(),
//													myLocation.getY() + myBlock.getOffsetY(),
//													myLocation.getZ() + myBlock.getOffsetZ());
//				Block b = newLocation.getBlock();
//				
//				Empire myEmpire = BlockUtils.getEmpireFromBlock(b, myPlugin);
//				if (myEmpire == null){
//					return false;
//				}
//			}
//		}
		return true;
	}
}
