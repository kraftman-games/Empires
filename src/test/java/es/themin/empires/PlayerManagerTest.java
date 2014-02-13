package es.themin.empires;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import es.themin.empires.util.CorePlayer;

public class PlayerManagerTest {

	

	@Before
	public void setupEnvironment(){
		
	}
	
	@Test
	public void createNewPlayerManager(){
		
		
		File pfile = PowerMockito.mock(File.class);
	       
        YamlConfiguration playerdata = PowerMockito.mock(YamlConfiguration.class);

        HashMap<UUID, CorePlayer> players = new HashMap<UUID, CorePlayer>();
		
	    PlayerManager myPlayerManager = new PlayerManager(playerdata, pfile, players);
		
		assertTrue(myPlayerManager.getPlayerData() == playerdata);
	}
}
