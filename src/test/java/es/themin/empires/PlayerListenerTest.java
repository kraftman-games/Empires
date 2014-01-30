package es.themin.empires;

import org.junit.Before;
import org.junit.Test;

import es.themin.empires.Listeners.PlayerListener;

public class PlayerListenerTest {

	PlayerListener myPlayerListener = null;
	empires _empires = new empires();
	
	
	@Before
	public void setupEnvironment(){
		myPlayerListener = new PlayerListener(_empires);
	}
	
	@Test
	public void newPlayerJoin(){
		//do we create an empire
		//do we generate a base core
		
		
		
	
	}
	
	@Test 
	void returningPlayerJoin(){
	}
	
	@Test 
	void playerStartWar(){
	}
	
	@Test 
	void playerDeathDuringWar(){
	}
	
	@Test 
	void playerDeathNotWar(){
	}
	
	
}
