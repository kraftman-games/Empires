package es.themin.empires;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import es.themin.empires.cores.Core;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.Rank;
import es.themin.empires.util.testing.newemp;

public class EmpiresDAL {

	File EmpireFile;
	File PlayerFile;
	BoneCP connectionPool = null;
	
	
	public EmpiresDAL(File eFile, File pFile, BoneCP connectionPool){
		EmpireFile = eFile;
		PlayerFile = pFile;
		this.connectionPool = connectionPool;
	}
	
	public void savePlayers(HashMap<UUID, EPlayer> players){
		savePlayers(players, PlayerFile);
	}
	
	public void savePlayers(HashMap<UUID, EPlayer> players, File myFile){
		YamlConfiguration playerdata = YamlConfiguration.loadConfiguration(myFile);
		
		for (EPlayer myPlayer : players.values()) {
    		if (myPlayer.getEmpire() != null){
    			playerdata.set(myPlayer.getUUID() + ".empire", myPlayer.getEmpire().getID());
    		}
    		playerdata.set(myPlayer.getUUID() + ".name", myPlayer.getName());
    	}
    	
        try {
                playerdata.save(myFile);
        }
        catch (IOException e) {
                //Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save playerdata.yml!");
        }
	}
	
	public HashMap<UUID, EPlayer> loadPlayers(){
		
		HashMap<UUID, EPlayer> myPlayers = new HashMap<UUID, EPlayer>();
		
		return myPlayers;
	}

	public EPlayer loadPlayer(UUID myUUID) {
		Connection connection = null;
		try {
			connection = connectionPool.getConnection(); 
			
			if (connection != null){
			
		        PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM `Players` WHERE `UUID` = ?");
		        stmnt.setString(1, myUUID.toString());
		        
				ResultSet results = stmnt.executeQuery();
				
				 if (results.next()) {
					 EPlayer myEPlayer = new EPlayer(myUUID, results.getString("Name"));
					 myEPlayer.setFirstSeen(results.getLong("FirstSeen"));
					 myEPlayer.setLastSeen(results.getLong("LastSeen"));
					 return myEPlayer;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

//	public void savePlayer(EPlayer myEPlayer) {
//		Connection connection = null;
//		try {
//			
//			connection = connectionPool.getConnection(); // fetch a connection
//			
//			if (connection != null){
//			
//		        PreparedStatement stmnt = connection.prepareStatement("UPDATE `Players` SET `LastSeen`=?,`Name`=? WHERE `UUID` = ?;");
//		        
//		        stmnt.setLong(1, myEPlayer.getLastSeen());
//		        stmnt.setString(2, myEPlayer.getName());
//		        stmnt.setString(3, myEPlayer.getUUID().toString());
//
//				Integer returnsInteger = stmnt.executeUpdate();
//				if (returnsInteger == 1){
//				}
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (connection != null) {
//				try {
//					connection.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

	public ArrayList<Empire> loadEmpires() {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveEmpires(ArrayList<Empire> empires){
		saveEmpires(empires, EmpireFile);
	}
	
	public void saveEmpires(ArrayList<Empire> empires, File myFile) {
		
		
		YamlConfiguration empiredata = YamlConfiguration.loadConfiguration(myFile);
		
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
            empiredata.save(myFile);
	    }
	    catch (IOException e) {
	            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save empiredata.yml!");
		    }
		
	}

	public void removeEmpire(Empire empire) {
		// TODO Auto-generated method stub
		
	}

	public Boolean updatePlayer(EPlayer myEPlayer) {
		Connection connection = null;
		try {
			
			connection = connectionPool.getConnection(); // fetch a connection
			
			if (connection != null){
			
		        PreparedStatement stmnt = connection.prepareStatement("INSERT INTO `Players` (`UUID`,`FirstSeen`,`LastSeen`,`Name`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE `LastSeen`=?,`Name`=?  ;");
		        stmnt.setString(1, myEPlayer.getUUID().toString());
		        stmnt.setLong(2, myEPlayer.getFirstSeen());
		        stmnt.setLong(3, myEPlayer.getLastSeen());
		        stmnt.setString(4, myEPlayer.getName());
		        
		        stmnt.setLong(5, myEPlayer.getLastSeen());
		        stmnt.setString(6, myEPlayer.getName());

				Integer returnsInteger = stmnt.executeUpdate();
				if (returnsInteger == 1){
					return true;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
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