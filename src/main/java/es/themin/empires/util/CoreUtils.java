package es.themin.empires.util;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.metadata.MetadataValue;

import es.themin.empires.empires;
import es.themin.empires.enums.CoreType;

public class CoreUtils {

	
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
	
	public static CoreType getCoreTypeFromBlock(Block myBlock, empires plugin){
		List<MetadataValue> values = myBlock.getMetadata("coreType");
		for(MetadataValue value : values){
			if (value.getOwningPlugin().equals(plugin)){
				String myCoreType = value.asString();
				switch (myCoreType){
				case "BASE":
					return CoreType.BASE;
				default:
					return null;
				}
			}
		}
		return null;
	}
}
