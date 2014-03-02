package es.themin.empires.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import es.themin.empires.Debug;
import es.themin.empires.cores.Core;
import es.themin.empires.cores.ICore;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.enums.EmpireState;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.EWorld;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;
import es.themin.empires.util.Rank;

public class ManagerAPI {

	private CoreManager Cores = null;
	private PlayerManager Players = null;
	private EmpireManager Empires = null;
	private SettingsManager Settings = null;
	private WorldManager Worlds = null;
	private ArrayList<IManager> Managers = null;
	
	public ManagerAPI(CoreManager myCoreManager, PlayerManager myPlayerManager, EmpireManager myEmpireManager, SettingsManager mySettingsManager, WorldManager myWorldManager ){
		Cores = myCoreManager;
		Players = myPlayerManager;
		Empires = myEmpireManager;
		Settings = mySettingsManager;
		Worlds = myWorldManager;
		
		Managers = new ArrayList<IManager>();
		
		Managers.add(myEmpireManager);
		Managers.add(myCoreManager);
		Managers.add(myPlayerManager);
		Managers.add(mySettingsManager);
		Managers.add(myWorldManager);
		
	}
	
	
	
	public void loadManagers(){
		for (IManager m : Managers){
			m.load();
		}
		
		addPlayersToEmpires();
		addCoresToWorlds();
		
	}

	private void addCoresToWorlds() {
		ConcurrentHashMap<UUID, ICore> myCores = Cores.getCores();
		if (myCores != null && !myCores.isEmpty()){
			for(ICore myCore : Cores.getCores().values()){
				EWorld myWorld = Worlds.getWorld(myCore.getLocation().getWorld().getUID());
				myWorld.addCore(myCore);
			}
		}
	}



	private void addPlayersToEmpires() {
		for(EPlayer myPlayer : Players.getPlayers().values()){
			if (myPlayer.getEmpireUUID() != null){
				Empire myEmpire = Empires.getEmpire(myPlayer.getEmpireUUID());
				myEmpire.addOnlinePlayer((myPlayer));
			}
		}
	}



	public void saveManagers(){
		for (IManager m : Managers){
			m.save();
		}
	}



	public EPlayer getEPlayer(Player player) {
		return Players.loadEPlayer(player);
	}
	
	public void loadPlayer(Player player){
		EPlayer myEPlayer = getEPlayer(player);
		if (myEPlayer.getEmpireUUID() != null){
			Empire myEmpire = getEmpire(myEPlayer);
			myEmpire.addOnlinePlayer(myEPlayer);
		}
	}



	public Empire getEmpire(UUID empireUUID) {
		return Empires.getEmpire(empireUUID);
	}

	public boolean playerHasPermission(EPlayer myEPlayer, EmpirePermission permission) {
		if (myEPlayer.getEmpireUUID() != null) {
			Empire empire = getEmpire(myEPlayer.getEmpireUUID());
			Debug.Console(empire.getOwnerUUID().toString() + " <- empire | player -> "+ myEPlayer.getUUID());
			if ((empire.getOwnerUUID().equals(myEPlayer.getUUID()))) {
				return true;
			} else {
				if (empire.playerHasARank(myEPlayer.getName())) {
					Rank rank = empire.getRankOfPlayer(myEPlayer.getName());
					if (rank.hasPermission(permission)) {
						return true;
					}
				}
			}
		} 
		myEPlayer.sendMessage(MsgManager.noempperm);
		return false;
	}



	public void listEmpires(EPlayer myEPlayer) {
		myEPlayer.sendMessage(ChatColor.GOLD + "=====" + ChatColor.LIGHT_PURPLE + "Empires" + ChatColor.GOLD + "=====");
		int i = 0;
		for (Empire empire : Empires.getEmpires().values()) {
			i++;
			myEPlayer.sendMessage(ChatColor.GREEN + empire.getName());
		}
		if (i == 0) {
			myEPlayer.sendMessage(ChatColor.RED + "No empires :(");
		}
	}



	public Empire getEmpire(EPlayer myEPlayer) {
		return getEmpire(myEPlayer.getEmpireUUID());
	}



	public Integer getEmpireCount() {
		return Empires.getEmpires().size();
	}



	public int getRank(Empire empire) {
		// TODO Auto-generated method stub
		return 0;
	}



	public int getCoreCount(Empire empire) {
		// TODO Auto-generated method stub
		return 0;
	}



	public void createEmpire(String string, EPlayer myEplayer) {
		Empires.createEmpire(string, myEplayer);
	}



	public void sendEmpireDetails(EPlayer myEPlayer, String string) {
		
			Empire myEmpire = getEmpire(myEPlayer);
			myEPlayer.sendMessage(ChatColor.GOLD + "=====" + ChatColor.LIGHT_PURPLE + myEmpire.getName() + ChatColor.GOLD + "=====");
			myEPlayer.sendMessage(ChatColor.GREEN + "Player #: " + ChatColor.LIGHT_PURPLE + myEmpire.numberOfPlayers());
			myEPlayer.sendMessage(ChatColor.GREEN + "Core #: " + ChatColor.LIGHT_PURPLE + Cores.getCoreCount(myEmpire));
			StringBuilder str = new StringBuilder();
			str.append(ChatColor.GOLD + "Players: ");
			int i = 0;
			for (EPlayer p : myEmpire.loadEPlayers().values()) {
				i++;
				str.append(ChatColor.GREEN + p.getName() + ", ");
			}
			if (i == 0) {
				str.append(ChatColor.RED + "No players :(");
			}
			myEPlayer.sendMessage(str.toString());
			myEPlayer.sendMessage(ChatColor.GREEN + "Owner: " + ChatColor.LIGHT_PURPLE + myEmpire.getOwnerUUID());		
	}



	public Empire getEmpire(String string) {
		return Empires.getEmpireWithName(string);
	}



	public boolean playerCanAddPlayer(Player player, String string) {
		// TODO Auto-generated method stub
		return false;
	}



	public void removePlayer(EPlayer myEPlayer) {
		Players.removePlayer(myEPlayer);
		
		Empire myEmpire = getEmpire(myEPlayer);
		if (myEmpire != null){
			myEmpire.removeOnlinePlayer(myEPlayer);
		}
		
	}

	public EWorld getEWorld(UUID worldUUID) {
		return Worlds.getWorld(worldUUID);
	}

	

	public void sendChatToEmpire(EPlayer myEPlayer, String chatMessage) {

		Empire empire = getEmpire(myEPlayer);
		String rank = empire.getRankPrefix(myEPlayer);
		
		
		String rankc = MsgManager.colourUp(rank);
		String format = ChatColor.WHITE + "[" + rankc +rank+ ChatColor.WHITE + "] [" + myEPlayer.getName() + ChatColor.WHITE + "] ";
		empire.broadcastMessage(format + ChatColor.YELLOW + chatMessage);
	}



	public String getSetting(String myKey) {
		return Settings.getSetting(myKey);
	}



	public void generateCore(EPlayer myEPlayer, ICore myCore) {
		
		Empire myEmpire = getEmpire(myEPlayer);
		EWorld myWorld = getWorld(myEPlayer);
		
		if (!myEmpire.canExpand(myEPlayer, myCore)){
			return;
		}
		
		
		if (myWorld.coreLocationIsValid(myEPlayer, myCore)){
			if (playerCanAffordCore(myEPlayer, myCore)){
				if (playerHasPermission(myEPlayer, myCore.getPlacePermission())){
					HashMap<UUID, ICore> myCores = Cores.getEmpireCores(myEmpire.getUUID(), myCore.getType());
					
					if (myCores.size() >= myEmpire.getCoreLimit(myCore.getType())){
						myEPlayer.sendMessage("You cannot create any more of those cores yet!");
					} else {
						myCore.setEmpireUUID(myEmpire.getUUID());
						myWorld.addCore(myCore);
						Cores.addCore(myCore);
						myEPlayer.sendMessage(myCore.getType().toString() + " Core successfully created");
						if (myCore.getType() != CoreType.CELL){
							myCore.build();
						}
						
						//charge the player
					}
				}
			}
		}
	}
	
	



	private boolean playerCanAffordCore(EPlayer myEPlayer, ICore myCore) {
	/* so this will depend on how we charge players,
	 * does it come out of their empire account
	 * out of their inventory
	 * or out of their personal saved value
	 * 
	 */
		
		
		return true;
	}



	private EWorld getWorld(EPlayer myEPlayer) {
		return Worlds.getWorld(myEPlayer.getWorld().getUID());
	}



	public void handleBlockLeftClick(PlayerInteractEvent event) {
		
		Block myBlock = event.getClickedBlock();
		
		if (myBlock == null){
			return;
		}
		
		EPlayer myEPlayer = getEPlayer(event.getPlayer());
		Empire myEmpire = getEmpire(myEPlayer);
		Player myPlayer = event.getPlayer();
		EWorld myEWorld = getEWorld(myBlock.getLocation().getWorld().getUID());
		
		if (myEPlayer.getEmpireUUID() == null){
			myEPlayer.sendMessage("You cannot attack other empires until you are in one!");
			event.setCancelled(true);
			return;
		}
		
		HashMap<UUID, ICore> myCores = myEWorld.getCores(myBlock.getX(), myBlock.getZ());
		
		if (myCores == null || myCores.isEmpty()){
			//the block isnt in an empire, we dont care
			return;
		}
		
		HashMap<UUID, ICore> myEnemyCores = filterEnemyCores(myCores, myEmpire.getUUID());
		
		if (!myEnemyCores.isEmpty()){
			//deal with them attacking an enemy
			myPlayer.sendMessage("You can't attack enemies right now!");
			event.setCancelled(true);
		}
		
		HashMap<UUID, ICore> myFriendlyCores = filterFriendlyCores(myCores, myEmpire.getUUID());
		
		if (!myFriendlyCores.isEmpty()){
			myFriendlyCores = filterByCenterOverlap(myFriendlyCores, myBlock.getX(), myBlock.getY(), myBlock.getZ());
			
			if (!myFriendlyCores.isEmpty()){
				myPlayer.sendMessage("You cannot destroy your own core!");
				event.setCancelled(true);
			}
		}
			
	}
	




	private HashMap<UUID, ICore> filterByCenterOverlap(HashMap<UUID, ICore> myFriendlyCore, int x, int y, int z) {
		HashMap<UUID, ICore> myCores = new HashMap<UUID, ICore>();
		for(ICore myCore : myFriendlyCore.values()){
			if (myCore.isInCore(x, y, z)){
				myCores.put(myCore.getUUID(), myCore);
			}
		}
		return myCores;
	}



	private HashMap<UUID, ICore> filterFriendlyCores(HashMap<UUID, ICore> myCores, UUID uuid) {
		HashMap<UUID, ICore> friendlyCores = new HashMap<UUID, ICore>();
		for(ICore myCore : myCores.values()){
			if (myCore.getEmpireUUID().equals(uuid)){
				friendlyCores.put(myCore.getUUID(), myCore);
			}
		}
		return friendlyCores;
	}
	
	private HashMap<UUID, ICore> filterEnemyCores(HashMap<UUID, ICore> myCores, UUID uuid) {
		HashMap<UUID, ICore> enemyCores = new HashMap<UUID, ICore>();
		for(ICore myCore : myCores.values()){
			if (!myCore.getEmpireUUID().equals(uuid)){
				enemyCores.put(myCore.getUUID(), myCore);
			}
		}
		return enemyCores;
	}



	private ICore chooseCore(ArrayList<ICore> myMatchingCores) {
		//later we might want to choose how we sort overlappign cores
		return myMatchingCores.get(0);
	}



	public void handleBlockRightClick(PlayerInteractEvent event) {

		Block myBlock = event.getClickedBlock();
		
		if (myBlock == null){
			return;
		}
		
		EPlayer myEPlayer = getEPlayer(event.getPlayer());
		Empire myEmpire = getEmpire(myEPlayer);
		Player myPlayer = event.getPlayer();
		EWorld myEWorld = getEWorld(myBlock.getLocation().getWorld().getUID());
		
		HashMap<UUID, ICore> myCores = myEWorld.getCores(myBlock.getX(), myBlock.getZ());
		
		if (myCores == null || myCores.isEmpty()){
			//the block isnt in an empire, we dont care
			return;
		}
		
		if (myEPlayer.getEmpireUUID() == null){
			myEPlayer.sendMessage("You cannot build in another persons empire!!");
			event.setCancelled(true);
			return;
		}
		
		HashMap<UUID, ICore> myEnemyCores = filterEnemyCores(myCores, myEmpire.getUUID());
		
		if (!myEnemyCores.isEmpty()){
			//deal with them attacking an enemy
			event.setCancelled(true);
		}
		
		HashMap<UUID, ICore> myFriendlyCores = filterFriendlyCores(myCores, myEmpire.getUUID());
		
		if (!myFriendlyCores.isEmpty()){
			myFriendlyCores = filterByCenterOverlap(myFriendlyCores, myBlock.getX(), myBlock.getY(), myBlock.getZ());
			
			if (!myFriendlyCores.isEmpty()){
				myPlayer.sendMessage("You cannot build on your own core!");
				event.setCancelled(true);
			}
		}
		
	}



	public void showEdges(UUID empireUUID) {
		HashMap<UUID, ICore> myCores = Cores.getEmpireCores(empireUUID);
		
		if (myCores != null && !myCores.isEmpty()){
			Debug.Console("found cores duirng showedges");
			Empire myEmpire = getEmpire(empireUUID);
			Boolean showEdges = true;
			if (myEmpire.getEmpireState() != EmpireState.ATWAR){
				Debug.Console("empires not at war duirng showedges");
								
				
				for(ICore myCore : myCores.values()){
					myCore.showEdges(myEmpire.getEdgesShown());
				}
				myEmpire.setEdgesShown(!myEmpire.getEdgesShown());
			}	
		}		
	}



	public void updatePlayerLocation(PlayerMoveEvent event, EPlayer myEPlayer) {
		long time = System.currentTimeMillis();
		Location newLocation = event.getPlayer().getLocation().getBlock().getLocation();
		EWorld myEWorld = getEWorld(myEPlayer.getWorld().getUID());
		
		if (myEPlayer.getLastLocationCheck() < (time - 1000)){
			if (newLocation.equals(myEPlayer.getLastLocation())){
				//they havnt moved, reset the timer
				myEPlayer.setLastLocationCheck(time);
			} else {
				UUID empireUuid = myEWorld.getEmpireUUID(newLocation);
				String locationName = "Wilderness";
				if (empireUuid != null){
					Empire myEmpire = getEmpire(empireUuid);
					locationName = myEmpire.getName();
					
					if (myEPlayer.getEmpireUUID() != null && !empireUuid.equals(myEPlayer.getEmpireUUID())){
						
						//loop through special cores to see if they do anything
						HashMap<UUID, ICore> myCores = myEWorld.getCores(newLocation);
						if (myCores != null && !myCores.isEmpty()){
							for (ICore myCore : myCores.values()){
								if (myCore.getType() == CoreType.CELL){
									myCore.build();
								}
							}
						}
						
					}
				}
				if (myEPlayer.getLastLocationName() != locationName){
					myEPlayer.sendMessage("~"+locationName);
					myEPlayer.setLastLocationName(locationName);
				}
				
			
				
				
				myEPlayer.setLastLocation(newLocation);
			}
			
			
			myEPlayer.setLastLocationCheck(time);
		}
		
	}
	public void regenAllBlocks() {
		for (EWorld world : Worlds.getWorlds().values()) {
			world.regenAllBlocks();
		}
	}

}











