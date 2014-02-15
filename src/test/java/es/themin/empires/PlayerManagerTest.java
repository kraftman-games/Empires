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

import es.themin.empires.managers.PlayerManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class PlayerManagerTest {


	@Before
	public void setupEnvironment(){
		
	}
	
	private Player getFakePlayer(){
		Player myPlayer = PowerMockito.mock(Player.class);
		PowerMockito.when(myPlayer.getName()).thenReturn("kraft");
		PowerMockito.when(myPlayer.getUniqueId()).thenReturn(UUID.randomUUID());
		
		
		return myPlayer;
	}
	
	private EPlayer GetFakeCorePlayer(){
		
		
		EPlayer myCorePlayer = new EPlayer(getFakePlayer());
		
		return myCorePlayer;
	}
	
	private Empire GetFakeEmpire(){
		Empire myEmpire = PowerMockito.mock(Empire.class);
		PowerMockito.when(myEmpire.getID()).thenReturn(1);
		return myEmpire;
	}
	
	private EPlayer GetFakeCorePlayerInEmpire(){
		
		EPlayer myCorePlayer = GetFakeCorePlayer();
		
		Empire myEmpire = GetFakeEmpire();
		myCorePlayer.setEmpire(myEmpire);
		
		return myCorePlayer;
	}
	
	
	
	
	@Test
	public void PlayerManagerSave(){
        EPlayer myCorePlayer = GetFakeCorePlayerInEmpire();
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        
        EmpiresDAL myEmpiresDal = PowerMockito.mock(EmpiresDAL.class);
	    PlayerManager myPlayerManager = new PlayerManager(myEmpiresDal, players);
	    myPlayerManager.save();
	    Mockito.verify(myEmpiresDal).savePlayers(players);
	}
	
	
	@Test 
	public void PlayerManagerSaveToOtherFile() throws IOException {
		File tfile = PowerMockito.mock(File.class);
        EPlayer myCorePlayer = GetFakeCorePlayerInEmpire();
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        EmpiresDAL myEmpiresDal = PowerMockito.mock(EmpiresDAL.class);
	    PlayerManager myPlayerManager = new PlayerManager(myEmpiresDal, players);

	    myPlayerManager.save(tfile);
	    
	    Mockito.verify(myEmpiresDal).savePlayers(players, tfile);
	}
	
	@Test 
	public void GetPlayerByName() {
        EPlayer myCorePlayer = GetFakeCorePlayerInEmpire();
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        
        myCorePlayer.setName("kraftman");
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        EmpiresDAL myEmpiresDal = PowerMockito.mock(EmpiresDAL.class);
	    PlayerManager myPlayerManager = new PlayerManager(myEmpiresDal, players);

	    assertTrue(myCorePlayer == myPlayerManager.getPlayer("kraftman"));
	}
	
	@Test 
	public void GetPlayerByUUID() {
        EPlayer myCorePlayer = GetFakeCorePlayerInEmpire();
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        EmpiresDAL myEmpiresDal = PowerMockito.mock(EmpiresDAL.class);
	    PlayerManager myPlayerManager = new PlayerManager(myEmpiresDal, players);

	    assertTrue(myCorePlayer == myPlayerManager.getPlayer(myCorePlayer.getUUID()));
	}
	
	@Test 
	public void TestPlayerExistsbyUUID() {
        EPlayer myCorePlayer = GetFakeCorePlayerInEmpire();
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
        EPlayer myCorePlayer = GetFakeCorePlayerInEmpire();
        HashMap<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();
        
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        EmpiresDAL myEmpiresDal = PowerMockito.mock(EmpiresDAL.class);
	    PlayerManager myPlayerManager = new PlayerManager(myEmpiresDal, players);

	    myPlayerManager.addPlayer(myCorePlayer);
	    

	    assertTrue(myPlayerManager.getPlayers().size() == 1);
	    assertTrue(myPlayerManager.getPlayers().containsKey(myCorePlayer.getUUID()));
	}
}

















