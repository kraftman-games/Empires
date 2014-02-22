package es.themin.empires.cmds.empire.subcmd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;

public class InvitePlayer extends EmpireSubCommand{

	private ManagerAPI myApi = null;
	
	public InvitePlayer(ManagerAPI api) {
		myApi = api;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		EPlayer myEPlayer = myApi.getEPlayer(player);
		if (myEPlayer == null || myEPlayer.getEmpireUUID() == null) {
			myEPlayer.sendMessage(MsgManager.notinemp);
			return false;
		}
		Empire empire = myApi.getEmpire(myEPlayer.getEmpireUUID());
		if (args.length == 1) {
			myEPlayer.sendMessage(MsgManager.toofewargs);
			return false;
		}
		if (Bukkit.getServer().getPlayer(args[1]) == null) {
			myEPlayer.sendMessage( ChatColor.RED +"That player is not online.");
			return false;
		}
		Player invited = Bukkit.getServer().getPlayer(args[1]);
		if (!invited.isOnline()) {
			myEPlayer.sendMessage( ChatColor.RED +"That player is not online.");
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
