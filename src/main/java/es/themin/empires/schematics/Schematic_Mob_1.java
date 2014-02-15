package es.themin.empires.schematics;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import es.themin.empires.enums.CoreType;
import es.themin.empires.util.FlagMatrix;
import es.themin.empires.util.UtilManager;

public class Schematic_Mob_1 extends Schematic{

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
		nstairs.add(world.getBlockAt(location.getBlockX() + 1,location.getBlockY(), location.getBlockZ() - 2));
		nstairs.add(world.getBlockAt(location.getBlockX() - 1,location.getBlockY(), location.getBlockZ() - 2));
		nstairs.add(world.getBlockAt(location.getBlockX(),location.getBlockY() + 3, location.getBlockZ() - 1));
		UtilManager.setStairsFromList(nstairs, Material.getMaterial(108), BlockFace.NORTH);
		
		ArrayList<Block> sstairs = new ArrayList<Block>();
		sstairs.add(world.getBlockAt(location.getBlockX() + 1,location.getBlockY(), location.getBlockZ() + 2));
		sstairs.add(world.getBlockAt(location.getBlockX() - 1,location.getBlockY(), location.getBlockZ() + 2));
		sstairs.add(world.getBlockAt(location.getBlockX(),location.getBlockY() + 3, location.getBlockZ() + 1));
		UtilManager.setStairsFromList(nstairs, Material.getMaterial(108), BlockFace.SOUTH);
		UtilManager.setStairsUpData(Material.getMaterial(108),world.getBlockAt(location.getBlockX() + 1,location.getBlockY() + 2, location.getBlockZ()) ,BlockFace.WEST);
	}

	@Override
	public void destroyFromCentre(Location location) {
		ArrayList<Block> blocks = new ArrayList<Block>();
	}

	@Override
	public CoreType coreType() {
		return CoreType.MOB;
	}

	@Override
	public ArrayList<Block> getBlocks(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Block> getFlagBlocks(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFlagBlock(Location location, FlagMatrix fm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLevel() {
		return 0;
	}
	

}
