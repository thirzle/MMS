package management;

public class SelfStudy {

	@SuppressWarnings("unused")
	private int selfstudyID;
	private String title;
	private int time;


	public SelfStudy(int selfstudyID, int time) {
		this.selfstudyID = selfstudyID;
		this.time = time;
	}
	
	
	public SelfStudy(int selfstudyID, int time, String title) {
		this.selfstudyID = selfstudyID;
		this.time = time;
		this.title = title;
	}


	public String toString() {
		return "to do...";
	}


	public int getTime() {
		return time;
	}
}
