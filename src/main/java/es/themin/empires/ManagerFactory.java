package es.themin.empires;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

import es.themin.empires.util.CorePlayer;

public class ManagerFactory {

	
	public PlayerManager CreatePlayerManager(){
		
		File pfile = SettingsManager.createFile("playerdata.yml");
	       
        YamlConfiguration playerdata = YamlConfiguration.loadConfiguration(pfile);
        
        HashMap<UUID, CorePlayer> players = new HashMap<UUID, CorePlayer>();
		
	    return new PlayerManager(playerdata, pfile, players);
	    
	}
}
