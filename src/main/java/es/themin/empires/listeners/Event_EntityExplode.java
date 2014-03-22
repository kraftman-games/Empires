package es.themin.empires.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import es.themin.empires.managers.ManagerBL;
import es.themin.empires.util.EWorld;

public class Event_EntityExplode implements Listener{
	
	private ManagerBL api;
	private Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Empires");
	public Event_EntityExplode(ManagerBL api) {
		this.api = api;
	}
	
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		//For this i was thinking that if the explosion happens inside an empire the blocks don't regen
		//They get scattered around the place
		//We can maybe set a property of one core to
		//'reduces effect of explosions in you empire'
		//explosions outside the empire will heal automatically to stop the messing up of the land;
		Location l1 = event.getLocation();
		World w = l1.getWorld();
		
		final EWorld ew = api.getEWorld(w.getUID());
		//if (ew == null || ew.getCores(l1.getBlockX(), l1.getBlockZ()).isEmpty()) {
			//Handles the event if it occured outside an empire
			/*final ArrayList<RegenBlock> blocks = RegenBlock.parseBlocks(event.blockList());
			ew.addRegenBlocks(blocks);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					for (RegenBlock b : blocks) {
						b.regen();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							Bukkit.getServer().getLogger().info("[Empires] Error 1 from Event_EntityExplode class");
						}
					}
					ew.removeRegenBlocks(blocks);
				}
				
			} ,400L);*/
			//}else {
			//Handles if the event happened inside an empire
			for (Block b : event.blockList()) {
				event.setYield(0);
				float x = (float) -0.5 + (float)(Math.random() * 1);
				float y = (float) -1 + (float)(Math.random() * 2);
				float z = (float) -0.5 + (float)(Math.random() * 1);
				@SuppressWarnings("deprecation")
				byte d = b.getData();
				Material m = b.getType();
				Location l2 = b.getLocation();
				FallingBlock myFallingBlock = w.spawnFallingBlock(l2, m, d);
				myFallingBlock.setVelocity(new Vector(x,y,z));
				myFallingBlock.setDropItem(false);
			}
		//}
	}

}
