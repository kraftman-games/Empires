package es.themin.empires.schematics;

import org.bukkit.Location;

import es.themin.empires.enums.CoreType;

public abstract class Schematic {
	public abstract void pasteFromCentre(Location location);
	public abstract void destroyFromCentre(Location location);
	public abstract CoreType coreType();
}
