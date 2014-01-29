package es.themin.empires.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SettingsManager {
	 
    private SettingsManager() { }
   
    static SettingsManager instance = new SettingsManager();
   
    public static SettingsManager getInstance() {
            return instance;
    }
   
    Plugin p;
   
    FileConfiguration config;
    File cfile;
   
    YamlConfiguration data;
    File dfile;
    
    YamlConfiguration coredata;
    File corefile;
    
    YamlConfiguration playerdata;
    File pfile;
    
    YamlConfiguration empiredata;
    File efile;
    
    YamlConfiguration worlddata;
    File wfile;
   
    public void setup(Plugin plugin) {
    	config = plugin.getConfig();
		cfile = new File(plugin.getDataFolder() + File.separator + "config.yml");

		if(!plugin.getDataFolder().exists()) {
			try {
				plugin.getDataFolder().createNewFile();
				plugin.getLogger().info("[Empires] config.yml not found, making you one");
			} 
			catch (IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not load folder");
			}
		}
		if (!cfile.exists()) {
			try {
				cfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not load config.yml");
				e.printStackTrace();
				
			}
		}
		getConfig().options().copyDefaults();
		config.options().copyDefaults(true);
		
		config.options().copyHeader();
		saveConfig();
        //random data file bellow :/
        
        dfile = new File(plugin.getDataFolder(), "data.yml");
       
        if (!dfile.exists()) {
                try {
                        dfile.createNewFile();
        				plugin.getLogger().info("[Empires] data.yml not found, making you one");
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
                }
        }
       
        data = YamlConfiguration.loadConfiguration(dfile);
        
        
        //player data bellow
        pfile = new File(plugin.getDataFolder(), "playerdata.yml");
        
        if (!pfile.exists()) {
                try {
                        pfile.createNewFile();
        				plugin.getLogger().info("[Empires] playerdata.yml not found, making you one");
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create playerdata.yml!");
                }
        }
       
        playerdata = YamlConfiguration.loadConfiguration(pfile);
        
        //empire data bellow
        efile = new File(plugin.getDataFolder(), "empiredata.yml");
        
        if (!efile.exists()) {
                try {
                        efile.createNewFile();
        				plugin.getLogger().info("[Empires] empiredata.yml not found, making you one");
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create empiredata.yml!");
                }
        }
       
       	empiredata = YamlConfiguration.loadConfiguration(efile);
       	
        wfile = new File(plugin.getDataFolder(), "worldconfig.yml");
        
        
        
        if (!wfile.exists()) {
                try {
                        wfile.createNewFile();
                        worlddata = YamlConfiguration.loadConfiguration(wfile);
                       	getWorldData().addDefault("worlds.world.allowcoreplace", bool(1));
                       	getWorldData().addDefault("worlds.world.allowcommanduse", bool(1));
                       	getWorldData().addDefault("worlds.world.allowplaceofcore.BASE", bool(1));
                       	getWorldData().addDefault("worlds.world.allowplaceofcore.MOB", bool(1));
                       	getWorldData().addDefault("worlds.world.allowplaceofcore.FARM", bool(1));
                       	getWorldData().addDefault("worlds.world.allowplaceofcore.MONSTER", bool(1));
                       	getWorldData().addDefault("worlds.world.allowplaceofcore.FORTIFICATION", bool(1));
                       	getWorldData().addDefault("worlds.world.allowplaceofcore.GRIEF", bool(1));
                       	getWorldData().addDefault("worlds.world.allowplaceofcore.OUTPOST", bool(1));
                       	getWorldData().options().copyDefaults();
                       	saveWorldData();
        				plugin.getLogger().info("[Empires] worldconfig.yml not found, making you one");
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create worlddata.yml!");
                }
        }
       
        worlddata = YamlConfiguration.loadConfiguration(wfile);
        
        //
    }
//###############################   
    public FileConfiguration getData() {
            return data;
    }
   
    public void saveData() {
            try {
                    data.save(dfile);
            }
            catch (IOException e) {
                    Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
            }
    }
   
    public void reloadData() {
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
    public FileConfiguration getPlayerData() {
        return playerdata;
    }

    public void savePlayerData() {
        try {
                playerdata.save(pfile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save playerdata.yml!");
        }
    }
    public void savePlayerDataToFile(File file) {
        try {
                playerdata.save(file);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save playerdata.yml!");
        }
    }

    public void reloadPlayerData() {
        playerdata = YamlConfiguration.loadConfiguration(pfile);
    }
  //######################EMPIRE DAT
    public FileConfiguration getEmpireData() {
        return empiredata;
    }

    public void saveEmpireData() {
        try {
                empiredata.save(efile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save empiredata.yml!");
        }
    }
    public void saveEmpireDataToFile(File file) {
        try {
                empiredata.save(file);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save empiredata.yml!");
        }
    }

    public void reloadEmpireData() {
        empiredata = YamlConfiguration.loadConfiguration(efile);
    }
 //#############################   
    
    //######################world DAT
    public FileConfiguration getWorldData() {
        return worlddata;
    }

    public void saveWorldData() {
        try {
                worlddata.save(wfile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save worlddata.yml!");
        }
    }
    public void saveWorldDataToFile(File file) {
        try {
                worlddata.save(file);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save worlddata.yml!");
        }
    }

    public void reloadWorldData() {
        worlddata = YamlConfiguration.loadConfiguration(wfile);
    }
 //#############################   
   
    public FileConfiguration getConfig() {
            return config;
    }
   
    public void saveConfig() {
            try {
                    config.save(cfile);
            }
            catch (IOException e) {
                    Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
            }
    }
    public void saveConfigToFile(File f) {
        try {
                config.save(f);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
        }
}
   
    public void reloadConfig() {
            config = YamlConfiguration.loadConfiguration(cfile);
    }
//###############################   
    public PluginDescriptionFile getDesc() {
            return p.getDescription();
    }
    public void saveAll(){
    	saveConfig();
    	saveEmpireData();
    	savePlayerData();
    	saveWorldData();
    	saveData();
    }
    public boolean bool(int i) {
    	if (i == 1) {
    		return true;
    	}
    	return false;
    }
}