package es.themin.empires.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
   
    public void setup(Plugin p) {
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
        cfile = new File(p.getDataFolder(), "config.yml");
        config = p.getConfig();
        config.options().copyDefaults(true);
        saveConfig();
       
        
        //random data file bellow :/
        
        dfile = new File(p.getDataFolder(), "data.yml");
       
        if (!dfile.exists()) {
                try {
                        dfile.createNewFile();
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
                }
        }
       
        data = YamlConfiguration.loadConfiguration(dfile);
        //core data bellow
        corefile = new File(p.getDataFolder(), "coredata.yml");
        
        if (!corefile.exists()) {
                try {
                        corefile.createNewFile();
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create coredata.yml!");
                }
        }
       
        coredata = YamlConfiguration.loadConfiguration(corefile);
        
        //player data bellow
        pfile = new File(p.getDataFolder(), "playerdata.yml");
        
        if (!pfile.exists()) {
                try {
                        pfile.createNewFile();
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create playerdata.yml!");
                }
        }
       
        playerdata = YamlConfiguration.loadConfiguration(pfile);
        
        //empire data bellow
        efile = new File(p.getDataFolder(), "empiredata.yml");
        
        if (!efile.exists()) {
                try {
                        efile.createNewFile();
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create empiredata.yml!");
                }
        }
       
       	empiredata = YamlConfiguration.loadConfiguration(efile);
       	
        wfile = new File(p.getDataFolder(), "worlddata.yml");
        
        if (!efile.exists()) {
                try {
                        efile.createNewFile();
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create worlddata.yml!");
                }
        }
       
       	empiredata = YamlConfiguration.loadConfiguration(efile);
        
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
    public FileConfiguration getCoreData() {
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
    }
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
                empiredata.save(wfile);
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
    	saveCoreData();
    }
}