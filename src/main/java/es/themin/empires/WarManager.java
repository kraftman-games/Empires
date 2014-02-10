package es.themin.empires;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import es.themin.empires.enums.BattleType;
import es.themin.empires.util.Empire;
import es.themin.empires.util.SettingsManager;
import es.themin.empires.wars.Battle;
import es.themin.empires.wars.War;
import es.themin.empires.wars.Battle.BattleTeam;

public class WarManager {
	
	private empires myPlugin;
	private ArrayList<War> wars = new ArrayList<War>();

	public WarManager(empires plugin) {
		myPlugin = plugin;
	}
	
	public ArrayList<War> getWars() {
		return wars;
	}

	public  void saveWars() {
		Bukkit.getServer().getPluginManager().getPlugin("Empires").getLogger().info("[Empires] Saving Wars ...");
		List<String> listofwars = new ArrayList<String>();
		for (War war : this.wars) {
			String idforsaving = war.getEmpire1().getName() + ":" + war.getEmpire2().getName() + ":" + war.getStart();
			listofwars.add(idforsaving);
			List<String> empire1allies = new ArrayList<String>();
			for (Empire empire : war.getAllEmpiresOnTeam1()) {
				empire1allies.add(empire.getName());
			}
			SettingsManager.getWarData().set(idforsaving, empire1allies);
			List<String> empire2allies = new ArrayList<String>();
			for (Empire empire : war.getAllEmpiresOnTeam2()) {
				empire2allies.add(empire.getName());
			}
			SettingsManager.getWarData().set(idforsaving, empire2allies);
			if (war.getVictor() != null)SettingsManager.getWarData().set(idforsaving + ".victor", war.getVictor().getName());
			if (war.getEnd() != 0)SettingsManager.getWarData().set( idforsaving + ".end", war.getEnd());
			SettingsManager.getWarData().set(idforsaving + ".empire1wins", war.getEmpire1Wins());
			SettingsManager.getWarData().set(idforsaving + ".empire2wins", war.getEmpire2Wins());
			SettingsManager.getWarData().set(idforsaving + ".ongoing", war.isOnGoing());
			SettingsManager.getWarData().set(idforsaving + ".team1percent", war.getTeam1Percent());
			List<String> listofbattles = new ArrayList<String>();
			for (Battle battle : war.getBattle()) {
				String Bidforsaving = battle.getEmpire1().getName() + ":" + battle.getEmpire2().getName() + ":" + battle.getStart();
				listofbattles.add(Bidforsaving);
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".end", battle.getEnd());
				List<String> bempire1allies = new ArrayList<String>();
				for (Empire empire : battle.getAllEmpiresOnTeam1()) {
					bempire1allies.add(empire.getName());
				}
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".empire1allies", bempire1allies);
				List<String> bempire2allies = new ArrayList<String>();
				for (Empire empire : battle.getAllEmpiresOnTeam2()) {
					bempire2allies.add(empire.getName());
				}
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".empire2allies", bempire2allies);
				if (battle.getVictor() !=null ) SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".victor", battle.getVictor().getName());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".type", battle.getType().toString());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".ongoing", battle.isOnGoing());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".team1points", battle.getTeam1Points());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".team2points", battle.getTeam2Points());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".endsintie", battle.endedInATie());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".killsforwin", battle.getKillsForWin());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".damageforwin", battle.getDamageForWin());
				SettingsManager.getWarData().set(idforsaving + ".battles." + Bidforsaving + ".attacker", battle.getAttackingTeam().toString());
			}
			SettingsManager.getWarData().set(idforsaving + ".battles.list", listofbattles);
		}
		SettingsManager.getWarData().set("wars", listofwars);
		Bukkit.getServer().getPluginManager().getPlugin("Empires").getLogger().info("[Empires] ... Wars Saved");
		SettingsManager.saveWarData();
	}
	
	//CANNOT BE RUN BEFORE EMPIRES ARE LOADED
	public  void loadWars() {
		List<String> listofwars = SettingsManager.getWarData().getStringList("wars");
		for (String warname : listofwars) {
			String[] warnamewords = warname.split(":");
			War war = new War(myPlugin, myPlugin.Empires.getEmpireWithName(warnamewords[0]), myPlugin.Empires.getEmpireWithName(warnamewords[0]));
			war.setStart(Long.parseLong(warnamewords[2]));
			List<String> empire1allies = SettingsManager.getWarData().getStringList(warname + ".empire1allies");
			for (String empire1ally : empire1allies) {
				war.addEmpireToTeam1(myPlugin.Empires.getEmpireWithName(empire1ally));
			}
			List<String> empire2allies = SettingsManager.getWarData().getStringList(warname + ".empire2allies");
			for (String empire2ally : empire2allies) {
				war.addEmpireToTeam2(myPlugin.Empires.getEmpireWithName(empire2ally));
			}
			if (SettingsManager.getWarData().getString(warname + ".victor") != null) war.setVictor(myPlugin.Empires.getEmpireWithName(SettingsManager.getWarData().getString(warname + ".victor")));
			war.setEnd(SettingsManager.getWarData().getLong(warname + ".end"));
			war.setEmpire1Wins(SettingsManager.getWarData().getInt(warname + ".empire1wins"));
			war.setEmpire2Wins(SettingsManager.getWarData().getInt(warname + ".empire2wins"));
			war.setOnGoing(SettingsManager.getWarData().getBoolean(warname + "ongoing"));
			war.setTeam1Percent(SettingsManager.getWarData().getLong(warname + ".team1percent"));
			List<String> battlenames = SettingsManager.getWarData().getStringList(warname + ".battles.list");
			for (String battlename : battlenames) {
				String[] battlenamewords = battlename.split(":");
				BattleType type = null;
				if (SettingsManager.getWarData().getString(warname + ".battles." + battlename + ".type").equalsIgnoreCase("DEATHMATCH")) type = BattleType.DEATHMATCH;
				if (SettingsManager.getWarData().getString(warname + ".battles." + battlename + ".type").equalsIgnoreCase("OBLITERATION")) type = BattleType.OBLITERATION;
				BattleTeam attacker = null;
				if (SettingsManager.getWarData().getString(warname + ".battles." + battlename + ".attacker").equalsIgnoreCase("team1")) attacker = BattleTeam.team1;
				if (SettingsManager.getWarData().getString(warname + ".battles." + battlename + ".attacker").equalsIgnoreCase("team2")) attacker = BattleTeam.team2;
				Battle battle = new Battle(myPlugin.Empires.getEmpireWithName(battlenamewords[0]), myPlugin.Empires.getEmpireWithName(battlenamewords[1]), war, type, attacker);
				//TODO add the battles loading.
			
			}
		}
	}
	
}
