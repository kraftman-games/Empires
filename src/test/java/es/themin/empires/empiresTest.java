package es.themin.empires;
import org.junit.Test;
import static org.junit.Assert.*;

import es.themin.empires.Utilities;

public class empiresTest {
	
	@Test
	public void myFirstUnitTest(){
		
		Utilities newEmpires = new Utilities();
		assertTrue(newEmpires.myTestBool());
	}
}
