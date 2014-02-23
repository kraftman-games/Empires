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

public class generatebasecore extends SubCommand{

	private ManagerAPI myApi = null;
	
	public generatebasecore(ManagerAPI api) {
		myApi = api;
	}

	public boolean onCommand(Player player, String[] args) {
		EPlayer myEPlayer = myApi.getEPlayer(player);
		
		Core myCore = CoreFactory.CreateCore(myEPlayer, CoreType.BASE);
		
		Bukkit.getServer().getLogger().info(myEPlayer.getEmpireUUID().toString());
		myApi.generateCore(myEPlayer, myCore);
		
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
