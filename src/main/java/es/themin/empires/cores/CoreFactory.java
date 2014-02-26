package es.themin.empires.cores;

import java.util.UUID;

import org.bukkit.Location;

import es.themin.empires.Debug;
import es.themin.empires.cores.coretypes.BaseCore;
import es.themin.empires.cores.coretypes.CellCore;
import es.themin.empires.cores.coretypes.GriefCore;
import es.themin.empires.enums.CoreType;

public class CoreFactory {

	
	public static ICore CreateCore(UUID empireUUID, Location myLocation, CoreType myCoreType){
		
		Integer xInteger = myLocation.getBlockX();
		Integer yInteger = myLocation.getBlockY();
		Integer zInteger = myLocation.getBlockZ();
		
		Location myLocation2 = new Location(myLocation.getWorld(), xInteger, yInteger, zInteger);
		
		ICore myCore = null;
		
		
		switch (myCoreType) {
		case BASE:
			myCore = new BaseCore( empireUUID,  myLocation2);
			break;
		case GRIEF:
			myCore = new GriefCore( empireUUID,  myLocation2);
			break;
		case CELL:
			myCore = new CellCore( empireUUID,  myLocation2);
			break;
		default:
			
		}
		myCore.setUUID(UUID.randomUUID());
		Debug.Console("new core uuid: "+myCore.getUUID());
		return myCore;
	}
}
