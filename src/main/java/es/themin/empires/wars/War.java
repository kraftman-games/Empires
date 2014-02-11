package es.themin.empires.wars;

import java.util.ArrayList;
import java.util.HashMap;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.WarManager;
import es.themin.empires.empires;
import es.themin.empires.util.CorePlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;
import es.themin.empires.util.SettingsManager;
import es.themin.empires.util.UtilManager;

public class War {

	public String warprefix = empires.warprefix;
	private ArrayList<Empire> empire1allies;
	private ArrayList<Empire> empire2allies;
	private Empire empire1;
	private Empire empire2;
	private Empire victor;
	private long start;
	private long end;
	private int empire1wins;
	private int empire2wins;
	public boolean endedintie;
	private boolean onGoing;
	private Long team1percent;
	private ArrayList<Battle> battles;
	private HashMap<Empire, Long> empire1alliesjoin;
	private HashMap<Empire, Long> empire2alliesjoin;
	private HashMap<Empire, Long> empire1alliesleave;
	private HashMap<Empire, Long> empire2alliesleave;
	private HashMap<Empire, Long> empire1alliesloss;
	private HashMap<Empire, Long> empire2alliesloss;
	private HashMap<Empire, Float> empire1alliespercentage;
	private HashMap<Empire, Float> empire2alliespercentage;
	private empires myPlugin;
	private WarManager Wars;
	
	public War(empires plugin, Empire team1, Empire team2) {
		myPlugin = plugin;
		Wars = myPlugin.Wars;
		this.empire1 = team1;
		this.empire2 = team2;
		this.empire1allies = new ArrayList<Empire>();
		this.empire2allies = new ArrayList<Empire>();
		this.empire1wins = 0;
		this.empire2wins = 0;
		this.battles = new ArrayList<Battle>();
		this.onGoing = false;
		this.team1percent = (long) 50;
		this.end = 0;
		this.start = 0;
		this.empire1alliesjoin = new HashMap<Empire, Long>();
		this.empire2alliesjoin = new HashMap<Empire, Long>();
		this.empire1alliesleave = new HashMap<Empire, Long>();
		this.empire2alliesleave = new HashMap<Empire, Long>();
		this.empire1alliesloss = new HashMap<Empire, Long>();
		this.empire2alliesjoin = new HashMap<Empire, Long>();
		this.empire1alliespercentage = new HashMap<Empire, Float>();
		this.empire2alliespercentage = new HashMap<Empire, Float>();
	}
	public void start() {
		this.onGoing = true;
		this.start = System.currentTimeMillis();
		for (Empire empire : getAllEmpires()) {
			empire.addWar(this);
		}
		Save();
	}
	public void end() {
		this.onGoing = false;
		this.end = System.currentTimeMillis();
		if (empire1wins > empire2wins) this.victor = empire1; this.endedintie = false;
		if (empire2wins > empire1wins) this.victor = empire1; this.endedintie = false;
		if (empire1wins == empire2wins) this.victor = null; this.endedintie = true;
		for (Empire empire : getAllEmpires()) {
			empire.removeWar(this);
		}
		Save();
	}
	public void endWithVictor(Empire empire) {
		this.onGoing = false;
		this.end = System.currentTimeMillis();
		this.victor = empire;
		this.endedintie = false;
		Save();
	}
	public Empire getEmpire1(){
		return empire1;
	}
	public Empire getEmpire2(){
		return empire2;
	}
	public long getStart(){
		return start;
	}
	public boolean isOnGoing() {
		return onGoing;
	}
	public boolean endedInTie() {
		return endedintie;
	}
	public void addBattle(Battle battle) {
		if (!(this.battles.contains(battle))) this.battles.add(battle);
		Save();
	}
	public Empire getVictor() {
		return this.victor;
	}
	public long getEnd() {
		return this.end;
	}
	public void setTeam1Wins(int wins) {
		this.empire1wins = wins;
		Save();
	}
	public void setTeam2Wins(int wins) {
		this.empire2wins = wins;
		Save();
	}
	public void addWinsToTeam1(int wins) {
		this.empire1wins = this.empire1wins + wins;
		Save();
	}
	public void addWinsToTeam2(int wins) {
		this.empire2wins = this.empire2wins + wins;
		Save();
	}
	public void addEmpireToTeam1(Empire empire) {
		empire1allies.add(empire);
		if (onGoing) empire.addWar(this);
		empire.Save();
		empire1alliesjoin.put(empire, System.currentTimeMillis());
		empire1alliespercentage.put(empire, (float) 100);
		Save();
	}
	public void addEmpireToTeam2(Empire empire) {
		empire2allies.add(empire);
		if (onGoing) empire.addWar(this);
		empire.Save();
		empire2alliesjoin.put(empire, System.currentTimeMillis());
		empire2alliespercentage.put(empire, (float) 100);
		Save();
	}
	public void removeEmpireFromTeam1(Empire empire) {
		empire2allies.add(empire);
		if (empire.getWars().contains(this)) empire.removeWar(this);
		empire.Save();
		Save();
	}
	public void removeEmpireFromTeam2(Empire empire) {
		empire2allies.remove(empire);
		if (empire.getWars().contains(this)) empire.removeWar(this);
		empire.Save();
		Save();
	}
	public boolean hasBattleOnGoing() {
		if (hasHadABattle()) {
			for (Battle battle : battles) {
				if (battle.isOnGoing()) return true;
			}
		}
		return false;
	}
	public Battle getOnGoingBattle() {
		if (hasHadABattle()) {
			for (Battle battle : battles) {
				if (battle.isOnGoing()) return battle;
			}
		}
		return null;
	}
	public Battle getLastBattle() {
		if (hasHadABattle()) {
			Battle myBattle = null;
			for (Battle battle : battles) {
				if (myBattle == null) myBattle = battle;
				else if (battle.getEnd() > myBattle.getEnd()) myBattle = battle;
			}
			return myBattle;
		}
		return null;
	}
	public boolean hasHadABattle() {
		if (!(battles.isEmpty())) return true;
		return false;
	}
	public ArrayList<Battle> getBattle(){
		return battles;
	}
	public ArrayList<Empire> getAllEmpires() {
		ArrayList<Empire> list = new ArrayList<Empire>();
		list.add(empire1);
		list.add(empire2);
		for (Empire empire : this.empire1allies) {
			if (!(list.contains(empire))) {
				list.add(empire);
			}
		}for (Empire empire : this.empire2allies) {
			if (!(list.contains(empire))) {
				list.add(empire);
			}
		}
		return list;
	}
	public ArrayList<Empire> getAllEmpiresOnTeam1() {
		ArrayList<Empire> list = new ArrayList<Empire>();
		list.add(empire1);
		for (Empire empire : this.empire1allies) {
			if (!(list.contains(empire))) {
				list.add(empire);
			}
		}
		return list;
	}
	public ArrayList<Empire> getAllEmpiresOnTeam2() {
		ArrayList<Empire> list = new ArrayList<Empire>();
		list.add(empire2);
		for (Empire empire : this.empire2allies) {
			if (!(list.contains(empire))) {
				list.add(empire);
			}
		}
		return list;
	}
	public void Save() {
		if (Wars.getWars().contains(this)) {
			Wars.getWars().remove(this);
		}
		Wars.getWars().add(this);
	}
	public void upDateEmpires() {
		for (Empire empire : getAllEmpires()) {
			empire.addWar(this);
		}
	}
	public Long getTeam1Percent() {
		return team1percent;
	}
	public void addTeam1Percent(Long l) {
		this.team1percent = team1percent + l;
		Save();
	}
	public void displayStatistic() {
		for (Empire empire : getAllEmpiresOnTeam1()){
			StringBuilder str = new StringBuilder();
			float fpc = getTeam1Percent();
			if (fpc < 35) {
				str.append(ChatColor.DARK_RED + "");
				str.append(fpc);
				//str.append("%");
			}else if (fpc < 50) {
				str.append(ChatColor.RED + "");
				str.append(fpc);
				//str.append("%");
			}else if (fpc < 65) {
				str.append(ChatColor.GOLD + "");
				str.append(fpc);
				//str.append("%");
			}else if (fpc < 80) {
				str.append(ChatColor.GREEN + "");
				str.append(fpc);
				//str.append("%");
			}else {
				str.append(ChatColor.DARK_GREEN + "");
				str.append(fpc);
				//str.append("%");
			}
			str.append("%");
			for (CorePlayer player : empire.getOnlinePlayers().values()) {
				BarAPI.setMessage(player.getPlayer(), ChatColor.DARK_GREEN + "You        " + str.toString() + ChatColor.DARK_RED + empire2.getName(), fpc);
			}
		}for (Empire empire : getAllEmpiresOnTeam2()) {
			StringBuilder str = new StringBuilder();
			float fpc = 100 - getTeam1Percent();
			if (fpc < 35) {
				str.append(ChatColor.DARK_RED + "");
				str.append(fpc);
				//str.append("%");
			}else if (fpc < 50) {
				str.append(ChatColor.RED + "");
				str.append(fpc);
				//str.append("%");
			}else if (fpc < 65) {
				str.append(ChatColor.GOLD + "");
				str.append(fpc);
				//str.append("%");
			}else if (fpc < 80) {
				str.append(ChatColor.GREEN + "");
				str.append(fpc);
				//str.append("%");
			}else {
				str.append(ChatColor.DARK_GREEN + "");
				str.append(fpc);
				//str.append("%");
			}
			str.append("%");
			for (CorePlayer player : empire.getOnlinePlayers().values()) {
				BarAPI.setMessage(player.getPlayer(), ChatColor.DARK_GREEN + "You        " + str.toString() +"        "+ ChatColor.DARK_RED + empire1.getName(), fpc);
			}
		}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getServer().getPluginManager().getPlugin("Empires"), new Runnable() {

			@Override
			public void run() {
				for (Empire empire : getAllEmpires()) {
					for (CorePlayer player : empire.getOnlinePlayers().values()) {
						BarAPI.removeBar(player.getPlayer());
					}
				}
			}
		}, 400L);
	}
	public void setStart(Long l) {
		this.start = l;
	}
	public void setEnd(Long l) {
		this.end = l;
	}
	public int getEmpire1Wins() {
		return empire1wins;
	}
	public int getEmpire2Wins(){
		return this.empire2wins;
	}
	public void setVictor(Empire empire) {
		this.victor = empire;
	}
	public void setEmpire1Wins(int i) {
		this.empire1wins = i;
	}
	public void setEmpire2Wins(int i) {
		this.empire2wins = i;
	}
	public void setOnGoing(boolean OGbool) {
		this.onGoing = OGbool;
	}
	public void setTeam1Percent(Long l) {
		this.team1percent = l;
	}
	public ArrayList<Empire> getEmpire1Allies() {
		return empire1allies;
	}
	public ArrayList<Empire> getEmpire2Allies() {
		return empire2allies;
	}
	public int getNumberOfEmpire1Allies() {
		return empire1allies.size();
	}
	public int getNumberOfEmpire2Allies() {
		return empire2allies.size();
	}
	public HashMap<Empire,Long> getEmpire1AlliesJoins(){
		return empire1alliesjoin;
	}
	public HashMap<Empire,Long> getEmpire2AlliesJoins(){
		return empire2alliesjoin;
	}
	public HashMap<Empire,Long> getEmpire1AlliesLeaves(){
		return empire1alliesleave;
	}
	public HashMap<Empire,Long> getEmpire2AlliesLeaves(){
		return empire2alliesleave;
	}
	public HashMap<Empire,Long> getEmpire1AlliesLosses(){
		return empire1alliesloss;
	}
	public HashMap<Empire,Long> getEmpire2AlliesLosses(){
		return empire2alliesloss;
	}
	public HashMap<Empire,Long> getEmpire1AlliesPercentages(){
		return empire1alliesloss;
	}
	public HashMap<Empire,Long> getEmpire2AlliesPercentages(){
		return empire2alliesloss;
	}
	public void setPercentageOfEmpire(Empire empire, Float f) {
		if (empire1allies.contains(empire)) {
			empire1alliespercentage.put(empire, f);
		}if (empire2allies.contains(empire)) {
			empire2alliespercentage.put(empire, f);
		}
	}
	public Float getPercentageOfEmpire(Empire empire) {
		if (empire1alliespercentage.containsKey(empire)) return empire1alliespercentage.get(empire);
		if (empire2alliespercentage.containsKey(empire)) return empire2alliespercentage.get(empire);
		return null;
	}
	public void checkForKnockOut() {
		for (Empire empire : empire1alliespercentage.keySet()) {
			if (empire1alliespercentage.get(empire) <=0 ) {
				empire.addWarLosses(1);
				removeEmpireFromTeam1(empire);
				this.empire1alliesloss.put(empire, System.currentTimeMillis());
				addTeam1Percent(SettingsManager.getConfig().getLong("wars.percentage_gain_per_knockout"));
				empire.broadcastMessage(warprefix + ChatColor.RED + "You have been weekend to the point where you cannot keep up the war against " + empire2.getName());
				empire.addWarLosses(1);
				for (Empire empire22 : getAllEmpiresOnTeam1()) {
					empire22.broadcastMessage(warprefix + ChatColor.RED + "Your ally, " + empire.getName() + ", has been defeated rally and salvage this war");
				}
				for (Empire empire22 : getAllEmpiresOnTeam2()) {
					empire22.broadcastMessage(warprefix + ChatColor.GREEN + empire.getName() + "Has been weekend to the point where they could not maintain their war effort, Victor draws closer");
				}
			}
		}
		for (Empire empire : empire2alliespercentage.keySet()) {
			if (empire2alliespercentage.get(empire) <=0 ) {
				empire.addWarLosses(1);
				removeEmpireFromTeam1(empire);
				this.empire2alliesloss.put(empire, System.currentTimeMillis());
				addTeam1Percent(-SettingsManager.getConfig().getLong("wars.percentage_gain_per_knockout"));
				empire.broadcastMessage(warprefix + ChatColor.RED + "You have been weekend to the point where you cannot keep up the war against " + empire1.getName());
				empire.addWarLosses(1);
				for (Empire empire22 : getAllEmpiresOnTeam1()) {
					empire22.broadcastMessage(warprefix + ChatColor.GREEN + empire.getName() + "Has been weekend to the point where they could not maintain their war effort, Victor draws closer");
				}
				for (Empire empire22 : getAllEmpiresOnTeam2()) {
					empire22.broadcastMessage(warprefix + ChatColor.RED + "Your ally, " + empire.getName() + ", has been defeated rally and salvage this war");
				}
			}
		}
		checkForEnd();
	}
	public void checkForEnd() {
		if (team1percent <= 0) {
			this.onGoing = false;
			this.end = System.currentTimeMillis();
			this.victor = empire2;
			this.endedintie = false;
			for (Empire empire : getAllEmpires()) {
				empire.removeWar(this);
			}
			Save();
			broadcastToTeam2(MsgManager.warwin);
			broadcastToTeam1(MsgManager.warloose);
		}if (team1percent >= 100) {
			broadcastToTeam1(MsgManager.warwin);
			broadcastToTeam2(MsgManager.warloose);
			this.onGoing = false;
			this.end = System.currentTimeMillis();
			this.victor = empire1;
			this.endedintie = false;
			for (Empire empire : getAllEmpires()) {
				empire.removeWar(this);
			}
			Save();
		}
	}
	public void broadcastToTeam1(String message) {
		for (Empire empire : getAllEmpiresOnTeam1()) {
			empire.broadcastMessage(message);
		}
	}
	public void broadcastToTeam2(String message) {
		for (Empire empire : getAllEmpiresOnTeam2()) {
			empire.broadcastMessage(message);
		}
	}
}
