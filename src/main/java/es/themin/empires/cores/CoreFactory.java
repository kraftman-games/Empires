package es.themin.empires.cores;

import java.util.UUID;

import org.bukkit.Location;

import es.themin.empires.cores.coretypes.BaseCore;
import es.themin.empires.cores.coretypes.CellCore;
import es.themin.empires.cores.coretypes.GriefCore;
import es.themin.empires.enums.CoreType;

public class CoreFactory {

	
	public static Core CreateCore(UUID empireUUID, Location myLocation, CoreType myCoreType){
		
		Integer xInteger = myLocation.getBlockX();
		Integer yInteger = myLocation.getBlockY();
		Integer zInteger = myLocation.getBlockZ();
		
		Location myLocation2 = new Location(myLocation.getWorld(), xInteger, yInteger, zInteger);
		
		Core myCore = null;
		
		
		switch (myCoreType) {
		case BASE:
			myCore = new BaseCore( empireUUID,  myLocation2);
		case GRIEF:
			myCore = new GriefCore( empireUUID,  myLocation2);
		case CELL:
			myCore = new CellCore( empireUUID,  myLocation2);
		default:
			
		}
		myCore.setUUID(UUID.randomUUID());
		
		return myCore;
	}
}
