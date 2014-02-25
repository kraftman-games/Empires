package es.themin.empires.cores;

import java.util.UUID;

import org.bukkit.Location;

import es.themin.empires.cores.coretypes.BaseCore;
import es.themin.empires.cores.coretypes.GriefCore;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.testing.newemp;

public class CoreFactory {

	
	public static Core CreateCore(UUID empireUUID, Location myLocation, CoreType myCoreType){
		
		Integer xInteger = myLocation.getBlockX();
		Integer yInteger = myLocation.getBlockY();
		Integer zInteger = myLocation.getBlockZ();
		
		Location myLocation2 = new Location(myLocation.getWorld(), xInteger, yInteger, zInteger);
		
		
		switch (myCoreType) {
		case BASE:
			return new BaseCore( empireUUID,  myLocation2);
		case GRIEF:
			return new GriefCore( empireUUID,  myLocation2);
		default:
			return null;
		}
	}
}
