
package es.themin.empires.cores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import es.themin.empires.Debug;
import es.themin.empires.empires;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.enums.PlaceType;
import es.themin.empires.schematics.Schematic;
import es.themin.empires.util.EPlayer;

public class Core {
	
	/* TODO
	* add a recovery cost per core
	* add an upgrade cost per core (per level too - use modifier)
	* 
	*/
	
	protected UUID myUUID;
	protected CoreType coreType;
	private Location location;
	private int level;
	private UUID empireUUID;
	private Schematic schem;
	protected ArrayList<CoreBlock> coreBlocks;
	
	// a tonne of this stuff isnt specific to the cores and needs to be moved to another object
	// ive started moving them to below for now
	protected Integer coreSize;
	protected Integer areaSize;
	protected ArrayList<CoreBlock> schematic;
	protected PlaceType placeType;
	protected int destroyCost;
	
	
	private EmpirePermission placePermission;
	
	
	public Core(UUID CoreUUID, CoreType type, Location location, UUID empireID) {
		
		this.coreSize = 8;
		this.myUUID = CoreUUID;
		this.empireUUID = empireID;
		this.coreType = type;
		this.location = location;
		
	}
	
	
	public Integer getCoreSize() {
		return coreSize;
	}

	public void setCoreSize(Integer coreSize) {
		this.coreSize = coreSize;
	}

	public Integer getAreaSize() {
		return areaSize;
	}

	public EmpirePermission getPlacePermission() {
		return placePermission;
	}


	public void setPlacePermission(EmpirePermission placePermission) {
		this.placePermission = placePermission;
	}


	public void setAreaSize(Integer areaSize) {
		this.areaSize = areaSize;
	}

	
	
	/**
	 * Creates a core that is generated by the player in game
	 */
	public Core(EPlayer myEPlayer) {
		
		
		this.coreSize = 8;
		this.empireUUID = myEPlayer.getEmpireUUID();
		this.location = myEPlayer.getLocation();
		this.level = 1;
		myUUID = UUID.randomUUID();
		//this.UUID = UUID.randomUUID()
		
//		this.setSchematic(CoreSchematic.getSchematic(myCoreType));
//		this.setDestroyCost(CoreSchematic.getDestroyCost(myCoreType));
//		this.setPlaceType(CoreSchematic.getPlaceType(myCoreType));
	}

	public CoreType getCoreType() {
		return coreType;
	}

	public void setCoreType(CoreType coreType) {
		this.coreType = coreType;
	}

	

	public void setId(UUID id) {
		myUUID = id;
	}

	public ArrayList<CoreBlock> getSchematic() {
		return schematic;
	}

	public void setSchematic(ArrayList<CoreBlock> schematic) {
		this.schematic = schematic;
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
	public UUID getEmpireUUID(){
		return this.empireUUID;
	}

	public void setEmpireUUID(UUID e){
		this.empireUUID = e;
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
	

	
	
	public boolean deploy(){
		
			this.build();
			//add to coreworld
			
			
			return true;
		//} 
		//return false;
	}
	
	public PlaceType getPlaceType() {
		return placeType;
	}

	public void setPlaceType(PlaceType placeType) {
		this.placeType = placeType;
	}
	public void build2() {
		this.getSchematic2().pasteFromCentre(this.location);
	}
	
	public void build() {
		Location myLocation = this.getLocation();
		for (CoreBlock myBlock : coreBlocks){
			Location newLocation = new Location(myLocation.getWorld(), myLocation.getX() + myBlock.getOffsetX(),
												myLocation.getY() + myBlock.getOffsetY(),
												myLocation.getZ() + myBlock.getOffsetZ());
			Block b = newLocation.getBlock();
			if (myBlock.getMaterial() != null){
				b.setType(myBlock.getMaterial());
			}
		}
	}
	


	
	public void destroy(){
		Location myLocation = this.getLocation();
		
		UUID myUUID = this.location.getWorld().getUID();
//		EWorld myCoreWorld = Worlds.getWorlds().get(myUUID);
//		myCoreWorld.removeCore(this);
		
		if (this.schematic != null){
			for (CoreBlock myBlock : this.schematic){
				Location newLocation = new Location(myLocation.getWorld(), myLocation.getX() + myBlock.getOffsetX(),
													myLocation.getY() + myBlock.getOffsetY(),
													myLocation.getZ() + myBlock.getOffsetZ());
				Block b = newLocation.getBlock();
				b.setType(Material.AIR);
			}
		}
	}


//	public void playerInteract(PlayerInteractEvent event, EPlayer myEPlayer) {
//		Player player = event.loadEPlayer();
//		//EPlayer myEPlayer = Players.loadEPlayer(player.getUniqueId());
//		
//		if (myEPlayer == null )
//			return;
//		
//		if (this.getType() == CoreType.GRIEF){
//			// treat it normally for residents
//			// allow insta break for enemies if not a protection block
//		} else {
//			
//			if (this.getEmpire() == myEPlayer.getEmpire()){
//				if (PlayerUtils.deductShards(player, this.getDestroyCost())){
//					this.Delete();
//					event.setCancelled(true);
//					Bukkit.broadcastMessage("deleted core block of type: " + this.getType());
//				} else {
//					player.sendMessage("You cannot afford to remove this core");
//				} 
//			}else {
//				Bukkit.broadcastMessage("cannot delete core block of type: " + this.getType());
//				event.setCancelled(true);
//			} 
//		}
//		
//	}

	public int getDestroyCost() {
		return destroyCost;
	}

	public void setDestroyCost(int destroyCost) {
		this.destroyCost = destroyCost;
	}
	
	public boolean isCoreBlock(Block myBlock){
		Integer x = this.getLocation().getBlockX();
		Integer z = this.getLocation().getBlockZ();
		Integer y = this.getLocation().getBlockY();
		
		Integer blockX = myBlock.getX();
		Integer blockY = myBlock.getY();
		Integer blockZ = myBlock.getZ();
		
		Debug.Console("Xmin: "+(x - coreSize)+" Zmin: "+ (z - coreSize)+ " BlockX: " + blockX + " BlockY: " + blockY );
		
		if (x - coreSize < blockX && x + coreSize > blockX){
			if (z - coreSize < blockZ && z + coreSize > blockZ){
				if (y - coreSize < blockY && y + coreSize > blockY){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isAreaBlock(Block myBlock){
		Integer x = this.getLocation().getBlockX();
		Integer z = this.getLocation().getBlockZ();
		Integer y = this.getLocation().getBlockY();
		
		if (x - this.getAreaSize() < myBlock.getX() && x + this.getAreaSize() > myBlock.getX()){
			if (z - this.getAreaSize() < myBlock.getZ() && z + this.getAreaSize() > myBlock.getZ()){
				return true;
			}
		}
		return false;
	}
	public Schematic getSchematic2() {
		for (Schematic schem : empires.schematics) {
			if (this.coreType == schem.coreType()) return schem;
		}
		return null;
	}

	public UUID getUUID() {
		return this.myUUID;
	}
	
	public UUID setUUID() {
		return this.myUUID;
	}
}

