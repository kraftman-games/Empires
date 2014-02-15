package es.themin.empires.managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.empires;
import es.themin.empires.util.EPlayer;

public class ManagerFactory {

	
	private static empires myPlugin;
	
	public ManagerFactory(empires plugin) {
		myPlugin = plugin;
	}


	private  File createFile(String fileName){
		
		File myFile = new File(myPlugin.getDataFolder() + File.separator + fileName);
		
		if (!myFile.exists()) {
		        try {
		        	myFile.createNewFile();
						//myPlugin.getLogger().info("[Empires] "+fileName+" not found, making you one");
		        }
		        catch (IOException e) {
		                //Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create "+fileName);
		        }
		}
		
		return myFile;
		
	}
	
	
	
	
	public PlayerManager CreatePlayerManager(){
        
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        File playerFile = createFile("Players.yml");
        File empireFile = createFile("Empires.yml");
        EmpiresDAL myempiresDAL = new EmpiresDAL(playerFile, empireFile);
		
	    return new PlayerManager(myempiresDAL, players);
	    
	}
	
	public EmpireManager CreateEmpireManager(){
		File playerFile = createFile("Players.yml");
        File empireFile = createFile("Empires.yml");
        EmpiresDAL myempiresDAL = new EmpiresDAL(playerFile, empireFile);
		
		return new EmpireManager(myempiresDAL);
	}
}
