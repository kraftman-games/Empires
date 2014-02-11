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
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import es.themin.empires.cores.Core;
import es.themin.empires.cores.CoreUtils;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.CoreWorld;
import es.themin.empires.util.Empire;
import es.themin.empires.util.Permissions;
import es.themin.empires.util.Rank;

public class SettingsManager {
	    
    private static empires myPlugin;
    private static WorldManager Worlds;
    private PlayerManager Players;
   
    static FileConfiguration config;
    static File cfile;
   
    static YamlConfiguration data;
    static File dfile;
    
    YamlConfiguration coredata;
    File corefile;
    
    static YamlConfiguration playerdata;
    static File pfile;
    
    static YamlConfiguration empiredata;
    static File efile;
    
    static YamlConfiguration worlddata;
    static File wfile;
    
    static YamlConfiguration messagedata;
    static File mfile;
    
    static YamlConfiguration wardata;
    static File warfile;
    
    public SettingsManager(empires plugin) {
    	myPlugin = plugin;
    	Worlds = plugin.Worlds;
    	Players = plugin.Players;
    }
    
    private static File createFile(String fileName){
    	
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
    
	public static void loadEmpires(empires plugin) {
		List<World> myWorlds = Bukkit.getServer().getWorlds();
		
		for(World myWorld : myWorlds){
			Worlds.getWorlds().put(myWorld.getUID(), new CoreWorld());
		}
		
		
		List<String> list = SettingsManager.getEmpireData().getStringList("empires");
		for (String empireName : list) {
			String[] words = empireName.split(":");
			Integer Id = Integer.parseInt(words[0]);
			String name = words[1];
			String owner = words[2];
			Empire empire = new Empire(myPlugin, Id, name, owner);
			
			loadEmpireCores(plugin, empire);
			
			loadEmpirePlayers(empire);
			
			loadEmpireRanks(empire);
			
			empire.Save();
		}
	}
	
	private static void loadEmpireRanks(Empire empire){
		List<String> rankList = SettingsManager.getEmpireData().getStringList(empire.getName() + ".ranks");
		for (String rankString : rankList) {
			String[] words2 = rankString.split(":");
			Rank rank = new Rank(Integer.parseInt(words2[0]), words2[1], empire, words2[2]);
			List<String> playersInRank = SettingsManager.getEmpireData().getStringList(empire.getName() + ".rank." + rankString + ".players");
			for (String playername : playersInRank) {
				rank.addPlayer(playername);
			}
			List<String> playerPermissions = SettingsManager.getEmpireData().getStringList(empire.getName() + ".rank." + rankString + ".permissions");
			for (String permission : playerPermissions) {
				EmpirePermission ep = Permissions.getPermission(permission);
				if (ep != null){
					rank.addPermission(ep);
				}
			}
			empire.addRank(rank);
		}
	}
	
	private static void loadEmpirePlayers(Empire empire){
		List<String> playerList = SettingsManager.getEmpireData().getStringList(empire.getName() + ".players");
		for ( String playerUUID : playerList) {
			UUID myUUID = UUID.fromString(playerUUID);
			//empire.addPlayer(myUUID);
		}
	}
	
	private static void loadEmpireCores(empires plugin, Empire empire){
		List<String> list2 = SettingsManager.getEmpireData().getStringList(empire.getName() + ".cores");
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
   
    public static void loadSettings() {
    	
    	
    	config = myPlugin.getConfig();
		
		cfile = createFile("config.yml");
		
		getConfig().options().copyDefaults();
		config.options().copyDefaults(true);
		
		config.options().copyHeader();
		saveConfig();
        //random data file bellow :/
		
		
        
		dfile = createFile("data.yml");
       
        data = YamlConfiguration.loadConfiguration(dfile);
        
        warfile = createFile("wars.yml");
        
        wardata = YamlConfiguration.loadConfiguration(warfile);
        
        pfile = createFile("playerdata.yml");
       
        playerdata = YamlConfiguration.loadConfiguration(pfile);
        
        mfile = createFile("messages.yml");
        
        efile = createFile("empiredata.yml");
       
       	empiredata = YamlConfiguration.loadConfiguration(efile);
       	
        wfile = new File(myPlugin.getDataFolder(), "worldconfig.yml");
        
        
        
        if (!wfile.exists()) {
                try {
                        wfile.createNewFile();
                        worlddata = YamlConfiguration.loadConfiguration(wfile);
                        worlddata.addDefault("worlds.world.allowcoreplace", true);
                        worlddata.addDefault("worlds.world.allowcommanduse", true);
                        worlddata.addDefault("worlds.world.allowplaceofcore.BASE", true);
                        worlddata.addDefault("worlds.world.allowplaceofcore.MOB", true);
                        worlddata.addDefault("worlds.world.allowplaceofcore.FARM", true);
                        worlddata.addDefault("worlds.world.allowplaceofcore.MONSTER", true);
                        worlddata.addDefault("worlds.world.allowplaceofcore.FORTIFICATION", true);
                        worlddata.addDefault("worlds.world.allowplaceofcore.GRIEF", true);
                        worlddata.addDefault("worlds.world.allowplaceofcore.OUTPOST", true);
                        worlddata.options().copyDefaults();
                       	saveWorldData();
        				myPlugin.getLogger().info("[Empires] worldconfig.yml not found, making you one");
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create worlddata.yml!");
                }
        }
       
        worlddata = YamlConfiguration.loadConfiguration(wfile);
        
        //
    }
public static YamlConfiguration getMessagedata() {
		return messagedata;
	}

	public static void setMessagedata(YamlConfiguration messagedata) {
		SettingsManager.messagedata = messagedata;
	}

	//###############################   
    public static FileConfiguration getData() {
            return data;
    }
    
   
    public static void saveData() {
            try {
                    data.save(dfile);
            }
            catch (IOException e) {
                    Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
            }
    }
   
    public static void reloadData() {
            data = YamlConfiguration.loadConfiguration(dfile);
    }
//############################CORE DAT
/*    public FileConfiguration getCoreData() {
        return coredata;
    }

    public void saveCoreData() {
        try {
                coredata.save(corefile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save coredata.yml!");
        }
    }

    public void reloadCoreData() {
        coredata = YamlConfiguration.loadConfiguration(corefile);
    }*/
  //#########################PLAYER DAT
    public static FileConfiguration getPlayerData() {
        return playerdata;
    }

    public static void savePlayerData() {
        try {
                playerdata.save(pfile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save playerdata.yml!");
        }
    }
    public static void savePlayerDataToFile(File file) {
        try {
                playerdata.save(file);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save playerdata.yml!");
        }
    }

    public static void reloadPlayerData() {
        playerdata = YamlConfiguration.loadConfiguration(pfile);
    }
  //######################EMPIRE DAT
    public static FileConfiguration getEmpireData() {
        return empiredata;
    }

    public static void saveEmpireData() {
        try {
                empiredata.save(efile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save empiredata.yml!");
        }
    }
    public static void saveEmpireDataToFile(File file) {
        try {
                empiredata.save(file);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save empiredata.yml!");
        }
    }

    public static void reloadEmpireData() {
        empiredata = YamlConfiguration.loadConfiguration(efile);
    }
 //#############################   
    
    //######################world DAT
    public static FileConfiguration getWorldData() {
        return worlddata;
    }

    public static void saveWorldData() {
        try {
                worlddata.save(wfile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save worlddata.yml!");
        }
    }
    public static void saveWorldDataToFile(File file) {
        try {
                worlddata.save(file);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save worlddata.yml!");
        }
    }

    public static void reloadWorldData() {
        worlddata = YamlConfiguration.loadConfiguration(wfile);
    }
 //######### WAR DATA 
    public static FileConfiguration getWarData() {
        return wardata;
    }

    public static void saveWarData() {
        try {
                wardata.save(warfile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save wars.yml!");
        }
    }
    public static void saveWarDataToFile(File file) {
        try {
                wardata.save(file);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save wars.yml!");
        }
    }

    public static void reloadWarData() {
        wardata = YamlConfiguration.loadConfiguration(warfile);
    }
 //#############################   
   
    public static FileConfiguration getConfig() {
            return config;
    }
   
    public static void saveConfig() {
            try {
                    config.save(cfile);
            }
            catch (IOException e) {
                    Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
            }
    }
    public static  void saveConfigToFile(File f) {
        try {
                config.save(f);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
        }
}
   
    public static void reloadConfig() {
            config = YamlConfiguration.loadConfiguration(cfile);
    }
//###############################   
    public static PluginDescriptionFile getDesc() {
            return myPlugin.getDescription();
    }
    public static void saveAll(){
    	//saveConfig();
    	saveEmpireData();
    	savePlayerData();
    	saveWorldData();
    	saveData();
    	saveWarData();
    }
    public boolean bool(int i) {
    	if (i == 1) {
    		return true;
    	}
    	return false;
    }
}
