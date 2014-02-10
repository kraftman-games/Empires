package es.themin.empires.cmds.ally;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.PlayerManager;
import es.themin.empires.empires;
import es.themin.empires.cmds.empire.EmpireSubCommand;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.CorePlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;
import es.themin.empires.util.UtilManager;

public class AllyRequestsCommand extends EmpireSubCommand{

	private empires myPlugin;
	private PlayerManager Players;
	
	public AllyRequestsCommand(empires plugin) {
		myPlugin = plugin;
		Players = plugin.Players;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		CorePlayer myCorePlayer = Players.getPlayer(player.getUniqueId());
		
		if (myCorePlayer == null || myCorePlayer.getEmpire() == null) {
			player.sendMessage(MsgManager.notinemp);
			return false;
		}
		Empire empire = myCorePlayer.getEmpire();
		player.sendMessage(MsgManager.createTitle(ChatColor.LIGHT_PURPLE + "Alliance Requests", ChatColor.GOLD));
		int i =0;
		for (Empire ally : empire.getAllianceRequests().keySet()) {
			i++;
			player.sendMessage("- " + ChatColor.GREEN + ally.getName() + ChatColor.WHITE + " - " + ChatColor.GOLD + MsgManager.createSmartTimeStamp(empire.getAllianceRequests().get(ally)));
		}
		if (i==0) player.sendMessage(ChatColor.RED + "You have not alliance requests :(");
		return false;
	}

	@Override
	public String name() {
		return "requests";
	}

	@Override
	public String info() {
		return "gives a lists of pending alliance requests";
	}

	@Override
	public String[] aliases() {
		return new String[] { "requests"};
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
