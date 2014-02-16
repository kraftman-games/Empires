package es.themin.empires.schematics;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import es.themin.empires.enums.CoreType;
import es.themin.empires.util.UtilManager;

public class Schematic_Base_20 extends Schematic{

	@SuppressWarnings("deprecation")
	@Override
	public void pasteFromCentre(Location location) {
		Location l = location;
		World w = l.getWorld();
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		
		/**
		 * Obsidian blocks bellow base
		 */
		ArrayList<Block> obs = new ArrayList<Block>();
		Location c301 = new Location(w,x-2,y-1,z-4);
		Location c302 = new Location(w,x+2,y-1,z+4);
		obs = UtilManager.loopAndAddToList(c301, c302, obs);
		Location c401 = new Location(w,x-4,y-1,z-2);
		Location c402 = new Location(w,x+4,y-1,z+2);
		obs = UtilManager.loopAndAddToList(c401, c402, obs);
		Location c501 = new Location(w,x-3,y-1,z-3);
		Location c502 = new Location(w,x+3,y-1,z+3);
		obs = UtilManager.loopAndAddToList(c501, c502, obs);
		w.getBlockAt(x+5,y-1,z).setType(Material.OBSIDIAN);
		w.getBlockAt(x-5,y-1,z).setType(Material.OBSIDIAN);
		w.getBlockAt(x,y-1,z+5).setType(Material.OBSIDIAN);
		w.getBlockAt(x,y-1,z-5).setType(Material.OBSIDIAN);
		for (Block b : obs) {
			b.setType(Material.OBSIDIAN);
		}
		/**
		 * Step blocks bellow base
		 */
		ArrayList<Block> slabs = new ArrayList<Block>();
		Location c601 = new Location(w,x-1,y-1,z-3);
		Location c602 = new Location(w,x+1,y-1,z+3);
		slabs = UtilManager.loopAndAddToList(c601, c602, slabs);
		Location c701 = new Location(w,x-3,y-1,z-1);
		Location c702 = new Location(w,x+3,y-1,z+1);
		slabs = UtilManager.loopAndAddToList(c701, c702, slabs);
		Location c801 = new Location(w,x-2,y-1,z-2);
		Location c802 = new Location(w,x+2,y-1,z+2);
		slabs = UtilManager.loopAndAddToList(c801, c802, slabs);
		for (Block b: slabs){
			b.setType(Material.DOUBLE_STEP);
		}
		/**
		 * Diamond blocks bellow base
		 */
		Location c101 = new Location(w,x-1,y-1,z-1);
		Location c102 = new Location(w,x+1,y-1,z+1);
		UtilManager.loopAndSet(c101, c102, Material.DIAMOND_BLOCK);
		
		/**
		 * Stone slabs level with base
		 */
		Location c201 = new Location(w,x-1,y,z-1);
		Location c202 = new Location(w,x+1,y,z+1);
		UtilManager.loopAndSet(c201, c202, Material.STEP);
		w.getBlockAt(x+2,y,z).setType(Material.STEP);
		w.getBlockAt(x-2,y,z).setType(Material.STEP);
		w.getBlockAt(x,y,z).setType(Material.STEP);
		w.getBlockAt(x,y,z).setType(Material.STEP);
		
		/**
		 * Obsidian pillars
		 */
		ArrayList<Block> ops = new ArrayList<Block>();
		Location c901 = new Location(w,x+4,y,z);
		Location c902 = new Location(w,x+4,y+2,z);
		ops = UtilManager.loopAndAddToList(c901, c902, ops);
		Location c111 = new Location(w,x-4,y,z);
		Location c112 = new Location(w,x-4,y+2,z);
		ops = UtilManager.loopAndAddToList(c111, c112, ops);
		Location c121 = new Location(w,x,y,z+4);
		Location c122 = new Location(w,x,y+2,z+4);
		ops = UtilManager.loopAndAddToList(c121, c122, ops);
		Location c131 = new Location(w,x,y,z-4);
		Location c132 = new Location(w,x,y+2,z-4);
		ops = UtilManager.loopAndAddToList(c131, c132, ops);
		
		Location c2901 = new Location(w,x+4,y,z-1);
		Location c2902 = new Location(w,x+4,y,z+1);
		ops = UtilManager.loopAndAddToList(c2901, c2902, ops);
		Location c2111 = new Location(w,x-4,y,z-1);
		Location c2112 = new Location(w,x-4,y,z+1);
		ops = UtilManager.loopAndAddToList(c2111, c2112, ops);
		Location c2121 = new Location(w,x-1,y,z+4);
		Location c2122 = new Location(w,x+1,y,z+4);
		ops = UtilManager.loopAndAddToList(c2121, c2122, ops);
		Location c2131 = new Location(w,x-1,y,z-4);
		Location c2132 = new Location(w,x+1,y,z-4);
		ops = UtilManager.loopAndAddToList(c2131, c2132, ops);
		
		Location c3901 = new Location(w,x+3,y+2,z);
		Location c3902 = new Location(w,x+3,y+4,z);
		ops = UtilManager.loopAndAddToList(c3901, c3902, ops);
		Location c3111 = new Location(w,x-3,y+2,z);
		Location c3112 = new Location(w,x-3,y+4,z);
		ops = UtilManager.loopAndAddToList(c3111, c3112, ops);
		Location c3121 = new Location(w,x,y+2,z+3);
		Location c3122 = new Location(w,x,y+4,z+3);
		ops = UtilManager.loopAndAddToList(c3121, c3122, ops);
		Location c3131 = new Location(w,x,y+2,z-3);
		Location c3132 = new Location(w,x,y+4,z-3);
		ops = UtilManager.loopAndAddToList(c3131, c3132, ops);
		
		ops.add(w.getBlockAt(x+2,y+3,z));
		ops.add(w.getBlockAt(x-2,y+3,z));
		ops.add(w.getBlockAt(x,y+3,z+2));
		ops.add(w.getBlockAt(x,y+3,z-2));
		
		for (Block b : ops){
			b.setType(Material.OBSIDIAN);
		}
		/**
		 * Glass part
		 */
		ArrayList<Block> glass = new ArrayList<Block>();
		glass.add(w.getBlockAt(x,y+4,z));
		glass.add(w.getBlockAt(x,y+5,z));
		glass.add(w.getBlockAt(x,y+6,z));
		for (Block b: glass){
			b.setType(Material.STAINED_GLASS);
			byte d = 0x3;
			b.setData(d);
		}
		/**
		 * Diamond star thingy
		 */
		ArrayList<Block> dia = new ArrayList<Block>();
		dia.add(w.getBlockAt(x+1,y+5,z));
		dia.add(w.getBlockAt(x-1,y+5,z));
		dia.add(w.getBlockAt(x,y+5,z+1));
		dia.add(w.getBlockAt(x,y+5,z-1));
		for (Block b: dia){
			b.setType(Material.DIAMOND_BLOCK);
		}
		/**
		 * Spines
		 */
		ArrayList<Block> sp = new ArrayList<Block>();
		
		sp.add(w.getBlockAt(x+5,y,z));
		sp.add(w.getBlockAt(x-5,y,z));
		sp.add(w.getBlockAt(x,y,z+5));
		sp.add(w.getBlockAt(x,y,z-5));
		
		sp.add(w.getBlockAt(x+5,y+1,z));
		sp.add(w.getBlockAt(x-5,y+1,z));
		sp.add(w.getBlockAt(x,y+1,z+5));
		sp.add(w.getBlockAt(x,y+1,z-5));
		
		sp.add(w.getBlockAt(x+4,y+1,z+1));
		sp.add(w.getBlockAt(x+4,y+1,z-1));
		sp.add(w.getBlockAt(x-4,y+1,z+1));
		sp.add(w.getBlockAt(x-4,y+1,z-1));
		sp.add(w.getBlockAt(x+1,y+1,z+4));
		sp.add(w.getBlockAt(x-1,y+1,z+4));
		sp.add(w.getBlockAt(x+1,y+1,z-4));
		sp.add(w.getBlockAt(x-1,y+1,z-4));
		
		sp.add(w.getBlockAt(x+3,y+2,z+1));
		sp.add(w.getBlockAt(x+3,y+2,z-1));
		sp.add(w.getBlockAt(x-3,y+2,z+1));
		sp.add(w.getBlockAt(x-3,y+2,z-1));
		sp.add(w.getBlockAt(x+1,y+2,z+3));
		sp.add(w.getBlockAt(x-1,y+2,z+3));
		sp.add(w.getBlockAt(x+1,y+2,z-3));
		sp.add(w.getBlockAt(x-1,y+2,z-3));
		
		sp.add(w.getBlockAt(x+3,y+4,z+1));
		sp.add(w.getBlockAt(x+3,y+4,z-1));
		sp.add(w.getBlockAt(x-3,y+4,z+1));
		sp.add(w.getBlockAt(x-3,y+4,z-1));
		sp.add(w.getBlockAt(x+1,y+4,z+3));
		sp.add(w.getBlockAt(x-1,y+4,z+3));
		sp.add(w.getBlockAt(x+1,y+4,z-3));
		sp.add(w.getBlockAt(x-1,y+4,z-3));
		
		sp.add(w.getBlockAt(x+4,y+3,z));
		sp.add(w.getBlockAt(x-4,y+3,z));
		sp.add(w.getBlockAt(x,y+3,z+4));
		sp.add(w.getBlockAt(x,y+3,z-4));
		
		sp.add(w.getBlockAt(x+3,y+5,z));
		sp.add(w.getBlockAt(x-3,y+5,z));
		sp.add(w.getBlockAt(x,y+5,z+3));
		sp.add(w.getBlockAt(x,y+5,z-3));
		
		for (Block b : sp) {
			b.setType(Material.COBBLE_WALL);
		}
		w.getBlockAt(l).setType(Material.BEACON);
	}

	@Override
	public void destroyFromCentre(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CoreType coreType() {
		return CoreType.BASE;
	}

	@Override
	public ArrayList<Block> getBlocks(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLevel() {
		return 20;
	}

	@Override
	public boolean isSafeToBuildAround(Location location) {
		// TODO Auto-generated method stub
		return false;
	}

}
