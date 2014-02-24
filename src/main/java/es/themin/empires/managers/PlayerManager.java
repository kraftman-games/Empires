package es.themin.empires.managers;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.util.EPlayer;

public class PlayerManager implements IManager {

	private HashMap<UUID, EPlayer> players;
	
    private EmpiresDAL myEmpiresDAL;
    //private Database sql = null;
	
	public PlayerManager( EmpiresDAL myEmpiresDAL, HashMap<UUID, EPlayer> players) {
	    this.players = players;
	    this.myEmpiresDAL = myEmpiresDAL;
	}
	
    public void save(){
    	Bukkit.getServer().getLogger().info("saving players");
    	myEmpiresDAL.createOrUpdatePlayers(players);
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
		
		if ( myEPlayer!= null){
			return myEPlayer;
		}
		
		long timeNow = System.currentTimeMillis();		
		myEPlayer = myEmpiresDAL.loadPlayer(myPlayer.getUniqueId());
		
		if (myEPlayer == null){
			myEPlayer = new EPlayer(myPlayer);
			myEPlayer.setFirstSeen(timeNow);
			Bukkit.getServer().getLogger().info("new player created");
		} else {
			Bukkit.getServer().getLogger().info("player loaded from DB");
		}

		myEPlayer.setLastSeen(timeNow);
		myEmpiresDAL.createOrUpdatePlayer(myEPlayer);
		players.put(myEPlayer.getUUID(), myEPlayer);
		myEPlayer.setPlayer(myPlayer);
		
		return myEPlayer;
	}


	

	public void removePlayer(EPlayer myEPlayer) {
		
		players.remove(myEPlayer.getUUID());
		myEmpiresDAL.createOrUpdatePlayer(myEPlayer);
		
	}

	public boolean playerCanAdd(Player player, String string) {
		EPlayer myEPlayer = loadEPlayer(player);
		if ( myEPlayer.getEmpireUUID() == null) {
			player.sendMessage(ChatColor.RED + "you are not in an empire");
			return false;
		}
		
		Player target = Bukkit.getServer().getPlayer(string);
		if (target == null || !target.isOnline()){
			player.sendMessage("Player could not be found");
			return false;
		} else {
			EPlayer myTargetPlayer = loadEPlayer(target);
			if (myTargetPlayer.getEmpireUUID() == null){
				myTargetPlayer.sendMessage(myTargetPlayer.getName()+" is already in an empire!");
				return false;
			} else {
				return true;
			}
		}
	}

	public HashMap<UUID, EPlayer> getPlayers() {
		return players;
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

