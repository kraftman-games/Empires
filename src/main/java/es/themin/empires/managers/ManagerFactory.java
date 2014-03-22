package es.themin.empires.managers;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.jolbox.bonecp.BoneCP;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.cores.ICore;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.EWorld;
import es.themin.empires.util.Empire;

public class ManagerFactory {

	File playerFile = null;
    File empireFile = null;
	
	
	
	
	public static PlayerManager CreatePlayerManager(EmpiresDAL myEmpiresDAL){
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        PlayerManager myPlayerManager = new PlayerManager(myEmpiresDAL, players);
	    return myPlayerManager;
	    
	}
	
	public static EmpireManager CreateEmpireManager(EmpiresDAL myEmpiresDAL){
		HashMap<UUID,Empire> empires = new HashMap<UUID,Empire>();
        EmpireManager MyEmpireManager = new EmpireManager(myEmpiresDAL, empires);
		return MyEmpireManager;
	}

	public static CoreManager CreateCoreManager(EmpiresDAL myEmpiresDAL) {
		ConcurrentHashMap<UUID, ICore> cores = new ConcurrentHashMap<UUID,ICore>();
		CoreManager myCoreManager = new  CoreManager(myEmpiresDAL, cores);
		return myCoreManager;
	}


	public static WorldManager CreateWorldManager(EmpiresDAL myEmpiresDAL) {
		HashMap<UUID,EWorld> worlds = new HashMap<UUID,EWorld>();
		
		return new WorldManager(myEmpiresDAL, worlds);
	}


	
	public static SettingsManager CreateSettingsManager(EmpiresDAL myEmpiresDAL){
		HashMap<String, String> settings = new HashMap<String,String>();
		SettingsManager mySettingsManager = new  SettingsManager(myEmpiresDAL, settings);
		return mySettingsManager;
	}
	
	
	public static ManagerAPI createManagerAPI(BoneCP connectionPool){
		
		EmpiresDAL myEmpiresDAL = new EmpiresDAL(connectionPool);
		
		PlayerManager myPlayerManager = CreatePlayerManager(myEmpiresDAL);
		CoreManager myCoreManager = CreateCoreManager(myEmpiresDAL);
		EmpireManager myEmpireManager = CreateEmpireManager(myEmpiresDAL);
		SettingsManager mySettingsManager = CreateSettingsManager(myEmpiresDAL);
		WorldManager myWorldManager = CreateWorldManager(myEmpiresDAL);
		
		
		return new ManagerAPI(myCoreManager, myPlayerManager,myEmpireManager, mySettingsManager, myWorldManager);
		
	}
}
















