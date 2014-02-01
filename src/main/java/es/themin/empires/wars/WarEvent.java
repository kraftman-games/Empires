package es.themin.empires.wars;

public class WarEvent {
	
	//This is a potential idea, im not sure yet
	private Long when;
	public enum WarEventType {BATTLSTART,BATTLEEND, START, END, EMPIREJOIN, EMPIRELEAVE, EMPIREFORCEDLEAVE, }
	private WarEventType type;
	public WarEvent(Long when, WarEventType type) {
		this.when = when;
		
	}
}
