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
    		loadPlayer(myPlayer);
    	}
    }

    public  void reload() {
    	players = new HashMap<UUID, EPlayer>();
    	this.load();
    }

	
	public EPlayer getPlayer(String playerName){
		for (EPlayer myCorePlayer : this.players.values()){
			if (myCorePlayer.getName().toLowerCase() == playerName.toLowerCase()){
				return myCorePlayer;
			}
		}
		return null;
	}
	
	public EPlayer getPlayer(UUID myUUID){
		return players.get(myUUID);
	}
	
	public void addPlayer(EPlayer myCorePlayer){
		players.put(myCorePlayer.getUUID(), myCorePlayer);
	}
	
    
    
    public HashMap<UUID, EPlayer> getPlayers() {
		return players;
	}


	public boolean playerExists(UUID uniqueId) {
		if (players.get(uniqueId) != null)
			return true;
		else
			return false;
	}

	public EPlayer loadPlayer(Player myPlayer) {
		
		//if the player is already loaded just return them
		if (players.get(myPlayer.getUniqueId()) != null){
			System.out.println(myPlayer.getName()+ " found in memory");
			return players.get(myPlayer.getUniqueId());
		}

		//otherwise try and get them from the db
		EPlayer myEPlayer = EmpiresDAL.loadPlayer(myPlayer.getUniqueId());
		
		//or create them if they dont exist
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
		
		EPlayer myEPlayer = getPlayer(player.getUniqueId());
		players.remove(player.getUniqueId());
		EmpiresDAL.createOrUpdatePlayer(myEPlayer);
		
	}

	
}


//for (Player player : Bukkit.getOnlinePlayers()) {
//	if (!playerExists(player.getUniqueId())){
//		EPlayer myCorePlayer = new EPlayer(player);
//		addPlayer(myCorePlayer);
//	}
//	
////	if (playerdata.get(player.getUniqueId() + ".empire") != null) {
////		int empireID = playerdata.getInt(player.getUniqueId() + ".empire");
////		if (empire != null){
////			CorePlayer myCorePlayer = new CorePlayer(player);
////			addPlayer(myCorePlayer);
////			player.sendMessage(myPlugin.plprefix + ChatColor.GREEN + "You were found to be in an empire");
////		}
////	}

