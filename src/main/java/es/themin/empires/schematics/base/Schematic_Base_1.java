package es.themin.empires.schematics.base;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

import es.themin.empires.enums.CoreType;
import es.themin.empires.schematics.Schematic;
import es.themin.empires.util.UtilManager;

public class Schematic_Base_1 extends Schematic{

	@Override
	public CoreType coreType() {
		return CoreType.BASE;
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public void pasteFromCentre(Location location) {
		playEffectsFrom(location);
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
		
		ArrayList<Block> obs = new ArrayList<Block>();
		obs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 3, location.getBlockZ() + 1));
		obs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 3, location.getBlockZ() + 1));
		obs.add(world.getBlockAt(location.getBlockX() + 1, location.getBlockY() + 3, location.getBlockZ() - 1));
		obs.add(world.getBlockAt(location.getBlockX() - 1, location.getBlockY() + 3, location.getBlockZ() - 1));
		for (Block b : obs) {
			b.setType(Material.DIAMOND_BLOCK);
		}

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
	public void playEffectsFrom(Location location) {
		boolean e01 = false;
		Location l = location;
		World w = l.getWorld();
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		Vector up = new Vector(0,0.5,0);
		Vector down = new Vector(0,-0.5,0);
		Vector north = new Vector(0,0,-0.5);
		Vector south = new Vector(0,0,0.5);
		Vector west = new Vector(-0.5,0,0);
		Vector east = new Vector(0.5,0,0);
		w.getBlockAt(l).setType(Material.AIR);
		 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Bukkit.getServer().getLogger().info("Effect error 1");
			}
		Block e011a = w.getBlockAt(x,y,z-1);
        FallingBlock e011 = w.spawnFallingBlock(new Location(w,x+1,y,z), Material.OBSIDIAN, (byte) 0);
        FallingBlock e012 = w.spawnFallingBlock(new Location(w,x-1,y,z), Material.OBSIDIAN, (byte) 0);
        FallingBlock e013 = w.spawnFallingBlock(new Location(w,x,y,z+1), Material.OBSIDIAN, (byte) 0);
        FallingBlock e014 = w.spawnFallingBlock(new Location(w,x,y,z-1), Material.OBSIDIAN, (byte) 0);
        /*e011.setVelocity(north);
        e012.setVelocity(east);
        e013.setVelocity(south);
        e014.setVelocity(west);*/
        e011.setVelocity(up);
        e012.setVelocity(up);
        e013.setVelocity(up);
        e014.setVelocity(up);
        /*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			Bukkit.getServer().getLogger().info("Effect error 1");
		}
		e01 = true;
		e011.remove();
		e012.remove();
		e013.remove();
		e014.remove();
		w.getBlockAt(x,y,z+1).setType(Material.OBSIDIAN);
		w.getBlockAt(x,y,z-1).setType(Material.OBSIDIAN);
		w.getBlockAt(x+1,y,z).setType(Material.OBSIDIAN);
		w.getBlockAt(x-1,y,z).setType(Material.OBSIDIAN);*/

	}

	@Override
	public Effect getEffectType() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean blocksAreCloseHorizontally(Block block1, Location block2) {
		double x1 = block1.getX();
		double z1 = block1.getZ();
		double x2 = block2.getX();
		double z2 = block2.getZ();
		double x3 = getPositiveDouble(x1-x2);
		double z3 = getPositiveDouble(z1-z2);
		if (x3 <= 0.05 && z3 <= 0.05) return true;
		return false;
	}
	public double getPositiveDouble(Double d) {
		if (d<0) {
			return -d;
		}
		return d;
	}
}
