package es.themin.empires.cores;

import org.bukkit.entity.Player;

import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpireState;
import es.themin.empires.util.Empire;
import es.themin.empires.util.EmpireUtils;
import es.themin.empires.util.UtilManager;

public class CoreUtils {

	
	public static Core createCore(Player myPlayer, CoreType myCoreType){
		Core myCore = null;
		
		//check player is in an empire
		//check the empire is not at war or rebuilding (for certain types??)
		//check that the player has permissions to place cores of this type
		//check that the empire can afford the core
		
		//check that the core can be placed here
		
		Empire myEmpire = UtilManager.getEmpireWithPlayer(myPlayer);
		
		if (myEmpire == null){
			myPlayer.sendMessage("You cannot create a core unless you are in an empire");
		}
		
		if (myEmpire.getEmpireState() != EmpireState.BATTLEREADY){
			if (myEmpire.getEmpireState() == EmpireState.ATWAR){
				myPlayer.sendMessage("You cannot expand your empire while you are at war!");
			} else if (myEmpire.getEmpireState() == EmpireState.REBUILDING){
				myPlayer.sendMessage("You cannot expand your empire until it has rebuilt!");
			} 
		}
		
		
		
		if (!EmpireUtils.empireCanAfford(myCoreType)){
			myPlayer.sendMessage("Your empire has no cores of that type to place");	
			return null;
		}
		
		
		
		return myCore;
	}
}
