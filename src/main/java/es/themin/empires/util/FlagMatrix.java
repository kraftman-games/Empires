package es.themin.empires.util;

import org.bukkit.Material;

import es.themin.empires.empires;
import es.themin.empires.managers.EmpireManager;
import es.themin.empires.managers.PlayerManager;

public class FlagMatrix {

	private empires myPlugin;
	public String plprefix;
	private EmpireManager Empires;
	private PlayerManager Players;
	private Material m1,m2,m3,m4;
	public FlagMatrix(Material m1, Material m2, Material m3, Material m4, empires plugin) {
		myPlugin = plugin;
		plprefix = plugin.plprefix;
		Empires = plugin.Empires;
		Players = plugin.Players;
	}
	public Material getM1(){
		return m1;
	}
	public Material getM2(){
		return m2;
	}
	public Material getM3(){
		return m3;
	}
	public Material getM4(){
		return m4;
	}
	public Material getM(int i) {
		switch (i) {
		case 1:return m1;
		case 2:return m2;
		case 3:return m3;
		case 4:return m4;
		default: return null;
		}
	}
}
