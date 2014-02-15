package es.themin.empires.managers;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.util.CorePlayer;

public class ManagerFactory {

	
	public PlayerManager CreatePlayerManager(){
		
		//File pfile = SettingsManager.createFile("playerdata.yml");
        
        HashMap<UUID, CorePlayer> players = new HashMap<UUID, CorePlayer>();
        
        EmpiresDAL myempiresDAL = new EmpiresDAL();
		
	    return new PlayerManager(myempiresDAL, players);
	    
	}
}
