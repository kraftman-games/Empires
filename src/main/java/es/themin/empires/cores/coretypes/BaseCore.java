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
import es.themin.empires.util.EPlayer;

public class BaseCore  extends Core implements ICore {

	public BaseCore(UUID empireUUID, Location myLocation) {
		super( empireUUID, myLocation);


		coreType = CoreType.BASE;
		coreSize = 2;
		areaSize = 32;
		placeType = PlaceType.OUTSIDE;
		

	    coreBlocks = new ArrayList<CoreBlock>();
	    coreBlocks.add(new CoreBlock(0,-1,0,Material.IRON_BLOCK));
	    coreBlocks.add(new CoreBlock(0,-1,1,Material.BRICK));
	    coreBlocks.add(new CoreBlock(0,-1,-1,Material.BRICK));
	    coreBlocks.add(new CoreBlock(1,-1,0,Material.BRICK));
	    coreBlocks.add(new CoreBlock(1,-1,-1,Material.BRICK));
	    coreBlocks.add(new CoreBlock(1,-1,1,Material.BRICK));
	    coreBlocks.add(new CoreBlock(-1,-1,0,Material.BRICK));
	    coreBlocks.add(new CoreBlock(-1,-1,1,Material.BRICK));
	    coreBlocks.add(new CoreBlock(-1,-1,-1,Material.BRICK));
	    
	}

}
