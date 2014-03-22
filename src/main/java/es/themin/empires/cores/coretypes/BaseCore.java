package es.themin.empires.cores.coretypes;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;

import es.themin.empires.Debug;
import es.themin.empires.cores.Core;
import es.themin.empires.cores.CoreBlock;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.PlaceType;

public class BaseCore  extends Core {

	public BaseCore(UUID empireUUID, Location myLocation) {
		super( empireUUID, myLocation);

		Debug.Console("Generatign base core");
		coreType = CoreType.BASE;
		coreSize = 2;
		areaSize = 32;
		setPlaceType(PlaceType.OUTSIDE);
		placeCost = 10;
		
		byte d = 0x0;
	    coreBlocks = new ArrayList<CoreBlock>();
	    coreBlocks.add(new CoreBlock(0,-1,0,Material.IRON_BLOCK,d));
	    coreBlocks.add(new CoreBlock(0,-1,1,Material.BRICK,d));
	    coreBlocks.add(new CoreBlock(0,-1,-1,Material.BRICK,d));
	    coreBlocks.add(new CoreBlock(1,-1,0,Material.BRICK,d));
	    coreBlocks.add(new CoreBlock(1,-1,-1,Material.BRICK,d));
	    coreBlocks.add(new CoreBlock(1,-1,1,Material.BRICK,d));
	    coreBlocks.add(new CoreBlock(-1,-1,0,Material.BRICK,d));
	    coreBlocks.add(new CoreBlock(-1,-1,1,Material.BRICK,d));
	    coreBlocks.add(new CoreBlock(-1,-1,-1,Material.BRICK,d));
	    
	}

}
