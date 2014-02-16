package es.themin.empires.managers;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;



import com.jolbox.bonecp.BoneCP;

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
    	EmpiresDAL.savePlayers(players);
    }

    public  void save(File datafile) {
    	EmpiresDAL.savePlayers(players, datafile);
    }
    
    public void load(){
    	players = EmpiresDAL.loadPlayers();
    }

    public  void reload() {
    	players = EmpiresDAL.loadPlayers();
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
		//so we want to get the player if it exists in memory
		//if it doesnt try and get it from the DAL
		// if there's still nothing then create it.
		if (players.get(myPlayer.getUniqueId()) != null){
			return players.get(myPlayer.getUniqueId());
		}
		
		EPlayer myEPlayer = EmpiresDAL.loadPlayer(myPlayer.getUniqueId());
		
		if (myEPlayer == null){
			myEPlayer = new EPlayer(myPlayer);
			if (EmpiresDAL.createPlayer(myEPlayer) == true){
				players.put(myEPlayer.getUUID(), myEPlayer);
			} else {
				//do something
			}
			
		}
		return myEPlayer;
	}



	public void removePlayer(Player player) {
		
		EPlayer myEPlayer = getPlayer(player.getUniqueId());
		EmpiresDAL.savePlayer(myEPlayer);
		
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

