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
		coreSize = 0;
		areaSize = 4;
		setPlaceType(PlaceType.INSIDE);
		
		byte d = 0x0;
		
		 	coreBlocks = new ArrayList<CoreBlock>();
		 	
		 	for (int y = -1; y <= 2; y++){
			 	for (int i = -2; i <= 2; i=i+4){
			 		for (int j = -2; j <= 2; j=j+4){
			 			coreBlocks.add(new CoreBlock(i,-1,j,Material.OBSIDIAN,d));
				 	}
			 	}
		 	}
		
	}
	
	

}
