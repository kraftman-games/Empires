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

import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.wars.War;

public class UtilManager {
	
	public static ArrayList<Empire> empires = new ArrayList<Empire>();
	public static HashMap<String, Empire> empireplayers = new HashMap<String, Empire>();
	public static ArrayList<Core> cores = new ArrayList<Core>();
	public static ArrayList<War> wars = new ArrayList<War>();
	public static HashMap<UUID,CoreWorld> worlds = new HashMap<UUID,CoreWorld>();
	private static HashMap<UUID, EmpirePlayer> EmpirePlayers = new HashMap<UUID, EmpirePlayer>();
	
	
	public EmpirePlayer getEmpirePlayer(String playerName)
	{
		Player myPlayer = Bukkit.getPlayer(playerName);
		
		if (myPlayer == null){
			return null;
		} else {
			return getEmpirePlayer(myPlayer.getUniqueId());
		}
		
	}
	
	public static EmpirePlayer getEmpirePlayer(UUID myUUID){
		
		return EmpirePlayers.get(myUUID);
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
	public static void saveEmpires() {
		List<String> list = new ArrayList<String>();
		for (Empire empire : empires) {
			StringBuilder str = new StringBuilder();
			str.append(empire.getId() + ":");
			str.append(empire.getName() + ":");
			str.append(empire.getOwner());
			list.add(str.toString());
			SettingsManager.getEmpireData().set(str.toString() + ".id", empire.getId());
			SettingsManager.getEmpireData().set(str.toString() + ".name", empire.getName());
			SettingsManager.getEmpireData().set(str.toString() + ".name", empire.getOwner());
			List<String> list2 = new ArrayList<String>();
			for (String player : empire.getPlayers()) {
				//FixedMetadataValue playerEmpire = new FixedMetadataValue (myPlugin, this.getId());
				list2.add(player);
				
			}
			SettingsManager.getEmpireData().set(str.toString() + ".players", list2);
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
			SettingsManager.getEmpireData().set(str.toString() + ".cores", list3);
			List<String> list4 = new ArrayList<String>();
			for (Rank rank : empire.getRanks()) {
				StringBuilder str3 = new StringBuilder();
				str3.append(rank.getWeight() + ":");
				str3.append(rank.getName() + ":");
				str3.append(rank.getPreifx());
				list4.add(str3.toString());
				List<String> list5 = new ArrayList<String>();
				for (String p : rank.getPlayers()) {
					list5.add(p);
				}
				SettingsManager.getEmpireData().set(str.toString() + ".rank." + str3.toString() + ".players", list5);
				List<String> list6 = new ArrayList<String>();
				for (EmpirePermission ep : rank.getPermissions()) {
					list6.add(ep.toString());
				}
				SettingsManager.getEmpireData().set(str.toString() + ".rank." + str3.toString() + ".permissions", list6);
			}
			SettingsManager.getEmpireData().set(str.toString() + ".ranks", list4);
		}
		SettingsManager.getEmpireData().set("empires", list);
	}
	public static HashMap<UUID, CoreWorld> getWorlds() {
		return worlds;
	}
	public static void setWorlds(HashMap<UUID, CoreWorld> worlds) {
		UtilManager.worlds = worlds;
	}
	
	public static void addWorld(World myWorld){
		worlds.put(myWorld.getUID(), new CoreWorld());
	}
	
	
	public static void loadEmpires() {
		List<World> myWorlds = Bukkit.getServer().getWorlds();
		
		for(World myWorld : myWorlds){
			worlds.put(myWorld.getUID(), new CoreWorld());
		}
		
		
		List<String> list = SettingsManager.getEmpireData().getStringList("empires");
		for (String s : list) {
			String[] words = s.split(":");
			Integer Id = Integer.parseInt(words[0]);
			String name = words[1];
			String owner = words[2];
			Empire empire = new Empire(Id, name, owner);
			List<String> list2 = SettingsManager.getEmpireData().getStringList(s + ".cores");
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
			    worlds.get(world2.getUID()).addCore(core);
				cores.add(core);
				empire.ac(core);
			}
			List<String> list3 = SettingsManager.getEmpireData().getStringList(s + ".players");
			for (String playername : list3) {
				empire.addPlayer(playername);
			}
			List<String> list4 = SettingsManager.getEmpireData().getStringList(s + ".ranks");
			for (String rankstring : list4) {
				String[] words2 = rankstring.split(":");
				Rank rank = new Rank(Integer.parseInt(words2[0]), words2[1], empire, words2[2]);
				List<String> list5 = SettingsManager.getEmpireData().getStringList(s + ".rank." + rankstring + ".players");
				for (String playername : list5) {
					rank.addPlayer(playername);
				}
				List<String> list6 = SettingsManager.getEmpireData().getStringList(s + ".rank." + rankstring + ".permissions");
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
			if (empire.getName().equalsIgnoreCase(name)) return empire;
		}
		return null;
	}
	public static boolean containsEmpireWithName(String name) {
		for (Empire empire : empires) {
			if (empire.getName().equalsIgnoreCase(name)) return true;
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

	
	public static String colourUp(String string) {
		string.replaceAll("&0", ChatColor.BLACK + "");
		string.replaceAll("&1", ChatColor.DARK_BLUE + "");
		string.replaceAll("&2", ChatColor.DARK_GREEN + "");
		string.replaceAll("&3", ChatColor.DARK_AQUA + "");
		string.replaceAll("&4", ChatColor.DARK_RED + "");
		string.replaceAll("&5", ChatColor.DARK_PURPLE + "");
		string.replaceAll("&6", ChatColor.GOLD + "");
		string.replaceAll("&7", ChatColor.GRAY + "");
		string.replaceAll("&8", ChatColor.DARK_GRAY + "");
		string.replaceAll("&9", ChatColor.BLUE + "");
		string.replaceAll("&a", ChatColor.GREEN + "");
		string.replaceAll("&b", ChatColor.AQUA + "");
		string.replaceAll("&c", ChatColor.RED + "");
		string.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
		string.replaceAll("&e", ChatColor.YELLOW + "");
		string.replaceAll("&f", ChatColor.WHITE + "");
		string.replaceAll("&k", ChatColor.MAGIC + "");
		string.replaceAll("&l", ChatColor.BOLD + "");
		string.replaceAll("&m", ChatColor.STRIKETHROUGH + "");
		string.replaceAll("&n", ChatColor.UNDERLINE + "");
		string.replaceAll("&o", ChatColor.ITALIC + "");
		string.replaceAll("&r", ChatColor.RESET + "");
		return string;
	}
	public static Empire getEmpireWithPlayer(Player myPlayer) {
		return empireplayers.get(myPlayer.getName());		
	}

	

}
