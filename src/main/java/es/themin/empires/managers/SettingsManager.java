package es.themin.empires.managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

import es.themin.empires.EmpiresDAL;
import es.themin.empires.empires;
import es.themin.empires.util.testing.newemp;

public class SettingsManager implements IManager {

	HashMap<String, String> settings =null;
	EmpiresDAL myEmpiresDAL = null;
	
	public SettingsManager(EmpiresDAL myDAL, HashMap<String, String> mySettings){
		myEmpiresDAL = myDAL;
		settings = mySettings;
	}
	
	@Override
	public void save() {
		myEmpiresDAL.createOrUpdateSettings(settings);
	}

	@Override
	public void load() {
		settings = myEmpiresDAL.loadSettings();
	}
	
	public String getSetting(String Key){
		return settings.get(Key);
	}
}