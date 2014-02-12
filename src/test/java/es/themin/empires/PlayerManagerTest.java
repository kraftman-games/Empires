package es.themin.empires;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Before;
import org.junit.Test;

import es.themin.empires.util.CorePlayer;

public class PlayerManagerTest {

	

	@Before
	public void setupEnvironment(){
		
	}
	
	@Test
	public void createNewPlayerManager(){
		
		File pfile = SettingsManager.createFile("playerdata.yml");
	       
        YamlConfiguration playerdata = YamlConfiguration.loadConfiguration(pfile);

        HashMap<UUID, CorePlayer> players = new HashMap<UUID, CorePlayer>();
		
	    PlayerManager myPlayerManager = new PlayerManager(playerdata, pfile, players);
		
		assertTrue(myPlayerManager.getPlayerData() == playerdata);
	}
}
