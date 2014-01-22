package es.themin.empires.util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import es.themin.empires.cores.Core;

public class CoreWorld {
	
	private HashMap<Integer, Core> Cores = new HashMap<Integer, Core>();
	private Point test;
	private Map<Point, ArrayList<Integer>> CoreGrid;
	private Integer GridSize;
	

	public CoreWorld() {
		
	}

	public HashMap<Integer, Core> getCores() {
		return Cores;
	}

	public void addCore(Core myCore){
		this.Cores.put(myCore.getId(), myCore);
		this.addCoreToGrid(myCore);
	}

	private void addCoreToGrid(Core myCore) {

		int x = myCore.getLocation().getBlockX();
		int z = myCore.getLocation().getBlockZ();
		
		
		Point gridPoint = new Point(x,z);
		if (CoreGrid.get(gridPoint) == null){
			CoreGrid.put(gridPoint, new ArrayList<Integer>());
		}
		CoreGrid.get(gridPoint).add(myCore.getId());
	}

}
