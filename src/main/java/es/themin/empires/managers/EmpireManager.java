package es.themin.empires.managers;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class EmpireManager implements IManager {
	
	private HashMap<UUID,Empire> empires = new HashMap<UUID,Empire>();
	private EmpiresDAL myEmpiresDAL;


	public EmpireManager(EmpiresDAL empiresDAL, HashMap<UUID,Empire> myEmpires) {
		myEmpiresDAL = empiresDAL;
		empires = myEmpires;
	}

	public HashMap<UUID,Empire> getEmpires() {
		return empires;
	}
	
	public void load(){
		empires = myEmpiresDAL.loadEmpires();

	}

    public void save() {
    	myEmpiresDAL.createOrUpdateEmpires(empires);
    }
    

    public  void reload() {
    	empires = myEmpiresDAL.loadEmpires();
    }
	
	public void addEmpire(Empire empire) {
		this.empires.put(empire.getUUID(),empire);
		myEmpiresDAL.createOrUpdateEmpire(empire);
		
	}

	public void removeEmpire(Empire empire) {
		empires.remove(empire.getUUID());
		myEmpiresDAL.removeEmpire(empire);
		
	}
	public Empire getEmpireWithName(String name) {
		for (Empire empire : empires.values()) {
			if (empire.getName().equalsIgnoreCase(name)) return empire;
		}
		return null;
	}
	public boolean containsEmpireWithName(String name) {
		for (Empire empire : this.getEmpires().values()) {
			if (empire.getName().equalsIgnoreCase(name)) return true;
		}
		return false;
	}
	public Empire getEmpirewithUUID(UUID ID) {
		return empires.get(ID);
	}
	public boolean containsEmpireWithId(UUID ID) {
		return empires.get(ID) != null ? true : false;
	}
	
	
	
	public int getRank(Empire myEmpire) {
		int xp = 0;
		int rank = 1;
		int total = 1;
		int pos = 1;
//		xp = this.numberOfPlayers() * 5;
//		for (Core core : cores) {
//			xp = xp + core.getLevel() * 2;
//		}
//		//xp = xp + this.numberOfAmplifiers() * 2;
//		for (Empire empire : Empires.getEmpires()) {
//			int xp2;
//			xp2 = empire.numberOfPlayers() * 5;
//			for (Core core : empire.getCores()) {
//				xp2 = xp2 + core.getLevel() * 2;
//			}
//			//xp2 = xp2 + empire.numberOfAmplifiers() * 2;
//			if (xp2 > xp) {
//				rank  = rank + pos;
//				pos = 1;
//			}if (xp2 == xp) {
//				pos ++;
//			}
//		}
		return rank;
	}

	public void saveEmpire(Empire myEmpire) {
		myEmpiresDAL.createOrUpdateEmpire(myEmpire);
		
	}

	public Empire getEmpire(UUID empireUUID) {
		return empires.get(empireUUID);
	}

	public Empire createEmpire(String empireName, EPlayer owner) {
		
		if (owner.getEmpireUUID() != null){
			owner.sendMessage("You are already in an empire");
			return null;
		}
		
		if (empireName.isEmpty()){
			owner.sendMessage("Your nation needs a name!");
			return null;
		}else if(getEmpireWithName(empireName) != null){
			owner.sendMessage("An empire with that name already exists!");
			return null;
		}

		Empire empire = new Empire(empireName,owner.getUUID());
		empires.put(empire.getUUID(), empire);
		
		empire.addPlayer(owner);

		saveEmpire(empire);
		
		owner.sendMessage(ChatColor.GREEN + "Created Empire: " + empireName);
		
		return empire;
	}

}


//public  void loadEmpires(empires plugin) {
//
//
//
//List<String> list = empiredata.getStringList("empires");
//for (String empireName : list) {
//	String[] words = empireName.split(":");
//	Integer Id = Integer.parseInt(words[0]);
//	String name = words[1];
//	String owner = words[2];
//	Empire empire = new Empire(this, name, UUID.fromString(owner));
//	
//	//loadEmpireCores(plugin, empire);
//	
//	loadEmpirePlayers(empire);
//	
//	loadEmpireRanks(empire);
//	
//	empire.Save();
//}
//}

//private  void loadEmpireRanks(Empire empire){
//List<String> rankList = empiredata.getStringList(empire.getName() + ".ranks");
//for (String rankString : rankList) {
//	String[] words2 = rankString.split(":");
//	Rank rank = new Rank(Integer.parseInt(words2[0]), words2[1], empire, words2[2]);
//	List<String> playersInRank = empiredata.getStringList(empire.getName() + ".rank." + rankString + ".players");
//	for (String playername : playersInRank) {
//		rank.addPlayer(playername);
//	}
//	List<String> playerPermissions = empiredata.getStringList(empire.getName() + ".rank." + rankString + ".permissions");
//	for (String permission : playerPermissions) {
//		EmpirePermission ep = Permissions.getPermission(permission);
//		if (ep != null){
//			rank.addPermission(ep);
//		}
//	}
//	empire.addRank(rank);
//}
//}

//private  void loadEmpirePlayers(Empire empire){
//List<String> playerList = empiredata.getStringList(empire.getName() + ".players");
//for ( String playerUUID : playerList) {
//	UUID myUUID = UUID.fromString(playerUUID);
//	//empire.addPlayer(myUUID);
//}
//}











