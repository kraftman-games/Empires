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
    
    private File createFile(String fileName){
    	
    	File myFile = new File(p.getDataFolder(), fileName);
        
        if (!myFile.exists()) {
                try {
                	myFile.createNewFile();
        				p.getLogger().info("[Empires] "+fileName+" not found, making you one");
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create "+fileName);
                }
        }
        
        return myFile;
    	
    }
   
    public void loadSettings(Plugin plugin) {
    	p = plugin;
    	
    	config = plugin.getConfig();
		
		cfile = createFile("config.yml");
		
		getConfig().options().copyDefaults();
		config.options().copyDefaults(true);
		
		config.options().copyHeader();
		saveConfig();
        //random data file bellow :/
        
		dfile = createFile("data.yml");
       
        data = YamlConfiguration.loadConfiguration(dfile);
        
        
        //player data bellow
        pfile = createFile("playerdata.yml");
       
        playerdata = YamlConfiguration.loadConfiguration(pfile);
        
        //empire data bellow
        efile = createFile("empiredata.yml");
        
       
       	empiredata = YamlConfiguration.loadConfiguration(efile);
       	
        wfile = new File(plugin.getDataFolder(), "worldconfig.yml");
        
        
        
        if (!wfile.exists()) {
                try {
                        wfile.createNewFile();
                        worlddata = YamlConfiguration.loadConfiguration(wfile);
                       	getWorldData().addDefault("worlds.world.allowcoreplace", true);
                       	getWorldData().addDefault("worlds.world.allowcommanduse", true);
                       	getWorldData().addDefault("worlds.world.allowplaceofcore.BASE", true);
                       	getWorldData().addDefault("worlds.world.allowplaceofcore.MOB", true);
                       	getWorldData().addDefault("worlds.world.allowplaceofcore.FARM", true);
                       	getWorldData().addDefault("worlds.world.allowplaceofcore.MONSTER", true);
                       	getWorldData().addDefault("worlds.world.allowplaceofcore.FORTIFICATION", true);
                       	getWorldData().addDefault("worlds.world.allowplaceofcore.GRIEF", true);
                       	getWorldData().addDefault("worlds.world.allowplaceofcore.OUTPOST", true);
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
    public static FileConfiguration getWorldData() {
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