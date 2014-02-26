package es.themin.empires.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class RegenBlock {
	
	private int x,y,z;
	private World world;
	private byte data;
	private Material material;
	public RegenBlock(World world,int x, int y, int z, Material material, byte data) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.material = material;
		this.data = data;
	}
	public RegenBlock(World world,int x, int y, int z, Material material) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.material = material;
		byte d = 0x0;
		this.data = d;
	}
	@SuppressWarnings("deprecation")
	public void regen() {
		Block block = world.getBlockAt(x, y, z);
		if (block.getType() == Material.AIR || block.getType() == Material.FIRE) {
			block.setType(material);
			block.setData(data);
		}
	}
	public static ArrayList<RegenBlock> parseBlocks(ArrayList<Block> blocks) {
		ArrayList<RegenBlock> rblocks = new ArrayList<RegenBlock>();
		for (Block b : blocks) {
			@SuppressWarnings("deprecation")
			RegenBlock rb = new RegenBlock(b.getWorld(),b.getX(),b.getY(),b.getZ(),b.getType(),b.getData());
			rblocks.add(rb);
		}
		return rblocks;
	}
	public static ArrayList<RegenBlock> parseBlocks(List<Block> blocks) {
		ArrayList<RegenBlock> rblocks = new ArrayList<RegenBlock>();
		for (Block b : blocks) {
			@SuppressWarnings("deprecation")
			RegenBlock rb = new RegenBlock(b.getWorld(),b.getX(),b.getY(),b.getZ(),b.getType(),b.getData());
			rblocks.add(rb);
		}
		return rblocks;
	}
}
