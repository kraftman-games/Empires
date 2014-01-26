package es.themin.empires;
import org.bukkit.entity.Player;
import org.junit.Test;

import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;
import static org.junit.Assert.*;
import org.mockito.Mockito;

public class EmpiresTest {
	
	
	
	@Test
	public void createNewEmpire(){
		
		String empireName = "testEmpire";
		TestPlayer myTestPlayer = new TestPlayer();
		myTestPlayer.setName("kraft");
		
		Empire empire = new Empire(empireName, myTestPlayer);
		assertTrue(empire.getName() == empireName);
		assertTrue(empire.getOwner() == myTestPlayer.getName());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createNewEmpireNoName(){
		
		String empireName = "";
		TestPlayer myTestPlayer = new TestPlayer();
		myTestPlayer.setName("kraft");
		
		Empire empire = new Empire(empireName, myTestPlayer);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createExistingEmpire(){
		
		String empireName = "testEmpire";
		TestPlayer myTestPlayer = new TestPlayer();
		myTestPlayer.setName("kraft");
		
		Empire empire = new Empire(empireName, myTestPlayer);
		assertTrue(empire.getName() == empireName);
		empire = new Empire(empireName, myTestPlayer);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createEmpirePlayerAlreadyInEmpire(){
		
		String empireName = "testEmpire";
		TestPlayer myTestPlayer = new TestPlayer();
		myTestPlayer.setName("kraft");
		
		Empire empire = new Empire(empireName, myTestPlayer);
		assertTrue(empire.getName() == empireName);
		empireName = "testEmpire2";
		empire = new Empire(empireName, myTestPlayer);
	}

	@Test
	public void loadEmpirefromConfig(){
		int empireID = 1;
		String empireName = "testEmpire";
		String ownerName = "kraft";
		
		Empire empire = new Empire(empireID, empireName, ownerName);
		assertTrue(empire.getId() == empireID);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void loadEmpirefromConfigDuplicateID(){
		int empireID = 1;
		String empireName = "testEmpire";
		String ownerName = "kraft";
		
		Empire empire = new Empire(empireID, empireName, ownerName);
		
		empire = new Empire(empireID, empireName, ownerName);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void loadEmpirefromConfigDuplicateName(){
		int empireID = 1;
		String empireName = "testEmpire";
		String ownerName = "kraft";
		
		Empire empire = new Empire(empireID, empireName, ownerName);
		empireID = 2;
		empire = new Empire(empireID, empireName, ownerName);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void loadEmpirefromConfigDuplicateOwner(){
		int empireID = 1;
		String empireName = "testEmpire";
		String ownerName = "kraft";
		
		Empire empire = new Empire(empireID, empireName, ownerName);
		empireID = 2;
		empireName = "testEmpire2";
		empire = new Empire(empireID, empireName, ownerName);
		assertTrue(empire.getId() == empireID);
	}
}






