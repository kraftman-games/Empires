package es.themin.empires.managers;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import com.jolbox.bonecp.BoneCP;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.empires;
import es.themin.empires.cores.Core;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class ManagerFactory {

	File playerFile = null;
    File empireFile = null;
    EmpiresDAL myEmpiresDAL = null;
	
	public ManagerFactory(empires plugin,BoneCP connectionPool) {
         myEmpiresDAL = new EmpiresDAL(playerFile, empireFile, connectionPool);
	}
	
	
	public PlayerManager CreatePlayerManager(){
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        PlayerManager myPlayerManager = new PlayerManager(myEmpiresDAL, players);
	    return myPlayerManager;
	    
	}
	
	public EmpireManager CreateEmpireManager(){
		HashMap<UUID,Empire> empires = new HashMap<UUID,Empire>();
        EmpireManager MyEmpireManager = new EmpireManager(myEmpiresDAL, empires);
		return MyEmpireManager;
	}

	public CoreManager CreateCoreManager() {
		HashMap<UUID, Core> cores = new HashMap<UUID,Core>();
		CoreManager myCoreManager = new  CoreManager(myEmpiresDAL, cores);
		return myCoreManager;
	}


	public WorldManager CreateWorldManager() {
		return new WorldManager();
	}


	public WarManager CreateWarManager(empires empires) {
		return new WarManager(empires);
	}
	
	public ManagerAPI createManagerAPI(){
		PlayerManager myPlayerManager = CreatePlayerManager();
		CoreManager myCoreManager = CreateCoreManager();
		EmpireManager myEmpireManager = CreateEmpireManager();
		
		return (new ManagerAPI(myCoreManager, myPlayerManager,myEmpireManager));
		
	}
}
















