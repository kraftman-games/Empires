package es.themin.empires.managers;

import java.util.HashMap;

import org.bukkit.Bukkit;

import es.themin.empires.EmpiresDAL;

public class SettingsManager implements IManager {

	HashMap<String, String> settings =null;
	EmpiresDAL myEmpiresDAL = null;
	
	public SettingsManager(EmpiresDAL myDAL, HashMap<String, String> mySettings){
		myEmpiresDAL = myDAL;
		settings = mySettings;
	}
	
	@Override
	public void save() {
		Bukkit.getServer().getLogger().info("saving settings");
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