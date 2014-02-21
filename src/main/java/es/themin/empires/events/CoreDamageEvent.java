package es.themin.empires.events;



import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreDamageType;

public class CoreDamageEvent extends Event implements Cancellable{
	
	private CoreDamageType damagetype;
	private Core core;
	private Block block;
	private Player player;
	public boolean cancelled;
	private static final HandlerList handlers = new HandlerList();
	public CoreDamageEvent(Block block, Core core, CoreDamageType coredamager, Player player) {
		this.block = block;
		this.core = core;
		this.damagetype = coredamager;
		this.player = player;
	}
    public boolean isCancelled() {
        return cancelled;
    }
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
    public Block getBlock(){
    	return block;
    }
    public Core getCore(){
    	return core;
    }
    public CoreDamageType getCoreDamager(){
    	return this.damagetype;
    }
    public Player loadEPlayer(){
    	return player;
    }
    public boolean isPlayerBreaking(){
    	if (damagetype == CoreDamageType.PLAYER) return true;
    	else return false;
    }
	

}
