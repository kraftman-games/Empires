
package es.themin.empires.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Core {
	
	enum CoreType {BASIC, MOB, FARM, MONSTER, GRIEF, FORTIFICATION;}
	private int Id;
	private CoreType type;
	private Location location;
	private int level;
	private Empire empire;
	public Core(int Id, CoreType type, Location location, int level, Empire empire) {
		this.empire = empire;
		this.Id = Id;
		this.type = type;
		this.location = location;
		this.level = level;
	}
	public int getId(){
		
		return Id;
	}
	public void setId(int Id){
		if (UtilityHashMaps.cores.contains(this)) UtilityHashMaps.cores.remove(this);
		this.Id = Id;
		UtilityHashMaps.cores.add(this);
	}
	public CoreType getType(){
		return type;
	}
	public Location getLocation(){
		return location;
	}
	public void setLocation(Location l) {
		if (UtilityHashMaps.cores.contains(this)) UtilityHashMaps.cores.remove(this);
		this.location = l;
		UtilityHashMaps.cores.add(this);
	}
	public int getLevel(){
		return level;
	}
	public void setLevel(int level){
		this.level = level;
	}
	public Empire getEmpire(){
		return empire;
	}
	public void setEmpire(Empire e){
		this.empire = e;
		UtilityHashMaps.empires.get(UtilityHashMaps.empires.indexOf(this.empire)).removeCore(this);
		UtilityHashMaps.empires.get(UtilityHashMaps.empires.indexOf(e)).addCore(this);
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
	public void setFlag(int position){
		
	}
	public void setFlag(){
		
	}
	public void build(){
		//this will build the core
		Block b = location.getBlock();
		b.setType(Material.IRON_BLOCK);
	}
	public void destroy(Material replacement){
		//destroys shit
	}

}

