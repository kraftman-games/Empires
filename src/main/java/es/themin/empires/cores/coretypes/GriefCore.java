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
		setPlaceType(PlaceType.EDGE);
		
		byte d = 0x0;
	    coreBlocks = new ArrayList<CoreBlock>();
	    coreBlocks.add(new CoreBlock(0,-1,0,Material.IRON_BLOCK,d));
	    coreBlocks.add(new CoreBlock(0,-1,1,Material.OBSIDIAN,d));
	    coreBlocks.add(new CoreBlock(0,-1,-1,Material.OBSIDIAN,d));
	    coreBlocks.add(new CoreBlock(1,-1,0,Material.OBSIDIAN,d));
	    coreBlocks.add(new CoreBlock(1,-1,-1,Material.OBSIDIAN,d));
	    coreBlocks.add(new CoreBlock(1,-1,1,Material.OBSIDIAN,d));
	    coreBlocks.add(new CoreBlock(-1,-1,0,Material.OBSIDIAN,d));
	    coreBlocks.add(new CoreBlock(-1,-1,1,Material.OBSIDIAN,d));
	    coreBlocks.add(new CoreBlock(-1,-1,-1,Material.OBSIDIAN,d));
	    
	    
	}

}
