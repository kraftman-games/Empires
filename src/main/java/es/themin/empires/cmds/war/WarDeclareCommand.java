package es.themin.empires.cmds.war;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import es.themin.empires.empires;
import es.themin.empires.cmds.empire.EmpireSubCommand;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.managers.SettingsManager;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.Empire;
import es.themin.empires.util.Rank;
import es.themin.empires.wars.War;

public class WarDeclareCommand extends EmpireSubCommand{
	
	
	public String warprefix = empires.warprefix;
	private ManagerAPI myApi = null;
	
	public WarDeclareCommand(ManagerAPI myAPI) {
		myApi = myAPI;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		EPlayer myEPlayer = myApi.getEPlayer(player);
		
		if (myEPlayer != null && myEPlayer.getEmpireUUID() != null) {
			Empire empire = myApi.getEmpire(myEPlayer.getEmpireUUID());
			if (!(empire.getOwnerUUID() == myEPlayer.getUUID())) {
				if (empire.playerHasARank(player.getName())) {
					Rank rank = empire.getRankOfPlayer(player.getName());
					if (!(rank.hasPermission(EmpirePermission.ATTACK))) player.sendMessage( ChatColor.RED +"You do not have permission to do this"); return false;
				}else {
					return false;
				}
			}
			if (args.length == 1) {
				player.sendMessage( ChatColor.RED + "Please define an empire");
				return false;
			}
			if ((myApi.getEmpire(args[1]) == null)) {
				player.sendMessage( ChatColor.RED + "That is not an empire");
				return false;
			}
			Empire attacked = myApi.getEmpire(args[1]);
			if (empire.getXP() - attacked.getXP() < -50) {
				player.sendMessage( ChatColor.RED + "You are too strong to attack this empire");
				return false;
			}
			if (empire.exAlliesContains(attacked)) {
				if (empire.getLastAllianceWith(attacked) + SettingsManager.getConfig().getLong("ex_ally_timer") * 60 * 1000 > System.currentTimeMillis()) {
					player.sendMessage( ChatColor.RED + "You cannot abandon an Ally then attack them this quickly, try again later");
					return false;
				}
			}
			War war = new War(empire, attacked);
			war.start();
			//war.upDateEmpires();
			empire.broadcastMessage(warprefix + ChatColor.RED + player.getDisplayName() + " declared war on " + attacked.getName());
			attacked.broadcastMessage(warprefix + ChatColor.RED + empire.getName() + " decalred war on you");
			war.Save();
		}else {
			player.sendMessage( ChatColor.RED + "You are not in an empire");
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
