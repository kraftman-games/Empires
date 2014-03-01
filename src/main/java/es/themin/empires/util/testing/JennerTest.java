package es.themin.empires.util.testing;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import es.themin.empires.cmds.SubCommand;

public class JennerTest extends SubCommand{

	@Override
	public boolean onCommand(Player player, String[] args) {
		Location l = player.getLocation();
		l.setX(player.getLocation().getX() + 2);
		Skeleton skele = (Skeleton) player.getWorld().spawnEntity(l, EntityType.SKELETON);
		skele.setRemoveWhenFarAway(false);
		skele.setSkeletonType(SkeletonType.NORMAL);
		skele.setCanPickupItems(true);
		
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
		LeatherArmorMeta helmetmeta = (LeatherArmorMeta) helmet.getItemMeta();
		helmetmeta.setColor(Color.GREEN);
		helmet.setItemMeta(helmetmeta);
		
		ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta chestmeta = (LeatherArmorMeta) chest.getItemMeta();
		chestmeta.setColor(Color.GREEN);
		chest.setItemMeta(chestmeta);
		
		ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		LeatherArmorMeta legmeta = (LeatherArmorMeta) leg.getItemMeta();
		legmeta.setColor(Color.GREEN);
		leg.setItemMeta(legmeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
		bootsmeta.setColor(Color.GREEN);
		boots.setItemMeta(bootsmeta);
		
		ItemStack bow = new ItemStack(Material.BOW, 1);

		
		skele.setCustomName(player.getName() + "'s archer");
		skele.setCustomNameVisible(true);
		
		World w = player.getWorld();
		w.dropItem(skele.getLocation(), helmet);
		w.dropItem(skele.getLocation(), chest);
		w.dropItem(skele.getLocation(), leg);
		w.dropItem(skele.getLocation(), boots);
		w.dropItem(skele.getLocation(), bow);
		return false;
				
		
	}

	@Override
	public String name() {
		return "jennertest";
	}

	@Override
	public String info() {
		return null;
	}

	@Override
	public String[] aliases() {
		return new String[] {"jt"};
	}

}
