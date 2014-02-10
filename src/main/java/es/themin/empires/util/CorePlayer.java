package es.themin.empires.util;

import java.util.UUID;

import org.bukkit.entity.Player;

public class CorePlayer {

	private UUID UUID;
	private Empire empire;
	private String name;

	public UUID getUUID() {
		return UUID;
	}

	public void setUUID(UUID uUID) {
		UUID = uUID;
	}

	public Empire getEmpire() {
		return empire;
	}

	public CorePlayer(Player player) {
		UUID = player.getUniqueId();
		name = player.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmpire(Empire myEmpire) {
		empire = myEmpire;
		
	}

	public boolean isAtWarWith(CorePlayer Enemy){
		return this.empire.isAtWarWith(Enemy.getEmpire());
		
	}
}
