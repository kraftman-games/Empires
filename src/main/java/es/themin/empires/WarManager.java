package es.themin.empires;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import es.themin.empires.enums.BattleType;
import es.themin.empires.util.Empire;
import es.themin.empires.wars.Battle;
import es.themin.empires.wars.War;
import es.themin.empires.wars.Battle.BattleTeam;

public class WarManager implements Manager {
	
	private empires myPlugin;
	private ArrayList<War> wars = new ArrayList<War>();
	
    static YamlConfiguration wardata;
    static File warfile;

	public WarManager(empires plugin) {
		myPlugin = plugin;
	}
	
	public ArrayList<War> getWars() {
		return wars;
	}

	public  void save() {
		Bukkit.getServer().getPluginManager().getPlugin("Empires").getLogger().info("[Empires] Saving Wars ...");
		List<String> listofwars = new ArrayList<String>();
		for (War war : this.wars) {
			String idforsaving = war.getEmpire1().getName() + ":" + war.getEmpire2().getName() + ":" + war.getStart();
			listofwars.add(idforsaving);
			List<String> empire1allies = new ArrayList<String>();
			for (Empire empire : war.getAllEmpiresOnTeam1()) {
				empire1allies.add(empire.getName());
			}
			wardata.set(idforsaving, empire1allies);
			List<String> empire2allies = new ArrayList<String>();
			for (Empire empire : war.getAllEmpiresOnTeam2()) {
				empire2allies.add(empire.getName());
			}
			wardata.set(idforsaving, empire2allies);
			if (war.getVictor() != null)wardata.set(idforsaving + ".victor", war.getVictor().getName());
			if (war.getEnd() != 0)wardata.set( idforsaving + ".end", war.getEnd());
			wardata.set(idforsaving + ".empire1wins", war.getEmpire1Wins());
			wardata.set(idforsaving + ".empire2wins", war.getEmpire2Wins());
			wardata.set(idforsaving + ".ongoing", war.isOnGoing());
			wardata.set(idforsaving + ".team1percent", war.getTeam1Percent());
			List<String> listofbattles = new ArrayList<String>();
			for (Battle battle : war.getBattle()) {
				String Bidforsaving = battle.getEmpire1().getName() + ":" + battle.getEmpire2().getName() + ":" + battle.getStart();
				listofbattles.add(Bidforsaving);
				wardata.set(idforsaving + ".battles." + Bidforsaving + ".end", battle.getEnd());
				List<String> bempire1allies = new ArrayList<String>();
				for (Empire empire : battle.getAllEmpiresOnTeam1()) {
					bempire1allies.add(empire.getName());
				}
				wardata.set(idforsaving + ".battles." + Bidforsaving + ".empire1allies", bempire1allies);
				List<String> bempire2allies = new ArrayList<String>();
				for (Empire empire : battle.getAllEmpiresOnTeam2()) {
					bempire2allies.add(empire.getName());
				}
				wardata.set(idforsaving + ".battles." + Bidforsaving + ".empire2allies", bempire2allies);
				if (battle.getVictor() !=null ) wardata.set(idforsaving + ".battles." + Bidforsaving + ".victor", battle.getVictor().getName());
				wardata.set(idforsaving + ".battles." + Bidforsaving + ".type", battle.getType().toString());
				wardata.set(idforsaving + ".battles." + Bidforsaving + ".ongoing", battle.isOnGoing());
				wardata.set(idforsaving + ".battles." + Bidforsaving + ".team1points", battle.getTeam1Points());
				wardata.set(idforsaving + ".battles." + Bidforsaving + ".team2points", battle.getTeam2Points());
				wardata.set(idforsaving + ".battles." + Bidforsaving + ".endsintie", battle.endedInATie());
				wardata.set(idforsaving + ".battles." + Bidforsaving + ".killsforwin", battle.getKillsForWin());
				wardata.set(idforsaving + ".battles." + Bidforsaving + ".damageforwin", battle.getDamageForWin());
				wardata.set(idforsaving + ".battles." + Bidforsaving + ".attacker", battle.getAttackingTeam().toString());
			}
			wardata.set(idforsaving + ".battles.list", listofbattles);
		}
		wardata.set("wars", listofwars);
		Bukkit.getServer().getPluginManager().getPlugin("Empires").getLogger().info("[Empires] ... Wars Saved");
		
		
		try {
            wardata.save(warfile);
	    }
	    catch (IOException e) {
	            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save wars.yml!");
	    }
	}
	
	//CANNOT BE RUN BEFORE EMPIRES ARE LOADED
	public  void load() {
		
		warfile = createFile("wars.yml");
        
        wardata = YamlConfiguration.loadConfiguration(warfile);
		
		List<String> listofwars = wardata.getStringList("wars");
		for (String warname : listofwars) {
			String[] warnamewords = warname.split(":");
			War war = new War(myPlugin, myPlugin.Empires.getEmpireWithName(warnamewords[0]), myPlugin.Empires.getEmpireWithName(warnamewords[0]));
			war.setStart(Long.parseLong(warnamewords[2]));
			List<String> empire1allies = wardata.getStringList(warname + ".empire1allies");
			for (String empire1ally : empire1allies) {
				war.addEmpireToTeam1(myPlugin.Empires.getEmpireWithName(empire1ally));
			}
			List<String> empire2allies = wardata.getStringList(warname + ".empire2allies");
			for (String empire2ally : empire2allies) {
				war.addEmpireToTeam2(myPlugin.Empires.getEmpireWithName(empire2ally));
			}
			if (wardata.getString(warname + ".victor") != null) war.setVictor(myPlugin.Empires.getEmpireWithName(wardata.getString(warname + ".victor")));
			war.setEnd(wardata.getLong(warname + ".end"));
			war.setEmpire1Wins(wardata.getInt(warname + ".empire1wins"));
			war.setEmpire2Wins(wardata.getInt(warname + ".empire2wins"));
			war.setOnGoing(wardata.getBoolean(warname + "ongoing"));
			war.setTeam1Percent(wardata.getLong(warname + ".team1percent"));
			List<String> battlenames = wardata.getStringList(warname + ".battles.list");
			for (String battlename : battlenames) {
				String[] battlenamewords = battlename.split(":");
				BattleType type = null;
				if (wardata.getString(warname + ".battles." + battlename + ".type").equalsIgnoreCase("DEATHMATCH")) type = BattleType.DEATHMATCH;
				if (wardata.getString(warname + ".battles." + battlename + ".type").equalsIgnoreCase("OBLITERATION")) type = BattleType.OBLITERATION;
				BattleTeam attacker = null;
				if (wardata.getString(warname + ".battles." + battlename + ".attacker").equalsIgnoreCase("team1")) attacker = BattleTeam.team1;
				if (wardata.getString(warname + ".battles." + battlename + ".attacker").equalsIgnoreCase("team2")) attacker = BattleTeam.team2;
				Battle battle = new Battle(myPlugin.Empires.getEmpireWithName(battlenamewords[0]), myPlugin.Empires.getEmpireWithName(battlenamewords[1]), war, type, attacker);
				//TODO add the battles loading.
			
			}
		}
	}
	
	
	   private  File createFile(String fileName){
	    	
	    	File myFile = new File(myPlugin.getDataFolder(), fileName);
	        
	        if (!myFile.exists()) {
	                try {
	                	myFile.createNewFile();
	        				myPlugin.getLogger().info("[Empires] "+fileName+" not found, making you one");
	                }
	                catch (IOException e) {
	                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create "+fileName);
	                }
	        }
	        
	        return myFile;
	    	
	    }
	   
	    public static FileConfiguration getWarData() {
	        return wardata;
	    }

	    
	    public static void saveWarDataToFile(File file) {
	        try {
	                wardata.save(file);
	        }
	        catch (IOException e) {
	                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save wars.yml!");
	        }
	    }

	    public void reload() {
	        wardata = YamlConfiguration.loadConfiguration(warfile);
	    }
}
