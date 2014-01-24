package es.themin.empires.util;

import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;

import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;

public class CoreWorld {
	
	private HashMap<Integer, Core> Cores;
	private HashMap<Integer, Amplifier> Amps;
	private Map<Point, HashMap<Integer, Core>> CoreGrid;
	private Map<Point, HashMap<Integer, Amplifier>> AmpGrid;
	private Integer GridSize = SettingsManager.getInstance().getConfig().getInt("gridsize");
	

	public CoreWorld() {
		CoreGrid = new HashMap<Point, HashMap<Integer, Core>>();
		AmpGrid = new HashMap<Point, HashMap<Integer, Amplifier>>();
		Cores = new HashMap<Integer, Core>();
		Amps = new HashMap<Integer, Amplifier>();
	}

	public Integer getGridSize() {
		return GridSize;
	}

	public HashMap<Integer, Core> getCores() {
		return Cores;
	}
	public HashMap<Integer, Amplifier> getAmplifiers() {
		return Amps;
	}
	
	public Core getCoreByID(Integer coreID){
		return Cores.get(coreID);
	}
	public Amplifier getAmplifierByID(Integer ampID){
		return Amps.get(ampID);
	}
	
	public void addCore(Core myCore){
		this.Cores.put(myCore.getId(), myCore);
		this.addCoreToGrid(myCore);
	}
	public void addAmplifier(Amplifier myAmp){
		this.Amps.put(myAmp.getId(), myAmp);
		this.addAmplifierToGrid(myAmp);
	}
	


	public void removeCore(Core myCore){
		this.Cores.remove(myCore.getId());
		this.removeCoreFromGrid(myCore);
	}
	public void removeAmplifier(Amplifier myAmp){
		this.Amps.remove(myAmp.getId());
		this.removeAmplifierFromGrid(myAmp);
	}

	private void addCoreToGrid(Core myCore) {
		int x = myCore.getLocation().getBlockX();
		int z = myCore.getLocation().getBlockZ();
		
		int areaSize = myCore.getAreaSize();
		
		for (int i = x-areaSize;i < x + areaSize; i +=areaSize){
			for (int j = z-areaSize;j < z + areaSize; j += areaSize){
				Point gridPoint = new Point((int)Math.floor(i/GridSize),(int)Math.floor(j/GridSize));
				//only add the core if its not listed already
				if (!CoreGrid.get(gridPoint).containsKey((myCore.getId()))){
					CoreGrid.get(gridPoint).put(myCore.getId(), myCore);
				}
			}
		}
	}
	private void addAmplifierToGrid(Amplifier myAmp) {
		int x = myAmp.getLocation().getBlockX();
		int z = myAmp.getLocation().getBlockZ();
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		//only add the core if its not listed already
		if (!AmpGrid.get(gridPoint).containsKey((myAmp.getId()))){
			AmpGrid.get(gridPoint).put(myAmp.getId(), myAmp);
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
	public void removeAmplifierFromGrid(Amplifier myAmp) {
		int x = myAmp.getLocation().getBlockX();
		int z = myAmp.getLocation().getBlockZ();
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		//remove the core if it exists
		if (AmpGrid.get(gridPoint).containsKey((myAmp.getId()))){
			AmpGrid.get(gridPoint).remove(myAmp.getId());
		}
	}
	
	public HashMap<Integer, Core> getCoresInGrid(int x, int z){
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		
		return CoreGrid.get(gridPoint);
	}
	public HashMap<Integer, Amplifier> getAmplifiersInGrid(int x, int z){
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		
		return AmpGrid.get(gridPoint);
	}
	public boolean hasCoresInGrid(int x, int z) {
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		if (CoreGrid.get(gridPoint) != null) return true;
		return false;
	}
	public boolean hasAmplifiersInGrid(int x, int z) {
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		if (AmpGrid.get(gridPoint) != null) return true;
		return false;
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

	public boolean isEdgeOfEmpire(Core myCore) {
		//get all nearby cores
		
		int areaSize = myCore.getAreaSize();
		int c1x1 = myCore.getLocation().getBlockX()-areaSize;
		int c1x2 = myCore.getLocation().getBlockX()+areaSize;
		int c1z1 = myCore.getLocation().getBlockZ()-areaSize;
		int c1z2 = myCore.getLocation().getBlockZ()+areaSize;
		
		
		
		for (int i = c1x1;i <= c1x2; i +=areaSize){
			for (int j = c1z1;j <= c1z2; j += areaSize){
				HashMap<Integer, Core> coreList = getCoresInGrid((int)Math.floor(i/GridSize),(int)Math.floor(j/GridSize));
				for (Core c : coreList.values()){
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
		return false;
	}
	public Point getCoords(Location location) {
		int x = location.getBlockX();
		int z = location.getBlockZ();
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		return gridPoint;
	}
	public boolean blockIsUnderControl(Block b) {
		int radius = SettingsManager.getInstance().getConfig().getInt("plot_size");
		Location location = b.getLocation();
		int x = location.getBlockX();
		int z = location.getBlockZ();
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		ArrayList<Point> grids = new ArrayList<Point>();
		for (int i = x-1; i < x+1 ; i++) {
			for (int j = z-1; j < z+1 ; j++) {
				Set<Integer> ampkeys = getAmplifiersInGrid(i,j).keySet();
				for (int e : ampkeys) {
					Amplifier amp = getAmplifiersInGrid(i,j).get(e);
					int ampx = amp.getLocation().getBlockX();
					int ampz = amp.getLocation().getBlockZ();
					if (getDifferenceBetween(x, ampx) <= radius || getDifferenceBetween(z, ampz) <= radius) return true; 
				}
				Set<Integer> corekeys = getCoresInGrid(i,j).keySet();
				for (int e : corekeys) {
					Core core = getCoresInGrid(i,j).get(e);
					int corex = core.getLocation().getBlockX();
					int corez = core.getLocation().getBlockZ();
					if (getDifferenceBetween(x, corez) <= radius || getDifferenceBetween(z, corex) <= radius) {
						if (core.getType() == CoreType.BASE) return true;
					}
				}
			}
		}
		return false;
		
	}
	public Empire getEmpireControllingBlock(Block b) {
		int radius = SettingsManager.getInstance().getConfig().getInt("plot_size");
		Location location = b.getLocation();
		int x = location.getBlockX();
		int z = location.getBlockZ();
		Point gridPoint = new Point((int)Math.floor(x/GridSize),(int)Math.floor(z/GridSize));
		ArrayList<Point> grids = new ArrayList<Point>();
		for (int i = x-1; i < x+1 ; i++) {
			for (int j = z-1; j < z+1 ; j++) {
				if (hasAmplifiersInGrid(i,j)) {
					Set<Integer> ampkeys = getAmplifiersInGrid(i,j).keySet();
					for (int e : ampkeys) {
						Amplifier amp = getAmplifiersInGrid(i,j).get(e);
						int ampx = amp.getLocation().getBlockX();
						int ampz = amp.getLocation().getBlockZ();
						if (getDifferenceBetween(x, ampx) <= radius || getDifferenceBetween(z, ampz) <= radius) return amp.getEmpire(); 
					}
				}
				if (hasCoresInGrid(i,j)) {
					Set<Integer> corekeys = getCoresInGrid(i,j).keySet();
					for (int e : corekeys) {
						Core core = getCoresInGrid(i,j).get(e);
						int corex = core.getLocation().getBlockX();
						int corez = core.getLocation().getBlockZ();
						if (getDifferenceBetween(x, corez) <= radius || getDifferenceBetween(z, corex) <= radius) {
							if (core.getType() == CoreType.BASE) return core.getEmpire();
						}
					}
				}
			}
		}
		return null;
	}
	public int getDifferenceBetween(int int1, int int2) {
		if (int1 > int2) return int1-int2;
		if (int2 > int1) return int2-int1;
		return 0;
	}
}




