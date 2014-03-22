package es.themin.empires.cores;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;

import es.themin.empires.enums.CoreType;
import es.themin.empires.enums.EmpirePermission;
import es.themin.empires.enums.PlaceType;
import es.themin.empires.util.EPlayer;

public interface ICore {

	
	public CoreType getCoreType();
	public PlaceType getPlaceType();
	public Location getLocation();
	public UUID getUUID();
	public UUID getEmpireUUID();
	public Integer getAreaSize();
	public Integer getCoreSize();
	public HashMap<UUID, EPlayer> getPlayers();
	public int getLevel();
	public CoreType getType();
	public void showEdges(Boolean edgesShown);
	public boolean isInArea(Integer locX, Integer locZ);
	public boolean isInCore(int x, int y, int z);
	public void build();
	public EmpirePermission getPlacePermission();
	public void setEmpireUUID(UUID uuid);
	public void setUUID(UUID randomUUID);
	public void OnEnter(EPlayer myEPlayer);
	public void OnLeave(EPlayer myEPlayer);
	public void OnUpdate(EPlayer myEPlayer);
}
