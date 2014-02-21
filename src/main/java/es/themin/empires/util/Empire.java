
package es.themin.empires.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import es.themin.empires.enums.EmpireState;
import es.themin.empires.wars.Battle;
import es.themin.empires.wars.War;

public class Empire {
	
//Important

//For use in this class	
	private UUID ID;
	private String name;
	private UUID owner;
	private HashMap<UUID,EPlayer> players = new HashMap<UUID,EPlayer>();
	//private ArrayList<Core> cores = new ArrayList<Core>();
	private ArrayList<Rank> ranks = new ArrayList<Rank>();
	private boolean isProtected;
	private String ownerprefix;
	private String defaultprefix;
	private boolean atWar;
	private Empire enemyEmpire;
	private boolean vunerable;
	private EmpireState empireState;
	private int warwins;
	private int warlosses;
	private int battlewins;
	private int battlelosses;
	private ArrayList<War> wars;
	private ArrayList<Empire> allies;
	private HashMap<Empire, Long> exallies;
	private HashMap<Empire, Long> exenemies;
	private Long lastbattleloss;
	private Long lastbattlewin;
	private HashMap<Empire, Long> allyrequests;
	private HashMap<Long,String> timeline;
	
	public Empire getEnemyEmpire() {
		return enemyEmpire;
	}

	public void setEnemyEmpire(Empire enemyEmpire) {
		this.enemyEmpire = enemyEmpire;
	}

	

	public Empire(String empireName, UUID myUUID){
		
		this.ID = UUID.randomUUID();
		this.name = empireName;
		this.owner = myUUID;
		this.atWar = false;
		this.warwins = 0;
		this.warlosses = 0;
		this.battlelosses = 0;
		this.battlewins = 0;
		this.wars = new ArrayList<War>();
		this.setProtected(true);
		this.allies = new ArrayList<Empire>();
		this.exallies = new HashMap<Empire, Long>();
		this.exenemies = new HashMap<Empire, Long>();
		this.lastbattleloss = (long) 0;
		this.lastbattlewin = (long) 0;
		this.allyrequests = new HashMap<Empire,Long>();
		this.timeline = new HashMap<Long,String>();
	}
	
	
	
	public UUID getUUID(){
		return ID;
	}
	public String getName(){
		return name;
	}
	public UUID getOwner(){
		return owner;
	}
	public void setOwner(UUID owner){
		this.owner = owner;
	}
	public HashMap<UUID,EPlayer> getPlayers(){
		return players;
	}
	
	public boolean hasPlayer(EPlayer myCorePlayer) {
		if (players.get(myCorePlayer.getUUID()) != null){
			return true;
		}
		return false;
	}
	
	public boolean hasPlayer(String playerName) {
		for(EPlayer myCorePlayer : players.values()){
			if (myCorePlayer.getName() == playerName){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasPlayer(Player myPlayer) {
		if (players.get(myPlayer.getUniqueId()) != null){
			return true;
		}
		return false;
	}
	
	public void addPlayer(EPlayer myCorePlayer){
		if (myCorePlayer.getEmpireUUID() == null){
			players.put(myCorePlayer.getUUID(), myCorePlayer);
		}
	}
	public void removePlayer(String p) {
		players.remove(p);
	}
	public void setName(String name){
		this.name = name;
	}

	

	public int numberOfPlayers(){
		int i = players.size();
		return i;
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
			if (rank.getPlayers().contains(playername)) return rank;
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
		for (Player player : Bukkit.getOnlinePlayers()) {
			EPlayer myCorePlayer = players.get(player.getUniqueId());
			if (myCorePlayer != null){
				player.sendMessage(message);
			}
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

	public boolean canPlayerAttack(Empire playerEmpire) {
		//Empire playerEmpire = Players.getPlayer(myPlayer.getUniqueId()).getEmpire();
		if (!this.isProtected()){
			if (this.isAtWar()){
				if (playerEmpire == this.getEnemyEmpire()){
					return true;
				}
				else {
					//myPlayer.sendMessage("This war is not yours to fight!");
					return false;
				}
				
			} else if (playerEmpire.isProtected){
				//myPlayer.sendMessage("You cannot attack an empire until yours is rebuilt!");
				return false;
			}
		} else {
			//myPlayer.sendMessage("There is no honor in attack this fallen empire");
			return false;
		}
		return false;
	}
	
	/**
	 * sets up a war between two empires
	 * @param eventPlayerEmpire
	 */
	/*public void startWar(Empire eventPlayerEmpire) {
		this.setAtWar(true);
		eventPlayerEmpire.setAtWar(true);
		this.setEnemyEmpire(eventPlayerEmpire);
		eventPlayerEmpire.setEnemyEmpire(eventPlayerEmpire);
		Save();
	}
	
	public void endWar(Empire eventPlayerEmpire){
		this.setAtWar(false);
		eventPlayerEmpire.setAtWar(false);
		this.setEnemyEmpire(null);
		eventPlayerEmpire.setEnemyEmpire(null);
		Save();
	}*/
	
	public boolean playerHasARank(String player) {
		for (Rank rank : ranks) {
			if (rank.getPlayers().contains(player)) return true;
		}
		return false;
	}

	public EmpireState getEmpireState() {
		return empireState;
	}

	public void setEmpireState(EmpireState empireState) {
		this.empireState = empireState;
	}
	public boolean isProtected() {
		return isProtected;
	}

	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}
	public boolean isAtWar() {
		if (wars.isEmpty()) return false;
		return true;
	}
	/*public void setAtWar(boolean atWar) {
		this.atWar = atWar;
	}*/
	public ArrayList<War> getWars() {
		return wars;
	}
	public void addWar(War war) {
		this.atWar = true;
		this.wars.add(war);
	}
	public void removeWar(War war) {
		this.wars.remove(war);
		if (wars.isEmpty()) this.atWar = false;
	}
	public boolean isInABattle() {
		if (isAtWar()) {
			for (War war : this.wars) {
				if (war.hasBattleOnGoing()) return true;
			}
		}
		return false;
	}
	public Battle getCurrentBattle() {
		if (isAtWar()) {
			for (War war : this.wars) {
				if (war.hasBattleOnGoing()) {
					return war.getOnGoingBattle();
				}
			}
		}
		return null;
	}
	public ArrayList<Empire> getAllies() {
		return allies;
	}
	public void addAlly(Empire empire) {
		allies.add(empire);
	}
	public void removeAlly(Empire empire) {
		allies.remove(empire);
	}
	public boolean hasAllies() {
		if (!(allies.isEmpty())) return true;
		return false;
	}
	public boolean isAlliedWith(Empire empire) {
		if (allies.contains(empire)) return true;
		return false;
	}
	public boolean isAtWarWith(Empire empire) {
		if (isAtWar()) {
			for (War war : wars) {
				if (war.getAllEmpiresOnTeam1().contains(this) && war.getAllEmpiresOnTeam2().contains(empire)) return true;
				else if (war.getAllEmpiresOnTeam2().contains(this) && war.getAllEmpiresOnTeam1().contains(empire)) return true;
			}
		}
		return false;
	}
	public War getWarAgainst(Empire empire) {
		if (isAtWar()) {
			for (War war : wars) {
				if (war.getAllEmpiresOnTeam1().contains(this) && war.getAllEmpiresOnTeam2().contains(empire)) return war;
				else if (war.getAllEmpiresOnTeam2().contains(this) && war.getAllEmpiresOnTeam1().contains(empire)) return war;
			}
		}
		return null;
	}
	public boolean isInBattleWith(Empire empire) {
		if (isAtWarWith(empire)) {
			War war = getWarAgainst(empire);
			if (war.hasBattleOnGoing()) {
				Battle battle = war.getOnGoingBattle();
				if (battle.getAllEmpiresOnTeam1().contains(this) && battle.getAllEmpiresOnTeam2().contains(empire)) return true;
				if (battle.getAllEmpiresOnTeam2().contains(this) && battle.getAllEmpiresOnTeam1().contains(empire)) return true;
			}
		}
		return false;
	}
	public int getNumberOfOnlinePlayers(){
		int number = 0;
		for (Player player : Bukkit.getOnlinePlayers()) {
			EPlayer myCorePlayer = players.get(player.getUniqueId());
			if (myCorePlayer != null){
				number++;
			}
		}
		return number;
	}
	public void addBattleWins(int i) {
		this.battlewins = this.battlewins + i;
	}
	public void addBattleLosses(int i) {
		this.battlelosses = this.battlelosses + i;
	}
	public HashMap<UUID,EPlayer> getOnlinePlayers() {
		HashMap<UUID,EPlayer> list = new HashMap<UUID,EPlayer>();
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			EPlayer myCorePlayer = players.get(player.getUniqueId());
			if (myCorePlayer != null){
				list.put(myCorePlayer.getUUID(), myCorePlayer);
			}
		}
		
		return list;
	}
	public void setLastBattleLoss(Long l) {
		this.lastbattleloss = l;
	}
	public Long getLastBattleLoss() {
		return lastbattleloss;
	}
	public void setLastBattleWin(Long l) {
		this.lastbattlewin = l;
	}
	public Long getLastBattleWin() {
		return lastbattlewin;
	}
	public boolean exAlliesContains(Empire e) {
		if (this.exallies.containsKey(e)) return true;
		return false;
	}public Long getLastAllianceWith(Empire e) {
		if (exAlliesContains(e)){
			return this.exallies.get(e);
		}
		return null;
	}
	public void addExAlly(Empire e) {
		this.exallies.put(e, System.currentTimeMillis());
	}
	public void addExAlly(Empire e, Long l) {
		this.exallies.put(e, l);
	}public void removeExAlly(Empire e) {
		if (exAlliesContains(e)) {
			this.exallies.remove(e);
		}
	}
	public void addWarLosses(int i) {
		this.warlosses = warlosses + i;
	}
	public void addWarWins(int i) {
		this.warwins = warwins + i;
	}public boolean exEnemiesContains(Empire e) {
		if (this.exenemies.containsKey(e)) return true;
		return false;
	}
	public void addExEnemy(Empire e) {
		this.exenemies.put(e, System.currentTimeMillis());
	}
	public void addExEnemy(Empire e, Long l) {
		this.exenemies.put(e, l);
	}
	public Long getLastEnemyWith(Empire e) {
		if (exEnemiesContains(e)) {
			return this.exenemies.get(e);
		}
		return null;
	}
	public void removeExEnemy(Empire e) {
		if (exEnemiesContains(e)) {
			this.exenemies.remove(e);
		}
	}
	public HashMap<Empire,Long> getAllianceRequests() {
		return this.allyrequests;
	}
	public void addAllyRequest(Empire e) {
		if (!this.allyrequests.containsKey(e)) {
			this.allyrequests.put(e, System.currentTimeMillis());
		}
	}
	public boolean hasAllyRequestFrom(Empire e) {
		if (this.allyrequests.containsKey(e)) return true;
		return false;
	}
	public void removeAllyRequest(Empire e) {
		this.allyrequests.remove(e);
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

	
}





