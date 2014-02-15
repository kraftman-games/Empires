package es.themin.empires.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.empires;
import es.themin.empires.cores.Core;
import es.themin.empires.cores.CoreUtils;
import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.Permissions;
import es.themin.empires.util.Rank;

public class EmpireManager implements Manager {
	
	private ArrayList<Empire> empires = new ArrayList<Empire>();
	
	private EmpiresDAL myEmpiresDAL;
	
    private File efile;

	public ArrayList<Empire> getEmpires() {
		return empires;
	}

	public EmpireManager(EmpiresDAL empiresDAL) {
		myEmpiresDAL = empiresDAL;
	}
	
	public void load(){
		empires = myEmpiresDAL.loadEmpires();

	}

    public void save() {
        this.save(efile);
    }

	public void save(File datafile) {
		myEmpiresDAL.saveEmpires(empires, datafile);
		
	}

    public  void reload() {
    	empires = myEmpiresDAL.loadEmpires();
        //empiredata = YamlConfiguration.loadConfiguration(efile);
    }
	
	public void addEmpire(Empire empire) {
		this.empires.add(empire);
		
	}
		
	public int nextUnusedEmpireId(){
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











