package es.themin.empires.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import enums.CoreUpgradeType;
import es.themin.empires.util.Core;

public class CoreUpgradeEvent extends Event implements Cancellable{
	
	private static final HandlerList handlers = new HandlerList();
	public boolean cancelled;
	private Core core;
	private CoreUpgradeType cut;
	public CoreUpgradeEvent(Core core, CoreUpgradeType cut) {
		this.core = core;
		this.cut = cut;
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
    public Core getCore(){
    	return this.core;
    }
    public CoreUpgradeType getUpgradeType(){
    	return this.cut;
    }

}
