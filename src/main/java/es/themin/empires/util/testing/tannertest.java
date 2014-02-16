package es.themin.empires.util.testing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.entity.Player;





import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import es.themin.empires.empires;
import es.themin.empires.cmds.SubCommand;

public class tannertest extends SubCommand{

	
	private empires myPlugin;
	
	public tannertest(empires empires) {
		myPlugin = empires;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		

		BoneCP connectionPool = null;
		Connection connection = null;
 
		try {
			// load the database driver (make sure this is in your classpath!)
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			// setup the connection pool
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl("jdbc:mysql://192.168.5.60/empirestest"); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
			config.setUsername("empires"); 
			config.setPassword("senimeth345");
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(1);
			connectionPool = new BoneCP(config); // setup the connection pool
			
			connection = connectionPool.getConnection(); // fetch a connection
			
			if (connection != null){
				System.out.println("Connection successful!");
				Statement stmt = connection.createStatement();
				
				Date now = new Date();
				String pattern = "yyyy-MM-dd";
		        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		        String mysqlDateString = formatter.format(now);
				
				String myQueryString = "INSERT INTO `Players` (`UUID`) VALUES ('"+ player.getUniqueId().toString()+"');";
					//" SET `UUID` = '" + player.getUniqueId().toString() + 
					//"', `FirstSeen` = '" + mysqlDateString + 
					//"', `LastSeen` = '" + mysqlDateString + "';";
				player.sendMessage(myQueryString);
				ResultSet rs = stmt.executeQuery(myQueryString); // do something with the connection.
				//player.sendMessage(rs.getString(0));
			}
			connectionPool.shutdown(); // shutdown connection pool.
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
		
		
//		Database sql = new MySQL(myPlugin.getLogger(), "test", "192.168.5.60", 3306, "empirestest", "empires", "senimeth345");
//		if (sql.open()){
//			Connection connection;
//			try {
//				connection = sql.getConnection();
//				
//				Date now = new Date();
//		        String pattern = "yyyy-MM-dd";
//		        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//		        String mysqlDateString = formatter.format(now);
//		        System.out.println("Java's Default Date Format: " + now);
//		        System.out.println("Mysql's Default Date Format: " + mysqlDateString);
//				
////		        String myQueryString = "REPLACE INTO `Players`" +
////						" SET `UUID` = '" + player.getUniqueId().toString() + 
////						"', `FirstSeen` = '" + mysqlDateString + 
////						"', `LastSeen` = '" + mysqlDateString + "';";
//		        String myQueryString = "replace INTO Players  set `UUID` = 'testr', `FirstSeen` = '2014-2-15', `LastSeen` = '2014-2-15';";
//		        connection.
//				player.sendMessage(myQueryString);
//				Statement st = connection.createStatement();
//				st.executeUpdate(myQueryString);
//				
//				
//				connection.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
		
		return true;

	}

	@Override
	public String name() {
		return "tannertest";
	}

	@Override
	public String info() {
		return "who knows";
	}

	@Override
	public String[] aliases() {
		return new String[] {"tt"};
	}
	
}


//<dependency>
//<groupId>gmail.favorlock</groupId>
//<artifactId>BoneSQLib</artifactId>
//<version>0.8.0.RELEASE</version>
//<scope>system</scope>
//<systemPath>${basedir}/src/main/resources/BoneSQLib-0.0.1.jar</systemPath>
//</dependency>


//int x = Integer.parseInt(args[1]);
//int y = Integer.parseInt(args[2]);
//
//int z = Integer.parseInt(args[2]);
//player.sendMessage("x: "+ x + "y: " + y + "z: "+z);
//
//
//
//Location newLoc = new Location(player.getWorld(), x, y, z);
//
//Chunk myChunk = newLoc.getBlock().getChunk();
//
//myChunk.load(true);
//
//player.sendMessage(myChunk.isLoaded()+ " block type: "+newLoc.getBlock().getType());


//Empire empire = UtilManager.empireplayers.get(player.getName());
//Core myCore = new Core(UtilManager.nextUnusedCoreId(), CoreType.GRIEF, player.getLocation(), 1, empire);
//empire.addCore(myCore);
//myCore.Save();
//player.sendMessage("testing grief block");
//return false;
//

//ItemStack myItem = new ItemStack(Material.FLINT);
//ItemMeta myMeta = myItem.getItemMeta();
//myMeta.setDisplayName("Core Shard");
//
//ArrayList<String> myLore = new ArrayList<String>();
//myLore.add(ChatColor.GREEN + "A faint glow eminates from within");
//
//myMeta.setLore(myLore);
//
//myItem.setItemMeta(myMeta);
//
//player.getInventory().addItem(myItem);
//player.sendMessage( ChatColor.RED + "You won teh shardz!");
//return false;