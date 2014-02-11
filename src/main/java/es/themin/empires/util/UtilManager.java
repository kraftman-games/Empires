package es.themin.empires.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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


	

}
