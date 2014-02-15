package es.themin.empires.cmds.ally;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.empire.EmpireSubCommand;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.EmpireManager;
import es.themin.empires.managers.PlayerManager;
import es.themin.empires.managers.SettingsManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.MsgManager;

public class AllyAddCommand extends EmpireSubCommand{

	
	public String plprefix;
	private Long time = SettingsManager.getConfig().getLong("time_between_re_ally");
	private  empires myPlugin;
	private  EmpireManager Empires;
	private PlayerManager Players;
	
	public AllyAddCommand(empires plugin) {
		myPlugin = plugin;
		Empires = plugin.Empires;
		plprefix = plugin.plprefix;
		Players = plugin.Players;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		
		EPlayer myPlayer = Players.getPlayer(player.getUniqueId());
		
		if (myPlayer == null) {
			player.sendMessage(MsgManager.notinemp);
			return false;
		}
		Empire empire = myPlayer.getEmpire();
		if (args.length == 1) {
			player.sendMessage(MsgManager.toofewargs + " do '/ally ?' for help");
			return false;
		}if (!Empires.containsEmpireWithName(args[1])) {
			player.sendMessage(MsgManager.empirenotfound);
			return false;
		}
		Empire ally = Empires.getEmpireWithName(args[1]);
		if (empire.isAtWarWith(ally)) {
			player.sendMessage(plprefix + ChatColor.RED  +"You cannot ally with empires you are at war with");
			return false;
		}/*if (empire.exEnemiesContains(ally)) {
			Long lastenemy = empire.getLastEnemyWith(ally);
			if (System.currentTimeMillis() > lastenemy + TODO) ;
		}*/
		if (empire.hasAllyRequestFrom(ally)) {
			empire.addAlly(ally);
			ally.addAlly(empire);
			empire.broadcastMessage(plprefix + ChatColor.GREEN + "You are now allies with " + ally.getName());
			ally.broadcastMessage(plprefix + ChatColor.GREEN + "You are now allies with " + empire.getName());
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
				player.sendMessage(plprefix + ChatColor.RED + "You cannot re-ally this empire yet");
				return false;
			}
		}
		if (ally.hasAllyRequestFrom(empire)) {
			player.sendMessage(plprefix + ChatColor.RED + "Your empire has already sent this empire an alliance request"); 
			return false;
		}
		ally.addAllyRequest(empire);
		ally.broadcastMessage(plprefix + ChatColor.GREEN + empire.getName() + " Has sent your empire an alliance request, do '/ally add " + empire.getName()+"' to accept it");
		empire.broadcastMessage(plprefix + ChatColor.GREEN + player.getName() + " has sent an alliance request to " + ally.getName());
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
