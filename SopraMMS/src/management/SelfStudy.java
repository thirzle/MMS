package management;

public class SelfStudy {
	
	@SuppressWarnings("unused")
	private String title;
	
	private int time;
	
	public SelfStudy(String title, int time){
		this.title = title;
		this.time = time;
	}
	
	public String toString(){
		return "to do...";
	}
	
	public int getTime(){
		return time;
	}
}
