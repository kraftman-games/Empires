package es.themin.empires.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.empires;
import es.themin.empires.cores.Core;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class ManagerFactory {

	
	private empires myPlugin;
	private ArrayList<IManager> Managers;
	File playerFile = null;
    File empireFile = null;
    EmpiresDAL myEmpiresDAL = null;
	
	public ManagerFactory(empires plugin,BoneCP connectionPool) {
		myPlugin = plugin;
		Managers = new ArrayList<IManager>();

		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl("jdbc:mysql://192.168.5.60/empirestest"); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
		config.setUsername("empires"); 
		config.setPassword("senimeth345");
		config.setMinConnectionsPerPartition(5);
		config.setMaxConnectionsPerPartition(10);
		config.setPartitionCount(1);
		config.setDefaultAutoCommit(true);
		
		 playerFile = createFile("Players.yml");
         empireFile = createFile("Empires.yml");
         myEmpiresDAL = new EmpiresDAL(playerFile, empireFile, connectionPool);
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
        
        PlayerManager myPlayerManager = new PlayerManager(myEmpiresDAL, players);
        Managers.add(myPlayerManager);
        
	    return myPlayerManager;
	    
	}
	
	public EmpireManager CreateEmpireManager(){
		        
		HashMap<UUID,Empire> empires = new HashMap<UUID,Empire>();
		
        EmpireManager MyEmpireManager = new EmpireManager(myEmpiresDAL, empires);
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
	


	public CoreManager CreateCoreManager() {
		
		HashMap<UUID, Core> cores = new HashMap<UUID,Core>();
		
		CoreManager myCoreManager = new  CoreManager(myEmpiresDAL, cores);
		Managers.add(myCoreManager);
		return myCoreManager;
	}


	public WorldManager CreateWorldManager() {
		return new WorldManager();
	}


	public WarManager CreateWarManager(empires empires) {
		return new WarManager(empires);
	}
}
















