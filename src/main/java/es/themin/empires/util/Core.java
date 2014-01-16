
package es.themin.empires.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Core {
	
	enum CoreType {BASIC, MOB, FARM, MONSTER, GRIEF, FORTIFICATION;}
	private int Id;
	private CoreType type;
	private Location location;
	private int level;
	private Empire empire;
	
	// for testing
	public Core(){
		
	}
	
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
	public CoreType getType(){
		return type;
	}
	public Location getLocation(){
		return location;
	}
	public void setLocation(Location l) {
		this.location = l;
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
		//do we need to get the empire if we already have it? wont e == e2 ?
		Empire e2 = UtilityHashMaps.getEmpireWithId(this.empire.getId());
		e2.removeCore(this);
		this.empire = e;
		e.addCore(this);
		Save();
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
	public void build(Player myPlayer){
		//this will build the core
		Location myLocation = myPlayer.getLocation();
		this.setLocation(myLocation);
		Block b = myLocation.getBlock();
		b.setType(Material.IRON_BLOCK);
	}
	public void destroy(Material replacement){
		//destroys shit
	}
	public void Save() {
		if (UtilityHashMaps.containsCoreWithId(this.Id)) {
			int i = UtilityHashMaps.cores.indexOf(UtilityHashMaps.containsCoreWithId(this.Id));
			UtilityHashMaps.cores.remove(i);
		}
		UtilityHashMaps.cores.add(this);
	}

}

