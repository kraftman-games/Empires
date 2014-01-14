package es.themin.empires.util;

import org.bukkit.Location;

public class Core {
	
	private enum CoreType { BASIC, MOB, FARM, MONSTER, GRIEF, FORTIFICATION;}
	private int Id;
	private CoreType type;
	private Location location;
	public Core(int Id, CoreType type, Location location) {
		this.Id = Id;
		this.type = type;
		this.location = location;
	}
	public int getId(){
		return Id;
	}
	public CoreType getType(){
		return type;
	}
	public Location getLocation(){
		return location;
	}
	public Location[] getFlagSlots() {
		CoreType type = getType();
		if (type == CoreType.BASIC) {
			Location[] slots = {new Location(location.getWorld(), location.getBlockX() + 1, location.getBlockY() + 2, location.getBlockZ()),new Location(location.getWorld(), location.getBlockX() - 1, location.getBlockY() + 2, location.getBlockZ()),new Location(location.getWorld(), location.getBlockX(), location.getBlockY() + 2, location.getBlockZ() + 1), new Location(location.getWorld(), location.getBlockX(), location.getBlockY() + 2, location.getBlockZ()+ 1)  };
			return slots;
		}
		else {
			return null;
		}
		
	}

}
