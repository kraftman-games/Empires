package es.themin.empires.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import es.themin.empires.CoreManager;
import es.themin.empires.EmpiresDAL;
import es.themin.empires.empires;
import es.themin.empires.util.EPlayer;

public class ManagerFactory {

	
	private static empires myPlugin;
	private static ArrayList<IManager> Managers;
	
	public ManagerFactory(empires plugin) {
		myPlugin = plugin;
		Managers = new ArrayList<IManager>();
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
		
        PlayerManager myPlayerManager = new PlayerManager(myempiresDAL, players);
        Managers.add(myPlayerManager);
        
        
	    return myPlayerManager;
	    
	}
	
	public EmpireManager CreateEmpireManager(){
		File playerFile = createFile("Players.yml");
        File empireFile = createFile("Empires.yml");
        EmpiresDAL myempiresDAL = new EmpiresDAL(playerFile, empireFile);
		
        EmpireManager MyEmpireManager = new EmpireManager(myempiresDAL);
        Managers.add(MyEmpireManager);
		return MyEmpireManager;
	}
	
	public void loadManagers(){
		for (IManager m : Managers){
			m.load();
		}
	}
	
	public void saveManagers(){
		for (IManager m : Managers){
			m.save();
		}
	}
	
	public void reloadManagers(){
		for (IManager m : Managers){
			m.reload();
		}
	}


	public CoreManager CreateCoreManager(empires empires) {
		return new CoreManager(empires);
	}


	public WorldManager CreateWorldManager(empires empires) {
		return new WorldManager(empires);
	}


	public WarManager CreateWarManager(empires empires) {
		return new WarManager(empires);
	}
}
















