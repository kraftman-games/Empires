package es.themin.empires.util.testing;

import org.bukkit.entity.Player;

import es.themin.empires.cmds.SubCommand;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;

public class showedges  extends SubCommand {

	
private ManagerAPI myApi = null;
	
	public showedges(ManagerAPI api) {
		myApi = api;
	}

	public boolean onCommand(Player player, String[] args) {
		EPlayer myEPlayer = myApi.getEPlayer(player);
		
		if (myEPlayer.getEmpireUUID() != null){
			myApi.showEdges(myEPlayer.getEmpireUUID());
		}
		
		return false;
		
	}

	public String name() {
		return "showedges";
	}

	public String info() {
		return "shows core edges";
	}

	public String[] aliases() {
		return new String[] {"se"};
	}

	
}
