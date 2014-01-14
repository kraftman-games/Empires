package es.themin.empires.util;


import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

import es.themin.empires.empires;

public class Permissions {
	public static empires plugin;
	public static Permission all = new Permission(plugin.getDescription().getName() + ".*");
	
	public static void setup() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.addPermission(all);
		
	}
	
	public static void shutdown(){
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.removePermission(all);
	}
}
