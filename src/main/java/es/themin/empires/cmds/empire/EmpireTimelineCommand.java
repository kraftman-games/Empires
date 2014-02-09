package es.themin.empires.cmds.empire;

import java.util.HashMap;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import es.themin.empires.empires;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;
import es.themin.empires.util.UtilManager;

public class EmpireTimelineCommand extends EmpireSubCommand{

	
	private Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Empires");
	private static HashMap<Player, Integer> cancelmap = new HashMap<Player,Integer>();
	private empires myPlugin;
	
	public EmpireTimelineCommand(empires plugin){
		myPlugin = plugin;
	}
	
	@Override
	public boolean onCommand(final Player player, String[] args) {
		if (!myPlugin.empireplayers.containsKey(player.getName())) {
			player.sendMessage(MsgManager.notinemp);
			return false;
		}
		
		final int i =0;
		final Empire empire = myPlugin.empireplayers.get(player.getName());
		final int passes = empire.getTimeLine().size();
		int dit = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				int i2 = i;
				//TODO
			}
			
		},0L,10L);
		cancelmap.put(player, dit);
		return false;
	}

	@Override
	public String name() {
		return "timeline";
	}

	@Override
	public String info() {
		return "display a full timeline of your empire (may take several minutes)";
	}

	@Override
	public String[] aliases() {
		return new String[] {"timeline"};
	}

	@Override
	public String[] variables() {
		return null;
	}

	@Override
	public EmpirePermission permission() {
		return null;
	}
	

}
