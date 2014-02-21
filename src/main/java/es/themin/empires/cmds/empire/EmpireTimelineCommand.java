package es.themin.empires.cmds.empire;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import es.themin.empires.empires;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.EmpireManager;
import es.themin.empires.managers.PlayerManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;

public class EmpireTimelineCommand extends EmpireSubCommand{

	
	private Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Empires");
	private static HashMap<Player, Integer> cancelmap = new HashMap<Player,Integer>();
	private empires myPlugin;
	private PlayerManager Players;
	private EmpireManager Empires;
	
	public EmpireTimelineCommand(empires plugin){
		myPlugin = plugin;
		Players = plugin.Players;
		Empires = plugin.Empires;
	}
	
	@Override
	public boolean onCommand(final Player player, String[] args) {
		EPlayer myCorePlayer = Players.getPlayer(player.getUniqueId());
		
		if (myCorePlayer == null || myCorePlayer.getEmpireUUID() == null) {
			player.sendMessage(MsgManager.notinemp);
			return false;
		}
		
		final int i =0;
		final Empire empire = Empires.getEmpire(myCorePlayer.getEmpireUUID());
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
