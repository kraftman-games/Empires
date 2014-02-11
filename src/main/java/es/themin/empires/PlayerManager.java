package es.themin.empires;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.util.CorePlayer;
import es.themin.empires.util.Empire;

public class PlayerManager {

	private HashMap<UUID, CorePlayer> players = new HashMap<UUID, CorePlayer>();
	private empires myPlugin;
	
	public PlayerManager(empires plugin) {
		myPlugin = plugin;
	}

	
	
	public CorePlayer getPlayers(String playerName){
		for (CorePlayer myCorePlayer : this.players.values()){
			if (myCorePlayer.getName().toLowerCase() == playerName.toLowerCase()){
				return myCorePlayer;
			}
		}
		return null;
	}
	
	public CorePlayer getPlayer(UUID myUUID){
		return players.get(myUUID);
	}
	
	public void addPlayer(CorePlayer myCorePlayer){
		players.put(myCorePlayer.getUUID(), myCorePlayer);
	}
	
    public void savePlayers(){
    	for (CorePlayer myPlayer : players.values()) {
    		SettingsManager.getPlayerData().set(myPlayer.getName() + ".empire", myPlayer.getEmpire());
    	}
    	SettingsManager.savePlayerData();
    }
    public void loadPlayers(){
    	for (Player player : Bukkit.getOnlinePlayers()) {
    		String name = player.getName();
    		if (SettingsManager.getPlayerData().get(name + ".empire") != null) {
    			Empire empire = myPlugin.Empires.getEmpireWithID(SettingsManager.getPlayerData().getInt(name + ".empire"));
    			if (empire != null){
    				CorePlayer myCorePlayer = new CorePlayer(player);
        			addPlayer(myCorePlayer);
        			player.sendMessage(myPlugin.plprefix + ChatColor.GREEN + "You were found to be in an empire");
    			}
    		}
    	}
    }
    
    public HashMap<UUID, CorePlayer> getPlayers() {
		return players;
	}


	public boolean playerExists(UUID uniqueId) {
		if (players.get(uniqueId) != null)
			return true;
		else
			return false;
	}


	
}
