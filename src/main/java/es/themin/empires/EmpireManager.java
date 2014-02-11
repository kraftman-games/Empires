package es.themin.empires;

import java.util.ArrayList;
import java.util.List;

import es.themin.empires.cores.Core;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.CorePlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.Rank;
import es.themin.empires.util.UtilManager;

public class EmpireManager {
	
	private empires myPlugin;
	private ArrayList<Empire> empires = new ArrayList<Empire>();

	public ArrayList<Empire> getEmpires() {
		return empires;
	}

	public EmpireManager(empires empires) {
		myPlugin = empires;
	}

	
	public void addEmpire(Empire empire) {
		this.empires.add(empire);
		
	}
	
	public int nextUnusedEmpireId(){
		int i = 0;
		while (getEmpireWithID(i) != null){
			i++;
		}
		return i;
		
	}

	public void removeEmpire(Empire empire) {
		int i = this.getEmpires().indexOf(empire);
		this.empires.remove(i);
		
	}
	public Empire getEmpireWithName(String name) {
		for (Empire empire : this.empires) {
			if (empire.getName().equalsIgnoreCase(name)) return empire;
		}
		return null;
	}
	public boolean containsEmpireWithName(String name) {
		for (Empire empire : this.getEmpires()) {
			if (empire.getName().equalsIgnoreCase(name)) return true;
		}
		return false;
	}
	public Empire getEmpireWithID(int Id) {
		for (Empire empire : this.getEmpires()) {
			if (empire.getId() == Id) return empire;
		}
		return null;
	}
	public boolean containsEmpireWithId(int Id) {
		for (Empire empire : this.getEmpires()) {
			if (empire.getId() == Id) return true;
		}
		return false;
	}
	
	public void saveEmpires() {
		List<String> list = new ArrayList<String>();
		for (Empire empire : this.empires) {
			StringBuilder str = new StringBuilder();
			str.append(empire.getId() + ":");
			str.append(empire.getName() + ":");
			str.append(empire.getOwner());
			list.add(str.toString());
			SettingsManager.getEmpireData().set(str.toString() + ".id", empire.getId());
			SettingsManager.getEmpireData().set(str.toString() + ".name", empire.getName());
			SettingsManager.getEmpireData().set(str.toString() + ".name", empire.getOwner());
			List<String> playerList = new ArrayList<String>();
			for (CorePlayer player : empire.getPlayers().values()) {
				//FixedMetadataValue playerEmpire = new FixedMetadataValue (myPlugin, this.getId());
				playerList.add(player.getName());
				
			}
			SettingsManager.getEmpireData().set(str.toString() + ".players", playerList);
			List<String> list3 = new ArrayList<String>();
			for (Core core : empire.getCores()) {
				StringBuilder str2 = new StringBuilder();
				str2.append(core.getId() + ":");
				str2.append(core.getType().toString() + ":");
				str2.append(core.getLocation().getWorld().getName() + ":");
				str2.append(core.getLocation().getBlockX() + ":");
				str2.append(core.getLocation().getBlockY() + ":");
				str2.append(core.getLocation().getBlockZ() + ":");
				str2.append(core.getLevel() + ":");
				str2.append(core.getEmpire().getId() + ":");
				str2.append(core.getEmpire().getName());
				list3.add(str2.toString());
			}
			SettingsManager.getEmpireData().set(str.toString() + ".cores", list3);
			List<String> list4 = new ArrayList<String>();
			for (Rank rank : empire.getRanks()) {
				StringBuilder str3 = new StringBuilder();
				str3.append(rank.getWeight() + ":");
				str3.append(rank.getName() + ":");
				str3.append(rank.getPreifx());
				list4.add(str3.toString());
				List<String> list5 = new ArrayList<String>();
				for (String p : rank.getPlayers()) {
					list5.add(p);
				}
				SettingsManager.getEmpireData().set(str.toString() + ".rank." + str3.toString() + ".players", list5);
				List<String> list6 = new ArrayList<String>();
				for (EmpirePermission ep : rank.getPermissions()) {
					list6.add(ep.toString());
				}
				SettingsManager.getEmpireData().set(str.toString() + ".rank." + str3.toString() + ".permissions", list6);
			}
			SettingsManager.getEmpireData().set(str.toString() + ".ranks", list4);
		}
		SettingsManager.getEmpireData().set("empires", list);
	}
}
