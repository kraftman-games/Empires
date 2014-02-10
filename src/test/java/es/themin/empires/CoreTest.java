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
import es.themin.empires.enums.PlaceType;
import es.themin.empires.util.CoreWorld;
import es.themin.empires.util.Empire;
import es.themin.empires.util.PlayerUtils;
import es.themin.empires.util.UtilManager;

public class CoreTest {

	private Empire myEmpire;
	private Empire myEnemyEmpire;
	private Player myPlayer;
	private Player myEnemyPlayer;
	private World myWorld;
	private Location  myLocation;
	private Location myEnemyLocation;
	
	
//	@Before
//	public void setupEnvironment(){
//		UtilManager.empires = new ArrayList<Empire>();
//		UtilManager.empireplayers = new HashMap<String, Empire>();
//		UtilManager.cores = new ArrayList<Core>();
//		UtilManager.worlds = new HashMap<UUID,CoreWorld>();
//		
//		myWorld = PowerMockito.mock(World.class);
//		UtilManager.addWorld(myWorld);
//		
//		myLocation = new Location(myWorld, 0, 0, 0);
//		myEnemyLocation = new Location(myWorld, 0, 0, 60);
//		
//		myPlayer= PowerMockito.mock(Player.class);
//		Mockito.when(myPlayer.getName()).thenReturn("kraft");
//		Mockito.when(myPlayer.getWorld()).thenReturn(myWorld);
//		Mockito.when(myPlayer.getLocation()).thenReturn(myLocation);	
//		myEmpire = new Empire("testEmp", myPlayer);
//		
//		myEnemyPlayer= PowerMockito.mock(Player.class);
//		Mockito.when(myEnemyPlayer.getName()).thenReturn("kraftEnemy");
//		Mockito.when(myEnemyPlayer.getWorld()).thenReturn(myWorld);
//		Mockito.when(myEnemyPlayer.getLocation()).thenReturn(myEnemyLocation);	
//		myEnemyEmpire = new Empire("testEnemyEmp", myEnemyPlayer);
//
////		Mockito.when(myPlayer.sendMessage(Mockito.anyString())).tehnAnswer(new Answer<void>() {
////		      @Override
////		      public void answer(InvocationOnMock invocation) throws Throwable {
////		    	  System.out.println((String) invocation.getArguments()[0]);
////		      }
////		    });
//		UtilManager.addWorld(myWorld);
		
		
//		
//	}
//	
//	@Test
//	public void createCore(){
//		
//		CoreType myCoreType = CoreType.BASE;
//		
//		Core myCore = CoreUtils.placeCore(myPlayer, myCoreType);
//		
//		assertTrue(myCore != null);
//		assertTrue(myCore.getEmpire() == myEmpire);
//		
//	}
//	
//	@Test
//	public void createGriefCoreOutsideEmpire(){
//		CoreType myCoreType = CoreType.GRIEF;
//		Core myCore = CoreUtils.placeCore(myPlayer, myCoreType);
//		assertTrue(myCore == null);
//	}
//	
//	@Test
//	public void createGriefCoreEdgeofEmpire(){
//		CoreType myCoreType = CoreType.BASE;
//		
//		Core myCore = CoreUtils.placeCore(myPlayer, myCoreType);
//		myCore.setCoreSize(32);
//		
//		
//		myLocation = new Location(myWorld, 29, 0, 29);
//		Core myGriefCore = CoreUtils.placeCore(myPlayer, CoreType.GRIEF);
//		
//		assertTrue(myGriefCore.getEmpire() == myEmpire);
//	
//	}
//	
//	@Test
//	public void createGriefCoreOutsideofEmpire(){
//		//base core to act as empire
//		CoreType myCoreType = CoreType.BASE;
//		Core myCore = CoreUtils.placeCore(myPlayer, myCoreType);
//		myLocation = new Location(myWorld, 600, 600, 600);
//		Mockito.when(myPlayer.getLocation()).thenReturn(myLocation);
//		//grief core with new location far away from grief core
//		Core myGriefCore = CoreUtils.placeCore(myPlayer, CoreType.GRIEF);
//		
//		assertTrue(myGriefCore == null);
//	
//	}
//	
//	@Test
//	public void createBaseCoreOutsideofEmpire(){
//		CoreType myCoreType = CoreType.BASE;
//		Core myCore = CoreUtils.placeCore(myPlayer, myCoreType);
//		myLocation = new Location(myWorld, 600, 600, 600);
//		Mockito.when(myPlayer.getLocation()).thenReturn(myLocation);	
//		Core myGriefCore = CoreUtils.placeCore(myPlayer, CoreType.BASE);
//		
//		assertTrue(myGriefCore != null);
//		assertTrue(myGriefCore.getEmpire() == myEmpire);
//	
//	}
//	
//	@Test
//	public void createNonGriefCoreOutsideofEmpire(){
//		CoreType myCoreType = CoreType.BASE;
//		Core myCore = CoreUtils.placeCore(myPlayer, myCoreType);
//		myLocation = new Location(myWorld, 600, 600, 600);
//		Mockito.when(myPlayer.getLocation()).thenReturn(myLocation);	
//		Core myGriefCore = CoreUtils.placeCore(myPlayer, CoreType.FARM);
//		
//		assertTrue(myGriefCore == null);
//	}
//	
//	@Test
//	public void createCoreNearEnemyEmpire(){
////		CoreType myCoreType = CoreType.BASE;
////		Core myCore = CoreUtils.placeCore(myPlayer, myCoreType);
////		
////		
////		Core myEnemyCore = CoreUtils.placeCore(myEnemyPlayer, myCoreType);
////		
////		UUID myUUID = myCore.getLocation().getWorld().getUID();
////		CoreWorld myCoreWorld = myPlugin.getWorlds().get(myUUID);
////		
////		assertTrue(myEnemyCore == null);
//	}
//	
//	
}












