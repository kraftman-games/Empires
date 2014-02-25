package es.themin.empires.util.testing;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import es.themin.empires.Debug;
import es.themin.empires.cmds.SubCommand;
import es.themin.empires.cores.Core;
import es.themin.empires.cores.CoreFactory;
import es.themin.empires.enums.CoreType;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;

public class GenerateCore extends SubCommand{

	private ManagerAPI myApi = null;
	
	public GenerateCore(ManagerAPI api) {
		myApi = api;
	}

	public boolean onCommand(Player player, String[] args) {
		EPlayer myEPlayer = myApi.getEPlayer(player);
		
		Core myCore = null;
		Debug.Console(args[0]);
		CoreType myType = CoreType.valueOf(args[0].toUpperCase());
		
		if (myType != null){
		
		 myCore = CoreFactory.CreateCore(myEPlayer.getEmpireUUID(), myEPlayer.getLocation(), myType);
		}
		
		Bukkit.getServer().getLogger().info(myEPlayer.getEmpireUUID().toString());
		myApi.generateCore(myEPlayer, myCore);
		
		return false;
		
	}

	public String name() {
		return "generatecore";
	}

	public String info() {
		return "generates any core";
	}

	public String[] aliases() {
		return new String[] {"gencore","gc"};
	}

}
