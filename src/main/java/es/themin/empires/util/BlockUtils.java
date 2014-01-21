package es.themin.empires.util;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.metadata.MetadataValue;

import es.themin.empires.empires;
import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;

public class BlockUtils {

	
	public static Core getCoreFromBlock(Block myBlock, empires plugin){
		List<MetadataValue> values = myBlock.getMetadata("core");
		for(MetadataValue value : values){
			if (value.getOwningPlugin().equals(plugin)){
				int id = value.asInt();
				Core core = UtilManager.getCoreWithId(id);
				return core;			
			}
		}
		return null;
	}
	
	public static Empire getEmpireFromBlock(Block myBlock, empires plugin){
		List<MetadataValue> values = myBlock.getMetadata("empire");
		for(MetadataValue value : values){
			if (value.getOwningPlugin().equals(plugin)){
				int id = value.asInt();
				Empire empire = UtilManager.getEmpireWithId(id);
				return empire;			
			}
		}
		return null;
	}
}
