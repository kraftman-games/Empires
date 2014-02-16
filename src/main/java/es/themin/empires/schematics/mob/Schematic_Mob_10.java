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

public class Schematic_Mob_10 extends Schematic{

	@Override
	public CoreType coreType() {
		return CoreType.MOB;
	}

	@Override
	public int getLevel() {
		return 10;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void pasteFromCentre(Location location) {
		Location l = location;
		World w = l.getWorld();
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		
		/**
		 * Bricks
		 */
		ArrayList<Block> bricks = new ArrayList<Block>();
		bricks.add(w.getBlockAt(x+2,y,z+1));
		bricks.add(w.getBlockAt(x+2,y,z-1));
		bricks.add(w.getBlockAt(x+2,y,z+2));
		bricks.add(w.getBlockAt(x+2,y,z-2));
		bricks.add(w.getBlockAt(x+1,y,z+2));
		bricks.add(w.getBlockAt(x+1,y,z-2));
		bricks.add(w.getBlockAt(x-2,y,z+1));
		bricks.add(w.getBlockAt(x-2,y,z-1));
		bricks.add(w.getBlockAt(x-2,y,z+2));
		bricks.add(w.getBlockAt(x-2,y,z-2));
		bricks.add(w.getBlockAt(x-1,y,z+2));
		bricks.add(w.getBlockAt(x-1,y,z-2));
		bricks.add(w.getBlockAt(x,y+2,z));
		bricks.add(w.getBlockAt(x,y+3,z));
		bricks.add(w.getBlockAt(x,y+4,z));
		for (Block b : bricks){
			b.setType(Material.BRICK);
		}
		
		ArrayList<Block> coal = new ArrayList<Block>();
		coal.add(w.getBlockAt(x+2,y+1,z+2));
		coal.add(w.getBlockAt(x+2,y+1,z-2));
		coal.add(w.getBlockAt(x-2,y+1,z+2));
		coal.add(w.getBlockAt(x-2,y+1,z-2));
		
		coal.add(w.getBlockAt(x+1,y,z+1));
		coal.add(w.getBlockAt(x+1,y,z-1));
		coal.add(w.getBlockAt(x-1,y,z+1));
		coal.add(w.getBlockAt(x-1,y,z-1));
		
		coal.add(w.getBlockAt(x+1,y+1,z+1));
		coal.add(w.getBlockAt(x+1,y+1,z-1));
		coal.add(w.getBlockAt(x-1,y+1,z+1));
		coal.add(w.getBlockAt(x-1,y+1,z-1));
		
		coal.add(w.getBlockAt(x+1,y+2,z+1));
		coal.add(w.getBlockAt(x+1,y+2,z-1));
		coal.add(w.getBlockAt(x-1,y+2,z+1));
		coal.add(w.getBlockAt(x-1,y+2,z-1));
		for (Block b:coal){
			b.setType(Material.COAL_BLOCK);
		}
		
		ArrayList<Block> sp =new ArrayList<Block>();
		sp.add(w.getBlockAt(x+2,y+2,z+2));
		sp.add(w.getBlockAt(x+2,y+2,z-2));
		sp.add(w.getBlockAt(x-2,y+2,z+2));
		sp.add(w.getBlockAt(x-2,y+2,z-2));
		
		sp.add(w.getBlockAt(x+1,y+3,z+1));
		sp.add(w.getBlockAt(x+1,y+3,z-1));
		sp.add(w.getBlockAt(x-1,y+3,z+1));
		sp.add(w.getBlockAt(x-1,y+3,z-1));
		
		sp.add(w.getBlockAt(x,y+5,z));
		
		for (Block b: sp) {
			b.setType(Material.COBBLE_WALL);
		}
		
		ArrayList<Block> slab = new ArrayList<Block>();
		
		slab.add(w.getBlockAt(x+2,y+3,z+2));
		slab.add(w.getBlockAt(x+2,y+3,z-2));
		slab.add(w.getBlockAt(x-2,y+3,z+2));
		slab.add(w.getBlockAt(x-2,y+3,z-2));
		slab.add(w.getBlockAt(x,y+6,z));
		byte d = 0x4;
		for (Block b:slab){
			b.setType(Material.STEP);
			b.setData(d);
		}
		
		ArrayList<Block> dia = new ArrayList<Block>();
		dia.add(w.getBlockAt(x+1,y+4,z+1));
		dia.add(w.getBlockAt(x+1,y+4,z-1));
		dia.add(w.getBlockAt(x-1,y+4,z+1));
		dia.add(w.getBlockAt(x-1,y+4,z-1));
		for (Block b:dia){
			b.setType(Material.DIAMOND_BLOCK);
		}
		
		UtilManager.setStairsData(Material.BRICK_STAIRS, w.getBlockAt(x+2,y+1,z+1), BlockFace.WEST);
		UtilManager.setStairsData(Material.BRICK_STAIRS, w.getBlockAt(x+2,y+1,z-1), BlockFace.WEST);
		UtilManager.setStairsData(Material.BRICK_STAIRS, w.getBlockAt(x-2,y+1,z+1), BlockFace.EAST);
		UtilManager.setStairsData(Material.BRICK_STAIRS, w.getBlockAt(x-2,y+1,z-1), BlockFace.EAST);
		UtilManager.setStairsData(Material.BRICK_STAIRS, w.getBlockAt(x+1,y+1,z+2), BlockFace.NORTH);
		UtilManager.setStairsData(Material.BRICK_STAIRS, w.getBlockAt(x-1,y+1,z+2), BlockFace.NORTH);
		UtilManager.setStairsData(Material.BRICK_STAIRS, w.getBlockAt(x+1,y+1,z-2), BlockFace.SOUTH);
		UtilManager.setStairsData(Material.BRICK_STAIRS, w.getBlockAt(x-1,y+1,z-2), BlockFace.SOUTH);
		
		UtilManager.setStairsUpData(Material.BRICK_STAIRS, w.getBlockAt(x+1,y+2,z), BlockFace.WEST);
		UtilManager.setStairsUpData(Material.BRICK_STAIRS, w.getBlockAt(x-1,y+2,z), BlockFace.EAST);
		UtilManager.setStairsUpData(Material.BRICK_STAIRS, w.getBlockAt(x,y+2,z+1), BlockFace.NORTH);
		UtilManager.setStairsUpData(Material.BRICK_STAIRS, w.getBlockAt(x,y+2,z-1), BlockFace.SOUTH);
		UtilManager.setStairsUpData(Material.BRICK_STAIRS, w.getBlockAt(x+1,y+3,z), BlockFace.WEST);
		UtilManager.setStairsUpData(Material.BRICK_STAIRS, w.getBlockAt(x-1,y+3,z), BlockFace.EAST);
		UtilManager.setStairsUpData(Material.BRICK_STAIRS, w.getBlockAt(x,y+3,z+1), BlockFace.NORTH);
		UtilManager.setStairsUpData(Material.BRICK_STAIRS, w.getBlockAt(x,y+3,z-1), BlockFace.SOUTH);
		UtilManager.setStairsData(Material.BRICK_STAIRS, w.getBlockAt(x+1,y+4,z), BlockFace.WEST);
		UtilManager.setStairsData(Material.BRICK_STAIRS, w.getBlockAt(x-1,y+4,z), BlockFace.EAST);
		UtilManager.setStairsData(Material.BRICK_STAIRS, w.getBlockAt(x,y+4,z+1), BlockFace.NORTH);
		UtilManager.setStairsData(Material.BRICK_STAIRS, w.getBlockAt(x,y+4,z-1), BlockFace.SOUTH);
	}

	@Override
	public ArrayList<Block> getBlocks(Location location) {
		Location l = location;
		World w = l.getWorld();
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		
		/**
		 * blocks
		 */
		ArrayList<Block> blocks = new ArrayList<Block>();
		blocks.add(w.getBlockAt(x+2,y,z+1));
		blocks.add(w.getBlockAt(x+2,y,z-1));
		blocks.add(w.getBlockAt(x+2,y,z+2));
		blocks.add(w.getBlockAt(x+2,y,z-2));
		blocks.add(w.getBlockAt(x+1,y,z+2));
		blocks.add(w.getBlockAt(x+1,y,z-2));
		blocks.add(w.getBlockAt(x-2,y,z+1));
		blocks.add(w.getBlockAt(x-2,y,z-1));
		blocks.add(w.getBlockAt(x-2,y,z+2));
		blocks.add(w.getBlockAt(x-2,y,z-2));
		blocks.add(w.getBlockAt(x-1,y,z+2));
		blocks.add(w.getBlockAt(x-1,y,z-2));
		blocks.add(w.getBlockAt(x,y+2,z));
		blocks.add(w.getBlockAt(x,y+3,z));
		blocks.add(w.getBlockAt(x,y+4,z));

		blocks.add(w.getBlockAt(x+2,y+1,z+2));
		blocks.add(w.getBlockAt(x+2,y+1,z-2));
		blocks.add(w.getBlockAt(x-2,y+1,z+2));
		blocks.add(w.getBlockAt(x-2,y+1,z-2));
		
		blocks.add(w.getBlockAt(x+1,y,z+1));
		blocks.add(w.getBlockAt(x+1,y,z-1));
		blocks.add(w.getBlockAt(x-1,y,z+1));
		blocks.add(w.getBlockAt(x-1,y,z-1));
		
		blocks.add(w.getBlockAt(x+1,y+1,z+1));
		blocks.add(w.getBlockAt(x+1,y+1,z-1));
		blocks.add(w.getBlockAt(x-1,y+1,z+1));
		blocks.add(w.getBlockAt(x-1,y+1,z-1));
		
		blocks.add(w.getBlockAt(x+1,y+2,z+1));
		blocks.add(w.getBlockAt(x+1,y+2,z-1));
		blocks.add(w.getBlockAt(x-1,y+2,z+1));
		blocks.add(w.getBlockAt(x-1,y+2,z-1));

		blocks.add(w.getBlockAt(x+2,y+2,z+2));
		blocks.add(w.getBlockAt(x+2,y+2,z-2));
		blocks.add(w.getBlockAt(x-2,y+2,z+2));
		blocks.add(w.getBlockAt(x-2,y+2,z-2));
		
		blocks.add(w.getBlockAt(x+1,y+3,z+1));
		blocks.add(w.getBlockAt(x+1,y+3,z-1));
		blocks.add(w.getBlockAt(x-1,y+3,z+1));
		blocks.add(w.getBlockAt(x-1,y+3,z-1));
		
		blocks.add(w.getBlockAt(x,y+5,z));
		
		

		
		blocks.add(w.getBlockAt(x+2,y+3,z+2));
		blocks.add(w.getBlockAt(x+2,y+3,z-2));
		blocks.add(w.getBlockAt(x-2,y+3,z+2));
		blocks.add(w.getBlockAt(x-2,y+3,z-2));
		blocks.add(w.getBlockAt(x,y+6,z));
		
		blocks.add(w.getBlockAt(x+1,y+4,z+1));
		blocks.add(w.getBlockAt(x+1,y+4,z-1));
		blocks.add(w.getBlockAt(x-1,y+4,z+1));
		blocks.add(w.getBlockAt(x-1,y+4,z-1));
		
		blocks.add(w.getBlockAt(x+2,y+1,z+1));
		blocks.add(w.getBlockAt(x+2,y+1,z-1));
		blocks.add(w.getBlockAt(x-2,y+1,z+1));
		blocks.add(w.getBlockAt(x-2,y+1,z-1));
		blocks.add(w.getBlockAt(x+1,y+1,z+2));
		blocks.add(w.getBlockAt(x-1,y+1,z+2));
		blocks.add(w.getBlockAt(x+1,y+1,z-2));
		blocks.add(w.getBlockAt(x-1,y+1,z-2));
		
		blocks.add(w.getBlockAt(x+1,y+2,z));
		blocks.add(w.getBlockAt(x-1,y+2,z));
		blocks.add(w.getBlockAt(x,y+2,z+1));
		blocks.add(w.getBlockAt(x,y+2,z-1));
		blocks.add(w.getBlockAt(x+1,y+3,z));
		blocks.add(w.getBlockAt(x-1,y+3,z));
		blocks.add(w.getBlockAt(x,y+3,z+1));
		blocks.add(w.getBlockAt(x,y+3,z-1));
		blocks.add(w.getBlockAt(x+1,y+4,z));
		blocks.add(w.getBlockAt(x-1,y+4,z));
		blocks.add(w.getBlockAt(x,y+4,z+1));
		blocks.add(w.getBlockAt(x,y+4,z-1));
		return blocks;
	}

}
