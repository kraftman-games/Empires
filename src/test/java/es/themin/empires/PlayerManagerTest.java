package es.themin.empires;



import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;




import com.jolbox.bonecp.BoneCP;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.managers.PlayerManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class PlayerManagerTest {


	@Before
	public void setupEnvironment(){
		
	}
	

	
	
	
	@Test
	public void PlayerManagerSave(){
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        
        
        
        EmpiresDAL myEmpiresDal = PowerMockito.mock(EmpiresDAL.class);
	    PlayerManager myPlayerManager = new PlayerManager(myEmpiresDal, players);
	    myPlayerManager.save();
	    Mockito.verify(myEmpiresDal).createOrUpdatePlayers(players);
	}
	
	

	
//	@Test 
//	public void GetPlayerByName() {
//        EPlayer myCorePlayer = PowerMockito.mock(EPlayer.class);
//        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
//        
//        Mockito.when(myCorePlayer.getName()).thenReturn("kraftman");
//        
//        players.put(myCorePlayer.getUUID(), myCorePlayer);
//        EmpiresDAL myEmpiresDal = PowerMockito.mock(EmpiresDAL.class);
//	    PlayerManager myPlayerManager = new PlayerManager(myEmpiresDal, players);
//
//	    assertTrue(myCorePlayer == myPlayerManager.getPlayer("kraftman"));
//	}
	
	@Test 
	public void GetPlayerByUUID() {
        EPlayer myCorePlayer = PowerMockito.mock(EPlayer.class);
        Mockito.when(myCorePlayer.getUUID()).thenReturn(UUID.randomUUID());
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        EmpiresDAL myEmpiresDal = PowerMockito.mock(EmpiresDAL.class);
	    PlayerManager myPlayerManager = new PlayerManager(myEmpiresDal, players);

	    assertTrue(myCorePlayer == myPlayerManager.getPlayer(myCorePlayer.getUUID()));
	}
	
	@Test 
	public void TestPlayerExistsbyUUID() {
        EPlayer myCorePlayer = PowerMockito.mock(EPlayer.class);
        Mockito.when(myCorePlayer.getUUID()).thenReturn(UUID.randomUUID());
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        EmpiresDAL myEmpiresDal = PowerMockito.mock(EmpiresDAL.class);
	    PlayerManager myPlayerManager = new PlayerManager(myEmpiresDal, players);

	    assertTrue(myPlayerManager.playerExists(myCorePlayer.getUUID()));
	    
	    UUID myUUID = UUID.randomUUID();
	    assertFalse(myPlayerManager.playerExists(myUUID));
	}
	
	@Test 
	public void TestAddPlayer() {
        EPlayer myCorePlayer = PowerMockito.mock(EPlayer.class);
        Mockito.when(myCorePlayer.getUUID()).thenReturn(UUID.randomUUID());
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        EmpiresDAL myEmpiresDal = PowerMockito.mock(EmpiresDAL.class);
	    PlayerManager myPlayerManager = new PlayerManager(myEmpiresDal, players);

	    myPlayerManager.addPlayer(myCorePlayer);
	    

	    assertTrue(myPlayerManager.getPlayers().size() == 1);
	    assertTrue(myPlayerManager.getPlayers().containsKey(myCorePlayer.getUUID()));
	}
}

















