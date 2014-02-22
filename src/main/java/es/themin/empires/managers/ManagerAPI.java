package es.themin.empires.managers;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
	private ArrayList<IManager> Managers = null;
	
	public ManagerAPI(CoreManager myCoreManager, PlayerManager myPlayerManager, EmpireManager myEmpireManager ){
		Cores = myCoreManager;
		Players = myPlayerManager;
		Empires = myEmpireManager;
		
		Managers = new ArrayList<IManager>()
		
		Managers.add(myEmpireManager);
		Managers.add(myCoreManager);
		Managers.add(myPlayerManager);
		
	}
	
	
	
	public void loadManagers(){
		for (IManager m : Managers){
			m.load();
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



	public Empire getEmpire(UUID empireUUID) {
		return Empires.getEmpire(empireUUID);
	}

	public boolean playerHasPermission(EPlayer myEPlayer, EmpirePermission permission) {
		if (myEPlayer.getEmpireUUID() != null) {
			Empire empire = getEmpire(myEPlayer.getEmpireUUID());
			if ((empire.getOwnerUUID() == myEPlayer.getUUID())) {
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
		return getEmpire(myEPlayer.getUUID());
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
		
	}



	public EWorld getEWorld(UUID worldUUID) {
		// TODO Auto-generated method stub
		return null;
	}



	public void addCore(Core myCore) {
		// TODO Auto-generated method stub
		
	}



	public void sendChatToEmpire(EPlayer myEPlayer, String chatMessage) {

		Empire empire = getEmpire(myEPlayer.getEmpireUUID());
		String rank;
		if (!(empire.playerHasARank(myEPlayer.getName()))) {
			if (empire.getOwnerUUID() == myEPlayer.getUUID()) {
				if (empire.getOwnerPrefix() == null) rank = "king";
				else rank = empire.getOwnerPrefix();
			}else {
				if (empire.getDefaultPrefix() == null) rank = "default";
				else rank = empire.getDefaultPrefix();
			}
		}else rank = empire.getRankOfPlayer(myEPlayer.getName()).getPreifx();
		String rankc = MsgManager.colourUp(rank);
		String format = ChatColor.WHITE + "[" + rankc + ChatColor.WHITE + "] [" + myEPlayer.getName() + ChatColor.WHITE + "] ";
		empire.broadcastMessage(format + ChatColor.YELLOW + chatMessage);
	}
	
	
	
}
