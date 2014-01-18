package es.themin.empires.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;

import es.themin.empires.enums.CoreType;

public class Amplifier {
	

	private Location location;
	private Empire empire;
	private int Id;
	private Material casing;
	public Amplifier(int Id, Location location, Empire empire) {
		this.Id = Id;
		this.location = location;
		this.empire = empire;
		int caseint = 0;
		if (empire.hasCoreOfType(CoreType.FORTIFICATION)) {
			caseint = empire.getCoreOfType(CoreType.FORTIFICATION).getLevel();
		}
	}


	public void setEmpire(Empire e){
		//do we need to get the empire if we already have it? wont e == e2 ?
		Empire e2 = this.empire;
		e2.removeAmplifier(this);
		this.empire = e;
		e.aa(this);
		Save();
	}
	public int getId(){
		return this.Id;
	}
	public Location getLocation(){
		return this.location;
	}
	public Empire getEmpire(){
		return this.empire;
	}
	public void Save() {
		if (UtilManager.containsAmplifierWithId(this.Id)) {
			int i = UtilManager.amps.indexOf(UtilManager.containsAmplifierWithId(this.Id));
			UtilManager.amps.remove(i);
		}
		UtilManager.amps.add(this);
	}
	public void generateCasing(){
		
		
	}

}
