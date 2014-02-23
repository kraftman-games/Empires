package es.themin.empires.cores;

import es.themin.empires.cores.coretypes.BaseCore;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.testing.newemp;

public class CoreFactory {

	
	public Core CreateCore(CoreType myCoreType, EPlayer myEPlayer){
		
		switch (myCoreType) {
		case BASE:
			return new BaseCore(myEPlayer);

		default:
			return null;
		}
	}
	
	
}
