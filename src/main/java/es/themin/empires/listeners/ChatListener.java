package es.themin.empires.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import es.themin.empires.managers.ManagerBL;
import es.themin.empires.util.EPlayer;

@SuppressWarnings("deprecation")
public class ChatListener implements Listener{
	
	private ManagerBL myApi;
	
	public ChatListener(ManagerBL myAPI) {
		myApi = myAPI;
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		EPlayer myEPlayer = myApi.getEPlayer(event.getPlayer());
		if (myEPlayer.isInEmpire() && myEPlayer.isInEmpireChat()){
			event.setCancelled(true);
			myApi.sendChatToEmpire(myEPlayer, event.getMessage());
		} 
	}
}
