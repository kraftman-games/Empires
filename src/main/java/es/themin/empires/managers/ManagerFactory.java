package es.themin.empires.managers;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;




import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import es.themin.empires.CoreManager;
import es.themin.empires.EmpiresDAL;
import es.themin.empires.empires;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class ManagerFactory {

	
	private empires myPlugin;
	private ArrayList<IManager> Managers;
	private BoneCP connectionPool;
	File playerFile = null;
    File empireFile = null;
    EmpiresDAL myempiresDAL = null;
	
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
		this.connectionPool = connectionPool;
		
		 playerFile = createFile("Players.yml");
         empireFile = createFile("Empires.yml");
         myempiresDAL = new EmpiresDAL(playerFile, empireFile, connectionPool);
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
        
        PlayerManager myPlayerManager = new PlayerManager(myempiresDAL, players);
        Managers.add(myPlayerManager);
        
	    return myPlayerManager;
	    
	}
	
	public EmpireManager CreateEmpireManager(){
		        
        ArrayList<Empire> empires = new ArrayList<Empire>();
		
        EmpireManager MyEmpireManager = new EmpireManager(myempiresDAL, empires);
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
















