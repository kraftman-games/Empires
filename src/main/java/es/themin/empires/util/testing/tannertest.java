package es.themin.empires.util.testing;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import es.themin.empires.cmds.SubCommand;
import es.themin.empires.cores.Core;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.Empire;
import es.themin.empires.util.UtilManager;

public class tannertest extends SubCommand{

	@Override
	public boolean onCommand(Player player, String[] args) {
		
		int range = 10000;
		
		int z = (int)(Math.random()*range);
		int x = (int)(Math.random()*range);
		
		player.sendMessage("X: "+x+"Z: "+z);
		
		while (getHabitableZone(player, x, z) == false){
			player.sendMessage("X: "+x+"Z: "+z);
			z = (int)(Math.random()*range);
			x = (int)(Math.random()*range);
		}
		
		
		return false;

	}

	@Override
	public String name() {
		return "tannertest";
	}

	@Override
	public String info() {
		return "who knows";
	}

	@Override
	public String[] aliases() {
		return new String[] {"tt"};
	}
	
	private boolean getHabitableZone(Player myPlayer, int x, int z){
		
		Block myBlock = myPlayer.getWorld().getHighestBlockAt(x,z);
		myBlock.getChunk().load();
		Chunk myChunk = myBlock.getChunk();
		//myChunk.load(true);
		
		if (myBlock.getType() != Material.AIR){
			if (myBlock.getType() == Material.GRASS){
				
				
				myPlayer.getWorld().loadChunk(myChunk);
				myPlayer.getWorld().refreshChunk(myChunk.getX(), myChunk.getZ());
				myPlayer.sendMessage("X: "+x+"Z: "+z);
				myPlayer.setFallDistance(0.0F);
				//myPlayer.teleport(myBlock.getLocation());
				
				UtilManager.tannerTemp = myBlock.getLocation();
				BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
				Plugin thisPlugin = Bukkit.getServer().getPluginManager().getPlugin("Empires");
				scheduler.scheduleSyncDelayedTask(thisPlugin, new Runnable() {
					@Override
		            public void run() {
						Bukkit.getServer().getPlayer("kraftman").teleport(UtilManager.tannerTemp);
						Bukkit.getServer().getPlayer("kraftman").sendMessage("moving you");
		            }
		        }, 120L);
				
				
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}

//int x = Integer.parseInt(args[1]);
//int y = Integer.parseInt(args[2]);
//
//int z = Integer.parseInt(args[2]);
//player.sendMessage("x: "+ x + "y: " + y + "z: "+z);
//
//
//
//Location newLoc = new Location(player.getWorld(), x, y, z);
//
//Chunk myChunk = newLoc.getBlock().getChunk();
//
//myChunk.load(true);
//
//player.sendMessage(myChunk.isLoaded()+ " block type: "+newLoc.getBlock().getType());


//Empire empire = UtilManager.empireplayers.get(player.getName());
//Core myCore = new Core(UtilManager.nextUnusedCoreId(), CoreType.GRIEF, player.getLocation(), 1, empire);
//empire.addCore(myCore);
//myCore.Save();
//player.sendMessage("testing grief block");
//return false;
//

//ItemStack myItem = new ItemStack(Material.FLINT);
//ItemMeta myMeta = myItem.getItemMeta();
//myMeta.setDisplayName("Core Shard");
//
//ArrayList<String> myLore = new ArrayList<String>();
//myLore.add(ChatColor.GREEN + "A faint glow eminates from within");
//
//myMeta.setLore(myLore);
//
//myItem.setItemMeta(myMeta);
//
//player.getInventory().addItem(myItem);
//player.sendMessage( ChatColor.RED + "You won teh shardz!");
//return false;