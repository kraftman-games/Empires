package es.themin.empires;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.CoreWorld;
import es.themin.empires.util.Empire;
import es.themin.empires.util.PlayerUtils;
import es.themin.empires.util.UtilManager;

public class CoreTest {

	private Empire myEmpire;
	private Player myPlayer;
	private World myWorld;
	private Location  myLocation;
	
	
	@Before
	public void setupEnvironment(){
		UtilManager.empires = new ArrayList<Empire>();
		UtilManager.empireplayers = new HashMap<String, Empire>();
		UtilManager.cores = new ArrayList<Core>();
		UtilManager.worlds = new HashMap<UUID,CoreWorld>();
		myPlayer= PowerMockito.mock(Player.class);
		myWorld = PowerMockito.mock(World.class);
		
		myLocation = new Location(myWorld, 0, 0, 0);
		
		Mockito.when(myPlayer.getName()).thenReturn("kraft");
		Mockito.when(myPlayer.getWorld()).thenReturn(myWorld);
		Mockito.when(myPlayer.getLocation()).thenReturn(myLocation);
		
		
		myEmpire = new Empire("testEmp", myPlayer);
	}
	
	@Test
	public void createCore(){
		
		CoreType myCoreType = CoreType.BASE;
		
		//Core myCore = PlayerUtils.createCore(myPlayer, myCoreType);
		
		
		
	}
	
	@Test
	public void tryCorePlace(){
		
	}
}
