package management;

public class SelfStudy {

	@SuppressWarnings("unused")
	private int selfstudyID;

	private int time;


	public SelfStudy(int selfstudyID, int time) {
		this.selfstudyID = selfstudyID;
		this.time = time;
	}


	public String toString() {
		return "to do...";
	}


	public int getTime() {
		return time;
	}
}
