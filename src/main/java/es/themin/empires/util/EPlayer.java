package es.themin.empires.util;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EPlayer {

	private UUID UUID;
	private Empire empire;
	private String name;
	private Player player;

	public UUID getUUID() {
		return UUID;
	}

	public void setUUID(UUID uUID) {
		UUID = uUID;
	}

	public Empire getEmpire() {
		return empire;
	}

	public EPlayer(Player player) {
		UUID = player.getUniqueId();
		name = player.getName();
		this.player = player;
		this.empire = null;
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

	public boolean isAtWarWith(EPlayer Enemy){
		return this.empire.isAtWarWith(Enemy.getEmpire());
		
	}
	
	public Location getLocation(){
		return this.player.getLocation();
	}

	public void sendMessage(String message) {
		player.sendMessage(message);
	}

	public Player getPlayer() {
		return this.player;
	}
}
