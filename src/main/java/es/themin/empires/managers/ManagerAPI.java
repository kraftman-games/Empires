package es.themin.empires.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import es.themin.empires.Debug;
import es.themin.empires.cores.Core;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.EWorld;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;
import es.themin.empires.util.Rank;
import es.themin.empires.util.testing.newemp;

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
		for(Core myCore : Cores.getCores().values()){
			EWorld myWorld = Worlds.getWorld(myCore.getLocation().getWorld().getUID());
			myWorld.addCore(myCore);
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



	public void generateCore(EPlayer myEPlayer, Core myCore) {
		
		Empire myEmpire = getEmpire(myEPlayer);
		EWorld myWorld = getWorld(myEPlayer);
		
		if (!myEmpire.canExpand(myEPlayer, myCore)){
			return;
		}
		
		
		if (myWorld.coreLocationIsValid(myEPlayer, myCore)){
			if (playerCanAffordCore(myEPlayer, myCore)){
				if (playerHasPermission(myEPlayer, myCore.getPlacePermission())){
					HashMap<UUID, Core> myCores = Cores.getEmpireCores(myEmpire.getUUID(), myCore.getType());
					
					if (myCores.size() >= myEmpire.getCoreLimit(myCore.getType())){
						myEPlayer.sendMessage("You cannot create any more of those cores yet!");
					} else {
						myCore.setEmpireUUID(myEmpire.getUUID());
						myWorld.addCore(myCore);
						Cores.addCore(myCore);
						myEPlayer.sendMessage(myCore.getType().toString() + " Core successfully created");
						myCore.build();
						
						//charge the player
					}
				}
			}
		}
	}
	
	



	private boolean playerCanAffordCore(EPlayer myEPlayer, Core myCore) {
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



	public void handleBlockClick(PlayerInteractEvent event) {
		
		Block myBlock = event.getClickedBlock();
		EPlayer myEPlayer = getEPlayer(event.getPlayer());
		Empire myEmpire = getEmpire(myEPlayer);
		Player myPlayer = event.getPlayer();
		EWorld myEWorld = getEWorld(myBlock.getLocation().getWorld().getUID());
		
		
		
		if (myEPlayer.getEmpireUUID() == null){
			myEPlayer.sendMessage("You cannot attack other empires until you are in one!");
			event.setCancelled(true);
			return;
		}
		
		HashMap<UUID, Core> myCores = myEWorld.getCores(myBlock.getX(), myBlock.getZ());
		
		if (myCores == null || myCores.isEmpty()){
			//the block isnt in an empire, we dont care
			return;
		}
		
		Debug.Console(myCores.size()+" cores found");
		
		HashMap<UUID, Core> myEnemyCores = filterEnemyCores(myCores, myEmpire.getUUID());
		
		Debug.Console(myEnemyCores.size()+" enemy cores found");
		
		if (!myEnemyCores.isEmpty()){
			//deal with them attacking an enemy
		}
		
		HashMap<UUID, Core> myFriendlyCores = filterFriendlyCores(myCores, myEmpire.getUUID());
		
		Debug.Console(myFriendlyCores.size()+" friendly cores found");
		
		if (!myFriendlyCores.isEmpty()){
			myFriendlyCores = filterByCenterOverlap(myFriendlyCores, myBlock.getX(), myBlock.getY(), myBlock.getZ());
			
			Debug.Console(myFriendlyCores.size()+" center cores found");
			
			if (!myFriendlyCores.isEmpty()){
				myPlayer.sendMessage("You cannot destroy your own core!");
				event.setCancelled(true);
			}
		}
			
	}




	private HashMap<UUID, Core> filterByCenterOverlap(HashMap<UUID, Core> myFriendlyCore, int x, int y, int z) {
		HashMap<UUID, Core> myCores = new HashMap<UUID, Core>();
		for(Core myCore : myCores.values()){
			if (myCore.isInCore(x, y, z));
		}
		return myCores;
	}



	private HashMap<UUID, Core> filterFriendlyCores(HashMap<UUID, Core> friendlyCores, UUID uuid) {
		for(Core myCore : friendlyCores.values()){
			if (myCore.getEmpireUUID().equals(uuid));
		}
		return friendlyCores;
	}
	
	private HashMap<UUID, Core> filterEnemyCores(HashMap<UUID, Core> enemyCores, UUID uuid) {
		for(Core myCore : enemyCores.values()){
			if (!myCore.getEmpireUUID().equals(uuid));
		}
		return enemyCores;
	}



	private Core chooseCore(ArrayList<Core> myMatchingCores) {
		//later we might want to choose how we sort overlappign cores
		return myMatchingCores.get(0);
	}
}
