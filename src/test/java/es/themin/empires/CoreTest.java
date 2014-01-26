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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;

import es.themin.empires.cores.Core;
import es.themin.empires.cores.CoreUtils;
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

//		Mockito.when(myPlayer.sendMessage(Mockito.anyString())).tehnAnswer(new Answer<void>() {
//		      @Override
//		      public void answer(InvocationOnMock invocation) throws Throwable {
//		    	  System.out.println((String) invocation.getArguments()[0]);
//		      }
//		    });
		UtilManager.addWorld(myWorld);
		
		
		myEmpire = new Empire("testEmp", myPlayer);
	}
	
	@Test
	public void createCore(){
		
		CoreType myCoreType = CoreType.BASE;
		
		Core myCore = CoreUtils.placeCore(myPlayer, myCoreType);
		
		assertTrue(myCore != null);
		assertTrue(myCore.getEmpire() == myEmpire);
		
	}
	
	@Test
	public void createGriefCoreOutsideEmpire(){
		
		CoreType myCoreType = CoreType.GRIEF;
		
		Core myCore = CoreUtils.placeCore(myPlayer, myCoreType);
		
		assertTrue(myCore == null);
		
		
	}
	
	
}
