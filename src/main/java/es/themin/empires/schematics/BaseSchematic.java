package es.themin.empires.schematics;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import es.themin.empires.enums.CoreType;
import es.themin.empires.util.FlagMatrix;
import es.themin.empires.util.UtilManager;

public class BaseSchematic extends Schematic{

	@Override
	public void pasteFromCentre(Location location) {
		
		World world = location.getWorld();
		Location ironcorner1 = new Location(location.getWorld(), location.getX() - 1, location.getY() - 1, location.getZ() - 1);
		Location ironcorner2 = new Location(location.getWorld(), location.getX() + 1, location.getY() - 1, location.getZ() + 1);
		UtilManager.loopAndSet(ironcorner1, ironcorner2, Material.IRON_BLOCK);
		Location obsidiancorner1 = new Location(location.getWorld(), location.getX() - 1, location.getY(), location.getZ() - 1);
		Location obsidiancorner2 = new Location(location.getWorld(), location.getX() + 1, location.getY(), location.getZ() + 1);
		UtilManager.loopAndSet(obsidiancorner1, obsidiancorner2, Material.OBSIDIAN);
		Block beacon = world.getBlockAt(location);
		beacon.setType(Material.BEACON);
		
		ArrayList<Block> sobs = new ArrayList<Block>();
		
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 1, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 1, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 1, location.getBlockZ() - 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 1, location.getBlockZ() - 1));
		
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 3, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 3, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 3, location.getBlockZ() - 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 3, location.getBlockZ() - 1));
		
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 4, location.getBlockZ()));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 4, location.getBlockZ()));
		sobs.add(world.getBlockAt(location.getBlockX(), location.getBlockY() + 4, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX(), location.getBlockY() + 4, location.getBlockZ() - 1));
		
		for (Block myObsidian : sobs) {
			myObsidian.setType(Material.OBSIDIAN);
		}
	}

	@Override
	public void destroyFromCentre(Location location) {
		World world = location.getWorld();
		Location ironcorner1 = new Location(location.getWorld(), location.getX() - 1, location.getY() - 1, location.getZ() - 1);
		Location ironcorner2 = new Location(location.getWorld(), location.getX() + 1, location.getY() - 1, location.getZ() + 1);
		UtilManager.loopAndSet(ironcorner1, ironcorner2, Material.AIR);
		Location obsidiancorner1 = new Location(location.getWorld(), location.getX() - 1, location.getY(), location.getZ() - 1);
		Location obsidiancorner2 = new Location(location.getWorld(), location.getX() + 1, location.getY(), location.getZ() + 1);
		UtilManager.loopAndSet(obsidiancorner1, obsidiancorner2, Material.AIR);
		Block beacon = world.getBlockAt(location);
		beacon.setType(Material.AIR);
		
		ArrayList<Block> sobs = new ArrayList<Block>();
		
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 1, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 1, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 1, location.getBlockZ() - 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 1, location.getBlockZ() - 1));
		
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 2, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 2, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 2, location.getBlockZ() - 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 2, location.getBlockZ() - 1));

		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 3, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 3, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 3, location.getBlockZ() - 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 3, location.getBlockZ() - 1));
		
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 4, location.getBlockZ()));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 4, location.getBlockZ()));
		sobs.add(world.getBlockAt(location.getBlockX(), location.getBlockY() + 4, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX(), location.getBlockY() + 4, location.getBlockZ() - 1));
		
		for (Block myObsidian : sobs) {
			myObsidian.setType(Material.AIR);
		}
	}

	@Override
	public CoreType coreType() {
		return CoreType.BASE;
	}

	@Override
	public ArrayList<Block> getBlocks(Location location) {
		ArrayList<Block> sobs = new ArrayList<Block>();
		World world = location.getWorld();
		Location ironcorner1 = new Location(location.getWorld(), location.getX() - 1, location.getY() - 1, location.getZ() - 1);
		Location ironcorner2 = new Location(location.getWorld(), location.getX() + 1, location.getY() - 1, location.getZ() + 1);
		sobs = UtilManager.loopAndAddToList(ironcorner1, ironcorner2, sobs);
		Location obsidiancorner1 = new Location(location.getWorld(), location.getX() - 1, location.getY(), location.getZ() - 1);
		Location obsidiancorner2 = new Location(location.getWorld(), location.getX() + 1, location.getY(), location.getZ() + 1);
		sobs = UtilManager.loopAndAddToList(obsidiancorner1, obsidiancorner2, sobs);
		Block beacon = world.getBlockAt(location);
		sobs.add(beacon);
		
		
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 1, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 1, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 1, location.getBlockZ() - 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 1, location.getBlockZ() - 1));
		
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 2, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 2, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 2, location.getBlockZ() - 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 2, location.getBlockZ() - 1));

		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 3, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 3, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 3, location.getBlockZ() - 1));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 3, location.getBlockZ() - 1));
		
		sobs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 4, location.getBlockZ()));
		sobs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 4, location.getBlockZ()));
		sobs.add(world.getBlockAt(location.getBlockX(), location.getBlockY() + 4, location.getBlockZ() + 1));
		sobs.add(world.getBlockAt(location.getBlockX(), location.getBlockY() + 4, location.getBlockZ() - 1));
		return sobs;
	}

	@Override
	public ArrayList<Block> getFlagBlocks(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public void setFlagBlock(Location location, FlagMatrix fm) {
		// TODO Auto-generated method stub
		
	}

}
