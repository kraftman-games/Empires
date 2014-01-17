
package es.themin.empires.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import enums.CoreType;

public class Core {
	
	
	private int Id;
	private CoreType coreType;
	private Location location;
	private int level;
	private Empire empire;
	
	// for testing
	public Core(){
		
	}
	
	public Core(int Id, CoreType type, Location location, int level, Empire empire) {
		this.empire = empire;
		this.Id = Id;
		this.coreType = type;
		this.location = location;
		this.level = level;
	}
	public int getId(){
		
		return Id;
	}
	public CoreType getType(){
		return coreType;
	}
	public void setType(CoreType myCoreType){
		this.coreType =  myCoreType;
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
		Empire e2 = UtilManager.getEmpireWithId(this.empire.getId());
		e2.removeCore(this);
		this.empire = e;
		e.addCore(this);
		Save();
	}
	public Location[] getFlagSlots() {
		CoreType type = getType();
		if (type == CoreType.BASE) {
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
		CoreSchematic myCoreSchematic = new CoreSchematic();
		myCoreSchematic.build(coreType, myPlayer);
		this.setLocation(myLocation);
		
	}
	public void destroy(Material replacement){
		//destroys shit
	}
	public void Save() {
		if (UtilManager.containsCoreWithId(this.Id)) {
			int i = UtilManager.cores.indexOf(UtilManager.containsCoreWithId(this.Id));
			UtilManager.cores.remove(i);
		}
		UtilManager.cores.add(this);
	}

}

