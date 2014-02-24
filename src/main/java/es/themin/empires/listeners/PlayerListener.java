package es.themin.empires.listeners;


import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import es.themin.empires.Debug;
import es.themin.empires.managers.ManagerAPI;
import es.themin.empires.util.EPlayer;
import es.themin.empires.util.EWorld;
import es.themin.empires.util.Empire;

public class PlayerListener implements Listener{
	
	private ManagerAPI myApi = null;
	
	public PlayerListener(ManagerAPI myAPI){
		myApi = myAPI;
	}
	
	
	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		
		myApi.loadPlayer(player);
		
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		EPlayer myEPlayer = myApi.getEPlayer(event.getPlayer());
		myApi.removePlayer(myEPlayer);
		
	}
		
	@EventHandler
	  public void onPlayerInteractEvent(PlayerInteractEvent event){
		if (event.getAction() == Action.LEFT_CLICK_BLOCK){
			myApi.handleBlockLeftClick(event);
		} else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			myApi.handleBlockRightClick(event);
			
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		long time = System.currentTimeMillis();
		EPlayer myEPlayer = myApi.getEPlayer(event.getPlayer());
		
		Location newLocation = event.getPlayer().getLocation().getBlock().getLocation();
		EWorld myEWorld = myApi.getEWorld(myEPlayer.getWorld().getUID());
		
		if (myEPlayer.getLastLocationCheck() < (time - 1000)){
			Debug.Console("Checking "+myEPlayer.getName()+"'s location");
			if (newLocation.equals(myEPlayer.getLastLocation())){
				//they havent moved
			} else {
				Debug.Console(myEPlayer.getName()+" has moved to new block: X: "+newLocation.getBlockX()+" Z: "+newLocation.getBlockZ());
				UUID empireUuid = myEWorld.getEmpireUUID(newLocation);
				String locationName = "Wilderness";
				if (empireUuid != null){
					Empire myEmpire = myApi.getEmpire(empireUuid);
					locationName = myEmpire.getName();
				}
				if (myEPlayer.getLastLocationName() != locationName){
					myEPlayer.sendMessage("~"+locationName);
					myEPlayer.setLastLocationName(locationName);
				}
				
				
				myEPlayer.setLastLocation(newLocation);
			}
			
			
			myEPlayer.setLastLocationCheck(time);
		}
		
		//check every x ms
		//round their location down to the nearest block
		//see if that block has changed
		//check if their empire location has changed
		//update them and reset the time etc if it has

	}
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (player.getKiller() instanceof Player) {
			Player killer = (Player) player.getKiller();
			
			EPlayer defender = myApi.getEPlayer(player);
			EPlayer attacker = myApi.getEPlayer(killer);
			
			
			if (defender != null && attacker != null) {
				
				
//				if (attacker.isAtWarWith(defender)) {
//					War war = attacker.getEmpire().getWarAgainst(defender.getEmpire());
//					if (!(attacker.getEmpire().isInABattle()) && !(defender.getEmpire().isInABattle())) {
//						Empire team1 = null;
//						Empire team2 = null;
//						BattleTeam attackingteam = null;
//						if (war.getAllEmpiresOnTeam1().contains(attacker)) {
//							team1 = attacker.getEmpire();
//							attackingteam = BattleTeam.team1;
//							team2 = defender.getEmpire();
//						}else if (war.getAllEmpiresOnTeam1().contains(defender)){
//							team1= defender.getEmpire();
//							attackingteam = BattleTeam.team2;
//							team2 = attacker.getEmpire();
//						}
//						Battle battle = new Battle(team1, team2, war, BattleType.DEATHMATCH, attackingteam);
//						battle.start();
//						war.addBattle(battle);
//						war.Save();
//						for (Empire empire : war.getAllEmpires()) {
//							if (empire != attacker.getEmpire() && empire != defender.getEmpire()) {
//								empire.broadcastMessage(warprefix + ChatColor.RED + "A Battle has broken out between " + attacker.getEmpire().getName() + " and "  + defender.getEmpire().getName() + ". Both sides fight to the death");
//							}
//						}
//						attacker.getEmpire().broadcastMessage(warprefix + ChatColor.RED + killer.getName() + " Has begun a battle against " + defender.getEmpire().getName() + " slaughter them to to tip the balance of the war in your favour");
//						defender.getEmpire().broadcastMessage(warprefix + ChatColor.RED + player.getName() + " Was killed by a member of " + attacker.getEmpire().getName() + " slaughter them to to tip the balance of the war in your favour");
//						battle.addPointsToTeamWithEmpire(attacker.getEmpire(), 1);
//					}else if (attacker.getEmpire().isInBattleWith(defender.getEmpire())) {
//						Battle battle = war.getOnGoingBattle();
//						if (battle.getType() == BattleType.DEATHMATCH) {
//							//Bukkit.broadcastMessage("kill");
//							battle.addPointsToTeamWithEmpire(attacker.getEmpire(), 1);
//						}
//					}
//					
//				}
			}
		}
		
	}
	
}



//this is the code to move them to a random location

//Block myBlock = myPlayer.getWorld().getHighestBlockAt(x,z);
//myBlock.getChunk().load();
//Chunk myChunk = myBlock.getChunk();
////myChunk.load(true);
//
//if (myBlock.getType() != Material.AIR){
//	if (myBlock.getType() == Material.GRASS){
//		
//		
//		myPlayer.getWorld().loadChunk(myChunk);
//		myPlayer.getWorld().refreshChunk(myChunk.getX(), myChunk.getZ());
//		myPlayer.sendMessage("X: "+x+"Z: "+z);
//		myPlayer.setFallDistance(0.0F);
//		myPlayer.teleport(new Location(myBlock.getWorld(), myBlock.getX(), myBlock.getY()+1, myBlock.getZ()));
//		
//		
////		myPlayer.setAllowFlight(true);
////		myPlayer.setFlying(true);
////		
////		UtilManager.tannerTemp = myBlock.getLocation();
////		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
////		Plugin thisPlugin = Bukkit.getServer().getPluginManager().getPlugin("Empires");
////		scheduler.scheduleSyncDelayedTask(thisPlugin, new Runnable() {
////			@Override
////            public void run() {
////				Bukkit.getServer().loadEPlayer("kraftman").teleport(UtilManager.tannerTemp);
////				Bukkit.getServer().loadEPlayer("kraftman").setFlying(false);
////
////				Bukkit.getServer().loadEPlayer("kraftman").setAllowFlight(false);
////				Bukkit.getServer().loadEPlayer("kraftman").sendMessage("moving you");
////            }
////        }, 400L);
//		
//		
//		return true;
//	} else {
//		return false;
//	}
//}
