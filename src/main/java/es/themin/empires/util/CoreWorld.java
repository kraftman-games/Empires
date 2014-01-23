package es.themin.empires.util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import es.themin.empires.cores.Core;

public class CoreWorld {
	
	private HashMap<Integer, Core> Cores;
	private Map<Point, HashMap<Integer, Core>> CoreGrid;
	private Integer GridSize;
	

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
		
		int coreSize = myCore.getAreaSize();
		
		for (int i = x-coreSize;i < x + coreSize; i +=coreSize){
			for (int j = z-coreSize;j < z + coreSize; j += coreSize){
				Point gridPoint = new Point((int)Math.floor(i/GridSize),(int)Math.floor(j/GridSize));
				//only add the core if its not listed already
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
		
		for (int i = x-coreSize;i < x + coreSize; i +=coreSize){
			for (int j = z-coreSize;j < z + coreSize; z += coreSize){
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
	
	public HashMap<Integer, Core> getEnemyCoresInGrid(int x, int z){
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		
		
		
		return CoreGrid.get(gridPoint);
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
}




