package es.themin.empires.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import es.themin.empires.enums.CoreType;

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
			str.append(empire.getName());
			list.add(str.toString());
			SettingsManager.getInstance().getEmpireData().set(str.toString() + ".id", empire.getId());
			SettingsManager.getInstance().getEmpireData().set(str.toString() + ".name", empire.getName());
			List<String> list2 = new ArrayList<String>();
			for (String player : empire.getPlayers()) {
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
		}
		SettingsManager.getInstance().getEmpireData().set("empires", list);
	}
	public static void loadEmpires() {
		List<String> list = SettingsManager.getInstance().getEmpireData().getStringList("empires");
		for (String s : list) {
			String[] words = s.split(":");
			Integer Id = Integer.parseInt(words[0]);
			String name = words[1];
			Empire empire = new Empire(Id, name);
			List<String> list2 = SettingsManager.getInstance().getEmpireData().getStringList(s + ".cores");
			for (String s2: list2) {
				String[] words2 = s2.split(":");
				int Id2  = Integer.parseInt(words2[0]);
				String type = words2[1];
				CoreType coretype = null;
				if (type.equalsIgnoreCase("BASIC")) coretype = CoreType.BASE;
				if (type.equalsIgnoreCase("MOB")) coretype = CoreType.MOB;
				if (type.equalsIgnoreCase("FARM")) coretype = CoreType.FARM;
				if (type.equalsIgnoreCase("GRIEF")) coretype = CoreType.GRIEF;
				if (type.equalsIgnoreCase("FORTIFICATION")) coretype = CoreType.FORTIFICATION;
				World world2 = Bukkit.getServer().getWorld(words2[2]);
				int x2 = Integer.parseInt(words[3]);
				int y2 = Integer.parseInt(words[4]);
				int z2 = Integer.parseInt(words[5]);
				Location location = new Location(world2, x2, y2, z2);
				int level = Integer.parseInt(words[6]);
				Core core = new Core(Id2, coretype, location, level, empire);
				core.setProtection(true);
				cores.add(core);
				empire.ac(core);
			}
			List<String> list3 = SettingsManager.getInstance().getEmpireData().getStringList(s + ".players");
			for (String playername : list3) {
				empire.addPlayer(playername);
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
