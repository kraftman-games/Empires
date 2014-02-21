package es.themin.empires.managers;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.util.EPlayer;

public class PlayerManager implements IManager {

	private HashMap<UUID, EPlayer> players;
	
    private EmpiresDAL EmpiresDAL;
    //private Database sql = null;
	
	public PlayerManager( EmpiresDAL myEmpiresDAL, HashMap<UUID, EPlayer> players) {
	    this.players = players;
	    this.EmpiresDAL = myEmpiresDAL;
	}
	
    public void save(){
    	EmpiresDAL.createOrUpdatePlayers(players);
    }

    
    public void load(){
    	for (Player myPlayer : Bukkit.getOnlinePlayers()){
    		loadEPlayer(myPlayer);
    	}
    }

    public  void reload() {
    	players = new HashMap<UUID, EPlayer>();
    	this.load();
    }

	
	public EPlayer loadEPlayer(String playerName){
		for (EPlayer myEPlayer : this.players.values()){
			if (myEPlayer.getName().toLowerCase() == playerName.toLowerCase()){
				return myEPlayer;
			}
		}
		return null;
	}
	
	public void addPlayer(EPlayer myEPlayer){
		players.put(myEPlayer.getUUID(), myEPlayer);
	}
	
    
    
    public HashMap<UUID, EPlayer> loadEPlayers() {
		return players;
	}


	public boolean playerExists(UUID uniqueId) {
		if (players.get(uniqueId) != null)
			return true;
		else
			return false;
	}

	public EPlayer loadEPlayer(Player myPlayer) {
		
		EPlayer myEPlayer = players.get(myPlayer.getUniqueId());

		if ( myEPlayer!= null)
			return myEPlayer;
		
		myEPlayer = EmpiresDAL.loadPlayer(myPlayer.getUniqueId());
		
		if (myEPlayer == null){
			myEPlayer = new EPlayer(myPlayer);
			long timeNow = System.currentTimeMillis()/1000;
			myEPlayer.setFirstSeen(timeNow);
			myEPlayer.setLastSeen(timeNow);
			EmpiresDAL.createOrUpdatePlayer(myEPlayer);
			players.put(myEPlayer.getUUID(), myEPlayer);
			
		} else {
			myEPlayer.setLastSeen(System.currentTimeMillis()/1000);
			EmpiresDAL.createOrUpdatePlayer(myEPlayer);
			players.put(myEPlayer.getUUID(), myEPlayer);
		}
		return myEPlayer;
	}


	

	public void removePlayer(Player player) {
		
		EPlayer myEPlayer = loadEPlayer(player);
		players.remove(player.getUniqueId());
		EmpiresDAL.createOrUpdatePlayer(myEPlayer);
		
	}

	
}


//for (Player player : Bukkit.getOnlinePlayers()) {
//	if (!playerExists(player.getUniqueId())){
//		EPlayer myEPlayer = new EPlayer(player);
//		addPlayer(myEPlayer);
//	}
//	
////	if (playerdata.get(player.getUniqueId() + ".empire") != null) {
////		int empireID = playerdata.getInt(player.getUniqueId() + ".empire");
////		if (empire != null){
////			CorePlayer myEPlayer = new CorePlayer(player);
////			addPlayer(myEPlayer);
////			player.sendMessage(myPlugin.plprefix + ChatColor.GREEN + "You were found to be in an empire");
////		}
////	}

