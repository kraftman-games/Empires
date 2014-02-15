package es.themin.empires.managers;

import java.util.HashMap;
import java.util.UUID;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.empires;
import es.themin.empires.util.EPlayer;

public class ManagerFactory {

	
	public PlayerManager CreatePlayerManager(){
        
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        
        EmpiresDAL myempiresDAL = new EmpiresDAL();
		
	    return new PlayerManager(myempiresDAL, players);
	    
	}
	
	public EmpireManager CreateEmpireManager(){
		
		EmpiresDAL myempiresDAL = new EmpiresDAL();
		
		return new EmpireManager(myempiresDAL);
	}
}
