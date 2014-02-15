package es.themin.empires.schematics;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Block;

import es.themin.empires.enums.CoreType;
import es.themin.empires.util.FlagMatrix;

public abstract class Schematic {
	public abstract void pasteFromCentre(Location location);
	public abstract void destroyFromCentre(Location location);
	public abstract CoreType coreType();
	public abstract ArrayList<Block> getBlocks(Location location);
	public abstract int getLevel();
}
