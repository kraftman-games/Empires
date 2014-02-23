package es.themin.empires.cores;

import es.themin.empires.cores.coretypes.BaseCore;
import es.themin.empires.cores.coretypes.GriefCore;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.testing.newemp;

public class CoreFactory {

	
	public static Core CreateCore(EPlayer myEPlayer, CoreType myCoreType){
		
		switch (myCoreType) {
		case BASE:
			return new BaseCore(myEPlayer);
		case GRIEF:
			return new GriefCore(myEPlayer);
		default:
			return null;
		}
	}
	
	
}
