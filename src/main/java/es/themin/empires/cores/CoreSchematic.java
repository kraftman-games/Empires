package es.themin.empires.cores;


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
		add(new CoreBlock(0,-1,0,Material.IRON_BLOCK, "core", "BASE"));
		add(new CoreBlock(0,-1,1,Material.DIRT, "core", "BASE"));
		add(new CoreBlock(0,-1,-1,Material.DIRT, "core", "BASE"));
		add(new CoreBlock(1,-1,0,Material.DIRT, "core", "BASE"));
		add(new CoreBlock(1,-1,-1,Material.DIRT, "core", "BASE"));
		add(new CoreBlock(1,-1,1,Material.DIRT, "core", "BASE"));
		add(new CoreBlock(-1,-1,0,Material.DIRT, "core", "BASE"));
		add(new CoreBlock(-1,-1,1,Material.DIRT, "core", "BASE"));
		add(new CoreBlock(-1,-1,-1,Material.DIRT, "core", "BASE"));
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
	
	public static int getDestroyCost(CoreType myCoreType){

		ArrayList<CoreBlock> mySchem = null;
		switch(myCoreType){
		case FARM:
			
		case MOB:
			
		case MONSTER:
			
		case GRIEF:
			return 0;
		case FORTIFICATION:
			
		case BASE:
			return 2;	
		}
		
		return 0;
	}
	
	private static ArrayList<CoreBlock>  GetGriefCore(){
		//special case just for the grief core
		
		//need to add a method of distinguishing between the core itself and the protected blocks
		ArrayList<CoreBlock> GriefCore = new ArrayList<CoreBlock>();
		for(int x = -16; x <=16; x++){
			for(int z = -16; z <=16; z++){
				for(int y = -16; y <=16; y++){
					CoreBlock myCoreBlock = new CoreBlock(x,y,z,null);
					myCoreBlock.setMetaKey("core");
					myCoreBlock.setMetaValue("grief");
					if (x == 0 && z == 0 && y == -1){
						myCoreBlock.setMetaKey("core");
						myCoreBlock.setMetaValue("griefcore");
						myCoreBlock.setMaterial(Material.BRICK);
					}
					
					GriefCore.add(myCoreBlock);
				}
			}
		}
		GriefCore.add(new CoreBlock(0,-1,0,Material.BRICK));
		return GriefCore;
	}
}











