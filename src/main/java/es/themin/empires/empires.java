package es.themin.empires;


import java.io.File;
import java.sql.Date;
import java.sql.Time;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import es.themin.empires.Listeners.BlockListener;
import es.themin.empires.Listeners.ChatListener;
import es.themin.empires.Listeners.CraftListener;
import es.themin.empires.Listeners.PlayerListener;
import es.themin.empires.Listeners.WorldListener;
import es.themin.empires.cmds.GlobalCommand;
import es.themin.empires.cmds.GridCommand;
import es.themin.empires.cmds.HomeCommand;
import es.themin.empires.cmds.empire.empire;
import es.themin.empires.cmds.war.WarCommand;
import es.themin.empires.cores.CoreSchematic;
import es.themin.empires.util.Empire;
import es.themin.empires.util.SettingsManager;
import es.themin.empires.util.UtilManager;
import es.themin.empires.util.testing.Recipes;
import es.themin.empires.util.testing.CommandManager;
 
public final class empires extends JavaPlugin {
 
	public static empires plugin;
	public static String plprefix = ("[" + ChatColor.LIGHT_PURPLE + "Empires" + ChatColor.WHITE + "] ");
	public static String warprefix = (ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "WAR" + ChatColor.GOLD + "] ");
	
	@Override
    public void onEnable(){
        
		SettingsManager.getInstance().setup(this);
		loadCommands();
		
		UtilManager.loadEmpires();
		loadPlayers();
		
		String pluginFolder = this.getDataFolder().getAbsolutePath() + "/backups";
		(new File(pluginFolder)).mkdirs();
		Recipes.setupamplifierRecipe();
		scheduleBackUps();
		
    }
 
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
		UtilManager.saveEmpires();
		SettingsManager.getInstance().saveAll();
		savePlayers();
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
    	pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new BlockListener(this), this);
		pm.registerEvents(new CraftListener(this), this);
		pm.registerEvents(new ChatListener(this), this);
		pm.registerEvents(new WorldListener(this), this);
    	
    }
    
    public void loadCommands() {
    	CommandManager utiltest = new CommandManager();
		CommandManager.addSubCommands();
		getCommand("utiltest").setExecutor(utiltest);
		empire empire_ce = new empire();
		WarCommand.setUp();
		getCommand("empire").setExecutor(empire_ce);
		getCommand("e").setExecutor(empire_ce);
		getCommand("emp").setExecutor(empire_ce);
		getCommand("all").setExecutor(new GlobalCommand(this));
		getCommand("grid").setExecutor(new GridCommand(this));
		getCommand("war").setExecutor(new WarCommand());
		getCommand("base").setExecutor(new HomeCommand(this));
    }
    public void savePlayers(){
    	for (String playername : UtilManager.empireplayers.keySet()) {
    		if (!(UtilManager.empireplayers.isEmpty()) && UtilManager.empireplayers.get(playername) != null) {
    			SettingsManager.getInstance().getPlayerData().set(playername + ".empire", UtilManager.empireplayers.get(playername).getId());
    		}
    	}
    	SettingsManager.getInstance().savePlayerData();
    }
    public void loadPlayers(){
    	for (Player player : Bukkit.getOnlinePlayers()) {
    		String name = player.getName();
    		if (SettingsManager.getInstance().getPlayerData().get(name + ".empire") != null) {
    			Empire empire = UtilManager.getEmpireWithId(SettingsManager.getInstance().getPlayerData().getInt(name + ".empire"));
    			UtilManager.empireplayers.put(name, empire);
    			player.sendMessage(plprefix + ChatColor.GREEN + "You were found to be in an empire");
    		}
    	}
    }
    private void scheduleBackUps() {
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
	    				
	    				long lastbackup = SettingsManager.getInstance().getData().getLong("lastbackup");
	    				
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
	    					File wfile = new File(epath + File.separator + "worldconfig.yml");
	    					File pfile = new File(epath + File.separator + "playerdata.yml");
	    					savePlayers();
	    					SettingsManager.getInstance().saveEmpireDataToFile(efile);
	    					SettingsManager.getInstance().saveConfigToFile(cfile);
	    					SettingsManager.getInstance().saveWorldDataToFile(wfile);
	    					SettingsManager.getInstance().savePlayerDataToFile(pfile);
	    					SettingsManager.getInstance().getData().set("lastbackup", System.currentTimeMillis());
	    					SettingsManager.getInstance().saveData();
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
    
}
