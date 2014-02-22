package es.themin.empires.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;

@SuppressWarnings("deprecation")
public class ChatListener implements Listener{
	
	private ManagerAPI myApi;
	
	public ChatListener(ManagerAPI myAPI) {
		myAPI = myAPI;
		
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		
		EPlayer myEPlayer = myApi.getEPlayer(event.getPlayer());
		
		if (myEPlayer.isInEmpire() && myEPlayer.isInEmpireChat()){
			event.setCancelled(true);
			myApi.sendChatToEmpire(myEPlayer, event.getMessage());
		} else {
			
		}
		
		
	}
}
