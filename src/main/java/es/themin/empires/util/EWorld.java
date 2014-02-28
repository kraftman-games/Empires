package es.themin.empires.util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.World;

import es.themin.empires.Debug;
import es.themin.empires.cores.Core;
import es.themin.empires.cores.ICore;
import es.themin.empires.enums.PlaceType;

public class EWorld {
	
	private UUID myUUID;
	
	private ConcurrentHashMap<UUID, ICore> Cores;
	private ConcurrentHashMap<Point, ConcurrentHashMap<UUID, ICore>> CoreGrid;
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
		CoreGrid = new ConcurrentHashMap<Point, ConcurrentHashMap<UUID, ICore>>();
		Cores = new ConcurrentHashMap<UUID, ICore>();
		myUUID = uuid;
		this.regenblocks = new ArrayList<RegenBlock>();
	}

	public Integer getGridSize() {
		return GridSize;
	}

	public ConcurrentHashMap<UUID, ICore> getCores() {
		return Cores;
	}

	public ICore getCoreByID(Integer coreID){
		return Cores.get(coreID);
	}

	public void addCore(ICore myCore){
		if (Cores.get(myCore.getUUID()) == null){
			this.Cores.put(myCore.getUUID(), myCore);
			this.addCoreToGrid(myCore);
		}
	}



	public void removeCore(ICore myCore){
		this.Cores.remove(myCore.getUUID());
		this.removeCoreFromGrid(myCore);
	}

	private void addCoreToGrid(ICore myCore) {
		int x = myCore.getLocation().getBlockX();
		int z = myCore.getLocation().getBlockZ();
		
		int areaSize = myCore.getAreaSize();
		Debug.Console("Area size: "+areaSize);
		
		for (int i = x-areaSize;i < x + areaSize; i +=areaSize){
			for (int j = z-areaSize;j < z + areaSize; j += areaSize){
				Point gridPoint = new Point((int)Math.floor(i/GridSize),(int)Math.floor(j/GridSize));
				//only add the core if its not listed already
				if (CoreGrid.get(gridPoint) == null) {
					CoreGrid.put(gridPoint, new ConcurrentHashMap<UUID,ICore>());
				}
				
				if (!CoreGrid.get(gridPoint).containsKey((myCore.getUUID()))){
					CoreGrid.get(gridPoint).put(myCore.getUUID(), myCore);
				}
			}
		}
	}
	
	private void removeCoreFromGrid(ICore myCore) {

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

	public ConcurrentHashMap<UUID, ICore> getCoresInGrid(int x, int z){
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

	
	public ConcurrentHashMap<UUID, ICore> getEnemyCoresInGrid(UUID myEmpireUUID, int x, int z){
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		ConcurrentHashMap<UUID, ICore> allCores = CoreGrid.get(gridPoint);
		ConcurrentHashMap<UUID, ICore> enemyCores = new ConcurrentHashMap<UUID, ICore>();
		
		if (allCores == null){
			return null;
		}
		
		for (ICore myCore : allCores.values()){
			if (myCore.getEmpireUUID().equals(myEmpireUUID) == false){
				enemyCores.put(myCore.getUUID(), myCore);
			}
		}
		if (enemyCores.isEmpty()){
			return null;
		}
		
		return enemyCores;
	}
	
	public ConcurrentHashMap<UUID, ICore> getFriendlyCoresInGrid(UUID myEmpireUUID, int x, int z){
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		
		ConcurrentHashMap<UUID, ICore> allCores = CoreGrid.get(gridPoint);
		
		Debug.Console("loaded new friendly cores list");
		
		if (allCores == null){
			return null;
		}
		
		Debug.Console(allCores.size()+" cores found");
		//only create this if allcores isnt null
		ConcurrentHashMap<UUID, ICore> friendlyCores = new ConcurrentHashMap<UUID, ICore>();
		
		for (ICore myCore : allCores.values()){
			if (myCore.getEmpireUUID().equals(myEmpireUUID)){
				Debug.Console("iterating through cores");
				friendlyCores.put(myCore.getUUID(), myCore);
			}
		}
		return friendlyCores;
	}
	
	public ConcurrentHashMap<UUID, ICore> getFriendlyCoresInGrid(UUID myEmpireUUID, Point myPoint){
		return getFriendlyCoresInGrid(myEmpireUUID, myPoint.x, myPoint.y);
	}
	
	
	public boolean isNearEnemyCore(ICore myCore){
		int range = getGridSize();
		
		Integer X = myCore.getLocation().getBlockX();
		Integer Z = myCore.getLocation().getBlockZ();
		
		for (int i = X-range;i <= X +range; i+=(range/2)){
			for (int j = Z-range;j <= Z+range; j+=(range/2)){
				ConcurrentHashMap<UUID, ICore> myCores = this.getEnemyCoresInGrid(myCore.getEmpireUUID(),i, j);
				if (myCores != null ){
					return true;
				}
			}
		}
		return false;
	}

	public boolean isEdgeOfEmpire(ICore myCore) {
		//get all nearby cores
		
		int areaSize = myCore.getAreaSize();
		int c1x1 = myCore.getLocation().getBlockX()-areaSize;
		int c1x2 = myCore.getLocation().getBlockX()+areaSize;
		int c1z1 = myCore.getLocation().getBlockZ()-areaSize;
		int c1z2 = myCore.getLocation().getBlockZ()+areaSize;
		
		for (int i = c1x1;i <= c1x2; i +=areaSize){
			for (int j = c1z1;j <= c1z2; j += areaSize){
				ConcurrentHashMap<UUID, ICore> coreList = getFriendlyCoresInGrid(myCore.getEmpireUUID(),i,j);
				if (coreList == null){
					return false;
				}
				
				for (ICore c : coreList.values()){
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
	public boolean isInsideEmpire(ICore myCore) {
		
		int areaSize = myCore.getAreaSize();
		int x = myCore.getLocation().getBlockX();
		int z = myCore.getLocation().getBlockZ();
		
		
		//check each corner of the grid is inside the empire
		for (int i = x-areaSize;i <= x + areaSize; i +=areaSize){
			for (int j = z-areaSize;j <= z + areaSize; z += areaSize){
				if (!isInEmpire(myCore, i,j)){
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
	public boolean isInEmpire(ICore myCore, int x, int z){
		
		ConcurrentHashMap<UUID, ICore> myEmpireCores = getFriendlyCoresInGrid(myCore.getEmpireUUID(), x, z);
		if (myEmpireCores == null ){
			return false;
		}
		
		for (ICore myFriendlyCore : myEmpireCores.values()){
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

	public ConcurrentHashMap<UUID, ICore> getFriendlyCoresInGrid(UUID empireUUID,	Location location) {
		return getFriendlyCoresInGrid(empireUUID, location.getBlockX(), location.getBlockZ());
	}

	public UUID getUUID() {
		return myUUID;
	}

	public boolean coreLocationIsValid(EPlayer myEPlayer, ICore myCore) {
		
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
		
		
		//INSIDE TYPES DONT WORK
		Debug.Console(myCore.getPlaceType().toString());
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
	
	public boolean coresOverlap( ICore myCore){
		ConcurrentHashMap<UUID, ICore> myCores = getFriendlyCoresInGrid(myCore.getEmpireUUID(), myCore.getLocation());
		
		if (myCores == null){
			return false;
		}
		
		int coreSize = myCore.getCoreSize();
		int c1x1 = myCore.getLocation().getBlockX()-coreSize;
		int c1x2 = myCore.getLocation().getBlockX()+coreSize;
		int c1z1 = myCore.getLocation().getBlockZ()-coreSize;
		int c1z2 = myCore.getLocation().getBlockZ()+coreSize;
		
		for(ICore friendlyCore : myCores.values()){
			
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
		
		ConcurrentHashMap<UUID, ICore> myCores = getCoresInGrid(x, z);
		if (myCores == null ){
			return null;
		}
		
		for (ICore myCore : myCores.values()){				
			if (myCore.isInArea(x,  z)){
				return myCore.getEmpireUUID();
			}
		}
		return null;
	}
	
	public HashMap<UUID, ICore> getCores(Integer locX, Integer locZ){
		ConcurrentHashMap<UUID, ICore> myCores = getCoresInGrid(locX, locZ);
		if (myCores == null ){
			return null;
		}
		
		HashMap<UUID, ICore> myOverlappingCores = new HashMap<UUID, ICore>();
		
		for (ICore myCore : myCores.values()){				
			if (myCore.isInArea(locX,  locZ)){
				myOverlappingCores.put(myCore.getUUID(), myCore);
			}
		}
		return myOverlappingCores;
	}

	public HashMap<UUID, ICore> getCores(Location newLocation) {
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





















