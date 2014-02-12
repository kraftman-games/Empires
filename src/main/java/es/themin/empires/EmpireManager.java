package es.themin.empires;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import es.themin.empires.cores.Core;
import es.themin.empires.cores.CoreUtils;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.CorePlayer;
import es.themin.empires.util.CoreWorld;
import es.themin.empires.util.Empire;
import es.themin.empires.util.Permissions;
import es.themin.empires.util.Rank;
import es.themin.empires.util.UtilManager;

public class EmpireManager implements Manager {
	
	private empires myPlugin;
	private ArrayList<Empire> empires = new ArrayList<Empire>();
	private PlayerManager Players;
	private WorldManager Worlds;
	

    private YamlConfiguration empiredata;
    private File efile;

	public ArrayList<Empire> getEmpires() {
		return empires;
	}

	public EmpireManager(empires plugin) {
		myPlugin = plugin;
		Players = plugin.Players;
		Worlds = plugin.Worlds;
	}
	
	public void load(){
		
		efile = createFile("empiredata.yml");
	       
       	empiredata = YamlConfiguration.loadConfiguration(efile);
	}

	
	private  File createFile(String fileName){
    	
    	File myFile = new File(myPlugin.getDataFolder(), fileName);
        
        if (!myFile.exists()) {
                try {
                	myFile.createNewFile();
        				myPlugin.getLogger().info("[Empires] "+fileName+" not found, making you one");
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create "+fileName);
                }
        }
        
        return myFile;
    	
    }
	
	public  FileConfiguration getEmpireData() {
        return empiredata;
    }

    public void save() {
        try {
                empiredata.save(efile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save empiredata.yml!");
        }
    }
//    public static void saveEmpireDataToFile(File file) {
//        try {
//                empiredata.save(file);
//        }
//        catch (IOException e) {
//                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save empiredata.yml!");
//        }
//    }

    public  void reload() {
        empiredata = YamlConfiguration.loadConfiguration(efile);
    }
	
	public void addEmpire(Empire empire) {
		this.empires.add(empire);
		
	}
	
	public  void loadEmpires(empires plugin) {
		List<World> myWorlds = Bukkit.getServer().getWorlds();
		
		for(World myWorld : myWorlds){
			Worlds.getWorlds().put(myWorld.getUID(), new CoreWorld());
		}
		
		
		List<String> list = empiredata.getStringList("empires");
		for (String empireName : list) {
			String[] words = empireName.split(":");
			Integer Id = Integer.parseInt(words[0]);
			String name = words[1];
			String owner = words[2];
			CorePlayer myCorePlayer = Players.getPlayer(UUID.fromString(owner));
			Empire empire = new Empire(myPlugin, name, myCorePlayer);
			
			loadEmpireCores(plugin, empire);
			
			loadEmpirePlayers(empire);
			
			loadEmpireRanks(empire);
			
			empire.Save();
		}
	}
	
	private  void loadEmpireRanks(Empire empire){
		List<String> rankList = empiredata.getStringList(empire.getName() + ".ranks");
		for (String rankString : rankList) {
			String[] words2 = rankString.split(":");
			Rank rank = new Rank(Integer.parseInt(words2[0]), words2[1], empire, words2[2]);
			List<String> playersInRank = empiredata.getStringList(empire.getName() + ".rank." + rankString + ".players");
			for (String playername : playersInRank) {
				rank.addPlayer(playername);
			}
			List<String> playerPermissions = empiredata.getStringList(empire.getName() + ".rank." + rankString + ".permissions");
			for (String permission : playerPermissions) {
				EmpirePermission ep = Permissions.getPermission(permission);
				if (ep != null){
					rank.addPermission(ep);
				}
			}
			empire.addRank(rank);
		}
	}
	
	private  void loadEmpirePlayers(Empire empire){
		List<String> playerList = empiredata.getStringList(empire.getName() + ".players");
		for ( String playerUUID : playerList) {
			UUID myUUID = UUID.fromString(playerUUID);
			//empire.addPlayer(myUUID);
		}
	}
	
	private  void loadEmpireCores(empires plugin, Empire empire){
		List<String> list2 = empiredata.getStringList(empire.getName() + ".cores");
		for (String s2: list2) {
			String[] words2 = s2.split(":");
			int coreID  = Integer.parseInt(words2[0]);
			
			CoreType coretype = CoreUtils.GetCoreType(words2[1]);
			
			World world2 = Bukkit.getServer().getWorld(words2[2]);
			int x2 = Integer.parseInt(words2[3]); // - 0:BASE:world:-249:78:223:1:0:kraft
			int y2 = Integer.parseInt(words2[4]);
			int z2 = Integer.parseInt(words2[5]);
			Location location = new Location(world2, x2, y2, z2);
			int level = Integer.parseInt(words2[6]);
			Core core = new Core(myPlugin, coreID, coretype, location, level, empire);
		    //core.build();
		    Worlds.getWorlds().get(world2.getUID()).addCore(core);
			plugin.Cores.getCores().add(core);
			empire.ac(core);
		}
	}
	
	public int nextUnusedEmpireId(){
		int i = 0;
		while (getEmpireWithID(i) != null){
			i++;
		}
		return i;
		
	}

	public void removeEmpire(Empire empire) {
		int i = this.getEmpires().indexOf(empire);
		this.empires.remove(i);
		
	}
	public Empire getEmpireWithName(String name) {
		for (Empire empire : this.empires) {
			if (empire.getName().equalsIgnoreCase(name)) return empire;
		}
		return null;
	}
	public boolean containsEmpireWithName(String name) {
		for (Empire empire : this.getEmpires()) {
			if (empire.getName().equalsIgnoreCase(name)) return true;
		}
		return false;
	}
	public Empire getEmpireWithID(int Id) {
		for (Empire empire : this.getEmpires()) {
			if (empire.getId() == Id) return empire;
		}
		return null;
	}
	public boolean containsEmpireWithId(int Id) {
		for (Empire empire : this.getEmpires()) {
			if (empire.getId() == Id) return true;
		}
		return false;
	}
	
	public void saveEmpires() {
		List<String> list = new ArrayList<String>();
		for (Empire empire : this.empires) {
			StringBuilder str = new StringBuilder();
			str.append(empire.getId() + ":");
			str.append(empire.getName() + ":");
			str.append(empire.getOwner());
			list.add(str.toString());
			empiredata.set(str.toString() + ".id", empire.getId());
			empiredata.set(str.toString() + ".name", empire.getName());
			empiredata.set(str.toString() + ".name", empire.getOwner());
			List<String> playerList = new ArrayList<String>();
			for (CorePlayer player : empire.getPlayers().values()) {
				//FixedMetadataValue playerEmpire = new FixedMetadataValue (myPlugin, this.getId());
				playerList.add(player.getName());
				
			}
			empiredata.set(str.toString() + ".players", playerList);
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
			empiredata.set(str.toString() + ".cores", list3);
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
				empiredata.set(str.toString() + ".rank." + str3.toString() + ".players", list5);
				List<String> list6 = new ArrayList<String>();
				for (EmpirePermission ep : rank.getPermissions()) {
					list6.add(ep.toString());
				}
				empiredata.set(str.toString() + ".rank." + str3.toString() + ".permissions", list6);
			}
			empiredata.set(str.toString() + ".ranks", list4);
		}
		empiredata.set("empires", list);
	}

	public boolean isValidName(String string) {
		if (string.isEmpty())
			return false;
		else if(getEmpireWithName(string) != null)
			return false;
		
		return true;
	}
}












