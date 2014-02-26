package es.themin.empires.util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;

import es.themin.empires.Debug;
import es.themin.empires.cores.Core;
import es.themin.empires.enums.PlaceType;

public class EWorld {
	
	private UUID myUUID;
	
	private HashMap<UUID, Core> Cores;
	private Map<Point, HashMap<UUID, Core>> CoreGrid;
	private Integer GridSize = 400;
	private World world;
	private String name;
	private ArrayList<RegenBlock> regenblocks;
	
	//TODO: IMPORTANT. check if the core its overlapping is itself

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public EWorld(UUID uuid) {
		CoreGrid = new HashMap<Point, HashMap<UUID, Core>>();
		Cores = new HashMap<UUID, Core>();
		myUUID = uuid;
		this.regenblocks = new ArrayList<RegenBlock>();
	}

	public Integer getGridSize() {
		return GridSize;
	}

	public HashMap<UUID, Core> getCores() {
		return Cores;
	}

	public Core getCoreByID(Integer coreID){
		return Cores.get(coreID);
	}

	public void addCore(Core myCore){
		if (Cores.get(myCore.getUUID()) == null){
			this.Cores.put(myCore.getUUID(), myCore);
			this.addCoreToGrid(myCore);
		}
	}



	public void removeCore(Core myCore){
		this.Cores.remove(myCore.getUUID());
		this.removeCoreFromGrid(myCore);
	}

	private void addCoreToGrid(Core myCore) {
		int x = myCore.getLocation().getBlockX();
		int z = myCore.getLocation().getBlockZ();
		
		int areaSize = myCore.getAreaSize();
		Debug.Console("Area size: "+areaSize);
		
		for (int i = x-areaSize;i < x + areaSize; i +=areaSize){
			for (int j = z-areaSize;j < z + areaSize; j += areaSize){
				Point gridPoint = new Point((int)Math.floor(i/GridSize),(int)Math.floor(j/GridSize));
				//only add the core if its not listed already
				if (CoreGrid.get(gridPoint) == null) {
					CoreGrid.put(gridPoint, new HashMap<UUID,Core>());
				}
				
				if (!CoreGrid.get(gridPoint).containsKey((myCore.getUUID()))){
					CoreGrid.get(gridPoint).put(myCore.getUUID(), myCore);
				}
			}
		}
	}
	
	private void removeCoreFromGrid(Core myCore) {

		int x = myCore.getLocation().getBlockX();
		int z = myCore.getLocation().getBlockZ();
		
		int coreSize = myCore.getAreaSize();
		
		for (int i = x-coreSize;i <= x + coreSize; i +=coreSize){
			for (int j = z-coreSize;j <= z + coreSize; z += coreSize){
				Point gridPoint = new Point((int)Math.floor(i/GridSize),(int)Math.floor(j/GridSize));
				//remove the core if it exists
				if (CoreGrid.get(gridPoint).containsKey(myCore.getUUID())){
					CoreGrid.get(gridPoint).remove(myCore.getUUID());
				}
			}
		}
	}

	public HashMap<UUID, Core> getCoresInGrid(int x, int z){
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));	
		Debug.Console("X: "+gridPoint.getX()+" Z: "+gridPoint.getY());
		return CoreGrid.get(gridPoint);
	}
	
	public boolean hasCoresInGrid(int x, int z) {
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		if (!(CoreGrid.get(gridPoint) == null) && CoreGrid.get(gridPoint).values().size() > 0) {
			return true;
		}
		return false;
	}

	
	public HashMap<UUID, Core> getEnemyCoresInGrid(UUID myEmpireUUID, int x, int z){
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		HashMap<UUID, Core> allCores = CoreGrid.get(gridPoint);
		HashMap<UUID, Core> enemyCores = new HashMap<UUID, Core>();
		
		if (allCores == null){
			return null;
		}
		
		for (Core myCore : allCores.values()){
			if (myCore.getEmpireUUID().equals(myEmpireUUID) == false){
				enemyCores.put(myCore.getUUID(), myCore);
			}
		}
		if (enemyCores.isEmpty()){
			return null;
		}
		
		return enemyCores;
	}
	
	public HashMap<UUID, Core> getFriendlyCoresInGrid(UUID myEmpireUUID, int x, int z){
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		HashMap<UUID, Core> allCores = CoreGrid.get(gridPoint);
		HashMap<UUID, Core> friendlyCores = new HashMap<UUID, Core>();
		
		if (allCores == null){
			return null;
		}
		
		for (Core myCore : allCores.values()){
			if (myCore.getEmpireUUID().equals(myEmpireUUID)){
				friendlyCores.put(myCore.getUUID(), myCore);
			}
		}
		return friendlyCores;
	}
	
	public HashMap<UUID, Core> getFriendlyCoresInGrid(UUID myEmpireUUID, Point myPoint){
		return getFriendlyCoresInGrid(myEmpireUUID, myPoint.x, myPoint.y);
	}
	
	
	public boolean isNearEnemyCore(Core myCore){
		int range = getGridSize();
		
		Integer X = myCore.getLocation().getBlockX();
		Integer Z = myCore.getLocation().getBlockZ();
		
		for (int i = X-range;i <= X +range; i+=(range/2)){
			for (int j = Z-range;j <= Z+range; j+=(range/2)){
				HashMap<UUID, Core> myCores = this.getEnemyCoresInGrid(myCore.getEmpireUUID(),i, j);
				if (myCores != null ){
					return true;
				}
			}
		}
		return false;
	}

	public boolean isEdgeOfEmpire(Core myCore) {
		//get all nearby cores
		
		int areaSize = myCore.getAreaSize();
		int c1x1 = myCore.getLocation().getBlockX()-areaSize;
		int c1x2 = myCore.getLocation().getBlockX()+areaSize;
		int c1z1 = myCore.getLocation().getBlockZ()-areaSize;
		int c1z2 = myCore.getLocation().getBlockZ()+areaSize;
		
		for (int i = c1x1;i <= c1x2; i +=areaSize){
			for (int j = c1z1;j <= c1z2; j += areaSize){
				HashMap<UUID, Core> coreList = getFriendlyCoresInGrid(myCore.getEmpireUUID(),i,j);
				if (coreList == null){
					return false;
				}
				
				for (Core c : coreList.values()){
					if (!(c.equals(myCore))){
						int c2areaSize = c.getAreaSize();
						int c2x1 = c.getLocation().getBlockX()-c2areaSize;
						int c2x2 = c.getLocation().getBlockX()+c2areaSize;
						int c2z1 = c.getLocation().getBlockZ()-c2areaSize;
						int c2z2 = c.getLocation().getBlockZ()+c2areaSize;
						
						//if the square is not outside of the other square
						if(!(c1x2 < c2x1 || c1x1 > c2x2 || c1z2 < c2z1 || c1z1 > c2z2)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	public Point getCoords(Location location) {
		int x = location.getBlockX();
		int z = location.getBlockZ();
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		return gridPoint;
	}
	
	public int getDifferenceBetween(int int1, int int2) {
		if (int1 > int2) return int1-int2;
		if (int2 > int1) return int2-int1;
		return 0;
	}

	/**
	 * Checks if the core's bounds are within the empire
	 * @param myCore
	 * @return
	 */
	public boolean isInsideEmpire(Core myCore) {
		
		int areaSize = myCore.getAreaSize();
		int x = myCore.getLocation().getBlockX();
		int z = myCore.getLocation().getBlockZ();
		
		for (int i = x-areaSize;i <= x + areaSize; i +=areaSize){
			for (int j = z-areaSize;j <= z + areaSize; z += areaSize){
				Point myPoint = new Point(i,j);
				if (!isInEmpire(myCore, myPoint)){
					return false;
				}
			}
		}
		return true;
	}
	
	
	/**
	 * returns true if the point is inside the empire provided
	 * @param myEmpire
	 * @param myPoint
	 * @return
	 */
	public boolean isInEmpire(Core myCore, Point myPoint){
		
		int x = (int) myPoint.getX();
		int z = (int) myPoint.getY();
		
		HashMap<UUID, Core> myEmpireCores = getFriendlyCoresInGrid(myCore.getEmpireUUID(), x, z);
		if (myEmpireCores == null ){
			return false;
		}
		
		for (Core myFriendlyCore : myEmpireCores.values()){
			if (myFriendlyCore != myCore){
				int areaSize = myFriendlyCore.getAreaSize();
				int x1 = myFriendlyCore.getLocation().getBlockX()-areaSize;
				int x2 = myFriendlyCore.getLocation().getBlockX()+areaSize;
				int z1 = myFriendlyCore.getLocation().getBlockZ()-areaSize;
				int z2 = myFriendlyCore.getLocation().getBlockZ()+areaSize;
				
				if (x > x1 && x < x2 && z > z1 && z < z2){
					return true;
				}
			}
		}
		
		return false;
	}

	public HashMap<UUID, Core> getFriendlyCoresInGrid(UUID empireUUID,	Location location) {
		return getFriendlyCoresInGrid(empireUUID, location.getBlockX(), location.getBlockZ());
	}

	public UUID getUUID() {
		return myUUID;
	}

	public boolean coreLocationIsValid(EPlayer myEPlayer, Core myCore) {
		
		// check if its too close to another empire
		if (myCore.getPlaceType() == PlaceType.OUTSIDE || myCore.getPlaceType() == PlaceType.EDGE){
			if (isNearEnemyCore(myCore)){
				myEPlayer.sendMessage("You cannot expand this near to an enemy empire");
				return false;
			}
		}
		
		if (myCore.getPlaceType() == PlaceType.EDGE){
			if (!isEdgeOfEmpire(myCore)){
				myEPlayer.sendMessage("Amps must be placed on the edge of your empire");
				return false;
			}
		}
		
		
		if (myCore.getPlaceType() == PlaceType.INSIDE){
			if (!isInsideEmpire(myCore)){
				myEPlayer.sendMessage("Cannot place outside of empire");
				return false;
			}
		}
		
		//we need to check that the cores themselves dont overlap
		if (coresOverlap(myCore )){
			myEPlayer.sendMessage("Cores cannot overlap!");
			return false;
		}	
		
		return true;
	}
	
	public boolean coresOverlap( Core myCore){
		HashMap<UUID, Core> myCores = getFriendlyCoresInGrid(myCore.getEmpireUUID(), myCore.getLocation());
		
		if (myCores == null){
			return false;
		}
		
		int coreSize = myCore.getCoreSize();
		int c1x1 = myCore.getLocation().getBlockX()-coreSize;
		int c1x2 = myCore.getLocation().getBlockX()+coreSize;
		int c1z1 = myCore.getLocation().getBlockZ()-coreSize;
		int c1z2 = myCore.getLocation().getBlockZ()+coreSize;
		
		for(Core friendlyCore : myCores.values()){
			
			int coreSize2 = friendlyCore.getCoreSize();
			int c2x1 = friendlyCore.getLocation().getBlockX()-coreSize2;
			int c2x2 = friendlyCore.getLocation().getBlockX()+coreSize2;
			int c2z1 = friendlyCore.getLocation().getBlockZ()-coreSize2;
			int c2z2 = friendlyCore.getLocation().getBlockZ()+coreSize2;
			
			if(!(c1x2 < c2x1 || c1x1 > c2x2 || c1z2 < c2z1 || c1z1 > c2z2)){
				return true;
			}
		}
		return false;
	}

	public UUID getEmpireUUID(Location newLocation) {
		int x = (int) newLocation.getX();
		int z = (int) newLocation.getZ();
		
		HashMap<UUID, Core> myCores = getCoresInGrid(x, z);
		if (myCores == null ){
			return null;
		}
		
		for (Core myCore : myCores.values()){				
			if (myCore.isInArea(x,  z)){
				return myCore.getEmpireUUID();
			}
		}
		return null;
	}
	
	public HashMap<UUID, Core> getCores(Integer locX, Integer locZ){
		HashMap<UUID, Core> myCores = getCoresInGrid(locX, locZ);
		if (myCores == null ){
			return null;
		}
		
		HashMap<UUID, Core> myOverlappingCores = new HashMap<UUID, Core>();
		
		for (Core myCore : myCores.values()){				
			if (myCore.isInArea(locX,  locZ)){
				myOverlappingCores.put(myCore.getUUID(), myCore);
			}
		}
		return myOverlappingCores;
	}

	public HashMap<UUID, Core> getCores(Location newLocation) {
		return getCores(newLocation.getBlockX(), newLocation.getBlockZ());
	}
	public void regenAllBlocks() {
		for (RegenBlock b : regenblocks) {
			b.regen();
		}
	}
	public void addRegenBlocks(ArrayList<RegenBlock> blocks) {
		for (RegenBlock b :blocks) {
			regenblocks.add(b);
		}
	}
	public void removeRegenBlocks(ArrayList<RegenBlock> blocks) {
		for (RegenBlock b :blocks) {
			regenblocks.remove(b);
		}
	}
	
	
}





















