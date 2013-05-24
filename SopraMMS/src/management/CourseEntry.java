package management;

import java.util.LinkedList;
import java.util.List;

public class CourseEntry extends Entry {

	@SuppressWarnings("unused")
	private List<String> courses = null;

	// TODO


	public CourseEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, int entryID, List<String> courses) {
		super(version, date, classification, approvalstatus, declined, entryID);
		this.courses = courses;
	}
	
	
	public CourseEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, int entryID, String course) {
		super(version, date, classification, approvalstatus, declined, entryID);
		courses.add(course);
	}
	
	
	public CourseEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, int entryID) {
		super(version, date, classification, approvalstatus, declined, entryID);
		courses = new LinkedList<String>();
	}


	public CourseEntry(String title, List<String> courses) {
		super(title);
		this.courses = courses;
	}


	public CourseEntry(List<String> courses) {
		super("Empty Title");
		this.courses = courses;
	}
	
	
	public void addCourse(String course){
		courses.add(course);
	}
}
