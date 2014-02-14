package es.themin.empires;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import es.themin.empires.util.CorePlayer;
import es.themin.empires.util.Empire;

public class PlayerManagerTest {

//	Mockito.when(myPlayer.getName()).thenReturn("kraft");
//	Mockito.when(myPlayer.getWorld()).thenReturn(myWorld);
//	Mockito.when(myPlayer.getLocation()).thenReturn(myLocation);	

	@Before
	public void setupEnvironment(){
		
	}
	
	private Player getFakePlayer(){
		Player myPlayer = PowerMockito.mock(Player.class);
		PowerMockito.when(myPlayer.getName()).thenReturn("kraft");
		PowerMockito.when(myPlayer.getUniqueId()).thenReturn(UUID.randomUUID());
		
		
		return myPlayer;
	}
	
	private CorePlayer GetFakeCorePlayer(){
		
		
		CorePlayer myCorePlayer = new CorePlayer(getFakePlayer());
		
		return myCorePlayer;
	}
	
	private Empire GetFakeEmpire(){
		Empire myEmpire = PowerMockito.mock(Empire.class);
		PowerMockito.when(myEmpire.getID()).thenReturn(1);
		return myEmpire;
	}
	
	private CorePlayer GetFakeCorePlayerInEmpire(){
		
		CorePlayer myCorePlayer = GetFakeCorePlayer();
		
		Empire myEmpire = GetFakeEmpire();
		myCorePlayer.setEmpire(myEmpire);
		
		return myCorePlayer;
	}
	
	
	@Test
	public void createNewPlayerManager(){
		
		
		File pfile = PowerMockito.mock(File.class);
        YamlConfiguration playerdata = PowerMockito.mock(YamlConfiguration.class);
        HashMap<UUID, CorePlayer> players = new HashMap<UUID, CorePlayer>();
		
	    PlayerManager myPlayerManager = new PlayerManager(playerdata, pfile, players);
		
		assertTrue(myPlayerManager.getPlayerData() == playerdata);
	}
	
	@Test
	public void PlayerManagerSave(){
		File pfile = PowerMockito.mock(File.class);
        YamlConfiguration playerdata = PowerMockito.mock(YamlConfiguration.class);
        CorePlayer myCorePlayer = GetFakeCorePlayerInEmpire();
        HashMap<UUID, CorePlayer> players = new HashMap<UUID, CorePlayer>();
        
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        
		
	    PlayerManager myPlayerManager = new PlayerManager(playerdata, pfile, players);

	    myPlayerManager.save();
	    Mockito.verify(playerdata).set(myCorePlayer.getUUID().toString()+".empire", myCorePlayer.getEmpire().getID());
	}
	
	
	@Test 
	public void PlayerManagerSaveToOtherFile() throws IOException {
		File pfile = PowerMockito.mock(File.class);
		File tfile = PowerMockito.mock(File.class);
        YamlConfiguration playerdata = PowerMockito.mock(YamlConfiguration.class);
        CorePlayer myCorePlayer = GetFakeCorePlayerInEmpire();
        HashMap<UUID, CorePlayer> players = new HashMap<UUID, CorePlayer>();
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        
	    PlayerManager myPlayerManager = new PlayerManager(playerdata, pfile, players);

	    myPlayerManager.save(tfile);
	    
	    Mockito.verify(playerdata).save(tfile);
	}
	
	@Test 
	public void GetPlayerByName() {
		File pfile = PowerMockito.mock(File.class);
        YamlConfiguration playerdata = PowerMockito.mock(YamlConfiguration.class);
        CorePlayer myCorePlayer = GetFakeCorePlayerInEmpire();
        HashMap<UUID, CorePlayer> players = new HashMap<UUID, CorePlayer>();
        
        myCorePlayer.setName("kraftman");
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        
	    PlayerManager myPlayerManager = new PlayerManager(playerdata, pfile, players);

	    assertTrue(myCorePlayer == myPlayerManager.getPlayer("kraftman"));
	}
	
	@Test 
	public void GetPlayerByUUID() {
		File pfile = PowerMockito.mock(File.class);;
        YamlConfiguration playerdata = PowerMockito.mock(YamlConfiguration.class);
        CorePlayer myCorePlayer = GetFakeCorePlayerInEmpire();
        HashMap<UUID, CorePlayer> players = new HashMap<UUID, CorePlayer>();
        
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        
	    PlayerManager myPlayerManager = new PlayerManager(playerdata, pfile, players);

	    assertTrue(myCorePlayer == myPlayerManager.getPlayer(myCorePlayer.getUUID()));
	}
	
	@Test 
	public void TestPlayerExistsbyUUID() {
		File pfile = PowerMockito.mock(File.class);;
        YamlConfiguration playerdata = PowerMockito.mock(YamlConfiguration.class);
        CorePlayer myCorePlayer = GetFakeCorePlayerInEmpire();
        HashMap<UUID, CorePlayer> players = new HashMap<UUID, CorePlayer>();
        
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        
	    PlayerManager myPlayerManager = new PlayerManager(playerdata, pfile, players);

	    assertTrue(myPlayerManager.playerExists(myCorePlayer.getUUID()));
	    
	    UUID myUUID = UUID.randomUUID();
	    assertFalse(myPlayerManager.playerExists(myUUID));
	}
	
	@Test 
	public void TestAddPlayer() {
		File pfile = PowerMockito.mock(File.class);;
        YamlConfiguration playerdata = PowerMockito.mock(YamlConfiguration.class);
        CorePlayer myCorePlayer = GetFakeCorePlayerInEmpire();
        HashMap<UUID, CorePlayer> players = new HashMap<UUID, CorePlayer>();
        
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        
	    PlayerManager myPlayerManager = new PlayerManager(playerdata, pfile, players);

	    myPlayerManager.addPlayer(myCorePlayer);
	    

	    assertTrue(myPlayerManager.getPlayers().size() == 1);
	    assertTrue(myPlayerManager.getPlayers().containsKey(myCorePlayer.getUUID()));
	}
}

















