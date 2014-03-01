package es.themin.empires.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class ArcherListener implements Listener{
	
	public ArcherListener() {
		
	}
	
	@EventHandler
	public void onEntityAttackEvent(EntityTargetLivingEntityEvent event) {
		if (event.getEntity().getType() == EntityType.SKELETON) {
			Skeleton skele = (Skeleton) event.getEntity();
			if (event.getTarget().getType() == EntityType.PLAYER) {
				Player player = (Player) event.getTarget();
				//This statement bellow would actually check if the skeleton belonged to the empire of the player
				if (skele.getCustomName() != null && skele.getCustomName().contains(player.getName())) {
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void onEntityMove(EntityShootBowEvent event) {
		if (event.getEntity().getType() == EntityType.SKELETON) {
			Skeleton skele = (Skeleton) event.getEntity();
			Arrow arrow =  (Arrow) event.getProjectile();
		}
	}

}
