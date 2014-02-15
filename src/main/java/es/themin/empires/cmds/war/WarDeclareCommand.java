package es.themin.empires.cmds.war;

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
import es.themin.empires.util.Rank;
import es.themin.empires.wars.War;

public class WarDeclareCommand extends EmpireSubCommand{
	
	public String plprefix;
	public String warprefix = empires.warprefix;
	private empires myPlugin;
	private EmpireManager Empires;
	private PlayerManager Players;
	
	public WarDeclareCommand(empires plugin) {
		myPlugin = plugin;
		Empires = plugin.Empires;
		plprefix = plugin.plprefix;
		Players = plugin.Players;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		EPlayer myCorePlayer = Players.getPlayer(player.getUniqueId());
		
		if (myCorePlayer != null && myCorePlayer.getEmpire() != null) {
			Empire empire = myCorePlayer.getEmpire();
			if (!(empire.getOwner() == myCorePlayer.getUUID())) {
				if (empire.playerHasARank(player.getName())) {
					Rank rank = empire.getRankOfPlayer(player.getName());
					if (!(rank.hasPermission(EmpirePermission.ATTACK))) player.sendMessage(plprefix + ChatColor.RED +"You do not have permission to do this"); return false;
				}else {
					return false;
				}
			}
			if (args.length == 1) {
				player.sendMessage(plprefix + ChatColor.RED + "Please define an empire");
				return false;
			}
			if (!(Empires.containsEmpireWithName(args[1]))) {
				player.sendMessage(plprefix + ChatColor.RED + "That is not an empire");
				return false;
			}
			Empire attacked = Empires.getEmpireWithName(args[1]);
			if (empire.getExp() - attacked.getExp() < -50) {
				player.sendMessage(plprefix + ChatColor.RED + "You are too strong to attack this empire");
				return false;
			}
			if (empire.exAlliesContains(attacked)) {
				if (empire.getLastAllianceWith(attacked) + SettingsManager.getConfig().getLong("ex_ally_timer") * 60 * 1000 > System.currentTimeMillis()) {
					player.sendMessage(plprefix + ChatColor.RED + "You cannot abandon an Ally then attack them this quickly, try again later");
					return false;
				}
			}
			War war = new War(myPlugin, empire, attacked);
			war.start();
			//war.upDateEmpires();
			empire.broadcastMessage(warprefix + ChatColor.RED + player.getDisplayName() + " declared war on " + attacked.getName());
			attacked.broadcastMessage(warprefix + ChatColor.RED + empire.getName() + " decalred war on you");
			war.Save();
		}else {
			player.sendMessage(plprefix + ChatColor.RED + "You are not in an empire");
		}
		return false;
	}

	@Override
	public String name() {
		return "declare";
	}

	@Override
	public String info() {
		return "Declare war on another empire";
	}

	@Override
	public String[] aliases() {
		return new String[] {"declare"};
	}

	@Override
	public String[] variables() {
		return null;
	}

	@Override
	public EmpirePermission permission() {
		return EmpirePermission.ATTACK;
	}
	private static int getDifferenceBetween(int i1, int i2) {
		if (i1 > i2) return i1-i2;
		if (i2 > i1) return i2-i1;
		return 0;
	}

}
