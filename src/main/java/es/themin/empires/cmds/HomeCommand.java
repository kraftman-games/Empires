package es.themin.empires.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.themin.empires.managers.ManagerBL;
import es.themin.empires.util.EPlayer;

public class HomeCommand implements CommandExecutor{
	
	
	private ManagerBL myApi = null;
	
	public HomeCommand(ManagerBL myAPI) {
		myApi = myAPI;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		EPlayer myEPlayer = myApi.getEPlayer(player);
		
		
		//i think we need to store a home location per player or empire rather than using the base core
		
//		ArrayList<Core> myCores = Cores.getEmpireCores(myEPlayer.getEmpire());
//		
//		Core BaseCore = null;
//		
//		if (myCores == null){
//			player.sendMessage("You have no cores, oh dear");
//		}
//		
//		for (Core myCore : myCores){
//			if (myCore.getType() == CoreType.BASE){
//				BaseCore = myCore;
//				break;
//			}			
//		}
//		
//		if (BaseCore == null){
//			player.sendMessage("you have no base core! oh dear.");
//			return true;
//		}
//		
//		player.teleport(BaseCore.getLocation());
		
		
		return true;
	}
	

}
