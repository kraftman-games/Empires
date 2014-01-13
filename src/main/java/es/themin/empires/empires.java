package es.themin.empires;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
 
public final class empires extends JavaPlugin {
 
	public boolean myTestBoolFunction(){
		boolean myBool = true;
		
		return myBool;
		
	}
	
	@Override
    public void onEnable(){
        PluginManager pm = this.getServer().getPluginManager();
        PluginDescriptionFile desc = this.getDescription();
		getLogger().info("onEnable has been invoked!");
    }
 
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
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
}
