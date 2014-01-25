package es.themin.empires.cores;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.naming.CompoundName;

import jnbt.ByteArrayTag;
import jnbt.CompoundTag;
import jnbt.NBTInputStream;
import jnbt.ShortTag;
import jnbt.StringTag;
import jnbt.Tag;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.PlaceType;
import es.themin.empires.enums.ProtectionType;

public class CoreSchematic {
	
	
	
	
	public static Schematic baseschem;
	public static Schematic farmschem;
	public static Schematic mobschem; 
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
			return GetBaseCore();	
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
				GriefCore.add(new CoreBlock(x,0,z,null, ProtectionType.BASE));
			}
		}
		
		CoreBlock myCoreBlock = new CoreBlock(0,-1,0,Material.BRICK, ProtectionType.CORE);
		
		GriefCore.add(myCoreBlock);
		
		return GriefCore;
	}
	
	private static ArrayList<CoreBlock>  GetBaseCore(){
		//special case just for the grief core
		
		//need to add a method of distinguishing between the core itself and the protected blocks
		ArrayList<CoreBlock> BaseCore = new ArrayList<CoreBlock>();
		for(int x = -32; x <=32; x++){
			for(int z = -32; z <=32; z++){
				BaseCore.add(new CoreBlock(x,0,z,null,ProtectionType.BASE));
			}
		}
		
		BaseCore.add(new CoreBlock(0,-1,0,Material.IRON_BLOCK,ProtectionType.CORE));
		BaseCore.add(new CoreBlock(0,-1,1,Material.DIRT,ProtectionType.CORE));
		BaseCore.add(new CoreBlock(0,-1,-1,Material.DIRT,ProtectionType.CORE));
		BaseCore.add(new CoreBlock(1,-1,0,Material.DIRT,ProtectionType.CORE));
		BaseCore.add(new CoreBlock(1,-1,-1,Material.DIRT,ProtectionType.CORE));
		BaseCore.add(new CoreBlock(1,-1,1,Material.DIRT,ProtectionType.CORE));
		BaseCore.add(new CoreBlock(-1,-1,0,Material.DIRT,ProtectionType.CORE));
		BaseCore.add(new CoreBlock(-1,-1,1,Material.DIRT,ProtectionType.CORE));
		BaseCore.add(new CoreBlock(-1,-1,-1,Material.DIRT,ProtectionType.CORE));
		
		return BaseCore;
	}

	public static PlaceType getPlaceType(CoreType myCoreType) {
		switch(myCoreType){
		case FARM:
			return PlaceType.INSIDE;
		case MOB:
			return PlaceType.INSIDE;
		case MONSTER:
			return PlaceType.INSIDE;
		case GRIEF:
			return PlaceType.EDGE;
		case FORTIFICATION:
			return PlaceType.INSIDE;
		case BASE:
			return PlaceType.OUTSIDE;	
		}
		
		return PlaceType.INSIDE;
	}
	public static void loadSchematics() {
		String epath = Bukkit.getServer().getPluginManager().getPlugin("Empires").getDataFolder().getAbsolutePath() + "/cores";
		(new File(epath)).mkdirs();
		File bcore = new File(epath + File.separator + "BASE.schematic");
		if (!bcore.exists()) {
			Bukkit.getServer().getLogger().info("Base schematic not found");
			Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getServer().getPluginManager().getPlugin("Empires"));
		}else {
			try {
				baseschem = loadSchematic(bcore);
			} catch (IOException e) {
				Bukkit.getServer().getLogger().info("error 1 loading schematics");
				e.printStackTrace();
			}
		}
	}

	
    public static Schematic loadSchematic(File file) throws IOException
    {
        FileInputStream stream = new FileInputStream(file);
        @SuppressWarnings("resource")
		NBTInputStream nbtStream = new NBTInputStream(new GZIPInputStream(stream));
 
        CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
        if (!schematicTag.getName().equals("Schematic")) {
            throw new IllegalArgumentException("Tag \"Schematic\" does not exist or is not first");
        }
 
        Map<String, Tag> schematic = schematicTag.getValue();
        if (!schematic.containsKey("Blocks")) {
            throw new IllegalArgumentException("Schematic file is missing a \"Blocks\" tag");
        }
 
        short width = getChildTag(schematic, "Width", ShortTag.class).getValue();
        short length = getChildTag(schematic, "Length", ShortTag.class).getValue();
        short height = getChildTag(schematic, "Height", ShortTag.class).getValue();
 
        String materials = getChildTag(schematic, "Materials", StringTag.class).getValue();
        if (!materials.equals("Alpha")) {
            throw new IllegalArgumentException("Schematic file is not an Alpha schematic");
        }
 
        byte[] blocks = getChildTag(schematic, "Blocks", ByteArrayTag.class).getValue();
        byte[] blockData = getChildTag(schematic, "Data", ByteArrayTag.class).getValue();
        return new Schematic(blocks, blockData, width, length, height);
    }
 
    /**
    * Get child tag of a NBT structure.
    *
    * @param items The parent tag map
    * @param key The name of the tag to get
    * @param expected The expected type of the tag
    * @return child tag casted to the expected type
    * @throws DataException if the tag does not exist or the tag is not of the
    * expected type
    */
    private static <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected) throws IllegalArgumentException
    {
        if (!items.containsKey(key)) {
            throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag");
        }
        Tag tag = items.get(key);
        if (!expected.isInstance(tag)) {
            throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());
        }
        return expected.cast(tag);
    }
}











