package es.themin.empires;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import es.themin.empires.util.CorePlayer;
import es.themin.empires.util.Empire;

public class PlayerManager implements Manager {

	private HashMap<UUID, CorePlayer> players = new HashMap<UUID, CorePlayer>();
	private empires myPlugin;
	
	private  YamlConfiguration playerdata;
    private File pfile;
	
	public PlayerManager(empires plugin) {
		myPlugin = plugin;
		
		pfile = createFile("playerdata.yml");
	       
        playerdata = YamlConfiguration.loadConfiguration(pfile);
	}
	
	private  File createFile(String fileName){
    	
    	File myFile = new File(myPlugin.getDataFolder(), fileName);
        
        if (!myFile.exists()) {
                try {
                	myFile.createNewFile();
        				myPlugin.getLogger().info("[Empires] "+fileName+" not found, making you one");
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create "+fileName);
                }
        }
        
        return myFile;
    	
    }
	

    public  FileConfiguration getPlayerData() {
        return playerdata;
    }

    public  void save() {
    	for (CorePlayer myPlayer : players.values()) {
    		playerdata.set(myPlayer.getUUID() + ".empire", myPlayer.getEmpire());
    		playerdata.set(myPlayer.getUUID() + ".name", myPlayer.getName());
    	}
    	
        try {
                playerdata.save(pfile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save playerdata.yml!");
        }
    }
    
    public void load(){
    	for (Player player : Bukkit.getOnlinePlayers()) {
    		if (!playerExists(player.getUniqueId())){
    			CorePlayer myCorePlayer = new CorePlayer(player);
    			addPlayer(myCorePlayer);
    		}
    		
    		if (playerdata.get(player.getUniqueId() + ".empire") != null) {
    			Empire empire = myPlugin.Empires.getEmpireWithID(playerdata.getInt(player.getUniqueId() + ".empire"));
    			if (empire != null){
    				CorePlayer myCorePlayer = new CorePlayer(player);
        			addPlayer(myCorePlayer);
        			player.sendMessage(myPlugin.plprefix + ChatColor.GREEN + "You were found to be in an empire");
    			}
    		}
    	}
    }

    public  void reloadPlayerData() {
        playerdata = YamlConfiguration.loadConfiguration(pfile);
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