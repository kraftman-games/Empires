package es.themin.empires;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

import com.jolbox.bonecp.BoneCP;

import es.themin.empires.cores.Core;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class EmpiresDAL {

	File EmpireFile;
	File PlayerFile;
	BoneCP connectionPool = null;
	
	
	public EmpiresDAL(File eFile, File pFile, BoneCP connectionPool){
		EmpireFile = eFile;
		PlayerFile = pFile;
		this.connectionPool = connectionPool;
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



	public HashMap<UUID,Empire> loadEmpires() {
		HashMap<UUID,Empire> myEmpires = new HashMap<UUID,Empire>();
		Connection connection = null;
		try {
			connection = connectionPool.getConnection(); 
			
			if (connection != null){
			
		        PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM `Empires`");
		        
				ResultSet results = stmnt.executeQuery();
				
				 while (results.next()) {
					 Empire myEmpire = new Empire(results.getString("Name"), UUID.fromString(results.getString("OwnerUUID")));
					 myEmpires.put(myEmpire.getUUID(), myEmpire);
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

	public Boolean executeEmpireUpdate(Connection myConnection, Empire myEmpire) throws SQLException{
		PreparedStatement stmnt = myConnection.prepareStatement("INSERT INTO `Empires` (`EmpireUUID`,`OwnerUUID`,`Name`,`Created`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE `OwnerUUID`=?,`Name`=? ;");
        stmnt.setString(1, myEmpire.getUUID().toString());
        stmnt.setString(2, myEmpire.getOwner().toString());
        stmnt.setString(3, myEmpire.getName());
        stmnt.setLong(4, System.currentTimeMillis()/1000);
        
        stmnt.setString(5, myEmpire.getOwner().toString());
        stmnt.setString(6, myEmpire.getName());

		Integer returnsInteger = stmnt.executeUpdate();
		if (returnsInteger == 1){
			return true;
		}
		return false;
	}
	
	public void saveEmpire(Empire myEmpire){
		Connection connection = null;
		try {
			connection = connectionPool.getConnection(); // fetch a connection
			
			if (connection != null){
			
				if (executeEmpireUpdate(connection, myEmpire)){
					connection.commit();
				} else {
					connection.rollback();
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
	
	public void saveEmpires(HashMap<UUID,Empire> myEmpires){
		Connection connection = null;
		try {
			connection = connectionPool.getConnection(); // fetch a connection
			
			if (connection != null){
				Boolean success = false;
				for (Empire myEmpire : myEmpires.values()){
					success = executeEmpireUpdate(connection, myEmpire) ? success : false;
				}
				if (success){
					connection.commit();
				} else {
					connection.rollback();
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

	public void removeEmpire(Empire empire) {
		// TODO Auto-generated method stub
		
	}
	
	
	private Boolean executePlayerUpdate(Connection myConnection, EPlayer myPlayer) throws SQLException{
		
		PreparedStatement stmnt = myConnection.prepareStatement("INSERT INTO `Players` (`PlayerUUID`,`FirstSeen`,`LastSeen`,`Name`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE `LastSeen`=?,`Name`=?  ;");
        stmnt.setString(1, myPlayer.getUUID().toString());
        stmnt.setLong(2, myPlayer.getFirstSeen());
        stmnt.setLong(3, myPlayer.getLastSeen());
        stmnt.setString(4, myPlayer.getName());
        
        stmnt.setLong(5, myPlayer.getLastSeen());
        stmnt.setString(6, myPlayer.getName());

		Integer returnsInteger = stmnt.executeUpdate();
		if (returnsInteger == 1){
			return true;
		}
		return false;
	}

	public Boolean createOrUpdatePlayer(EPlayer myEPlayer) {
		Connection connection = null;
		try {
			connection = connectionPool.getConnection(); 
			
			if (connection != null){
			
		        if (executePlayerUpdate(connection, myEPlayer)) {
		        	connection.commit();
		        	return true;
		        } else {
		        	connection.rollback();
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
	
	public Boolean createOrUpdatePlayers(HashMap<UUID, EPlayer> myEPlayers) {
		Connection connection = null;
		try {
			connection = connectionPool.getConnection(); 
			
			if (connection != null){
			
				Boolean success = true;
				
				for (EPlayer myPlayer : myEPlayers.values()){
					success = executePlayerUpdate(connection, myPlayer) ? success : false;
				}
				
		        if (success) {
		        	connection.commit();
		        	return true;
		        } else {
		        	connection.rollback();
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
	
	public Boolean updateCore(Core myCore){
		Connection connection = null;
		try {
			
			connection = connectionPool.getConnection(); // fetch a connection
			
			if (connection != null){
			
//		        PreparedStatement stmnt = connection.prepareStatement("INSERT INTO `Cores` (`CoreUUID`,`EmpireUUID`,`CoreTypeID`,`World`,`X`,`Y`,`Z`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE `LastSeen`=?,`Name`=?  ;");
//		        stmnt.setString(1, myCore.getUUID().toString());
//		        stmnt.setString(2, myCore.getEmpireUUID());
//		        stmnt.setString(3, myCore.getCoreType());
//		        stmnt.setString(4, myCore.getName());
//		        
//		        stmnt.setLong(5, myCore.getLastSeen());
//		        stmnt.setString(6, myCore.getName());
//
//				Integer returnsInteger = stmnt.executeUpdate();
//				if (returnsInteger == 1){
//					return true;
//				}
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

	public void saveCores(HashMap<UUID, Core> cores) {
		// TODO Auto-generated method stub
		
	}
}
//
//public void savePlayers(HashMap<UUID, EPlayer> players, File myFile){
//	YamlConfiguration playerdata = YamlConfiguration.loadConfiguration(myFile);
//	
//	for (EPlayer myPlayer : players.values()) {
//		if (myPlayer.getEmpire() != null){
//			playerdata.set(myPlayer.getUUID() + ".empire", myPlayer.getEmpire().getUUID());
//		}
//		playerdata.set(myPlayer.getUUID() + ".name", myPlayer.getName());
//	}
//	
//    try {
//            playerdata.save(myFile);
//    }
//    catch (IOException e) {
//            //Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save playerdata.yml!");
//    }
//}


//public void savePlayer(EPlayer myEPlayer) {
//Connection connection = null;
//try {
//	
//	connection = connectionPool.getConnection(); // fetch a connection
//	
//	if (connection != null){
//	
//        PreparedStatement stmnt = connection.prepareStatement("UPDATE `Players` SET `LastSeen`=?,`Name`=? WHERE `UUID` = ?;");
//        
//        stmnt.setLong(1, myEPlayer.getLastSeen());
//        stmnt.setString(2, myEPlayer.getName());
//        stmnt.setString(3, myEPlayer.getUUID().toString());
//
//		Integer returnsInteger = stmnt.executeUpdate();
//		if (returnsInteger == 1){
//		}
//	}
//	
//} catch (SQLException e) {
//	e.printStackTrace();
//} finally {
//	if (connection != null) {
//		try {
//			connection.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//}
//}

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