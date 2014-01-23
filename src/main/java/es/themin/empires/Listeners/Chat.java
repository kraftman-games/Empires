package es.themin.empires.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import es.themin.empires.empires;
import es.themin.empires.cmds.empire.ChatCommand;
import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;

@SuppressWarnings("deprecation")
public class Chat implements Listener{
	
	private empires plugin;
	public Chat(empires plugin) {
		this.plugin = plugin;
	}
	public String plprefix = plugin.plprefix;
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		
		Player player = event.getPlayer();
		if (ChatCommand.empirechatplayers.contains(player)) {
			event.setCancelled(true);
			if (UtilManager.empireplayers.containsKey(player.getName())) {
				
				Empire empire = UtilManager.empireplayers.get(player.getName());
				String rank;
				if (!(empire.playerHasARank(player.getName()))) {
					if (empire.getOwner().equalsIgnoreCase(player.getName())) {
						if (empire.getOwnerPrefix() == null) rank = "king";
						else rank = empire.getOwnerPrefix();
					}else {
						if (empire.getDefaultPrefix() == null) rank = "default";
						else rank = empire.getDefaultPrefix();
					}
				}else rank = empire.getRankOfPlayer(player.getName()).getPreifx();
				String rankc = UtilManager.colourUp(rank);
				String format = ChatColor.WHITE + "[" + rankc + ChatColor.WHITE + "] [" + player.getDisplayName() + ChatColor.WHITE + "] ";
				empire.broadcastMessage(format + ChatColor.YELLOW + event.getMessage());
			}else {
				player.sendMessage(plprefix + ChatColor.RED + "You are not in an empire anymore so cannot talk in this channel do '/g'");
			}
		}
	}
}
