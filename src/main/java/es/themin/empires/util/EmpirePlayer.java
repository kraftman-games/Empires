
package es.themin.empires.util;


import org.bukkit.entity.Player;

public class EmpirePlayer {
	
	private Player player;
	private Empire empire;
	public EmpirePlayer(Player player, Empire empire){
		this.player = player;
		this.empire = empire;
	}

	public Player getPlayer(){
		return player;
	}
	
	public boolean isInEmpire() {
		if (empire != null) return true;
		else return false;
	}
	public Empire getEmpire(){
		return empire;
	}
	public void setEmpire(Empire empire){
		this.empire = empire;
		UtilManager.empireplayers.put(this.player.getName(), this);
	}
}
