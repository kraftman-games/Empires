package es.themin.empires.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.metadata.FixedMetadataValue;

import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpirePermission;

public class UtilManager {
	
	public static ArrayList<Empire> empires = new ArrayList<Empire>();
	public static HashMap<String, Empire> empireplayers = new HashMap<String, Empire>();
	public static ArrayList<Core> cores = new ArrayList<Core>();
	public static ArrayList<Amplifier> amps = new ArrayList<Amplifier>();
	public static ArrayList<World> worlds = new ArrayList<World>();
	
	
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
	public static void saveEmpires() {
		List<String> list = new ArrayList<String>();
		for (Empire empire : empires) {
			StringBuilder str = new StringBuilder();
			str.append(empire.getId() + ":");
			str.append(empire.getName() + ":");
			str.append(empire.getOwner());
			list.add(str.toString());
			SettingsManager.getInstance().getEmpireData().set(str.toString() + ".id", empire.getId());
			SettingsManager.getInstance().getEmpireData().set(str.toString() + ".name", empire.getName());
			SettingsManager.getInstance().getEmpireData().set(str.toString() + ".name", empire.getOwner());
			List<String> list2 = new ArrayList<String>();
			for (String player : empire.getPlayers()) {
				//FixedMetadataValue playerEmpire = new FixedMetadataValue (myPlugin, this.getId());
				list2.add(player);
				
			}
			SettingsManager.getInstance().getEmpireData().set(str.toString() + ".players", list2);
			List<String> list3 = new ArrayList<String>();
			for (Core core : empire.getCores()) {
				StringBuilder str2 = new StringBuilder();
				str2.append(core.getId() + ":");
				str2.append(core.getType().toString() + ":");
				str2.append(core.getLocation().getWorld().getName() + ":");
				str2.append(core.getLocation().getBlockX() + ":");
				str2.append(core.getLocation().getBlockY() + ":");
				str2.append(core.getLocation().getBlockZ() + ":");
				str2.append(core.getLevel() + ":");
				str2.append(core.getEmpire().getId() + ":");
				str2.append(core.getEmpire().getName());
				list3.add(str2.toString());
			}
			SettingsManager.getInstance().getEmpireData().set(str.toString() + ".cores", list3);
			List<String> list4 = new ArrayList<String>();
			for (Rank rank : empire.getRanks()) {
				StringBuilder str3 = new StringBuilder();
				str3.append(rank.getWeight() + ":");
				str3.append(rank.getName() + ":");
				list4.add(str3.toString());
				List<String> list5 = new ArrayList<String>();
				for (String p : rank.getPlayers()) {
					list5.add(p);
				}
				SettingsManager.getInstance().getEmpireData().set(str.toString() + ".rank." + str3.toString() + ".players", list5);
				List<String> list6 = new ArrayList<String>();
				for (EmpirePermission ep : rank.getPermissions()) {
					list6.add(ep.toString());
				}
				SettingsManager.getInstance().getEmpireData().set(str.toString() + ".rank." + str3.toString() + ".permissions", list6);
			}
			SettingsManager.getInstance().getEmpireData().set(str.toString() + ".ranks", list4);
		}
		SettingsManager.getInstance().getEmpireData().set("empires", list);
	}
	public static void loadEmpires() {
		List<String> list = SettingsManager.getInstance().getEmpireData().getStringList("empires");
		for (String s : list) {
			String[] words = s.split(":");
			Integer Id = Integer.parseInt(words[0]);
			String name = words[1];
			String owner = words[2];
			Empire empire = new Empire(Id, name, owner);
			List<String> list2 = SettingsManager.getInstance().getEmpireData().getStringList(s + ".cores");
			for (String s2: list2) {
				String[] words2 = s2.split(":");
				int Id2  = Integer.parseInt(words2[0]);
				String type = words2[1];
				CoreType coretype = null;
				if (type.equalsIgnoreCase("BASE")) coretype = CoreType.BASE;
				if (type.equalsIgnoreCase("MOB")) coretype = CoreType.MOB;
				if (type.equalsIgnoreCase("FARM")) coretype = CoreType.FARM;
				if (type.equalsIgnoreCase("MONSTER")) coretype = CoreType.MONSTER;
				if (type.equalsIgnoreCase("GRIEF")) coretype = CoreType.GRIEF;
				if (type.equalsIgnoreCase("FORTIFICATION")) coretype = CoreType.FORTIFICATION;
				if (type.equalsIgnoreCase("OUTPOST")) coretype = CoreType.OUTPOST;
				World world2 = Bukkit.getServer().getWorld(words2[2]);
				int x2 = Integer.parseInt(words2[3]); // - 0:BASE:world:-249:78:223:1:0:kraft
				int y2 = Integer.parseInt(words2[4]);
				int z2 = Integer.parseInt(words2[5]);
				Location location = new Location(world2, x2, y2, z2);
				int level = Integer.parseInt(words2[6]);
				Core core = new Core(Id2, coretype, location, level, empire);
			    core.build();
				cores.add(core);
				empire.ac(core);
			}
			List<String> list3 = SettingsManager.getInstance().getEmpireData().getStringList(s + ".players");
			for (String playername : list3) {
				empire.addPlayer(playername);
			}
			List<String> list4 = SettingsManager.getInstance().getEmpireData().getStringList(s + ".ranks");
			for (String rankstring : list4) {
				String[] words2 = rankstring.split(":");
				Rank rank = new Rank(Integer.parseInt(words2[0]), words2[1], empire);
				List<String> list5 = SettingsManager.getInstance().getEmpireData().getStringList(s + ".rank." + rankstring + ".players");
				for (String playername : list5) {
					rank.addPlayer(playername);
				}
				List<String> list6 = SettingsManager.getInstance().getEmpireData().getStringList(s + ".rank." + rankstring + ".permissions");
				for (String permission : list6) {
					EmpirePermission ep = null;
					if (permission.equalsIgnoreCase("PLACE_AMPLIFIER")) ep = EmpirePermission.PLACE_AMPLIFIER;
					if (permission.equalsIgnoreCase("ADD_PLAYER")) ep = EmpirePermission.ADD_PLAYER;
					if (permission.equalsIgnoreCase("KICK_PLAYER")) ep = EmpirePermission.KICK_PLAYER;
					if (permission.equalsIgnoreCase("PLACE_ALTER")) ep = EmpirePermission.PLACE_ALTER;
					if (permission.equalsIgnoreCase("UPGRADE_CORE")) ep = EmpirePermission.UPGRADE_CORE;
					if (permission.equalsIgnoreCase("SET_FLAG")) ep = EmpirePermission.SET_FLAG;
					if (permission.equalsIgnoreCase("ALLY")) ep = EmpirePermission.ALLY;
					if (permission.equalsIgnoreCase("ATTACK")) ep = EmpirePermission.ATTACK;
					rank.addPermission(ep);
				}
				empire.addRank(rank);
			}
			empire.Save();
		}
	}
	public static Empire getEmpireWithName(String name) {
		for (Empire empire : empires) {
			if (empire.getName() == name) return empire;
		}
		return null;
	}
	public static boolean containsEmpireWithName(String name) {
		for (Empire empire : empires) {
			if (empire.getName() == name) return true;
		}
		return false;
	}
	public static Empire getEmpireWithId(int Id) {
		for (Empire empire : empires) {
			if (empire.getId() == Id) return empire;
		}
		return null;
	}
	public static boolean containsEmpireWithId(int Id) {
		for (Empire empire : empires) {
			if (empire.getId() == Id) return true;
		}
		return false;
	}
	public static Core getCoreWithId(int Id) {

		for (Core core : cores) {
			if (core.getId() == Id) return core;
		}
		return null;
	}
	public static Core getCoreWithLocation(Location l) {
		for (Core core : cores) {
			if (core.getLocation() == l) return core;
		}
		return null;
	}
	public static boolean containsCoreWithId(int Id) {
		for (Core core : cores) {
			if (core.getId() == Id) return true;
		}
		return false;
	}
	public static int nextUnusedCoreId(){
		int i = 0;
		while (getCoreWithId(i) != null){
			i++;
		}
		return i;
	}
	public static int nextUnusedEmpireId(){
		int i = 0;
		while (getEmpireWithId(i) != null){
			i++;
		}
		return i;
		
	}
	public static Empire getEmpireWithCore(Core c) {
		for (Empire empire : empires) {
			if (empire.hasCore(c)) {
				return empire;
			}
		}
		return null;
	}
//amps
	public static Amplifier getAmplifierWithId(int Id) {
		for (Amplifier amp : amps) {
			if (amp.getId() == Id) return amp;
		}
		return null;
	}
	public static int nextUnusedAmplifierId(){
		int id = 0;
		for (int i = 0 ; i != -1; i++) {
			if (getAmplifierWithId(i) == null) {
				id = i;
				i = -1;
			}
		}
		return id;
	}
	public static boolean containsAmplifierWithId(int Id) {
		for (Amplifier amp: amps) {
			if (amp.getId() == Id) return true;
		}
		return false;
	}
	

}
