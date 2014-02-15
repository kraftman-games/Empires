package es.themin.empires;
import org.bukkit.entity.Player;

public class EmpiresTest {
	
	private Player myPlayer;
	
//	@Before
//	public void setupEnvironment(){
//		UtilManager.empires = new ArrayList<Empire>();
//		UtilManager.empireplayers = new HashMap<String, Empire>();
//		UtilManager.cores = new ArrayList<Core>();
//		UtilManager.worlds = new HashMap<UUID,CoreWorld>();
//		myPlayer= PowerMockito.mock(Player.class);
//		Mockito.when(myPlayer.getName()).thenReturn("kraft");
//	}
//	
//	@Test
//	public void createNewEmpire(){
//		
//		String empireName = "testEmpire1";
//		
//		Empire empire = new Empire(empireName, myPlayer);
//		assertTrue(empire.getName() == empireName);
//		assertTrue(empire.getOwner() == myPlayer.getName());
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void createNewEmpireNoName(){
//		
//		String empireName = "";
//		
//		Empire empire = new Empire(empireName, myPlayer);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void createExistingEmpire(){
//		
//		String empireName = "testEmpire2";
//		Empire empire = new Empire(empireName, myPlayer);
//		assertTrue(empire.getName() == empireName);
//		empire = new Empire(empireName, myPlayer);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void createEmpirePlayerAlreadyInEmpire(){
//		
//		String empireName = "testEmpire3";
//		
//		Empire empire = new Empire(empireName, myPlayer);
//		assertTrue(empire.getName() == empireName);
//		empireName = "testEmpire4";
//		empire = new Empire(empireName, myPlayer);
//	}
//
//	@Test
//	public void loadEmpirefromConfig(){
//		int empireID = 1;
//		String empireName = "testEmpire5";
//		
//		Empire empire = new Empire(empireID, empireName, myPlayer.getName());
//		assertTrue(empire.getId() == empireID);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void loadEmpirefromConfigDuplicateID(){
//		int empireID = 1;
//		String empireName = "testEmpire6";
//		
//		Empire empire = new Empire(empireID, empireName, myPlayer.getName());
//		
//		empire = new Empire(empireID, empireName, myPlayer.getName());
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void loadEmpirefromConfigDuplicateName(){
//		int empireID = 1;
//		String empireName = "testEmpire7";
//		
//		Empire empire = new Empire(empireID, empireName, myPlayer.getName());
//		empireID = 2;
//		empire = new Empire(empireID, empireName, myPlayer.getName());
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void loadEmpirefromConfigDuplicateOwner(){
//		int empireID = 1;
//		String empireName = "testEmpire8";
//		
//		Empire empire = new Empire(empireID, empireName, myPlayer.getName());
//		empireID = 2;
//		empireName = "testEmpire9";
//		empire = new Empire(empireID, empireName, myPlayer.getName());
//		assertTrue(empire.getId() == empireID);
//	}
}






