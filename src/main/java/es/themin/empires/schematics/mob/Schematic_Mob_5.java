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

public class Schematic_Mob_5 extends Schematic{

	@SuppressWarnings("deprecation")
	@Override
	public void pasteFromCentre(Location location) {
		World world = location.getWorld();
		
		ArrayList<Block> wstairs = new ArrayList<Block>();
		wstairs.add(world.getBlockAt(location.getBlockX() + 2,location.getBlockY(), location.getBlockZ() + 1));
		wstairs.add(world.getBlockAt(location.getBlockX() + 2,location.getBlockY(), location.getBlockZ() - 1));
		wstairs.add(world.getBlockAt(location.getBlockX() + 1,location.getBlockY() + 3, location.getBlockZ()));
		UtilManager.setStairsFromList(wstairs, Material.getMaterial(108), BlockFace.WEST);
		
		ArrayList<Block> estairs = new ArrayList<Block>();
		estairs.add(world.getBlockAt(location.getBlockX() - 2,location.getBlockY(), location.getBlockZ() + 1));
		estairs.add(world.getBlockAt(location.getBlockX() - 2,location.getBlockY(), location.getBlockZ() - 1));
		estairs.add(world.getBlockAt(location.getBlockX() - 1,location.getBlockY() + 3, location.getBlockZ()));
		UtilManager.setStairsFromList(estairs, Material.getMaterial(108), BlockFace.EAST);
		
		ArrayList<Block> nstairs = new ArrayList<Block>();
		nstairs.add(world.getBlockAt(location.getBlockX() + 1,location.getBlockY(), location.getBlockZ() + 2));
		nstairs.add(world.getBlockAt(location.getBlockX() - 1,location.getBlockY(), location.getBlockZ() + 2));
		nstairs.add(world.getBlockAt(location.getBlockX(),location.getBlockY() + 3, location.getBlockZ() + 1));
		UtilManager.setStairsFromList(nstairs, Material.getMaterial(108), BlockFace.NORTH);
		
		ArrayList<Block> sstairs = new ArrayList<Block>();
		sstairs.add(world.getBlockAt(location.getBlockX() + 1,location.getBlockY(), location.getBlockZ() - 2));
		sstairs.add(world.getBlockAt(location.getBlockX() - 1,location.getBlockY(), location.getBlockZ() - 2));
		sstairs.add(world.getBlockAt(location.getBlockX(),location.getBlockY() + 3, location.getBlockZ() - 1));
		UtilManager.setStairsFromList(sstairs, Material.getMaterial(108), BlockFace.SOUTH);
		
		UtilManager.setStairsUpData(Material.getMaterial(108),world.getBlockAt(location.getBlockX() + 1,location.getBlockY() + 2, location.getBlockZ()) ,BlockFace.WEST);
		UtilManager.setStairsUpData(Material.getMaterial(108),world.getBlockAt(location.getBlockX() - 1,location.getBlockY() + 2, location.getBlockZ()) ,BlockFace.EAST);
		UtilManager.setStairsUpData(Material.getMaterial(108),world.getBlockAt(location.getBlockX(),location.getBlockY() + 2, location.getBlockZ() + 1) ,BlockFace.NORTH);
		UtilManager.setStairsUpData(Material.getMaterial(108),world.getBlockAt(location.getBlockX(),location.getBlockY() + 2, location.getBlockZ() - 1) ,BlockFace.SOUTH);

		ArrayList<Block> coal = new ArrayList<Block>();
		coal.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY(), location.getBlockZ() + 1));
		coal.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY(), location.getBlockZ() - 1));
		coal.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY(), location.getBlockZ() + 1));
		coal.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY(), location.getBlockZ() - 1));
		coal.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY()+1, location.getBlockZ() + 1));
		coal.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY()+1, location.getBlockZ() - 1));
		coal.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY()+1, location.getBlockZ() + 1));
		coal.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY()+1, location.getBlockZ() - 1));
		for (Block b : coal){
			b.setType(Material.COAL_BLOCK);
		}
		
		ArrayList<Block> wall = new ArrayList<Block>();
		wall.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY()+2, location.getBlockZ() + 1));
		wall.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY()+2, location.getBlockZ() - 1));
		wall.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY()+2, location.getBlockZ() + 1));
		wall.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY()+2, location.getBlockZ() - 1));
		for (Block b : wall) {
			b.setType(Material.COBBLE_WALL);
		}
		ArrayList<Block> dia = new ArrayList<Block>();
		dia.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY()+3, location.getBlockZ() + 1));
		dia.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY()+3, location.getBlockZ() - 1));
		dia.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY()+3, location.getBlockZ() + 1));
		dia.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY()+3, location.getBlockZ() - 1));
		for (Block b : dia) {
			b.setType(Material.DIAMOND_BLOCK);
		}
		
		world.getBlockAt(location).setType(Material.WORKBENCH);
		world.getBlockAt(location.getBlockX(),location.getBlockY() + 2, location.getBlockZ()).setType(Material.BRICK);
		world.getBlockAt(location.getBlockX(),location.getBlockY() + 3, location.getBlockZ()).setType(Material.SMOOTH_BRICK);
		world.getBlockAt(location.getBlockX(),location.getBlockY() + 4, location.getBlockZ()).setType(Material.COBBLE_WALL);
		world.getBlockAt(location.getBlockX(),location.getBlockY() + 5, location.getBlockZ()).setType(Material.STEP);
		byte d = 0x4;
		world.getBlockAt(location.getBlockX(),location.getBlockY() + 5, location.getBlockZ()).setData(d);
		
		
	}


	@Override
	public CoreType coreType() {
		return CoreType.MOB;
	}

	@Override
	public ArrayList<Block> getBlocks(Location location) {
		ArrayList<Block> blocks = new ArrayList<Block>();
		World world = location.getWorld();
		
		
		blocks.add(world.getBlockAt(location.getBlockX() + 2,location.getBlockY(), location.getBlockZ() + 1));
		blocks.add(world.getBlockAt(location.getBlockX() + 2,location.getBlockY(), location.getBlockZ() - 1));
		blocks.add(world.getBlockAt(location.getBlockX() + 1,location.getBlockY() + 3, location.getBlockZ()));	
		
		blocks.add(world.getBlockAt(location.getBlockX() - 2,location.getBlockY(), location.getBlockZ() + 1));
		blocks.add(world.getBlockAt(location.getBlockX() - 2,location.getBlockY(), location.getBlockZ() - 1));
		blocks.add(world.getBlockAt(location.getBlockX() - 1,location.getBlockY() + 3, location.getBlockZ()));

		blocks.add(world.getBlockAt(location.getBlockX() + 1,location.getBlockY(), location.getBlockZ() + 2));
		blocks.add(world.getBlockAt(location.getBlockX() - 1,location.getBlockY(), location.getBlockZ() + 2));
		blocks.add(world.getBlockAt(location.getBlockX(),location.getBlockY() + 3, location.getBlockZ() + 1));
		
		blocks.add(world.getBlockAt(location.getBlockX() + 1,location.getBlockY(), location.getBlockZ() - 2));
		blocks.add(world.getBlockAt(location.getBlockX() - 1,location.getBlockY(), location.getBlockZ() - 2));
		blocks.add(world.getBlockAt(location.getBlockX(),location.getBlockY() + 3, location.getBlockZ() - 1));
		
		blocks.add(world.getBlockAt(location.getBlockX() + 1,location.getBlockY() + 2, location.getBlockZ()));
		blocks.add(world.getBlockAt(location.getBlockX() - 1,location.getBlockY() + 2, location.getBlockZ()));
		blocks.add(world.getBlockAt(location.getBlockX(),location.getBlockY() + 2, location.getBlockZ() + 1));
		blocks.add(world.getBlockAt(location.getBlockX(),location.getBlockY() + 2, location.getBlockZ() - 1));

		blocks.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY(), location.getBlockZ() + 1));
		blocks.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY(), location.getBlockZ() - 1));
		blocks.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY(), location.getBlockZ() + 1));
		blocks.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY(), location.getBlockZ() - 1));
		blocks.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY()+1, location.getBlockZ() + 1));
		blocks.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY()+1, location.getBlockZ() - 1));
		blocks.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY()+1, location.getBlockZ() + 1));
		blocks.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY()+1, location.getBlockZ() - 1));
		
		blocks.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY()+2, location.getBlockZ() + 1));
		blocks.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY()+2, location.getBlockZ() - 1));
		blocks.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY()+2, location.getBlockZ() + 1));
		blocks.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY()+2, location.getBlockZ() - 1));
		
		blocks.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY()+3, location.getBlockZ() + 1));
		blocks.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY()+3, location.getBlockZ() - 1));
		blocks.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY()+3, location.getBlockZ() + 1));
		blocks.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY()+3, location.getBlockZ() - 1));
		
		
		blocks.add(world.getBlockAt(location));
		blocks.add(world.getBlockAt(location.getBlockX(),location.getBlockY() + 2, location.getBlockZ()));
		blocks.add(world.getBlockAt(location.getBlockX(),location.getBlockY() + 3, location.getBlockZ()));
		blocks.add(world.getBlockAt(location.getBlockX(),location.getBlockY() + 4, location.getBlockZ()));
		blocks.add(world.getBlockAt(location.getBlockX(),location.getBlockY() + 5, location.getBlockZ()));
		
		return blocks;
	}


	@Override
	public int getLevel() {
		return 5;
	}


}
