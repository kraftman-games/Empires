package es.themin.empires;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import es.themin.empires.cmds.HomeCommand;
import es.themin.empires.cmds.ally.AllyCommandStem;
import es.themin.empires.cmds.empire.EmpireCommand;
import es.themin.empires.cmds.war.WarCommand;
import es.themin.empires.enums.ConfirmType;
import es.themin.empires.listeners.BlockListener;
import es.themin.empires.listeners.ChatListener;
import es.themin.empires.listeners.CraftListener;
import es.themin.empires.listeners.Event_BlockPlace;
import es.themin.empires.listeners.PlayerListener;
import es.themin.empires.listeners.WorldListener;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.managers.ManagerFactory;
import es.themin.empires.managers.SettingsManager;
import es.themin.empires.schematics.Schematic;
import es.themin.empires.schematics.base.Schematic_Base_20;
import es.themin.empires.schematics.mob.Schematic_Mob_1;
import es.themin.empires.util.MsgManager;
import es.themin.empires.util.UtilManager;
import es.themin.empires.util.testing.Recipes;
import es.themin.empires.util.testing.UtilityTesting;
 
public final class empires extends JavaPlugin {
 
	
	//stuff we definatly need here
	private ManagerAPI myAPI = null;
	BoneCP connectionPool = null;
	public UtilManager utils;
	
	//stuff that probably shouldnt be here
	
	public String plprefix = ("[" + ChatColor.LIGHT_PURPLE + "Empires" + ChatColor.WHITE + "] ");
	public static String warprefix = (ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "WAR" + ChatColor.GOLD + "] ");
	public static ArrayList<Material> destroyable = new ArrayList<Material>();
	private HashMap<Player, ConfirmType> confirms = new HashMap<Player, ConfirmType>();
	public static ArrayList<Schematic> schematics;

	@Override
    public void onEnable(){
        
        loadMySQL();
        
        myAPI = ManagerFactory.createManagerAPI(connectionPool);
        myAPI.loadManagers();
        loadCommands(myAPI);
        
        utils = new UtilManager(this);
		
		Recipes.setupamplifierRecipe();
		MsgManager.setPrefix(plprefix);
		
		registerEvents();
		loadSchematics();
		
    }
	

    @Override
    public void onDisable() {
    	myAPI.saveManagers();
		Bukkit.getServer().clearRecipes();
		BlockListener.fixBurns();
		final ScoreboardManager sbm = Bukkit.getScoreboardManager();
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.setScoreboard(sbm.getNewScoreboard());
			BarAPI.removeBar(player);
		}
		connectionPool.shutdown();
    }
    
    public void registerEvents(){
    	PluginManager pm = this.getServer().getPluginManager();
    	pm.registerEvents(new Event_BlockPlace(myAPI), this);
    	pm.registerEvents(new PlayerListener(myAPI), this);
		pm.registerEvents(new BlockListener(myAPI), this);
		pm.registerEvents(new CraftListener(myAPI), this);
		pm.registerEvents(new ChatListener(myAPI), this);
		pm.registerEvents(new WorldListener(myAPI), this);
    	
    }
    
    private void loadMySQL(){
    	BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl("jdbc:mysql://192.168.5.60/empirestest"); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
		config.setUsername("empires"); 
		config.setPassword("senimeth345");
		config.setMinConnectionsPerPartition(5);
		config.setMaxConnectionsPerPartition(10);
		config.setPartitionCount(1);
		
		try{
			connectionPool = new BoneCP(config); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void loadCommands(ManagerAPI myAPI) {
		
		EmpireCommand empire_ce = new EmpireCommand(myAPI);
		getCommand("empire").setExecutor(empire_ce);
		getCommand("e").setExecutor(empire_ce);
		getCommand("emp").setExecutor(empire_ce);
		getCommand("utiltest").setExecutor(new UtilityTesting(myAPI));
		getCommand("war").setExecutor(new WarCommand(myAPI));
		getCommand("base").setExecutor(new HomeCommand(myAPI));
		
		getCommand("ally").setExecutor(new AllyCommandStem(myAPI));
    }

  
    public void loadSchematics() {
    	schematics = new ArrayList<Schematic>();
    	schematics.add(new Schematic_Mob_1());
    	schematics.add(new Schematic_Base_20());
    	destroyable.add(Material.STONE);
    	destroyable.add(Material.AIR);
    	destroyable.add(Material.WATER);
    	destroyable.add(Material.DIRT);
    	destroyable.add(Material.GRASS);
    	destroyable.add(Material.WOOD);
    	destroyable.add(Material.LOG);
    	destroyable.add(Material.WOOD_STAIRS);
    	destroyable.add(Material.WOOD_STEP);
    	destroyable.add(Material.COBBLESTONE);
    	destroyable.add(Material.COBBLESTONE_STAIRS);
    	destroyable.add(Material.SMOOTH_BRICK);
    	destroyable.add(Material.SMOOTH_STAIRS);
    }

	
    
}




//private void scheduleBackUps() {
//	String pluginFolder = this.getDataFolder().getAbsolutePath() + "/backups";
//	(new File(pluginFolder)).mkdirs();
//	
//	if (getConfig().getString("enable_back_ups").equalsIgnoreCase("true")) {
//		
//		try {
//			final Long l = Long.parseLong(getConfig().getString("time_between_back_ups_in_hours")) * 3600000;
//			getLogger().info("[Empires] Automatic backups enabled");
//        	for (Player player : Bukkit.getServer().getOnlinePlayers()) {
//        		if (player.isOp()) {
//        			player.sendMessage(plprefix + ChatColor.GREEN + "Automatic backups enabled");
//        		}
//        	}
//        	Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
//
//    			@SuppressWarnings("deprecation")
//    			@Override
//    			public void run() {
//    				
//    				long lastbackup = SettingsManager.getData().getLong("lastbackup");
//    				
//    				if (System.currentTimeMillis() - lastbackup > l) {
//    					getLogger().info("[Empires] Backing Up");
//    					for (Player player : Bukkit.getServer().getOnlinePlayers()) {
//    			    		if (player.isOp()) {
//    			    			player.sendMessage(plprefix + ChatColor.GREEN + "Backing Up");
//    			    		}
//    			    	}
//    					Date date = new Date(System.currentTimeMillis());
//    					Time time = new Time(date.getTime());
//    					StringBuilder str = new StringBuilder();
//    					str.append(time.getHours() + ":" + time.getMinutes() + "-");
//    					str.append(date.getDay() + "-");
//    					str.append(date.getMonth() + "-");
//    					str.append(date.getYear());
//    					String epath = Bukkit.getServer().getPluginManager().getPlugin("Empires").getDataFolder().getAbsolutePath() + "/backups/backup-" + str.toString();
//    					(new File(epath)).mkdirs();
//    					File efile = new File(epath + File.separator + "empiredata.yml");
//    					File cfile = new File(epath + File.separator + "config.yml");
//    					File pfile = new File(epath + File.separator + "playerdata.yml");
////    					Players.save(pfile);
////    					Empires.save(efile);
//    					SettingsManager.saveConfigToFile(cfile);
////    					SettingsManager.saveWorldDataToFile(wfile);
//    					Players.save();
//    					SettingsManager.getData().set("lastbackup", System.currentTimeMillis());
//    					SettingsManager.saveData();
//    					getLogger().info("[Empires] Backed Up");
//    					for (Player player : Bukkit.getServer().getOnlinePlayers()) {
//    			    		if (player.isOp()) {
//    			    			player.sendMessage(plprefix + ChatColor.GREEN + "Backed Up");
//    			    		}
//    			    	}
//    				}
//    			}
//        		
//        	}, 100L, 12000L);
//		}catch(NumberFormatException e) {
//			getLogger().severe("[Empires] Time between backups is invalid");
//		}
//		
//	}else {
//		getLogger().info("[Empires] Automatic backups disabled");
//    	for (Player player : Bukkit.getServer().getOnlinePlayers()) {
//    		if (player.isOp()) {
//    			player.sendMessage(plprefix + ChatColor.GREEN + "Automatic backups disabled");
//    		}
//    	}
//	}
//	
//}
