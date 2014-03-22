
package es.themin.empires.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.cores.ICore;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpireState;

public class Empire {
	
//Important

//For use in this class	
	private UUID ID;
	private String name;
	private UUID owner;
	private HashMap<UUID,EPlayer> onlinePlayers = new HashMap<UUID,EPlayer>();
	private ArrayList<Rank> ranks = new ArrayList<Rank>();
	private String ownerprefix;
	private String defaultprefix;
	private EmpireState empireState = EmpireState.BATTLEREADY;
	
	private HashMap<Long,String> timeline;
	private Boolean edgesShown = false;
	
	public Boolean getEdgesShown() {
		return edgesShown;
	}

	public void setEdgesShown(Boolean edgesShown) {
		this.edgesShown = edgesShown;
	}

	private HashMap<CoreType, Integer> coreLimits;
	

	public Empire(String empireName, UUID myUUID){
		
		this.ID = UUID.randomUUID();
		this.name = empireName;
		this.owner = myUUID;
		this.timeline = new HashMap<Long,String>();
		
		coreLimits =  new HashMap<CoreType, Integer>();
		coreLimits.put(CoreType.BASE, 1);
		coreLimits.put(CoreType.GRIEF, 10);
		coreLimits.put(CoreType.FARM, 0);
		coreLimits.put(CoreType.FORTIFICATION, 0);
		coreLimits.put(CoreType.MOB, 0);
		coreLimits.put(CoreType.MONSTER, 0);
		coreLimits.put(CoreType.OUTPOST, 0);

		coreLimits.put(CoreType.CELL, 5);
	}
	

	public Empire(String empireName,UUID empireUUID,UUID myUUID){
		
		this.ID = empireUUID;
		this.name = empireName;
		this.owner = myUUID;
		
		this.timeline = new HashMap<Long,String>();
		
		//temp
		coreLimits =  new HashMap<CoreType, Integer>();
		coreLimits.put(CoreType.BASE, 1);
		coreLimits.put(CoreType.GRIEF, 10);
		coreLimits.put(CoreType.FARM, 0);
		coreLimits.put(CoreType.FORTIFICATION, 0);
		coreLimits.put(CoreType.MOB, 0);
		coreLimits.put(CoreType.MONSTER, 0);
		coreLimits.put(CoreType.OUTPOST, 0);
		coreLimits.put(CoreType.CELL, 5);
	}
	
	
	
	public UUID getUUID(){
		return ID;
	}
	public String getName(){
		return name;
	}
	public UUID getOwnerUUID(){
		return owner;
	}
	public void setOwnerUUID(UUID owner){
		this.owner = owner;
	}
	public HashMap<UUID,EPlayer> loadEPlayers(){
		return onlinePlayers;
	}
	
	public boolean hasPlayer(EPlayer myEPlayer) {
		if (onlinePlayers.get(myEPlayer.getUUID()) != null){
			return true;
		}
		return false;
	}
	
	public boolean hasPlayer(String playerName) {
		for(EPlayer myEPlayer : onlinePlayers.values()){
			if (myEPlayer.getName() == playerName){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasPlayer(Player myPlayer) {
		if (onlinePlayers.get(myPlayer.getUniqueId()) != null){
			return true;
		}
		return false;
	}
	
	public void addPlayer(EPlayer myEPlayer){
		if (myEPlayer.getEmpireUUID() == null){
			myEPlayer.setEmpireUUID(this.getUUID());
			onlinePlayers.put(myEPlayer.getUUID(), myEPlayer);
			myEPlayer.sendMessage(ChatColor.GREEN + "You were added to " + this.getName());
		}
	}
	
	public void addOnlinePlayer(EPlayer myEPlayer){
		if (myEPlayer.getEmpireUUID().equals(ID)){
			if (onlinePlayers.get(myEPlayer) == null){
				onlinePlayers.put(myEPlayer.getUUID(), myEPlayer);
			}
		}
	}
	
	
	public void removePlayer(String p) {
		onlinePlayers.remove(p);
	}
	public void setName(String name){
		this.name = name;
	}

	

	public int numberOfPlayers(){
		return onlinePlayers.size();
	}
	
	public ArrayList<Rank> getRanks(){
		return ranks;
	}
	public void addRank(Rank rank) {
		ranks.add(rank);
	}
	public void removeRank(Rank rank) {
		ranks.remove(rank);
	}
	public boolean hasRankWithName(String name) {
		for (Rank r : ranks) {
			if (r.getName() == name) {
				return true;
			}
		}
		return false;
	}
	public boolean hasRankWithNameAp(String name) {
		for (Rank r : ranks) {
			if (r.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	public boolean hasRankWithNameAp2(String name, Player player) {

		for (Rank r : ranks) {
			player.sendMessage("|" + r.getName() + "|" + name + "|");
			if (r.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	public boolean hasRankWithWeight(int weight) {
		for (Rank r : ranks) {
			if (r.getWeight() == weight) {
				return true;
			}
		}
		return false;
	}
	public Rank getRankOfPlayer(String playername) {
		for (Rank rank : ranks) {
			if (rank.loadEPlayers().contains(playername)) return rank;
		}
		return null;
	}
	public void setRankOfPlayer(String playername, Rank rank) {
		for (Rank rank2 : ranks) {
			rank2.removePlayer(playername);
		}
		rank.addPlayer(playername);
	}

	
	public Rank getRankWithName(String name) {
		for (Rank rank : ranks) {
			if (rank.getName().equalsIgnoreCase(name))return rank;
		}
		return null;
	}
	
	public void removePlayerFromRank(String p, Rank r) {
		for (Rank rank : ranks) {
			if (rank.getName() == r.getName()) {
				rank.removePlayer(p);
			}
		}
	}


	public void broadcastMessage(String message) {
		for (EPlayer myEPlayer : onlinePlayers.values()){
			myEPlayer.sendMessage(message);
		}
	}
	
	public String getOwnerPrefix() {
		return ownerprefix;
	}
	public String getDefaultPrefix() {
		return defaultprefix;
	}
	public void setOwnerPrefix(String s) {
		this.ownerprefix = s;
	}
	public void setDefaultPrefix(String s) {
		this.defaultprefix = s;
	}

	public boolean playerHasARank(String player) {
		for (Rank rank : ranks) {
			if (rank.loadEPlayers().contains(player)) return true;
		}
		return false;
	}

	public EmpireState getEmpireState() {
		return empireState;
	}

	public void setEmpireState(EmpireState empireState) {
		this.empireState = empireState;
	}
	

	
	
	
	public int getNumberOfOnlinePlayers(){
		int number = 0;
		for (Player player : Bukkit.getOnlinePlayers()) {
			EPlayer myEPlayer = onlinePlayers.get(player.getUniqueId());
			if (myEPlayer != null){
				number++;
			}
		}
		return number;
	}
	
	public HashMap<Long,String> getTimeLine() {
		return timeline;
	}
	public void addToTimeline(String string) {
		timeline.put(System.currentTimeMillis(), string);
	}

	public Integer getXP() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getCoreLimit(CoreType type) {
		return coreLimits.get(type);
	}

	public boolean canExpand(EPlayer myEPlayer, ICore myCore) {
		//check the empire is in a state where it can expand
		if (getEmpireState() != EmpireState.BATTLEREADY){
			if (getEmpireState() == EmpireState.ATWAR){
				myEPlayer.sendMessage("You cannot expand your empire while you are at war!");
				return false;
			} else if (getEmpireState() == EmpireState.REBUILDING){
				myEPlayer.sendMessage("You cannot expand your empire until it has rebuilt!");
				return false;
			} 
		}
		return true;
	}

	public String getRankPrefix(EPlayer myEPlayer) {
		if (!playerHasARank(myEPlayer.getName())) {
			if (getOwnerUUID().equals(myEPlayer.getUUID())) {
				if (getOwnerPrefix() == null) {
					return "king";
				}
				else {
					return getOwnerPrefix();
				}
			}else {
				if (getDefaultPrefix() == null){
					 return "default";
				} else {
					 return getDefaultPrefix();
				}
			}
		}else {
			return getRankOfPlayer(myEPlayer.getName()).getPrefix();
		}
	}

	public void removeOnlinePlayer(EPlayer myEPlayer) {
		if (onlinePlayers.get(myEPlayer) != null){
		onlinePlayers.remove(myEPlayer);
		}
		
	}

	
}





