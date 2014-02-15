package es.themin.empires.managers;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.util.EPlayer;

public class ManagerFactory {

	
	public PlayerManager CreatePlayerManager(){
		
		//File pfile = SettingsManager.createFile("playerdata.yml");
        
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        
        EmpiresDAL myempiresDAL = new EmpiresDAL();
		
	    return new PlayerManager(myempiresDAL, players);
	    
	}
}