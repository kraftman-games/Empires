package es.themin.empires;


import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import com.gmail.favorlock.bonesqlib.Database;
import com.gmail.favorlock.bonesqlib.MySQL;

import es.themin.empires.Listeners.BlockListener;
import es.themin.empires.Listeners.ChatListener;
import es.themin.empires.Listeners.CraftListener;
import es.themin.empires.Listeners.Event_BlockPlace;
import es.themin.empires.Listeners.PlayerListener;
import es.themin.empires.Listeners.WorldListener;
import es.themin.empires.cmds.GlobalCommand;
import es.themin.empires.cmds.GridCommand;
import es.themin.empires.cmds.HomeCommand;
import es.themin.empires.cmds.ally.AllyCommandStem;
import es.themin.empires.cmds.empire.EmpireCommand;
import es.themin.empires.cmds.war.WarCommand;
import es.themin.empires.enums.ConfirmType;
import es.themin.empires.managers.EmpireManager;
import es.themin.empires.managers.IManager;
import es.themin.empires.managers.ManagerFactory;
import es.themin.empires.managers.PlayerManager;
import es.themin.empires.managers.SettingsManager;
import es.themin.empires.managers.WarManager;
import es.themin.empires.managers.WorldManager;
import es.themin.empires.schematics.BaseSchematic;
import es.themin.empires.schematics.Schematic;
import es.themin.empires.schematics.Schematic_Base_20;
import es.themin.empires.schematics.Schematic_Mob_1;
import es.themin.empires.util.MsgManager;
import es.themin.empires.util.UtilManager;
import es.themin.empires.util.testing.Recipes;
import es.themin.empires.util.testing.UtilityTesting;
 
public final class empires extends JavaPlugin {
 
	public static empires plugin;
	public String plprefix = ("[" + ChatColor.LIGHT_PURPLE + "Empires" + ChatColor.WHITE + "] ");
	public static String warprefix = (ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "WAR" + ChatColor.GOLD + "] ");
	
	
	private HashMap<Player, ConfirmType> confirms = new HashMap<Player, ConfirmType>();
	
	public EmpireManager Empires;
	public CoreManager Cores;
	public WorldManager Worlds;
	public WarManager Wars;
	public PlayerManager Players;
	public static ArrayList<Schematic> schematics;
	public SettingsManager settings = new SettingsManager(this);
	public UtilManager utils;
	
	private ManagerFactory ManagerFactory;
	
	ArrayList<IManager> Managers = new ArrayList<IManager>();
	
	@Override
    public void onEnable(){
        plugin = this;
        
        ManagerFactory = new ManagerFactory(this);
        
         Empires = ManagerFactory.CreateEmpireManager();
    	 Cores = ManagerFactory.CreateCoreManager(this); 
    	 Worlds = ManagerFactory.CreateWorldManager(this);
    	 Wars = ManagerFactory.CreateWarManager(this);
    	 Players = ManagerFactory.CreatePlayerManager();
    	 
    	ManagerFactory.loadManagers();
        
        settings = new SettingsManager(this);
        utils = new UtilManager(this);
        
        settings.loadSettings();
		
		Recipes.setupamplifierRecipe();
		
		MsgManager.setPrefix(plprefix);
		
		loadCommands();
		Players.load();
		scheduleBackUps();
		registerEvents();
		loadSchematics();
		
		
    }
	

    @Override
    public void onDisable() {
        
    	ManagerFactory.saveManagers();
		SettingsManager.saveAll();
		Bukkit.getServer().clearRecipes();
		BlockListener.fixBurns();
		final ScoreboardManager sbm = Bukkit.getScoreboardManager();
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.setScoreboard(sbm.getNewScoreboard());
			BarAPI.removeBar(player);
		}
    }
    
    public void registerEvents(){
    	PluginManager pm = this.getServer().getPluginManager();
    	pm.registerEvents(new Event_BlockPlace(this), this);
    	pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new BlockListener(this), this);
		pm.registerEvents(new CraftListener(this), this);
		pm.registerEvents(new ChatListener(this), this);
		pm.registerEvents(new WorldListener(this), this);
    	
    }
    
    public void loadCommands() {
		
		EmpireCommand empire_ce = new EmpireCommand(this);
		getCommand("empire").setExecutor(empire_ce);
		getCommand("e").setExecutor(empire_ce);
		getCommand("emp").setExecutor(empire_ce);
		getCommand("utiltest").setExecutor(new UtilityTesting(this));
		getCommand("all").setExecutor(new GlobalCommand(this));
		getCommand("grid").setExecutor(new GridCommand(this));
		getCommand("war").setExecutor(new WarCommand(this));
		getCommand("base").setExecutor(new HomeCommand(this));
		
		AllyCommandStem ally_ce = new AllyCommandStem(this);
		getCommand("ally").setExecutor(ally_ce);
		ally_ce.setUp();
    }

    private void scheduleBackUps() {
    	String pluginFolder = this.getDataFolder().getAbsolutePath() + "/backups";
		(new File(pluginFolder)).mkdirs();
    	
    	if (getConfig().getString("enable_back_ups").equalsIgnoreCase("true")) {
    		
			try {
				final Long l = Long.parseLong(getConfig().getString("time_between_back_ups_in_hours")) * 3600000;
				getLogger().info("[Empires] Automatic backups enabled");
	        	for (Player player : Bukkit.getServer().getOnlinePlayers()) {
	        		if (player.isOp()) {
	        			player.sendMessage(plprefix + ChatColor.GREEN + "Automatic backups enabled");
	        		}
	        	}
	        	Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

	    			@SuppressWarnings("deprecation")
	    			@Override
	    			public void run() {
	    				
	    				long lastbackup = SettingsManager.getData().getLong("lastbackup");
	    				
	    				if (System.currentTimeMillis() - lastbackup > l) {
	    					getLogger().info("[Empires] Backing Up");
	    					for (Player player : Bukkit.getServer().getOnlinePlayers()) {
	    			    		if (player.isOp()) {
	    			    			player.sendMessage(plprefix + ChatColor.GREEN + "Backing Up");
	    			    		}
	    			    	}
	    					Date date = new Date(System.currentTimeMillis());
	    					Time time = new Time(date.getTime());
	    					StringBuilder str = new StringBuilder();
	    					str.append(time.getHours() + ":" + time.getMinutes() + "-");
	    					str.append(date.getDay() + "-");
	    					str.append(date.getMonth() + "-");
	    					str.append(date.getYear());
	    					String epath = Bukkit.getServer().getPluginManager().getPlugin("Empires").getDataFolder().getAbsolutePath() + "/backups/backup-" + str.toString();
	    					(new File(epath)).mkdirs();
	    					File efile = new File(epath + File.separator + "empiredata.yml");
	    					File cfile = new File(epath + File.separator + "config.yml");
	    					File pfile = new File(epath + File.separator + "playerdata.yml");
	    					Players.save(pfile);
	    					Empires.save(efile);
	    					SettingsManager.saveConfigToFile(cfile);
//	    					SettingsManager.saveWorldDataToFile(wfile);
	    					Players.save();
	    					SettingsManager.getData().set("lastbackup", System.currentTimeMillis());
	    					SettingsManager.saveData();
	    					getLogger().info("[Empires] Backed Up");
	    					for (Player player : Bukkit.getServer().getOnlinePlayers()) {
	    			    		if (player.isOp()) {
	    			    			player.sendMessage(plprefix + ChatColor.GREEN + "Backed Up");
	    			    		}
	    			    	}
	    				}
	    			}
	        		
	        	}, 100L, 12000L);
			}catch(NumberFormatException e) {
				getLogger().severe("[Empires] Time between backups is invalid");
			}
    		
    	}else {
    		getLogger().info("[Empires] Automatic backups disabled");
        	for (Player player : Bukkit.getServer().getOnlinePlayers()) {
        		if (player.isOp()) {
        			player.sendMessage(plprefix + ChatColor.GREEN + "Automatic backups disabled");
        		}
        	}
    	}
    	
    }
    public void loadSchematics() {
    	this.schematics = new ArrayList<Schematic>();
    	this.schematics.add(new BaseSchematic());
    	this.schematics.add(new Schematic_Mob_1());
    	this.schematics.add(new Schematic_Base_20());
    }

	
    
}
