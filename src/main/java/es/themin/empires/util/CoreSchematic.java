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
			
		case FORTIFICATION:
			
		case BASE:
			return mySchem = BaseCore;	
		}
		
		return null;
	}
}











