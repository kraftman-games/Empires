package es.themin.empires.cores;

import java.util.UUID;

import org.bukkit.Location;

import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.PlaceType;

public interface ICore {

	
	public CoreType getCoreType();
	public PlaceType getPlaceType();
	public Location getLocation();
	public UUID getUUID();
	public UUID getEmpireUUID();
	public Integer getAreaSize();
	public Integer getCoreSize();
}
