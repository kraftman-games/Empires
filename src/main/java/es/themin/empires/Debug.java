package es.themin.empires;

import java.io.Console;

import org.bukkit.Bukkit;

public class Debug {

	
	public static void Console(String msg){
		Bukkit.getServer().getLogger().info(msg);
	}
}
