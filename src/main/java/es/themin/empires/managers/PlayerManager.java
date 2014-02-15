package es.themin.empires.managers;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.util.EPlayer;

public class PlayerManager implements Manager {

	private HashMap<UUID, EPlayer> players;
	
    private EmpiresDAL EmpiresDAL;
	
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
    	for (Player player : Bukkit.getOnlinePlayers()) {
    		if (!playerExists(player.getUniqueId())){
    			EPlayer myCorePlayer = new EPlayer(player);
    			addPlayer(myCorePlayer);
    		}
    		
//    		if (playerdata.get(player.getUniqueId() + ".empire") != null) {
//    			int empireID = playerdata.getInt(player.getUniqueId() + ".empire");
//    			if (empire != null){
//    				CorePlayer myCorePlayer = new CorePlayer(player);
//        			addPlayer(myCorePlayer);
//        			player.sendMessage(myPlugin.plprefix + ChatColor.GREEN + "You were found to be in an empire");
//    			}
//    		}
    	}
    }

    public  void reload() {
        //playerdata = YamlConfiguration.loadConfiguration(pfile);
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



	public void loadPlayer(UUID uniqueId) {
		// TODO Auto-generated method stub
		
	}




	
}
