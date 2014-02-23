package es.themin.empires.util.testing;

import org.bukkit.entity.Player;

import es.themin.empires.cmds.SubCommand;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;

public class newemp extends SubCommand{
	private ManagerAPI myApi = null;
	
	public newemp(ManagerAPI api) {
		myApi = api;
	}

	public boolean onCommand(Player player, String[] args) {
		
		if (args.length > 1 && args[1] != null){
			EPlayer myEplayer = myApi.getEPlayer(player);
			myApi.createEmpire(args[1], myEplayer);
		} else {
			player.sendMessage("you must provide an empire name");
			return false;
		}
					
		return false;
	}

	public String name() {
		return "newemp";
	}
	public String info() {
		return "creates an empire";
	}
	public String[] aliases() {
		return new String[] { "ne" };
	}
	
}
