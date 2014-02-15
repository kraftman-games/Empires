package es.themin.empires;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import es.themin.empires.cores.Core;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.Rank;

public class EmpiresDAL {


	public void savePlayers(HashMap<UUID, EPlayer> players){
		//call the below with a default file.
		
	}
	
	public void savePlayers(HashMap<UUID, EPlayer> players, File datafile){
		YamlConfiguration playerdata = YamlConfiguration.loadConfiguration(datafile);
		
		for (EPlayer myPlayer : players.values()) {
    		if (myPlayer.getEmpire() != null){
    			playerdata.set(myPlayer.getUUID() + ".empire", myPlayer.getEmpire().getID());
    		}
    		playerdata.set(myPlayer.getUUID() + ".name", myPlayer.getName());
    	}
    	
        try {
                playerdata.save(datafile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save playerdata.yml!");
        }
		
	}
	
	public HashMap<UUID, EPlayer> loadPlayers(){
		
		HashMap<UUID, EPlayer> myPlayers = new HashMap<UUID, EPlayer>();
		
		return myPlayers;
	}

	public EPlayer loadPlayer(UUID uniqueId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void savePlayer(EPlayer myEPlayer) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Empire> loadEmpires() {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveEmpires(ArrayList<Empire> empires, File datafile) {
		YamlConfiguration empiredata = YamlConfiguration.loadConfiguration(datafile);
		
		List<String> list = new ArrayList<String>();
		for (Empire empire : empires) {
			StringBuilder str = new StringBuilder();
			str.append(empire.getID() + ":");
			str.append(empire.getName() + ":");
			str.append(empire.getOwner());
			list.add(str.toString());
			empiredata.set(str.toString() + ".id", empire.getID());
			empiredata.set(str.toString() + ".name", empire.getName());
			List<String> playerList = new ArrayList<String>();
			for (EPlayer player : empire.getPlayers().values()) {
				//FixedMetadataValue playerEmpire = new FixedMetadataValue (myPlugin, this.getId());
				playerList.add(player.getName());
				
			}
			empiredata.set(str.toString() + ".players", playerList);
			List<String> list3 = new ArrayList<String>();
			for (Core core : empire.getCores()) {
				StringBuilder str2 = new StringBuilder();
				str2.append(core.getId() + ":");
				str2.append(core.getType().toString() + ":");
				str2.append(core.getLocation().getWorld().getName() + ":");
				str2.append(core.getLocation().getBlockX() + ":");
				str2.append(core.getLocation().getBlockY() + ":");
				str2.append(core.getLocation().getBlockZ() + ":");
				str2.append(core.getLevel() + ":");
				str2.append(core.getEmpire().getID() + ":");
				str2.append(core.getEmpire().getName());
				list3.add(str2.toString());
			}
			empiredata.set(str.toString() + ".cores", list3);
			List<String> list4 = new ArrayList<String>();
			for (Rank rank : empire.getRanks()) {
				StringBuilder str3 = new StringBuilder();
				str3.append(rank.getWeight() + ":");
				str3.append(rank.getName() + ":");
				str3.append(rank.getPreifx());
				list4.add(str3.toString());
				List<String> list5 = new ArrayList<String>();
				for (String p : rank.getPlayers()) {
					list5.add(p);
				}
				empiredata.set(str.toString() + ".rank." + str3.toString() + ".players", list5);
				List<String> list6 = new ArrayList<String>();
				for (EmpirePermission ep : rank.getPermissions()) {
					list6.add(ep.toString());
				}
				empiredata.set(str.toString() + ".rank." + str3.toString() + ".permissions", list6);
			}
			empiredata.set(str.toString() + ".ranks", list4);
		}
		empiredata.set("empires", list);
		
		try {
            empiredata.save(datafile);
	    }
	    catch (IOException e) {
	            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save empiredata.yml!");
		    }
		
	}

	public void removeEmpire(Empire empire) {
		// TODO Auto-generated method stub
		
	}
}

//private  File createFile(String fileName){
//
////File myFile = new File(myPlugin.getDataFolder() + File.separator + fileName);
////
////if (!myFile.exists()) {
////        try {
////        	myFile.createNewFile();
////				myPlugin.getLogger().info("[Empires] "+fileName+" not found, making you one");
////        }
////        catch (IOException e) {
////                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create "+fileName);
////        }
////}
//
//return myFile;
//
//}
//efile = createFile("empiredata.yml");

	//empiredata = YamlConfiguration.loadConfiguration(efile);