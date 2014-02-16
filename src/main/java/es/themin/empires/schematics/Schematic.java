package es.themin.empires.schematics;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;

import es.themin.empires.empires;
import es.themin.empires.enums.CoreType;

public abstract class Schematic {
	public abstract CoreType coreType();
	public abstract int getLevel();
	public abstract Effect getEffectType();
	public void playEffectsFrom(Location location) {
		World w = location.getWorld();
		Random r = new Random();
		for (Block b : getBlocks(location)) {
			int i = r.nextInt(10);
			if (i==5) {
				if (getEffectType() != null) {
					w.playEffect(b.getLocation(), getEffectType(), 3);;
				}
				w.playSound(b.getLocation(), Sound.ANVIL_USE, 1, 3);
				
			}
		}

	}
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
