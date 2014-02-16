package es.themin.empires.util.testing;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.entity.Player;

import com.gmail.favorlock.bonesqlib.Database;
import com.gmail.favorlock.bonesqlib.MySQL;

import es.themin.empires.empires;
import es.themin.empires.cmds.SubCommand;

public class tannertest extends SubCommand{

	
	private empires myPlugin;
	
	public tannertest(empires empires) {
		myPlugin = empires;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		

		Database sql = new MySQL(myPlugin.getLogger(), "test", "192.168.5.60", 3306, "empirestest", "empires", "senimeth345");
		if (sql.open()){
			Connection connection;
			try {
				connection = sql.getConnection();
				
				Date now = new Date();
		        String pattern = "yyyy-MM-dd";
		        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		        String mysqlDateString = formatter.format(now);
		        System.out.println("Java's Default Date Format: " + now);
		        System.out.println("Mysql's Default Date Format: " + mysqlDateString);
				
		        String myQueryString = "REPLACE INTO `Players`" +
						" SET `UUID` = '" + player.getUniqueId().toString() + 
						"', `FirstSeen` = '" + mysqlDateString + 
						"', `LastSeen` = '" + mysqlDateString + "';";
				player.sendMessage(myQueryString);
				Statement st = connection.createStatement();
				st.executeUpdate(myQueryString);
				
				
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
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