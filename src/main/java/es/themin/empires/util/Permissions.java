package es.themin.empires.util;


import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

import es.themin.empires.empires;
import es.themin.empires.enums.EmpirePermission;

public class Permissions {
	public static empires plugin;
	public static Permission all = new Permission(plugin.getDescription().getName() + ".*");
	
	public static void setup() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.addPermission(all);
		
	}
	
	public static EmpirePermission getPermission(String myPermission){
		
		myPermission = myPermission.toLowerCase();
		
		return EmpirePermission.valueOf(myPermission);
		
		
	}
	
	public static void shutdown(){
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.removePermission(all);
	}
}
