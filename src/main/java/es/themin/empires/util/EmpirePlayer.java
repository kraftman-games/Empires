package es.themin.empires.util;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class EmpirePlayer {
	
	public ArrayList<Player> playerinempires = new ArrayList<Player>();
	private Player player;
	private Empire empire;
	public EmpirePlayer(Player player){
		this.player = player;
	}

	public Player getPlayer(){
		return player;
	}
	
	public boolean isInEmpire() {
		if (playerinempires.contains(player)) return true;
		else return false;
	}
	public Empire getEmpire(){
		return empire;
	}
}
