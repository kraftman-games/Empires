package es.themin.empires;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class ManagerFactory {

	
	public PlayerManager CreatePlayerManager(){
		
		File pfile = SettingsManager.createFile("playerdata.yml");
	       
        YamlConfiguration playerdata = YamlConfiguration.loadConfiguration(pfile);
		
	    return new PlayerManager(playerdata, pfile);
		
	}
}
