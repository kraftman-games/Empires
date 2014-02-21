package es.themin.empires;



import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import es.themin.empires.managers.PlayerManager;
import es.themin.empires.util.EPlayer;

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
//	public void loadEPlayerByName() {
//        EPlayer myEPlayer = PowerMockito.mock(EPlayer.class);
//        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
//        
//        Mockito.when(myEPlayer.getName()).thenReturn("kraftman");
//        
//        players.put(myEPlayer.getUUID(), myEPlayer);
//        EmpiresDAL myEmpiresDal = PowerMockito.mock(EmpiresDAL.class);
//	    PlayerManager myPlayerManager = new PlayerManager(myEmpiresDal, players);
//
//	    assertTrue(myEPlayer == myPlayerManager.loadEPlayer("kraftman"));
//	}
	
	@Test 
	public void loadPlayer() {
        Player myPlayer = PowerMockito.mock(Player.class);
        Mockito.when(myPlayer.getUniqueId()).thenReturn(UUID.randomUUID());
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        
        
        
        EmpiresDAL myEmpiresDal = PowerMockito.mock(EmpiresDAL.class);
	    PlayerManager myPlayerManager = new PlayerManager(myEmpiresDal, players);
	    myPlayerManager.loadEPlayer(myPlayer);

	    assertTrue(myPlayer.getUniqueId() == myPlayerManager.loadEPlayer(myPlayer).getUUID());
	}
	
	@Test 
	public void TestPlayerExistsbyUUID() {
        EPlayer myEPlayer = PowerMockito.mock(EPlayer.class);
        Mockito.when(myEPlayer.getUUID()).thenReturn(UUID.randomUUID());
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        
        
        players.put(myEPlayer.getUUID(), myEPlayer);
        EmpiresDAL myEmpiresDal = PowerMockito.mock(EmpiresDAL.class);
	    PlayerManager myPlayerManager = new PlayerManager(myEmpiresDal, players);

	    assertTrue(myPlayerManager.playerExists(myEPlayer.getUUID()));
	    
	    UUID myUUID = UUID.randomUUID();
	    assertFalse(myPlayerManager.playerExists(myUUID));
	}
	
	@Test 
	public void TestAddPlayer() {
        EPlayer myEPlayer = PowerMockito.mock(EPlayer.class);
        Mockito.when(myEPlayer.getUUID()).thenReturn(UUID.randomUUID());
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        
        
        players.put(myEPlayer.getUUID(), myEPlayer);
        EmpiresDAL myEmpiresDal = PowerMockito.mock(EmpiresDAL.class);
	    PlayerManager myPlayerManager = new PlayerManager(myEmpiresDal, players);

	    myPlayerManager.addPlayer(myEPlayer);
	    

	    assertTrue(myPlayerManager.loadEPlayers().size() == 1);
	    assertTrue(myPlayerManager.loadEPlayers().containsKey(myEPlayer.getUUID()));
	}
}


















