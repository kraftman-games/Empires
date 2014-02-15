package es.themin.empires;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class EmpiresDAL {

//	private  File createFile(String fileName){
//    	
////    	File myFile = new File(myPlugin.getDataFolder() + File.separator + fileName);
////        
////        if (!myFile.exists()) {
////                try {
////                	myFile.createNewFile();
////        				myPlugin.getLogger().info("[Empires] "+fileName+" not found, making you one");
////                }
////                catch (IOException e) {
////                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create "+fileName);
////                }
////        }
//        
//        return myFile;
//    	
//    }
	
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

	public EPlayer loadPlayer(UUID uniqueId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void savePlayer(EPlayer myEPlayer) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Empire> loadEmpires() {
		// TODO Auto-generated method stub
		return null;
	}
}
