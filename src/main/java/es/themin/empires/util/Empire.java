package es.themin.empires.util;

public class Empire {
	
	private int Id;
	private String name;
	
	public Empire(int Id){
		this.Id = Id;
	}
	
	public int getId(){
		return Id;
	}
	
	public String getName(){
		return name;
	}
	
}
