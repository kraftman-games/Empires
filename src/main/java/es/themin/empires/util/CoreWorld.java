package es.themin.empires.util;

import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import es.themin.empires.cores.Core;
import es.themin.empires.cores.Schematic;
import es.themin.empires.enums.CoreType;

public class CoreWorld {
	
	private HashMap<Integer, Core> Cores;
	private Map<Point, HashMap<Integer, Core>> CoreGrid;
	private Integer GridSize = SettingsManager.getInstance().getConfig().getInt("gridsize");
	
	
	//TODO: IMPORTANT. check if the core its overlapping is itself

	public CoreWorld() {
		CoreGrid = new HashMap<Point, HashMap<Integer, Core>>();
		Cores = new HashMap<Integer, Core>();
	}

	public Integer getGridSize() {
		return GridSize;
	}

	public HashMap<Integer, Core> getCores() {
		return Cores;
	}

	public Core getCoreByID(Integer coreID){
		return Cores.get(coreID);
	}

	public void addCore(Core myCore){
		this.Cores.put(myCore.getId(), myCore);
		this.addCoreToGrid(myCore);
	}



	public void removeCore(Core myCore){
		this.Cores.remove(myCore.getId());
		this.removeCoreFromGrid(myCore);
	}

	private void addCoreToGrid(Core myCore) {
		int x = myCore.getLocation().getBlockX();
		int z = myCore.getLocation().getBlockZ();
		
		int areaSize = myCore.getAreaSize();
		
		for (int i = x-areaSize;i < x + areaSize; i +=areaSize){
			for (int j = z-areaSize;j < z + areaSize; j += areaSize){
				Point gridPoint = new Point((int)Math.floor(i/GridSize),(int)Math.floor(j/GridSize));
				//only add the core if its not listed already
				if (CoreGrid.get(gridPoint) == null) {
					CoreGrid.put(gridPoint, new HashMap<Integer,Core>());
				}
				
				if (!CoreGrid.get(gridPoint).containsKey((myCore.getId()))){
					CoreGrid.get(gridPoint).put(myCore.getId(), myCore);
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
				if (CoreGrid.get(gridPoint).containsKey(myCore.getId())){
					CoreGrid.get(gridPoint).remove(myCore.getId());
				}
			}
		}
	}

	public HashMap<Integer, Core> getCoresInGrid(int x, int z){
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));	
		return CoreGrid.get(gridPoint);
	}
	
	public boolean hasCoresInGrid(int x, int z) {
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		if (!(CoreGrid.get(gridPoint) == null) && CoreGrid.get(gridPoint).values().size() > 0) {
			return true;
		}
		return false;
	}

	
	public HashMap<Integer, Core> getEnemyCoresInGrid(Empire myEmpire, int x, int z){
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		HashMap<Integer, Core> allCores = CoreGrid.get(gridPoint);
		HashMap<Integer, Core> enemyCores = new HashMap<Integer, Core>();
		
		for (Core myCore : allCores.values()){
			if (myCore.getEmpire() != myEmpire){
				enemyCores.put(myCore.getId(), myCore);
			}
		}
		return CoreGrid.get(gridPoint);
	}
	
	public HashMap<Integer, Core> getFriendlyCoresInGrid(Empire myEmpire, int x, int z){
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		HashMap<Integer, Core> allCores = CoreGrid.get(gridPoint);
		HashMap<Integer, Core> enemyCores = new HashMap<Integer, Core>();
		
		for (Core myCore : allCores.values()){
			if (myCore.getEmpire() == myEmpire){
				enemyCores.put(myCore.getId(), myCore);
			}
		}
		return CoreGrid.get(gridPoint);
	}
	
	public HashMap<Integer, Core> getFriendlyCoresInGrid(Empire myEmpire, Point myPoint){
		return getFriendlyCoresInGrid(myEmpire, myPoint.x, myPoint.y);
	}
	
	
	public boolean isNearEnemyCore(Core myCore){
		int range = this.getGridSize();
		
		for (int i = -range;i <= range; i+=range){
			for (int j = -range;j <= range; j+=range){
				HashMap<Integer, Core> myCores = this.getCoresInGrid(i, j);
				for(Core myFoundCore : myCores.values()){
					if (myCore.getEmpire().equals(myFoundCore.getEmpire())){
						return true;
					}
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
				HashMap<Integer, Core> coreList = getFriendlyCoresInGrid(myCore.getEmpire(),(int)Math.floor(i/GridSize),(int)Math.floor(j/GridSize));
				for (Core c : coreList.values()){
					if (!(c == myCore)){
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
		
		HashMap<Integer, Core> myEmpireCores = getFriendlyCoresInGrid(myCore.getEmpire(), x, z);
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

	public HashMap<Integer, Core> getFriendlyCoresInGrid(Empire empire,	Location location) {
		return getFriendlyCoresInGrid(empire, location.getBlockX(), location.getBlockZ());
	}

}





















