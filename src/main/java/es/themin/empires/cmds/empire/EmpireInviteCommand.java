package es.themin.empires.cmds.empire;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;
import es.themin.empires.util.UtilManager;

public class EmpireInviteCommand extends EmpireSubCommand{

	public String plprefix = empires.plprefix;
	private empires myPlugin;
	
	public EmpireInviteCommand(empires empires) {
		myPlugin = empires;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if (!myPlugin.getEmpireplayers().containsKey(player.getName())) {
			player.sendMessage(MsgManager.notinemp);
			return false;
		}
		Empire empire = myPlugin.getEmpireplayers().get(player.getName());
		if (args.length == 1) {
			player.sendMessage(MsgManager.toofewargs);
			return false;
		}
		if (Bukkit.getServer().getPlayer(args[1]) == null) {
			player.sendMessage(plprefix + ChatColor.RED +"That player is not online.");
			return false;
		}
		Player invited = Bukkit.getServer().getPlayer(args[1]);
		if (!invited.isOnline()) {
			player.sendMessage(plprefix + ChatColor.RED +"That player is not online.");
			return false;
		}
		return false;
		
	}

	@Override
	public String name() {
		return "invite";
	}

	@Override
	public String info() {
		return "invite a player to join you empire";
	}

	@Override
	public String[] aliases() {
		return new String[] {"invite"};
	}

	@Override
	public String[] variables() {
		return new String[] {"player"};
	}

	@Override
	public EmpirePermission permission() {
		return EmpirePermission.INVITE;
	}
	

}
