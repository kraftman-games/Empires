package es.themin.empires;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.UUID;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import es.themin.empires.managers.EmpireManager;
import es.themin.empires.util.Empire;

public class EmpireManagerTest {

	
	
//	@Before
//	public void setupEnvironment(){
//		
//	}
//	
//	@Test
//	public void createNewEmpire(){
//		
//		empires myPlugin = new empires();
//		
//		
//	}
	
	@Test
	public void GetEmpiresTest(){
		
        EmpiresDAL myempiresDAL = PowerMockito.mock(EmpiresDAL.class);
        HashMap<UUID,Empire> empires = new HashMap<UUID,Empire>();
        
        EmpireManager MyEmpireManager = new EmpireManager(myempiresDAL, empires);
        
		assertTrue(MyEmpireManager.getEmpires() == empires);
		
	}
	
	@Test
	public void LoadEmpiresTest(){
		
        EmpiresDAL myempiresDAL = PowerMockito.mock(EmpiresDAL.class);
        HashMap<UUID,Empire> empires = new HashMap<UUID,Empire>();
        
        EmpireManager MyEmpireManager = new EmpireManager(myempiresDAL, empires);
        MyEmpireManager.load();
		Mockito.verify(myempiresDAL).loadEmpires();
		
	}
	
	@Test
	public void SaveEmpiresTest(){
		
        EmpiresDAL myempiresDAL = PowerMockito.mock(EmpiresDAL.class);
        HashMap<UUID,Empire> empires = new HashMap<UUID,Empire>();
        
        EmpireManager MyEmpireManager = new EmpireManager(myempiresDAL, empires);
        MyEmpireManager.save();
		Mockito.verify(myempiresDAL).createOrUpdateEmpires(empires);
		
	}
	
	
	
	
	@Test
	public void AddEmpireTest(){
		
        EmpiresDAL myempiresDAL = PowerMockito.mock(EmpiresDAL.class);
        
        @SuppressWarnings( "unchecked" )
        HashMap<UUID,Empire> empires = PowerMockito.mock(HashMap.class);
        
        EmpireManager MyEmpireManager = new EmpireManager(myempiresDAL, empires);
        
        Empire myEmpire = Mockito.mock(Empire.class);
        
        MyEmpireManager.addEmpire(myEmpire);
        
        Mockito.verify(empires).put(myEmpire.getUUID(),myEmpire);
		
	}
	
	
	@Test
	public void RemoveEmpireTest(){
		
        EmpiresDAL myempiresDAL = PowerMockito.mock(EmpiresDAL.class);
        @SuppressWarnings( "unchecked" )
        HashMap<UUID,Empire> empires = PowerMockito.mock(HashMap.class);
        
        EmpireManager MyEmpireManager = new EmpireManager(myempiresDAL, empires);
        
        Empire myEmpire = Mockito.mock(Empire.class);
        
        MyEmpireManager.addEmpire(myEmpire);
        
        MyEmpireManager.removeEmpire(myEmpire);
        
        Mockito.verify(empires).remove(myEmpire.getUUID());
        Mockito.verify(myempiresDAL).removeEmpire(myEmpire);
        
	}
	
	@Test
	public void GetEmpireByNameTest(){
		
        EmpiresDAL myempiresDAL = PowerMockito.mock(EmpiresDAL.class);
        HashMap<UUID,Empire> empires = new HashMap<UUID,Empire>();
        
        EmpireManager MyEmpireManager = new EmpireManager(myempiresDAL, empires);
        
        Empire myEmpire = Mockito.mock(Empire.class);
        String EmpireName = "KraftPire";
        
        Mockito.when(myEmpire.getName()).thenReturn(EmpireName);
        
        MyEmpireManager.addEmpire(myEmpire);
        
        assertTrue(MyEmpireManager.getEmpireWithName(EmpireName) == myEmpire);
	}
	
//	@Test
//	public void GetEmpireByIDTest(){
//		
//        EmpiresDAL myempiresDAL = PowerMockito.mock(EmpiresDAL.class);
//        ArrayList<Empire> empires = new ArrayList<Empire>();
//        
//        EmpireManager MyEmpireManager = new EmpireManager(myempiresDAL, empires);
//        
//        Empire myEmpire = Mockito.mock(Empire.class);
//        
//        MyEmpireManager.addEmpire(myEmpire);
//        
//        assertTrue(MyEmpireManager.getEmpireWithID(0) == myEmpire);
//	}
	

	
	
	
}




















