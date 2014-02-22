package es.themin.empires.managers;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;

import es.themin.empires.empires;
import es.themin.empires.wars.War;

public class WarManager implements IManager {
	
	
	private ArrayList<War> wars = new ArrayList<War>();
	
    static YamlConfiguration wardata;
    static File warfile;

	public WarManager(empires plugin) {

	}
	
	public ArrayList<War> getWars() {
		return wars;
	}

	public  void save() {
		
	}
	
	//CANNOT BE RUN BEFORE EMPIRES ARE LOADED
	public  void load() {
		
	}
	
	
}
