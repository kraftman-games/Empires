package es.themin.empires.enums;

public enum PlaceType {
	/**
	 * Can only be placed inside the boundaris of an empire
	 */
	INSIDE, 
	/**
	 * Can be placed outside an empire, eg outpost or base
	 */
	OUTSIDE,
	/**
	 * Must have an edge touching a current empire, eg amps
	 */
	EDGE,
	/**
	 * For future use maybe. Can only be placed inside an enemies empire, eg teleport or heal
	 */
	ENEMY
}
