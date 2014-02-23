package es.themin.empires.cores.coretypes;

import es.themin.empires.cores.Core;
import es.themin.empires.cores.ICore;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.EPlayer;

public class BaseCore  extends Core implements ICore {

	public BaseCore(EPlayer myEPlayer) {
		super(myEPlayer);


		coreType = CoreType.BASE;
		coreSize = 8;
		areaSize = 16;
	}

}
