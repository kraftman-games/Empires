package es.themin.empires.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import es.themin.empires.cmds.empire.ChatCommand;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;

@SuppressWarnings("deprecation")
public class ChatListener implements Listener{
	
	private ManagerAPI myApi;
	
	public ChatListener(ManagerAPI myAPI) {
		myAPI = myAPI;
		
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		
		EPlayer myEPlayer = myApi.getEPlayer(event.getPlayer());
		if (ChatCommand.empirechatplayers.contains(myEPlayer)) {
			event.setCancelled(true);
			if (myEPlayer != null) {
				
				Empire empire = myApi.getEmpire(myEPlayer.getEmpireUUID());
				String rank;
				if (!(empire.playerHasARank(myEPlayer.getName()))) {
					if (empire.getOwnerUUID() == myEPlayer.getUUID()) {
						if (empire.getOwnerPrefix() == null) rank = "king";
						else rank = empire.getOwnerPrefix();
					}else {
						if (empire.getDefaultPrefix() == null) rank = "default";
						else rank = empire.getDefaultPrefix();
					}
				}else rank = empire.getRankOfPlayer(myEPlayer.getName()).getPreifx();
				String rankc = MsgManager.colourUp(rank);
				String format = ChatColor.WHITE + "[" + rankc + ChatColor.WHITE + "] [" + myEPlayer.getName() + ChatColor.WHITE + "] ";
				empire.broadcastMessage(format + ChatColor.YELLOW + event.getMessage());
			}else {
				myEPlayer.sendMessage(ChatColor.RED + "You are not in an empire anymore so cannot talk in this channel do '/g'");
			}
		}
	}
}
