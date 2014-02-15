package es.themin.empires;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import es.themin.empires.util.EPlayer;

public class EmpiresDAL {

	public void savePlayers(HashMap<UUID, EPlayer> players){
		//call the below with a default file.
		
	}
	
	public void savePlayers(HashMap<UUID, EPlayer> players, File datafile){
		YamlConfiguration playerdata = YamlConfiguration.loadConfiguration(datafile);
		
		for (EPlayer myPlayer : players.values()) {
    		if (myPlayer.getEmpire() != null){
    			playerdata.set(myPlayer.getUUID() + ".empire", myPlayer.getEmpire().getID());
    		}
    		playerdata.set(myPlayer.getUUID() + ".name", myPlayer.getName());
    	}
    	
        try {
                playerdata.save(datafile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save playerdata.yml!");
        }
		
	}
	
	public HashMap<UUID, EPlayer> loadPlayers(){
		
		HashMap<UUID, EPlayer> myPlayers = new HashMap<UUID, EPlayer>();
		
		return myPlayers;
	}
}
