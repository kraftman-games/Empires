package es.themin.empires.managers;

import java.io.File;
import java.util.ArrayList;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.util.Empire;

public class EmpireManager implements IManager {
	
	private ArrayList<Empire> empires = new ArrayList<Empire>();
	private EmpiresDAL myEmpiresDAL;


	public EmpireManager(EmpiresDAL empiresDAL, ArrayList<Empire> myEmpires) {
		myEmpiresDAL = empiresDAL;
		empires = myEmpires;
	}

	public ArrayList<Empire> getEmpires() {
		return empires;
	}
	
	public void load(){
		empires = myEmpiresDAL.loadEmpires();

	}

    public void save() {
    	myEmpiresDAL.saveEmpires(empires);
    }
    
    public void save(File myFile){
    	myEmpiresDAL.saveEmpires(empires, myFile);
    }

    public  void reload() {
    	empires = myEmpiresDAL.loadEmpires();
    }
	
	public void addEmpire(Empire empire) {
		this.empires.add(empire);
		
	}
		
	public int getUnusedEmpireID(){
		int i = 0;
		while (getEmpireWithID(i) != null){
			i++;
		}
		return i;
		
	}

	public void removeEmpire(Empire empire) {
		int i = this.getEmpires().indexOf(empire);
		this.empires.remove(i);
		myEmpiresDAL.removeEmpire(empire);
		
	}
	public Empire getEmpireWithName(String name) {
		for (Empire empire : this.empires) {
			if (empire.getName().equalsIgnoreCase(name)) return empire;
		}
		return null;
	}
	public boolean containsEmpireWithName(String name) {
		for (Empire empire : this.getEmpires()) {
			if (empire.getName().equalsIgnoreCase(name)) return true;
		}
		return false;
	}
	public Empire getEmpireWithID(int Id) {
		for (Empire empire : this.getEmpires()) {
			if (empire.getID() == Id) return empire;
		}
		return null;
	}
	public boolean containsEmpireWithId(int Id) {
		for (Empire empire : this.getEmpires()) {
			if (empire.getID() == Id) return true;
		}
		return false;
	}
	
	public boolean isValidName(String string) {
		if (string.isEmpty())
			return false;
		else if(getEmpireWithName(string) != null)
			return false;
		
		return true;
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











