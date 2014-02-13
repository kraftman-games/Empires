package es.themin.empires;

import static org.junit.Assert.*;

import java.io.File;
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
        
		//PowerMockito.when(playerdata.set(myCorePlayer.getUUID().toString(), myCorePlayer.getEmpire())).re
        
        HashMap<UUID, CorePlayer> players = new HashMap<UUID, CorePlayer>();
        
        
        players.put(myCorePlayer.getUUID(), myCorePlayer);
        
		
	    PlayerManager myPlayerManager = new PlayerManager(playerdata, pfile, players);

	    myPlayerManager.save();
	    Mockito.verify(playerdata).set(myCorePlayer.getUUID().toString()+".empire", myCorePlayer.getEmpire().getID());
	}
}

















