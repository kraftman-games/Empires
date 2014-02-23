package es.themin.empires.cores.coretypes;

import java.util.ArrayList;

import org.bukkit.Material;

import es.themin.empires.cores.Core;
import es.themin.empires.cores.CoreBlock;
import es.themin.empires.cores.ICore;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.EPlayer;

public class BaseCore  extends Core implements ICore {

	public BaseCore(EPlayer myEPlayer) {
		super(myEPlayer);


		coreType = CoreType.BASE;
		coreSize = 8;
		areaSize = 16;
		

	    coreBlocks = new ArrayList<CoreBlock>();
	    coreBlocks.add(new CoreBlock(0,-1,0,Material.IRON_BLOCK));
	    coreBlocks.add(new CoreBlock(0,-1,1,Material.DIRT));
	    coreBlocks.add(new CoreBlock(0,-1,-1,Material.DIRT));
	    coreBlocks.add(new CoreBlock(1,-1,0,Material.DIRT));
	    coreBlocks.add(new CoreBlock(1,-1,-1,Material.DIRT));
	    coreBlocks.add(new CoreBlock(1,-1,1,Material.DIRT));
	    coreBlocks.add(new CoreBlock(-1,-1,0,Material.DIRT));
	    coreBlocks.add(new CoreBlock(-1,-1,1,Material.DIRT));
	    coreBlocks.add(new CoreBlock(-1,-1,-1,Material.DIRT));
	    
	}

}
