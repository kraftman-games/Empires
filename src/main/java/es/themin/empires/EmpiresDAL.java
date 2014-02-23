package es.themin.empires;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.jolbox.bonecp.BoneCP;

import es.themin.empires.cores.Core;
import es.themin.empires.cores.CoreFactory;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.EWorld;
import es.themin.empires.util.Empire;
import es.themin.empires.util.testing.newemp;

public class EmpiresDAL {

	BoneCP connectionPool = null;
	
	public EmpiresDAL(BoneCP connectionPool){
		this.connectionPool = connectionPool;
	}

	public EPlayer loadPlayer(UUID myUUID) {
		Connection connection = null;
		try {
			connection = connectionPool.getConnection(); 
			
			if (connection != null){
			
		        PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM `Players` WHERE `PlayerUUID` = ?");
		        stmnt.setString(1, myUUID.toString());
		        
				ResultSet results = stmnt.executeQuery();
				
				 if (results.next()) {
					 EPlayer myEPlayer = new EPlayer(myUUID, results.getString("Name"));
					 myEPlayer.setFirstSeen(results.getLong("FirstSeen"));
					 myEPlayer.setLastSeen(results.getLong("LastSeen"));
					 
					 String empireUUID = results.getString("EmpireUUID");
					 
					 Bukkit.getServer().getLogger().info(results.getString("Name") + " " + results.getLong("FirstSeen") + " " + results.getString("EmpireUUID"));
					 if (empireUUID != null && empireUUID.isEmpty() == false){
						 myEPlayer.setEmpireUUID(UUID.fromString(empireUUID));
					 }
					 
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
					 Empire myEmpire = new Empire(results.getString("Name"),UUID.fromString(results.getString("EmpireUUID")), UUID.fromString(results.getString("OwnerUUID")));
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

	public HashMap<UUID,Core> loadCores() {
		HashMap<UUID,Core> myCores = new HashMap<UUID,Core>();
		Connection connection = null;
		try {
			connection = connectionPool.getConnection(); 
			
			if (connection != null){
			
		        PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM `Cores`");
		        
				ResultSet results = stmnt.executeQuery();
				
				 while (results.next()) {
					 CoreType myCoreType = CoreType.valueOf(results.getString("CoreType"));
					 World myWorld = Bukkit.getWorld(UUID.fromString(results.getString("WorldUUID")));
					 Double X =  results.getDouble("X");
					 Double Y =  results.getDouble("Y");
					 Double Z =  results.getDouble("Z");
					 UUID myCoreUuid = UUID.fromString(results.getString("CoreUUID"));
					 
					 Location myLocation = new Location(myWorld,X,Y,Z);
					 UUID myEmpireUUID = UUID.fromString(results.getString("EmpireUUID"));
					 Core myCore = CoreFactory.CreateCore(myEmpireUUID, myLocation, myCoreType);
					 myCore.setUUID(myCoreUuid);
					 
					myCores.put(myCore.getUUID(), myCore);
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
		return myCores;
	}

	
	
	public void createOrUpdateEmpires(HashMap<UUID,Empire> myEmpires){
		Connection myConnection = null;
		try {
			myConnection = connectionPool.getConnection(); // fetch a connection
			
			if (myConnection != null){
				PreparedStatement stmnt = myConnection.prepareStatement("INSERT INTO `Empires` (`EmpireUUID`,`OwnerUUID`,`Name`,`Created`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE `OwnerUUID`=?,`Name`=? ;");
			       
				for (Empire myEmpire : myEmpires.values()){
					
					stmnt.setString(1, myEmpire.getUUID().toString());
			        stmnt.setString(2, myEmpire.getOwnerUUID().toString());
			        stmnt.setString(3, myEmpire.getName());
			        stmnt.setLong(4, System.currentTimeMillis()/1000);
			        
			        stmnt.setString(5, myEmpire.getOwnerUUID().toString());
			        stmnt.setString(6, myEmpire.getName());
					stmnt.addBatch();
					
				}
				stmnt.executeBatch();
				myConnection.commit();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	
	public void createOrUpdateEmpire(Empire myEmpire){
		HashMap<UUID,Empire> myEmpiresHashMap = new HashMap<UUID,Empire>();
		myEmpiresHashMap.put(myEmpire.getUUID(), myEmpire);
		createOrUpdateEmpires(myEmpiresHashMap);
	}
	
	public void createOrUpdatePlayers(HashMap<UUID,EPlayer> myPlayers){
		Connection myConnection = null;
		try {
			myConnection = connectionPool.getConnection(); // fetch a connection
			
			if (myConnection != null){
				PreparedStatement stmnt = myConnection.prepareStatement("INSERT INTO `Players` (`PlayerUUID`,`FirstSeen`,`LastSeen`,`Name`,`EmpireUUID`) VALUES (?,?,?,?,?) "
																		+" ON DUPLICATE KEY UPDATE `LastSeen`=?,`Name`=?,`EmpireUUID`=?  ;");
			       
				for (EPlayer myPlayer : myPlayers.values()){
					
					String EmpireUUID;
					
					if (myPlayer.getEmpireUUID() == null){
						EmpireUUID = "";
					} else {
						EmpireUUID = myPlayer.getEmpireUUID().toString();
					}
					
			        stmnt.setString(1, myPlayer.getUUID().toString());
			        stmnt.setLong(2, myPlayer.getFirstSeen());
			        stmnt.setLong(3, myPlayer.getLastSeen());
			        stmnt.setString(4, myPlayer.getName());
			        stmnt.setString(5, EmpireUUID);
			        
			        stmnt.setLong(6, myPlayer.getLastSeen());
			        stmnt.setString(7, myPlayer.getName());
			        stmnt.setString(8, EmpireUUID);
					stmnt.addBatch();
					
				}
				stmnt.executeBatch();
				myConnection.commit();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	public void createOrUpdatePlayer(EPlayer myPlayer){
		HashMap<UUID,EPlayer> myPlayers = new HashMap<UUID,EPlayer>();
		myPlayers.put(myPlayer.getUUID(), myPlayer);
		createOrUpdatePlayers(myPlayers);
	}
	
	public void createOrUpdateCores(HashMap<UUID,Core> myCores){
		Connection myConnection = null;
		try {
			myConnection = connectionPool.getConnection(); // fetch a connection
			
			if (myConnection != null){
				PreparedStatement stmnt = myConnection.prepareStatement("INSERT INTO `Cores` SET `CoreUUID`=?,`EmpireUUID`=?,`CoreType`=?,`WorldUUID`=?,`X`=?,`Y`=?,`Z`=?"+
																			" ON DUPLICATE KEY UPDATE `CoreUUID`=?,`EmpireUUID`=?,`CoreType`=?,`WorldUUID`=?,`X`=?,`Y`=?,`Z`=?;");
			       
				for (Core myCore : myCores.values()){
					
			        stmnt.setString(1, myCore.getUUID().toString());
			        stmnt.setString(2, myCore.getEmpireUUID().toString());
			        stmnt.setString(3, myCore.getCoreType().toString());
			        stmnt.setString(4, myCore.getLocation().getWorld().getUID().toString());
			        stmnt.setDouble(5, myCore.getLocation().getX());
			        stmnt.setDouble(6, myCore.getLocation().getY());
			        stmnt.setDouble(7, myCore.getLocation().getZ());
			        
			        stmnt.setString(8, myCore.getUUID().toString());
			        stmnt.setString(9, myCore.getEmpireUUID().toString());
			        stmnt.setString(10, myCore.getCoreType().toString());
			        stmnt.setString(11, myCore.getLocation().getWorld().getUID().toString());
			        stmnt.setDouble(12, myCore.getLocation().getX());
			        stmnt.setDouble(13, myCore.getLocation().getY());
			        stmnt.setDouble(14, myCore.getLocation().getZ());
			        
					stmnt.addBatch();
					
				}
				stmnt.executeBatch();
				myConnection.commit();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	public void createOrUpdateCore(Core myCore){
		HashMap<UUID,Core> myCores = new HashMap<UUID,Core>();
		myCores.put(myCore.getUUID(), myCore);
		createOrUpdateCores(myCores);
	}
	


	public void removeEmpire(Empire empire) {
		// TODO Auto-generated method stub
		
	}




	public void deleteCore(Core myCore) {
		Connection connection = null;
		try {
			connection = connectionPool.getConnection(); 
			
			if (connection != null){
			
		        PreparedStatement stmnt = connection.prepareStatement("DELETE FROM `Cores` where `CoreUUID` = ?");
		        stmnt.setString(1, myCore.getUUID().toString());
				Integer results = stmnt.executeUpdate();
				
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

	public void createOrUpdateSettings(HashMap<String, String> mySettings) {
		Connection myConnection = null;
		try {
			myConnection = connectionPool.getConnection(); // fetch a connection
			
			if (myConnection != null && !mySettings.isEmpty()){
				PreparedStatement stmnt = myConnection.prepareStatement("INSERT INTO `Settings` SET `Key` = ?,`Value` =? ON DUPLICATE KEY UPDATE `Value` =? ");
			       
				for (Map.Entry<String, String> entry : mySettings.entrySet()) {
				    
					stmnt.setString(1,entry.getKey());
			        stmnt.setString(2, entry.getValue());
			        
			        stmnt.setString(3, entry.getValue());
			        stmnt.setString(2, entry.getValue());
				}
				
				stmnt.executeBatch();
				myConnection.commit();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}	
	}

	public HashMap<String, String> loadSettings() {
		HashMap<String,String> mySettings = new HashMap<String,String>();
		Connection connection = null;
		try {
			connection = connectionPool.getConnection(); 
			
			if (connection != null){
			
		        PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM `Settings`");
		        
				ResultSet results = stmnt.executeQuery();
				
				 while (results.next()) {
					 String myKey = results.getString("Key");
					 String myValue = results.getString("Value");
					 mySettings.put(myKey, myValue);
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
		return mySettings;
	}

	public HashMap<UUID, EWorld> loadWorlds() {
		HashMap<UUID,EWorld> myWorlds = new HashMap<UUID,EWorld>();
		Connection connection = null;
		try {
			connection = connectionPool.getConnection(); 
			
			if (connection != null){
			
		        PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM `Worlds`");
		        
				ResultSet results = stmnt.executeQuery();
				
				 while (results.next()) {
					 UUID worldUUID = UUID.fromString(results.getString("WorldUUID"));
					 EWorld myWorld = new EWorld(worldUUID);
					 myWorlds.put(myWorld.getUUID(), myWorld);
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
		return myWorlds;
	}

	public void createOrUpdateWorlds(HashMap<UUID,EWorld> myWorlds){
		Connection myConnection = null;
		try {
			myConnection = connectionPool.getConnection(); // fetch a connection
			
			if (myConnection != null){
				PreparedStatement stmnt = myConnection.prepareStatement("INSERT INTO `Worlds` SET `WorldUUID`=?,`Name`=? ON DUPLICATE KEY UPDATE `Name`=? ;");
			       
				for (EWorld myWorld : myWorlds.values()){
					
					stmnt.setString(1, myWorld.getUUID().toString());
					stmnt.setString(2, myWorld.getName());
					stmnt.setString(3, myWorld.getName());
					
					stmnt.addBatch();
					
				}
				stmnt.executeBatch();
				myConnection.commit();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	
	public void createOrUpdateWorld(EWorld myWorld){
		HashMap<UUID,EWorld> myEmpiresHashMap = new HashMap<UUID,EWorld>();
		myEmpiresHashMap.put(myWorld.getUUID(), myWorld);
		createOrUpdateWorlds(myEmpiresHashMap);
	}
}

//private Boolean executeCoreUpdate(Connection myConnection, Core myCore){
//PreparedStatement stmnt = connection.prepareStatement("INSERT INTO `Cores` (`CoreUUID`,`EmpireUUID`,`CoreTypeID`,`World`,`X`,`Y`,`Z`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE `LastSeen`=?,`Name`=?  ;");
//stmnt.setString(1, myCore.getUUID().toString());
//stmnt.setString(2, myCore.getEmpireUUID());
//stmnt.setString(3, myCore.getCoreType());
//stmnt.setString(4, myCore.getName());
//
//stmnt.setLong(5, myCore.getLastSeen());
//stmnt.setString(6, myCore.getName());
//
//Integer returnsInteger = stmnt.executeUpdate();
//if (returnsInteger == 1){
//	return true;
//}	
//}
//

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