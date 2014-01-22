
package es.themin.empires.cores;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import es.themin.empires.empires;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.PlaceType;
import es.themin.empires.util.BlockUtils;
import es.themin.empires.util.CoreWorld;
import es.themin.empires.util.Empire;
import es.themin.empires.util.PlayerUtils;
import es.themin.empires.util.UtilManager;

public class Core {
	
	/* TODO
	* add a recovery cost per core
	* add an upgrade cost per core (per level too - use modifier)
	* 
	*/
	
	private int Id;
	private CoreType coreType;
	private Location location;
	private Integer size;
	private int level;
	private Empire empire;
	private ArrayList<CoreBlock> schematic;
	private PlaceType placeType;
	private int destroyCost;
	
	public Core(int Id, CoreType type, Location location, int level, Empire empire) {
		this.size = 16;
		this.empire = empire;
		this.Id = Id;
		this.coreType = type;
		this.location = location;
		this.level = level;
		this.setSchematic(CoreSchematic.getSchematic(type));
		this.setDestroyCost(CoreSchematic.getDestroyCost(type));
		this.setPlaceType(CoreSchematic.getPlaceType(type));
	}
	
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public CoreType getCoreType() {
		return coreType;
	}

	public void setCoreType(CoreType coreType) {
		this.coreType = coreType;
	}

	

	public void setId(int id) {
		Id = id;
	}

	public ArrayList<CoreBlock> getSchematic() {
		return schematic;
	}

	public void setSchematic(ArrayList<CoreBlock> schematic) {
		this.schematic = schematic;
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
		Empire e2 = this.empire;
		e2.removeCore(this);
		this.empire = e;
		e.ac(this);
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
//	public void build(Player myPlayer){
//		//this will build the core
//		Location myLocation = myPlayer.getLocation();
//		CoreSchematic myCoreSchematic = new CoreSchematic();
//		myCoreSchematic.build(coreType, myPlayer);
//		this.setLocation(myLocation);
//		
//	}
	
	public boolean canPlace(){
		//if its an amp/base/outpost, check distance to nearest enemy amps
		//if its not, check its within the boundaries of the players empire
		//if its an amp it must have an edge next to another amp
		Location myLocation = this.getLocation();

		empires myPlugin = (empires) Bukkit.getPluginManager().getPlugin("Empires");
		
		int overLap = 0;
		
		// check if its too close to another empire
		if (this.getPlaceType() == PlaceType.OUTSIDE || this.getPlaceType() == PlaceType.EDGE)
		for (int x = -50; x <= 50; x++){
			for (int z = -50; z <= 50; z++){
				Location newLocation = new Location(myLocation.getWorld(), myLocation.getX() + x,
						myLocation.getY() ,
						myLocation.getZ() +z);
				Block b = newLocation.getBlock();
				Empire blockEmpire = (BlockUtils.getEmpireFromBlock(b, myPlugin));
				if (blockEmpire != null){
					if (blockEmpire != this.getEmpire()){
						//probably wanna return the reason at some point
						return false;
					} else {
						overLap++;
					}
				}
			}
		}
		
		if (this.getPlaceType() == PlaceType.EDGE && overLap > 6){
			return true;
		}
		
				
		if (this.schematic != null){
			for (CoreBlock myBlock : this.schematic){
				Location newLocation = new Location(myLocation.getWorld(), myLocation.getX() + myBlock.getOffsetX(),
													myLocation.getY() + myBlock.getOffsetY(),
													myLocation.getZ() + myBlock.getOffsetZ());
				Block b = newLocation.getBlock();
				
				Empire myEmpire = BlockUtils.getEmpireFromBlock(b, myPlugin);
				if (myEmpire == null){
					return false;
				}
			}
		}
		return true;
	}
	
	
	public boolean deploy(){
		if (this.canPlace()){
			this.protect(true);
			this.build();
			//add to coreworld
			UUID myUUID = this.location.getWorld().getUID();
			CoreWorld myCoreWorld = UtilManager.getWorlds().get(myUUID);
			myCoreWorld.addCore(this);
			
			return true;
		} 
		return false;
	}
	
	public PlaceType getPlaceType() {
		return placeType;
	}

	public void setPlaceType(PlaceType placeType) {
		this.placeType = placeType;
	}

	public void protect(boolean setProtected){
		Location myLocation = this.getLocation();
		JavaPlugin myPlugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("Empires");
		
		
		if (this.schematic != null){
			for (CoreBlock myBlock : this.schematic){
				Location newLocation = new Location(myLocation.getWorld(), myLocation.getX() + myBlock.getOffsetX(),
													myLocation.getY() + myBlock.getOffsetY(),
													myLocation.getZ() + myBlock.getOffsetZ());
				Block b = newLocation.getBlock();

				FixedMetadataValue myCoreID = new FixedMetadataValue (myPlugin, this.getId());
				FixedMetadataValue myEmpireID = new FixedMetadataValue (myPlugin, this.getEmpire().getId());
				FixedMetadataValue myGriefMeta = new FixedMetadataValue (myPlugin, myBlock.getProtection());
				
				if (setProtected){
					b.setMetadata("core", myCoreID);
					b.setMetadata("empire", myEmpireID);
					b.setMetadata("protection", myGriefMeta);}
				else {
					b.removeMetadata("core", myPlugin);
					b.removeMetadata("empire", myPlugin);
					b.removeMetadata("protection", myPlugin);
				}					
			}
		}
	}
	
	public void build() {
		Location myLocation = this.getLocation();
		
		if (this.schematic != null){
			for (CoreBlock myBlock : this.schematic){
				Location newLocation = new Location(myLocation.getWorld(), myLocation.getX() + myBlock.getOffsetX(),
													myLocation.getY() + myBlock.getOffsetY(),
													myLocation.getZ() + myBlock.getOffsetZ());
				Block b = newLocation.getBlock();
				if (myBlock.getMaterial() != null){
					b.setType(myBlock.getMaterial());
				}
			}
		}
	}
	

	
	public void Save() {
		if (UtilManager.containsCoreWithId(this.Id)) {
			int i = UtilManager.cores.indexOf(UtilManager.containsCoreWithId(this.Id));
			UtilManager.cores.remove(i);
		}
		UtilManager.cores.add(this);
	}
	public void Delete() {
		this.protect(false);
		this.destroy();
		UtilManager.getEmpireWithCore(this).removeCore(this);
		if (UtilManager.containsCoreWithId(this.Id)) {
			int i = UtilManager.cores.indexOf(this);
			UtilManager.cores.remove(i);
		}
		
	}
	
	public void destroy(){
		Location myLocation = this.getLocation();
		
		UUID myUUID = this.location.getWorld().getUID();
		CoreWorld myCoreWorld = UtilManager.getWorlds().get(myUUID);
		myCoreWorld.removeCore(this);
		
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


	public void playerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (this.getType() == CoreType.GRIEF){
			// treat it normally for residents
			// allow insta break for enemies if not a protection block
		} else {
			
			if (this.getEmpire() == UtilManager.empireplayers.get(player.getName())){
				if (PlayerUtils.deductShards(player, this.getDestroyCost())){
					this.Delete();
					event.setCancelled(true);
					Bukkit.broadcastMessage("deleted core block of type: " + this.getType());
				} else {
					player.sendMessage("You cannot afford to remove this core");
				} 
			}else {
				Bukkit.broadcastMessage("cannot delete core block of type: " + this.getType());
				event.setCancelled(true);
			} 
		}
		
	}

	public int getDestroyCost() {
		return destroyCost;
	}

	public void setDestroyCost(int destroyCost) {
		this.destroyCost = destroyCost;
	}

}

