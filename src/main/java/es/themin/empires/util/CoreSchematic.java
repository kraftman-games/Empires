package es.themin.empires.util;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import enums.CoreType;

public class CoreSchematic {

	public CoreSchematic() {
		
		
		
	}
	
	public void build(CoreType myCoreType, Player myPlayer){
		
		Location myLocation = myPlayer.getLocation();
		
		//rotate the core so its in front of the player.
		
		switch(myCoreType){
		case FARM:
			
		case MOB:
			
		case MONSTER:
			
		case GRIEF:
			
		case FORTIFICATION:
			
		case BASE:
			buildBaseCore(myLocation);
		
	}
	}

	private void buildBaseCore(Location myLocation) {
		// TODO Auto-generated method stub
		Block b = myLocation.getBlock();
		b.setType(Material.IRON_BLOCK);
	}
}











