package es.themin.empires.util;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import es.themin.empires.enums.CoreType;

public class CoreSchematic {
	
	private static ArrayList<CoreBlock> BaseCore = new ArrayList<CoreBlock>()
			{{
		add(new CoreBlock(0,-1,0,Material.IRON_BLOCK));
		add(new CoreBlock(0,-1,1,Material.DIRT));
		add(new CoreBlock(0,-1,-1,Material.DIRT));
		add(new CoreBlock(1,-1,0,Material.DIRT));
		add(new CoreBlock(1,-1,-1,Material.DIRT));
		add(new CoreBlock(1,-1,1,Material.DIRT));
		add(new CoreBlock(-1,-1,0,Material.DIRT));
		add(new CoreBlock(-1,-1,1,Material.DIRT));
		add(new CoreBlock(-1,-1,-1,Material.DIRT));
	}};
	
	
	
	public static ArrayList<CoreBlock> getSchematic(CoreType myCoreType){

		ArrayList<CoreBlock> mySchem = null;
		switch(myCoreType){
		case FARM:
			
		case MOB:
			
		case MONSTER:
			
		case GRIEF:
			return GetGriefCore();
		case FORTIFICATION:
			
		case BASE:
			return BaseCore;	
		}
		
		return null;
	}
	
	private static ArrayList<CoreBlock>  GetGriefCore(){
		//special case just for the grief core
		ArrayList<CoreBlock> GriefCore = new ArrayList<CoreBlock>();
		for(int x = -16; x <=16; x++){
			for(int z = -16; z <=16; z++){
				for(int y = -16; y <=16; y++){
					GriefCore.add(new CoreBlock(0,-1,0,null));
				}
			}
		}
		
		return GriefCore;
	}
}











