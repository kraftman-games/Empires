package es.themin.empires;

import static org.junit.Assert.assertTrue;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import es.themin.empires.enums.CoreType;
import es.themin.empires.util.Empire;

public class CoreTest {

	Empire testEmpire;
	
	@Before
	public void createEmpire(){
		int empireID = 1;
		String empireName = "testEmpire";
		String ownerName = "kraft";
		
		testEmpire = new Empire(empireID, empireName, ownerName);
	}
	
	@Test
	public void myFirstUnitTest(){
		
		Utilities newEmpires = new Utilities();
		assertTrue(newEmpires.myTestBool());
	}
	
	@Test
	public void createCore(){
		TestPlayer myPlayer = new TestPlayer();
		//Location myLocation = new Location(1,2,3);
		//myPlayer.setLocation(myLocation);
		
		//Core(int Id, CoreType type, Location location, int level, Empire empire) {
		
	}
	
	@Test
	public void tryCorePlace(){
		
	}
}
