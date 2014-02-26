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

public class CellCore extends Core implements ICore  {

	public CellCore(UUID empireUUID, Location myLocation) {
		super(empireUUID, myLocation);


		coreType = CoreType.CELL;
		coreSize = 2;
		areaSize = 8;
		placeType = PlaceType.INSIDE;
		
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
		    
		    coreBlocks.add(new CoreBlock(0,1,1,Material.BRICK));
		    coreBlocks.add(new CoreBlock(0,1,-1,Material.BRICK));
		    coreBlocks.add(new CoreBlock(1,1,0,Material.BRICK));
		    coreBlocks.add(new CoreBlock(1,1,-1,Material.BRICK));
		    coreBlocks.add(new CoreBlock(1,1,1,Material.BRICK));
		    coreBlocks.add(new CoreBlock(-1,1,0,Material.BRICK));
		    coreBlocks.add(new CoreBlock(-1,1,1,Material.BRICK));
		    coreBlocks.add(new CoreBlock(-1,1,-1,Material.BRICK));
		
	}
	
	public void build(){
		super.build();
		
		//set some timer for release
		
	}

}