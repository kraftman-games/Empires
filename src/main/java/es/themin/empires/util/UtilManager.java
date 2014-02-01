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
import es.themin.empires.enums.BattleType;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.wars.Battle;
import es.themin.empires.wars.Battle.BattleTeam;
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

	public static void saveWars() {
		Bukkit.getServer().getPluginManager().getPlugin("Empires").getLogger().info("[Empires] Saving Wars ...");
		List<String> listofwars = new ArrayList<String>();
		for (War war : wars) {
			String idforsaving = war.getEmpire1().getName() + ":" + war.getEmpire2().getName() + ":" + war.getStart();
			listofwars.add(idforsaving);
			List<String> empire1allies = new ArrayList<String>();
			for (Empire empire : war.getAllEmpiresOnTeam1()) {
				empire1allies.add(empire.getName());
			}
			SettingsManager.getWarData().set(idforsaving, empire1allies);
			List<String> empire2allies = new ArrayList<String>();
			for (Empire empire : war.getAllEmpiresOnTeam2()) {
				empire2allies.add(empire.getName());
			}
			SettingsManager.getWarData().set(idforsaving, empire2allies);
			if (war.getVictor() != null)SettingsManager.getWarData().set(idforsaving + ".victor", war.getVictor().getName());
			if (war.getEnd() != 0)SettingsManager.getWarData().set( idforsaving + ".end", war.getEnd());
			SettingsManager.getWarData().set(idforsaving + ".empire1wins", war.getEmpire1Wins());
			SettingsManager.getWarData().set(idforsaving + ".empire2wins", war.getEmpire2Wins());
			SettingsManager.getWarData().set(idforsaving + ".ongoing", war.isOnGoing());
			SettingsManager.getWarData().set(idforsaving + ".team1percent", war.getTeam1Percent());
			List<String> listofbattles = new ArrayList<String>();
			for (Battle battle : war.getBattle()) {
				String Bidforsaving = battle.getEmpire1().getName() + ":" + battle.getEmpire2().getName() + ":" + battle.getStart();
				listofbattles.add(Bidforsaving);
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".end", battle.getEnd());
				List<String> bempire1allies = new ArrayList<String>();
				for (Empire empire : battle.getAllEmpiresOnTeam1()) {
					bempire1allies.add(empire.getName());
				}
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".empire1allies", bempire1allies);
				List<String> bempire2allies = new ArrayList<String>();
				for (Empire empire : battle.getAllEmpiresOnTeam2()) {
					bempire2allies.add(empire.getName());
				}
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".empire2allies", bempire2allies);
				if (battle.getVictor() !=null ) SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".victor", battle.getVictor().getName());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".type", battle.getType().toString());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".ongoing", battle.isOnGoing());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".team1points", battle.getTeam1Points());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".team2points", battle.getTeam2Points());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".endsintie", battle.endedInATie());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".killsforwin", battle.getKillsForWin());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".damageforwin", battle.getDamageForWin());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".attacker", battle.getAttackingTeam().toString());
			}
			SettingsManager.getWarData().set(idforsaving + ".battles.list", listofbattles);
		}
		SettingsManager.getWarData().set("wars", listofwars);
		Bukkit.getServer().getPluginManager().getPlugin("Empires").getLogger().info("[Empires] ... Wars Saved");
		SettingsManager.saveWarData();
	}
	//CANNOT BE RUN BEFORE EMPIRES ARE LOADED
	public static void loadWars() {
		List<String> listofwars = SettingsManager.getWarData().getStringList("wars");
		for (String warname : listofwars) {
			String[] warnamewords = warname.split(":");
			War war = new War(getEmpireWithName(warnamewords[0]), getEmpireWithName(warnamewords[0]));
			war.setStart(Long.parseLong(warnamewords[2]));
			List<String> empire1allies = SettingsManager.getWarData().getStringList(warname + ".empire1allies");
			for (String empire1ally : empire1allies) {
				war.addEmpireToTeam1(getEmpireWithName(empire1ally));
			}
			List<String> empire2allies = SettingsManager.getWarData().getStringList(warname + ".empire2allies");
			for (String empire2ally : empire2allies) {
				war.addEmpireToTeam2(getEmpireWithName(empire2ally));
			}
			if (SettingsManager.getWarData().getString(warname + ".victor") != null) war.setVictor(getEmpireWithName(SettingsManager.getWarData().getString(warname + ".victor")));
			war.setEnd(SettingsManager.getWarData().getLong(warname + ".end"));
			war.setEmpire1Wins(SettingsManager.getWarData().getInt(warname + ".empire1wins"));
			war.setEmpire2Wins(SettingsManager.getWarData().getInt(warname + ".empire2wins"));
			war.setOnGoing(SettingsManager.getWarData().getBoolean(warname + "ongoing"));
			war.setTeam1Percent(SettingsManager.getWarData().getLong(warname + ".team1percent"));
			List<String> battlenames = SettingsManager.getWarData().getStringList(warname + ".battles.list");
			for (String battlename : battlenames) {
				String[] battlenamewords = battlename.split(":");
				BattleType type = null;
				if (SettingsManager.getWarData().getString(warname + ".battles." + battlename + ".type").equalsIgnoreCase("DEATHMATCH")) type = BattleType.DEATHMATCH;
				if (SettingsManager.getWarData().getString(warname + ".battles." + battlename + ".type").equalsIgnoreCase("OBLITERATION")) type = BattleType.OBLITERATION;
				BattleTeam attacker = null;
				if (SettingsManager.getWarData().getString(warname + ".battles." + battlename + ".attacker").equalsIgnoreCase("team1")) attacker = BattleTeam.team1;
				if (SettingsManager.getWarData().getString(warname + ".battles." + battlename + ".attacker").equalsIgnoreCase("team2")) attacker = BattleTeam.team2;
				Battle battle = new Battle(getEmpireWithName(battlenamewords[0]), getEmpireWithName(battlenamewords[1]), war, type, attacker);
				//TODO add the battles loading.
			
			}
		}
	}
	public static String createTitle(String message, ChatColor color) {
		int length = message.length();
		int dashlength = 50 - length;
		dashlength = (int) dashlength / 2 -4;
		StringBuilder str = new StringBuilder();
		str.append(color + ".oOo");
		for (int i = 1 ; i < dashlength ; i++) {
			str.append("=");
		}
		str.append(ChatColor.RESET + message + color);
		for (int i = 1 ; i < dashlength ; i++) {
			str.append("=");
		}
		str.append(color + "oOo.");
		return str.toString();
	}

}
