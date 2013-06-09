package management;

import java.util.Calendar;
import java.util.Random;


//TODO:
//namen adden bitte, alle die an der Klasse geschafft haben...
/**
 * 
 * @author AJ, add names here...
 *
 */
public class SelfStudy {

	private long selfstudyID;
	private String title;
	private int time;
	private Random random;

	public SelfStudy(String title, int time) {
		random = new Random();
		this.selfstudyID = createSelfStudyID(); 
		this.title = title;
		this.time = time;
		
	}

	public SelfStudy(long selfstudyID, int time, String title) {
		this.selfstudyID = selfstudyID;
		this.time = time;
		this.title = title;
	}

	private long createSelfStudyID() {
		return random.nextInt(1000) + 100
				* Calendar.getInstance().getTimeInMillis();

	}

	public String toString() {
		return title + ": " + time;
	}

	public int getTime() {
		return time;
	}
	
	public long getSelfstudyID() {
		return selfstudyID;
	}

	public void setSelfstudyID(long selfstudyID) {
		this.selfstudyID = selfstudyID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}