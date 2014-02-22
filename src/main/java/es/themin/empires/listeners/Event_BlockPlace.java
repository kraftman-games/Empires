package es.themin.empires.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.ItemMeta;

import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.schematics.Schematic;
import es.themin.empires.schematics.base.Schematic_Base_1;
import es.themin.empires.util.EPlayer;

public class Event_BlockPlace implements Listener{
	private ManagerAPI myApi = null;
	
	public Event_BlockPlace(ManagerAPI myAPI) {
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		//EPlayer myEPlayer = myApi.getEPlayer(player);
		ItemMeta im = event.getItemInHand().getItemMeta();
		Material placed = event.getBlockPlaced().getType();
		if (placed == Material.BEACON) {
			if (im.getDisplayName().equalsIgnoreCase("base core")) {
				/*if (myEPlayer == null) {
					player.sendMessage(MsgManager.notinemp);
					event.setCancelled(true);
				}
				Empire myEmpire = myEPlayer.getEmpire();
				if (myEmpire == null) {
					player.sendMessage(MsgManager.notinemp);
					event.setCancelled(true);
				}
				Core myCore = new Core(myPlugin, myPlugin.Cores.nextUnusedCoreId(), CoreType.BASE, event.getBlock().getLocation(), 1, myEmpire);
				myEmpire.addCore(myCore);
				World world = player.getWorld();
				UUID uuid = world.getUID();
				myCore.build2();
				EWorld cw = Worlds.getWorld(uuid);
				cw.addCore(myCore);*/
				event.setCancelled(true);
				Schematic schem = new Schematic_Base_1();
				//schem.pasteFromCentre(event.getBlock().getLocation());
				schem.playEffectsFrom(event.getBlock().getLocation());
			}
		}
			
		
	}

}
