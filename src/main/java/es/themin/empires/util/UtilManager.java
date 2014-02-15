package es.themin.empires.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import es.themin.empires.empires;
import es.themin.empires.cores.Core;
import es.themin.empires.enums.BattleType;
import es.themin.empires.enums.ConfirmType;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.wars.Battle;
import es.themin.empires.wars.Battle.BattleTeam;
import es.themin.empires.wars.War;
import es.themin.empires.cores.CoreUtils;

public class UtilManager {
	

	private static empires myPlugin;
	
	public UtilManager(empires empires) {
		myPlugin = empires;
	}

	
/*	public void saveCores() {
		List<String> list = new ArrayList<String>();
		for (Core core : cores) {
			StringBuilder str = new StringBuilder();
			str.append(core.getId() + ":");
			str.append(core.getType().toString() + ":");
			str.append(core.getLocation().getWorld() + ":");
			str.append(core.getLocation().getBlockX() + ":");
			str.append(core.getLocation().getBlockY() + ":");
			str.append(core.getLocation().getBlockZ() + ":");
			str.append(core.getLevel() + ":");
			str.append(core.getEmpire().getId() + ":");
			str.append(core.getEmpire().getName());
			list.add(str.toString());
		}
		SettingsManager.getInstance().getCoreData().set("cores", list);
	}*/
	public static void loopAndSet(Location corner1, Location corner2, Material material) {
		if (corner1.getWorld() == corner2.getWorld()) {
			
			World world = corner1.getWorld();
			
			int x1 = corner1.getBlockX();
			int y1 = corner1.getBlockY();
			int z1 = corner1.getBlockZ();
			
			int x2 = corner2.getBlockX();
			int y2 = corner2.getBlockY();
			int z2 = corner2.getBlockZ();
			
			if (x1 > x2) {
				x1 = corner2.getBlockX();
				x2 = corner1.getBlockX();
			}if (y1 > y2) {
				y1 = corner2.getBlockY();
				y2 = corner1.getBlockY();
			}if (z1 > z2) {
				z1 = corner2.getBlockZ();
				z2 = corner2.getBlockZ();
			}
			
			for (int xPoint = x1; xPoint <= x2; xPoint++) { 
		        for (int yPoint = y1; yPoint <= y2; yPoint++) {
		            for (int zPoint = z1; zPoint <= z2; zPoint++) {
		                Block b = world.getBlockAt(xPoint, yPoint, zPoint);
		                b.setType(material);
		            }
		        }
		    }
		}
	}
	public static void loopAndAddToList(Location corner1, Location corner2, ArrayList<Block> list) {
		if (corner1.getWorld() == corner2.getWorld()) {
			
			World world = corner1.getWorld();
			
			int x1 = corner1.getBlockX();
			int y1 = corner1.getBlockY();
			int z1 = corner1.getBlockZ();
			
			int x2 = corner2.getBlockX();
			int y2 = corner2.getBlockY();
			int z2 = corner2.getBlockZ();
			
			if (x1 > x2) {
				x1 = corner2.getBlockX();
				x2 = corner1.getBlockX();
			}if (y1 > y2) {
				y1 = corner2.getBlockY();
				y2 = corner1.getBlockY();
			}if (z1 > z2) {
				z1 = corner2.getBlockZ();
				z2 = corner2.getBlockZ();
			}
			
			for (int xPoint = x1; xPoint <= x2; xPoint++) { 
		        for (int yPoint = y1; yPoint <= y2; yPoint++) {
		            for (int zPoint = z1; zPoint <= z2; zPoint++) {
		                Block b = world.getBlockAt(xPoint, yPoint, zPoint);
		                list.add(b);
		            }
		        }
		    }
		}
	}

	

}
