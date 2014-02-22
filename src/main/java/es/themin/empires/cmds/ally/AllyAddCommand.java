package es.themin.empires.cmds.ally;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.EmpireSubCommand;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.managers.SettingsManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;

public class AllyAddCommand extends EmpireSubCommand{

	private Long time = SettingsManager.getConfig().getLong("time_between_re_ally");
	
	private ManagerAPI myApi = null;
	
	public AllyAddCommand(ManagerAPI myAPI) {
		myApi = myAPI;
	}

	@Override
	public boolean onCommand(EPlayer myEPlayer, String[] args) {
		
		if (!myEPlayer.isInEmpire()) {
			myEPlayer.sendMessage(MsgManager.notinemp);
			return false;
		}
		Empire empire = myApi.getEmpire(myEPlayer);
		if (args.length == 1) {
			myEPlayer.sendMessage(MsgManager.toofewargs + " do '/ally ?' for help");
			return false;
		}if (myApi.getEmpire(args[1]) == null) {
			myEPlayer.sendMessage(MsgManager.empirenotfound);
			return false;
		}
		Empire ally = myApi.getEmpire(args[1]);
		if (empire.isAtWarWith(ally)) {
			myEPlayer.sendMessage( ChatColor.RED  +"You cannot ally with empires you are at war with");
			return false;
		}/*if (empire.exEnemiesContains(ally)) {
			Long lastenemy = empire.getLastEnemyWith(ally);
			if (System.currentTimeMillis() > lastenemy + TODO) ;
		}*/
		if (empire.hasAllyRequestFrom(ally)) {
			empire.addAlly(ally);
			ally.addAlly(empire);
			empire.broadcastMessage( ChatColor.GREEN + "You are now allies with " + ally.getName());
			ally.broadcastMessage( ChatColor.GREEN + "You are now allies with " + empire.getName());
			if (empire.exAlliesContains(ally)) {
				empire.removeExAlly(ally);
			}
			if (ally.exAlliesContains(empire)) {
				ally.removeExAlly(empire);
			}
			if (empire.exEnemiesContains(ally)) {
				empire.removeExEnemy(ally);
			}if (ally.exEnemiesContains(empire)) {
				ally.removeExEnemy(empire);
			}
			empire.removeAllyRequest(ally);
			return false;
		}
		if (empire.exAlliesContains(ally)) {
			Long lastalliance = empire.getLastAllianceWith(ally);
			if (System.currentTimeMillis() < lastalliance + time) {
				myEPlayer.sendMessage(ChatColor.RED + "You cannot re-ally this empire yet");
				return false;
			}
		}
		if (ally.hasAllyRequestFrom(empire)) {
			myEPlayer.sendMessage(ChatColor.RED + "Your empire has already sent this empire an alliance request"); 
			return false;
		}
		ally.addAllyRequest(empire);
		ally.broadcastMessage( ChatColor.GREEN + empire.getName() + " Has sent your empire an alliance request, do '/ally add " + empire.getName()+"' to accept it");
		empire.broadcastMessage( ChatColor.GREEN + myEPlayer.getName() + " has sent an alliance request to " + ally.getName());
		return false;
	}

	@Override
	public String name() {
		return "add";
	}

	@Override
	public String info() {
		return "send a request to another empire to ally with them";
	}

	@Override
	public String[] aliases() {
		return new String[] {"add"};
	}

	@Override
	public String[] variables() {
		return new String[] {"emprie"};
	}

	@Override
	public EmpirePermission permission() {
		return EmpirePermission.ALLY;
	}

	
}
