package es.themin.empires.util.testing;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import es.themin.empires.cmds.SubCommand;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class generatebasecore extends SubCommand{

	private ManagerAPI myApi = null;
	
	public generatebasecore(ManagerAPI api) {
		myApi = api;
	}

	public boolean onCommand(Player player, String[] args) {
		EPlayer myEPlayer = myApi.getEPlayer(player);
		
		if (myEPlayer == null || myEPlayer.getEmpireUUID() == null) {
			myEPlayer.sendMessage( ChatColor.RED + "You are not in an empire");
			return false;
		}
		Empire empire = myApi.getEmpire(myEPlayer.getEmpireUUID());
		ItemStack item = new ItemStack(Material.BEACON, 1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("base core");
		item.setItemMeta(im);
		player.getInventory().addItem(item);
		/*if (empire.hasCoreOfType(CoreType.BASE)) {
			player.sendMessage(plprefix + ChatColor.RED + "You already have a core of this type");
			return false;
		}
		Core myCore = new Core(myPlugin, myPlugin.Cores.nextUnusedCoreId(), CoreType.BASE, player.getLocation(), 1, empire);
		empire.addCore(myCore);
		World world = player.getWorld();
		UUID uuid = world.getUID();
		CoreWorld cw = Worlds.getWorlds().get(uuid);
		cw.addCore(myCore);
		*/
		return false;
		
	}

	public String name() {
		return "generatebasecore";
	}

	public String info() {
		return "generates a core";
	}

	public String[] aliases() {
		return new String[] {"genbase" , "gbc" , "genbasecore", "generatebase"};
	}

}
