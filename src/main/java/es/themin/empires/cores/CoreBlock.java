package es.themin.empires.cores;

import org.bukkit.Material;

import es.themin.empires.enums.ProtectionType;

public class CoreBlock {
	private int offsetX;
	private int offsetY;
	private int offsetZ;
	private Material material;	
	private ProtectionType protection;
	
	
	
	public ProtectionType getProtection() {
		return protection;
	}

	public void setProtection(ProtectionType protection) {
		this.protection = protection;
	}

	public CoreBlock(int x, int y, int z, Material material, ProtectionType protection){
		this.offsetX = x;
		this.offsetY = y;
	    this.offsetZ = z;
	    this.material = material;
	    this.protection = protection;
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
