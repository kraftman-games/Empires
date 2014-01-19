package es.themin.empires;


import java.io.File;
import java.sql.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import es.themin.empires.Listeners.BlockListener;
import es.themin.empires.Listeners.Craft;
import es.themin.empires.Listeners.Login_Quit;
import es.themin.empires.util.Empire;
import es.themin.empires.util.SettingsManager;
import es.themin.empires.util.UtilManager;
import es.themin.empires.util.testing.Recipes;
import es.themin.empires.util.testing.UtilityTesting;
 
public final class empires extends JavaPlugin {
 
	public static empires plugin;
	public boolean myTestBoolFunction(){
		boolean myBool = true;
		
		return myBool;
		
	}
	public static String plprefix = ("[" + ChatColor.LIGHT_PURPLE + "Empires" + ChatColor.WHITE + "] ");
	
	@Override
    public void onEnable(){
        PluginManager pm = this.getServer().getPluginManager();
        //PluginDescriptionFile desc = this.getDescription();
		getLogger().info("onEnable has been invoked!");
		SettingsManager.getInstance().setup(this);
		getCommands();
		pm.registerEvents(new Login_Quit(this), this);
		pm.registerEvents(new BlockListener(this), this);
		pm.registerEvents(new Craft(this), this);
		UtilManager.loadEmpires();
		UtilityTesting.setUp();
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
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	if(cmd.getName().equalsIgnoreCase("basic")){ // If the player typed /basic then do the following...
    		// doSomething
    		getLogger().info("your crap plugin works!");
    		return true;
    	} //If this has happened the function will return true. 
            // If this hasn't happened the a value of false will be returned.
    	return false; 
    }
    
    public void getCommands() {
    	UtilityTesting utiltest = new UtilityTesting();
		UtilityTesting.setUp();
		getCommand("utiltest").setExecutor(utiltest);
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
				if (System.currentTimeMillis() - lastbackup > 604800000) {
					getLogger().info("[Empires] Backing Up");
					for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			    		if (player.isOp()) {
			    			player.sendMessage(plprefix + ChatColor.GREEN + "Backing Up");
			    		}
			    	}
					Date date = new Date(System.currentTimeMillis());
					StringBuilder str = new StringBuilder();
					str.append(date.getDay() + "-");
					str.append(date.getMonth() + "-");
					str.append(date.getYear());
					File efile = new File(plugin.getDataFolder() + "/backups/backup-" + str.toString() + File.separator + "empiredata.yml");
					SettingsManager.getInstance().saveEmpireDataToFile(efile);
					SettingsManager.getInstance().getData().set("lastbackup", System.currentTimeMillis());
					getLogger().info("[Empires] Backed Up");
					for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			    		if (player.isOp()) {
			    			player.sendMessage(plprefix + ChatColor.GREEN + "Backed Up");
			    		}
			    	}
				}
			}
    		
    	}, 100L, 12000L);
    	
    }
    
}
