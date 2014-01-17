package es.themin.empires.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import es.themin.empires.empires;
import es.themin.empires.util.EmpirePlayer;
import es.themin.empires.util.SettingsManager;
import es.themin.empires.util.UtilManager;

public class Login_Quit implements Listener{
	
	private empires plugin;
	public Login_Quit(empires registerEvents){
		this.plugin = registerEvents;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		if (!(UtilManager.empireplayers.containsKey(event.getPlayer()))) {
			if (SettingsManager.getInstance().getPlayerData().get(player.getName()) == null) {
				EmpirePlayer ep = new EmpirePlayer(player, null);
				UtilManager.empireplayers.put(player.getName(), ep);
			}else {
				if (SettingsManager.getInstance().getPlayerData().getString(player.getName() + ".empire") == null) {
					EmpirePlayer ep = new EmpirePlayer(player, null);
					UtilManager.empireplayers.put(player.getName(), ep);
				}else {
					EmpirePlayer ep = new EmpirePlayer(player, UtilManager.getEmpireWithName(SettingsManager.getInstance().getPlayerData().getString(player.getName() + ".empire")));
					UtilManager.empireplayers.put(player.getName(), ep);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (UtilManager.empireplayers.containsKey(event.getPlayer()) && UtilManager.empireplayers.get(player).getEmpire() != null) {
			SettingsManager.getInstance().getPlayerData().set(player.getName() + ".empire", UtilManager.empireplayers.get(player).getEmpire().getName());
			SettingsManager.getInstance().savePlayerData();
		}
	}
		

}
