package es.themin.empires.schematics;

import org.bukkit.Location;

public abstract class Schematic {
	public abstract void pasteFromCentre(Location location);
	public abstract void destroyFromCentre(Location location);
}
