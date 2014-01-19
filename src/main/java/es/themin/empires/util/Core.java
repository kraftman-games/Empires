
package es.themin.empires.util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
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

import es.themin.empires.enums.CoreType;

public class Core {
	
	
	private int Id;
	private CoreType coreType;
	private Location location;
	private int level;
	private Empire empire;
	private ArrayList<CoreBlock> schematic;
	
	public ArrayList<CoreBlock> getSchematic() {
		return schematic;
	}

	public void setSchematic(ArrayList<CoreBlock> schematic) {
		this.schematic = schematic;
	}

	
	public Core(int Id, CoreType type, Location location, int level, Empire empire) {
		this.empire = empire;
		this.Id = Id;
		this.coreType = type;
		this.location = location;
		this.level = level;
		this.schematic = CoreSchematic.getSchematic(type);
		this.setProtection(true);
		this.build();
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
	
	public void setProtection(boolean setProtected){
		Location myLocation = this.getLocation();
		JavaPlugin myPlugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("Empires");
		
		if(this.getType() == CoreType.GRIEF){
			for (int x = -16; x <= 16; x++){
				for (int z = -16; z <= 16; z++){
					for (int y = 0; y <= 256; y++){
						
						Location tempLocation = new Location(myLocation.getWorld(), myLocation.getX() + x, myLocation.getY() + y, myLocation.getZ() + z);
						Block b = tempLocation.getBlock();
						
						FixedMetadataValue myMetaData = new FixedMetadataValue (myPlugin, this.getEmpire().getId());
						b.setMetadata("empire", myMetaData);
						
					}
				}				
			}
			
			
		}
		
		if (this.schematic != null){
			for (CoreBlock myBlock : this.schematic){
				Location newLocation = new Location(myLocation.getWorld(), myLocation.getX() + myBlock.getOffsetX(),
													myLocation.getY() + myBlock.getOffsetY(),
													myLocation.getZ() + myBlock.getOffsetZ());
				Block b = newLocation.getBlock();
				
				
				
			
				FixedMetadataValue myMetaData2 = new FixedMetadataValue (myPlugin, this.getId());
				if (setProtected){
					b.setMetadata("core", myMetaData2);}
				else {
					b.removeMetadata("core", myPlugin);
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
				b.setType(myBlock.getMaterial());
			}
		}
	}
	
	public void destroy(){
		Location myLocation = this.getLocation();
		
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
	
	public void Save() {
		if (UtilManager.containsCoreWithId(this.Id)) {
			int i = UtilManager.cores.indexOf(UtilManager.containsCoreWithId(this.Id));
			UtilManager.cores.remove(i);
		}
		UtilManager.cores.add(this);
	}
	public void Delete() {
		this.setProtection(false);
		this.destroy();
		UtilManager.getEmpireWithCore(this).removeCore(this);
		if (UtilManager.containsCoreWithId(this.Id)) {
			int i = UtilManager.cores.indexOf(this);
			UtilManager.cores.remove(i);
		}
		
	}


	public void onBlockBreak(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (this.getEmpire() == UtilManager.empireplayers.get(player.getName())){
			Inventory myInventory = player.getInventory();
	          for(ItemStack myStack : myInventory.getContents()){
	              if(myStack!= null && myStack.getType().equals(Material.FLINT)){
	            	  ItemMeta myMeta = myStack.getItemMeta();
	            	  if (myMeta.getDisplayName() != null && myMeta.getDisplayName().equals("Core Shard") && myStack.getAmount() > 1){
	            		  Bukkit.broadcastMessage("deleted core block of type: " + this.getType());
	            		  if(myStack.getAmount() == 2) {
	            			  myInventory.remove(myStack);
	            		  } else {
	            			  myStack.setAmount(myStack.getAmount()-2);
	            		  }
	            		  player.updateInventory();
	            		  this.Delete();
	            	  }
	              }
	          }
	          
	          event.setCancelled(true);
			  player.sendMessage("You cannot afford to remove your core");
		}else {
			Bukkit.broadcastMessage("cannot delete core block of type: " + this.getType());
			event.setCancelled(true);
		}
		
	}

}

