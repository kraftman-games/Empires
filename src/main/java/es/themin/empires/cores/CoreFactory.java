package es.themin.empires.cores;

import java.util.UUID;

import org.bukkit.Location;

import es.themin.empires.cores.coretypes.BaseCore;
import es.themin.empires.cores.coretypes.GriefCore;
import es.themin.empires.enums.CoreType;

public class CoreFactory {

	
	public static Core CreateCore(UUID empireUUID, Location myLocation, CoreType myCoreType){
		
		switch (myCoreType) {
		case BASE:
			return new BaseCore( empireUUID,  myLocation);
		case GRIEF:
			return new GriefCore( empireUUID,  myLocation);
		default:
			return null;
		}
	}
	
	
}
