package es.themin.empires.util;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import es.themin.empires.enums.CoreType;

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
		
		JavaPlugin myPlugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("Empires");
		
		FixedMetadataValue   myMetaData = new FixedMetadataValue (myPlugin, "BASE");
		b.setMetadata("coreType", myMetaData);
	}
	public void build(Core myCore){
		
		CoreType myCoreType = myCore.getType();
		
		//rotate the core so its in front of the player.
		
		switch(myCoreType){
		case FARM:
			
		case MOB:
			
		case MONSTER:
			
		case GRIEF:
			
		case FORTIFICATION:
			
		case BASE:
			buildBaseCore(myCore);
		
		}
	}
	private void buildBaseCore(Core core) {
		// TODO Auto-generated method stub
		Location myLocation = core.getLocation();
		Block b = myLocation.getBlock();
		b.setType(Material.IRON_BLOCK);
		
		JavaPlugin myPlugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("Empires");
		
		FixedMetadataValue   myMetaData = new FixedMetadataValue (myPlugin, "BASE");
		FixedMetadataValue   myMetaData2 = new FixedMetadataValue (myPlugin, core.getId());
		b.setMetadata("coreType", myMetaData);
		b.setMetadata("core", myMetaData2);
	}
}











