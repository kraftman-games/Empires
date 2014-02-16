package es.themin.empires.schematics.mob;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import es.themin.empires.enums.CoreType;
import es.themin.empires.schematics.Schematic;
import es.themin.empires.util.UtilManager;

public class Schematic_Mob_1 extends Schematic{

	
	
	@SuppressWarnings("deprecation")
	@Override
	public void pasteFromCentre(Location location) {
		World w = location.getWorld();
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		
		ArrayList<Block> bricks = new ArrayList<Block>();
		bricks.add(w.getBlockAt(x+1,y,z+1));
		bricks.add(w.getBlockAt(x-1,y,z+1));
		bricks.add(w.getBlockAt(x+1,y,z-1));
		bricks.add(w.getBlockAt(x-1,y,z-1));
		for (Block b : bricks){
			b.setType(Material.BRICK);
		}
		
		ArrayList<Block> dia = new ArrayList<Block>();
		dia.add(w.getBlockAt(x+1,y+1,z+1));
		dia.add(w.getBlockAt(x-1,y+1,z+1));
		dia.add(w.getBlockAt(x+1,y+1,z-1));
		dia.add(w.getBlockAt(x-1,y+1,z-1));
		for (Block b : dia){
			b.setType(Material.DIAMOND_BLOCK);
		}
		
		ArrayList<Block> coal = new ArrayList<Block>();
		coal.add(w.getBlockAt(x+1,y+1,z+1));
		coal.add(w.getBlockAt(x-1,y+1,z+1));
		coal.add(w.getBlockAt(x+1,y+1,z-1));
		coal.add(w.getBlockAt(x-1,y+1,z-1));
		coal.add(w.getBlockAt(x,y+2,z));
		coal.add(w.getBlockAt(x,y+3,z));
		for (Block b : coal){
			b.setType(Material.COAL_BLOCK);
		}
		
		UtilManager.setStairsUpData(Material.BRICK_STAIRS,w.getBlockAt(x+1,y+2,z),BlockFace.WEST);
		UtilManager.setStairsUpData(Material.BRICK_STAIRS,w.getBlockAt(x-1,y+2,z) ,BlockFace.EAST);
		UtilManager.setStairsUpData(Material.BRICK_STAIRS,w.getBlockAt(x,y+2,z+1),BlockFace.NORTH);
		UtilManager.setStairsUpData(Material.BRICK_STAIRS,w.getBlockAt(x,y+2,z-1),BlockFace.SOUTH);
		
		UtilManager.setStairsData(Material.BRICK_STAIRS,w.getBlockAt(x+1,y+3,z),BlockFace.WEST);
		UtilManager.setStairsData(Material.BRICK_STAIRS,w.getBlockAt(x-1,y+3,z) ,BlockFace.EAST);
		UtilManager.setStairsData(Material.BRICK_STAIRS,w.getBlockAt(x,y+2,3+1),BlockFace.NORTH);
		UtilManager.setStairsData(Material.BRICK_STAIRS,w.getBlockAt(x+1,y+2,3+1),BlockFace.NORTH);
		UtilManager.setStairsData(Material.BRICK_STAIRS,w.getBlockAt(x-1,y+2,3+1),BlockFace.NORTH);
		UtilManager.setStairsData(Material.BRICK_STAIRS,w.getBlockAt(x,y+2,3-1),BlockFace.SOUTH);
		UtilManager.setStairsData(Material.BRICK_STAIRS,w.getBlockAt(x+1,y+2,3-1),BlockFace.SOUTH);
		UtilManager.setStairsData(Material.BRICK_STAIRS,w.getBlockAt(x-1,y+2,3-1),BlockFace.SOUTH);
		
		w.getBlockAt(x,y+4,z).setType(Material.COBBLE_WALL);
		w.getBlockAt(x,y+5,z).setType(Material.STEP);
		byte d = 0x5;
		w.getBlockAt(x,y+5,z).setData(d);
		w.getBlockAt(location).setType(Material.WORKBENCH);
	}


	@Override
	public CoreType coreType() {
		return CoreType.MOB;
	}

	@Override
	public ArrayList<Block> getBlocks(Location location) {
		World w = location.getWorld();
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		ArrayList<Block> blocks = new ArrayList<Block>();
		
		
		blocks.add(w.getBlockAt(x+1,y,z+1));
		blocks.add(w.getBlockAt(x-1,y,z+1));
		blocks.add(w.getBlockAt(x+1,y,z-1));
		blocks.add(w.getBlockAt(x-1,y,z-1));
		
		blocks.add(w.getBlockAt(x+1,y+1,z+1));
		blocks.add(w.getBlockAt(x-1,y+1,z+1));
		blocks.add(w.getBlockAt(x+1,y+1,z-1));
		blocks.add(w.getBlockAt(x-1,y+1,z-1));
		
		blocks.add(w.getBlockAt(x+1,y+1,z+1));
		blocks.add(w.getBlockAt(x-1,y+1,z+1));
		blocks.add(w.getBlockAt(x+1,y+1,z-1));
		blocks.add(w.getBlockAt(x-1,y+1,z-1));
		blocks.add(w.getBlockAt(x,y+2,z));
		blocks.add(w.getBlockAt(x,y+3,z));

		blocks.add(w.getBlockAt(x+1,y+2,z));
		blocks.add(w.getBlockAt(x-1,y+2,z));
		blocks.add(w.getBlockAt(x,y+2,z+1));
		blocks.add(w.getBlockAt(x,y+2,z-1));
		
		blocks.add(w.getBlockAt(x+1,y+3,z));
		blocks.add(w.getBlockAt(x-1,y+3,z));
		blocks.add(w.getBlockAt(x,y+2,3+1));
		blocks.add(w.getBlockAt(x+1,y+2,3+1));
		blocks.add(w.getBlockAt(x-1,y+2,3+1));
		blocks.add(w.getBlockAt(x,y+2,3-1));
		blocks.add(w.getBlockAt(x+1,y+2,3-1));
		blocks.add(w.getBlockAt(x-1,y+2,3-1));
		
		blocks.add(w.getBlockAt(x,y+4,z));
		blocks.add(w.getBlockAt(x,y+5,z));
		blocks.add(w.getBlockAt(location));
		
		return blocks;
	}

	@Override
	public int getLevel() {
		return 0;
	}

}
