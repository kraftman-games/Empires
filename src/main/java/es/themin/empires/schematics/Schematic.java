package es.themin.empires.schematics;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import es.themin.empires.empires;
import es.themin.empires.enums.CoreType;

public abstract class Schematic {
	public abstract CoreType coreType();
	public abstract int getLevel();
	public abstract void pasteFromCentre(Location location);
	public void destroyFromCentre(Location location) {
		for (Block b:getBlocks(location)) {
			b.setType(Material.AIR);
		}
	}
	
	public abstract ArrayList<Block> getBlocks(Location location);
	
	public boolean isSafeToBuildAround(Location location) {
		for (Block b:getBlocks(location)) {
			Material m = b.getType();
			if (!empires.destroyable.contains(m)) {
				return false;
			}
		}
		return true;
	}
}
