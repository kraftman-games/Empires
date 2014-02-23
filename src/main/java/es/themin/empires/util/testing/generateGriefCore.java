package es.themin.empires.util.testing;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import es.themin.empires.cmds.SubCommand;
import es.themin.empires.cores.Core;
import es.themin.empires.cores.CoreFactory;
import es.themin.empires.enums.CoreType;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;

public class generateGriefCore extends SubCommand{

	private ManagerAPI myApi = null;
	
	public generateGriefCore(ManagerAPI api) {
		myApi = api;
	}

	public boolean onCommand(Player player, String[] args) {
		EPlayer myEPlayer = myApi.getEPlayer(player);
		
		Core myCore = CoreFactory.CreateCore(myEPlayer.getEmpireUUID(), myEPlayer.getLocation(), CoreType.GRIEF);
		
		Bukkit.getServer().getLogger().info(myEPlayer.getEmpireUUID().toString());
		myApi.generateCore(myEPlayer, myCore);
		
		return false;
		
	}

	public String name() {
		return "generategriefcore";
	}

	public String info() {
		return "generates a grief core";
	}

	public String[] aliases() {
		return new String[] {"gengrief","ggc"};
	}

}
