package es.themin.empires.cores.coretypes;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;

import es.themin.empires.cores.Core;
import es.themin.empires.cores.CoreBlock;
import es.themin.empires.cores.ICore;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.PlaceType;

public class GriefCore extends Core implements ICore{

	public GriefCore(UUID empireUUID, Location myLocation) {
		super(empireUUID, myLocation);


		coreType = CoreType.GRIEF;
		coreSize = 2;
		areaSize = 16;
		placeType = PlaceType.EDGE;
		
	    coreBlocks = new ArrayList<CoreBlock>();
	    coreBlocks.add(new CoreBlock(0,-1,0,Material.OBSIDIAN));
	    
	}

}
