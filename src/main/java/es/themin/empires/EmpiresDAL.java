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
		ArrayList<Empire> myEmpires = new ArrayList<Empire>();
		Connection connection = null;
		try {
			connection = connectionPool.getConnection(); 
			
			if (connection != null){
			
		        PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM `Empires`");
		        
				ResultSet results = stmnt.executeQuery();
				
				 while (results.next()) {
					 Empire myEmpire = new Empire(results.getString("Name"), UUID.fromString(results.getString("OwnerUUID")));
					 myEmpires.add(myEmpire);
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
		return myEmpires;
	}

	
	public void saveEmpire(Empire myEmpire){
		Connection connection = null;
		try {
			
			connection = connectionPool.getConnection(); // fetch a connection
			
			if (connection != null){
			
		        PreparedStatement stmnt = connection.prepareStatement("INSERT INTO `Empires` (`UUID`,`OwnerUUID`,`Name`,`Created`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE `OwnerUUID`=?,`Name`=? ;");
		        stmnt.setString(1, myEmpire.getID().toString());
		        stmnt.setString(2, myEmpire.getOwner().toString());
		        stmnt.setString(3, myEmpire.getName());
		        stmnt.setLong(4, System.currentTimeMillis()/1000);
		        
		        stmnt.setString(5, myEmpire.getOwner().toString());
		        stmnt.setString(6, myEmpire.getName());

				Integer returnsInteger = stmnt.executeUpdate();
				if (returnsInteger == 1){
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
	}
	
	public void saveEmpires(ArrayList<Empire> myEmpires){
		//for now just do them individually, later it will be better to build a statement
		for(Empire myEmpire : myEmpires){
			saveEmpire(myEmpire);
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