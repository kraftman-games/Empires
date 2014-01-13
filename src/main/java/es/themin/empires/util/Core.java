package es.themin.empires.util;

public class Core {
	
	private int Id;
	private CoreType type;
	public Core(int Id, CoreType type) {
		this.Id = Id;
		this.type = type;
	}
	public int getId(){
		return Id;
	}
	public CoreType getType(){
		return type;
	}
	

}
