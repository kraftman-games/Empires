package es.themin.empires.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EPlayer {

	private UUID UUID;
	private UUID empireUUID;
	private String name;
	//private Player player;
	private long firstSeen;
	private long lastSeen;

	public long getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(long lastSeen) {
		this.lastSeen = lastSeen;
	}

	public long getFirstSeen() {
		return firstSeen;
	}

	public void setFirstSeen(long timeNow) {
		this.firstSeen = timeNow;
	}

	public UUID getUUID() {
		return UUID;
	}

	public void setUUID(UUID uUID) {
		UUID = uUID;
	}


	
	public UUID getEmpireUUID() {
		return empireUUID;
	}

	public void setEmpireUUID(UUID empireUUID) {
		this.empireUUID = empireUUID;
	}

	public EPlayer(UUID mUUID, String playerName) {
		UUID = mUUID;
		name = playerName;
	}

	public EPlayer(Player player) {
		UUID = player.getUniqueId();
		name = player.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public boolean isAtWarWith(EPlayer Enemy){
		//return this.empire.isAtWarWith(Enemy.getEmpire());
		return false;
	}
	
	public Location getLocation(){
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
            if(p.getUniqueId().equals(this.getUUID())){
                return p.getLocation();
            }
        }
            return null;
	}
//
	public Player getPlayer() {
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
            if(p.getUniqueId().equals(this.getUUID())){
                return p;
            }
        }
            return null;
	}
	
	
//	public boolean canPlayerAttack(Player myPlayer) {
//		Empire playerEmpire = Players.getPlayer(myPlayer.getUniqueId()).getEmpire();
//		if (!this.isProtected()){
//			if (this.isAtWar()){
//				if (playerEmpire == this.getEnemyEmpire()){
//					return true;
//				}
//				else {
//					myPlayer.sendMessage("This war is not yours to fight!");
//					return false;
//				}
//				
//			} else if (playerEmpire.isProtected){
//				myPlayer.sendMessage("You cannot attack an empire until yours is rebuilt!");
//				return false;
//			}
//		} else {
//			myPlayer.sendMessage("There is no honor in attack this fallen empire");
//			return false;
//		}
//		return false;
//	}
}
