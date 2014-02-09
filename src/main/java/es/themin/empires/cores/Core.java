
package es.themin.empires.cores;

import java.util.ArrayList;
import java.util.HashMap;
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
	private Integer coreSize;
	private Integer areaSize;
	private int level;
	private Empire empire;
	private ArrayList<CoreBlock> schematic;
	private PlaceType placeType;
	private int destroyCost;
	private empires myPlugin;
	
	private HashMap<Location, Block> GriefedBlocks = new HashMap<Location, Block>();
	
	public void addGriefedBlock(Block myBlock){
		GriefedBlocks.put(myBlock.getLocation(), myBlock);
		//start a recovery timer if there isnt one already
		
	}
	
	public Integer getCoreSize() {
		return coreSize;
	}

	public void setCoreSize(Integer coreSize) {
		this.coreSize = coreSize;
	}

	public Integer getAreaSize() {
		//return areaSize;
		//what is this
		return 1;
	}

	public void setAreaSize(Integer areaSize) {
		this.areaSize = areaSize;
	}

	public Core(empires plugin,int Id, CoreType type, Location location, int level, Empire empire) {
		this.myPlugin = plugin;
		this.coreSize = 8;
		this.empire = empire;
		this.Id = Id;
		this.coreType = type;
		this.location = location;
		this.level = level;
		this.setSchematic(CoreSchematic.getSchematic(type));
		this.setDestroyCost(CoreSchematic.getDestroyCost(type));
		this.setPlaceType(CoreSchematic.getPlaceType(type));
	}
	
	

	/**
	 * Creates a core that is generated by the player in game
	 */
	public Core(Player myPlayer, CoreType myCoreType) {
		this.coreSize = 8;
		this.empire = UtilManager.getEmpireWithPlayer(myPlayer);
		this.coreType = myCoreType;
		this.location = myPlayer.getLocation();
		this.level = 1;
		this.setSchematic(CoreSchematic.getSchematic(myCoreType));
		this.setDestroyCost(CoreSchematic.getDestroyCost(myCoreType));
		this.setPlaceType(CoreSchematic.getPlaceType(myCoreType));
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
			int i = myPlugin.cores.indexOf(UtilManager.containsCoreWithId(this.Id));
			myPlugin.cores.remove(i);
		}
		myPlugin.cores.add(this);
	}
	public void Delete() {
		this.destroy();
		UtilManager.getEmpireWithCore(this).removeCore(this);
		if (UtilManager.containsCoreWithId(this.Id)) {
			int i = myPlugin.cores.indexOf(this);
			myPlugin.cores.remove(i);
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
			
			if (this.getEmpire() == myPlugin.empireplayers.get(player.getName())){
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
	
	public boolean isCoreBlock(Block myBlock){
		Integer x = this.getLocation().getBlockX();
		Integer z = this.getLocation().getBlockZ();
		Integer y = this.getLocation().getBlockY();
		
		if (x - this.getCoreSize() < myBlock.getX() && x + this.getCoreSize() > myBlock.getX()){
			if (z - this.getCoreSize() < myBlock.getZ() && z + this.getCoreSize() > myBlock.getZ()){
				if (y - this.getCoreSize() < myBlock.getY() && z + this.getCoreSize() > myBlock.getY()){
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

}

