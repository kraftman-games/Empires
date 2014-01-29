package es.themin.empires.util.testing;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import es.themin.empires.cmds.SubCommand;
import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.UtilManager;

public class ehome extends SubCommand{
	
	public boolean onCommand(Player player, String[] args) {
		ArrayList<Core> myCores = UtilManager.empireplayers.get(player).getCores();
		
		Core BaseCore = null;
		
		if (myCores == null){
			player.sendMessage("You have no cores, oh dear");
		}
		
		for (Core myCore : myCores){
			if (myCore.getType() == CoreType.BASE){
				BaseCore = myCore;
				break;
			}			
		}
		
		if (BaseCore == null){
			player.sendMessage("you have no base core! oh dear.");
			return true;
		}
		
		player.teleport(BaseCore.getLocation());
		
		
		return true;
	}
	
	public String name() {
		return "ehome";
	}

	public String info() {
		return "Go Home";
	}
	
	public String[] aliases() {
		return new String[] { "empirehome" };
	}

}
