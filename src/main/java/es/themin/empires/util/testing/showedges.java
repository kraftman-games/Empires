package es.themin.empires.util.testing;

import org.bukkit.entity.Player;

import es.themin.empires.cmds.SubCommand;
import es.themin.empires.managers.ManagerBL;
import es.themin.empires.util.EPlayer;

public class showedges  extends SubCommand {

	
private ManagerBL myApi = null;
	
	public showedges(ManagerBL api) {
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
