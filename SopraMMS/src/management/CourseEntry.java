package management;

import java.util.LinkedList;
import java.util.List;

public class CourseEntry extends Entry {

	@SuppressWarnings("unused")
	private List<String> courses = null;

	// TODO


	public CourseEntry(int version, String timestamp, boolean classification,
			boolean approved, boolean rejected, String title, List<String> courses) {
		super(version, timestamp, classification, approved, rejected, "Studiengänge");
		this.courses = courses;
	}
	
	
	public CourseEntry(int version, String timestamp, boolean classification,
			boolean approved, boolean rejected, String title, String course) {
		super(version, timestamp, classification, approved, rejected, "Studiengänge");
		courses.add(course);
	}
	
	
	public CourseEntry(int version, String timestamp, boolean classification,
			boolean approved, boolean rejected, String title) {
		super(version, timestamp, classification, approved, rejected, "Studiengänge");
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
