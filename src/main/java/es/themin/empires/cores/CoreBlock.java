package es.themin.empires.cores;

import java.util.ArrayList;

import org.bukkit.Material;

import es.themin.empires.enums.ProtectionType;

public class CoreBlock {
	private int offsetX;
	private int offsetY;
	private int offsetZ;
	private Material material;	
	
	

	public CoreBlock(int x, int y, int z, Material material){
		this.offsetX = x;
		this.offsetY = y;
	    this.offsetZ = z;
	    this.material = material;
	    
	    
	}

	public int getOffsetX() {
		return offsetX;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	public int getOffsetZ() {
		return offsetZ;
	}

	public void setOffsetZ(int offsetZ) {
		this.offsetZ = offsetZ;
	}
	
}
