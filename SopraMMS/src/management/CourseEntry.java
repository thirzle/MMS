package management;

import java.util.LinkedList;
import java.util.List;

public class CourseEntry extends Entry {

	// course --> String[] {courseID, degree}
	private List<String[]> courses = new LinkedList<String[]>();


	// public CourseEntry(int version, String date, boolean classification,
	// boolean approvalstatus, boolean declined, long entryID, String title,
	// List<String> courses) {
	// super(version, date, classification, approvalstatus, declined, entryID,
	// title);
	// this.courses = courses;
	// }
	//
	//
	// public CourseEntry(int version, String date, boolean classification,
	// boolean approvalstatus, boolean declined, long entryID, String title,
	// String course) {
	// super(version, date, classification, approvalstatus, declined, entryID,
	// title);
	// courses.add(course);
	// }
	//
	//
	// public CourseEntry(int version, String date, boolean classification,
	// boolean approvalstatus, boolean declined, long entryID, String title) {
	// super(version, date, classification, approvalstatus, declined, entryID,
	// title);
	// courses = new LinkedList<String>();
	// }

	// updated version
	public CourseEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID,
			String title, int order, List<String[]> courses) {
		super(version, date, classification, approvalstatus, declined, entryID,
				title, order);
		this.courses = courses;
	}

	public CourseEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID,
			String title, int order, String course, String degree) {
		super(version, date, classification, approvalstatus, declined, entryID,
				title, order);
		courses.add(new String[] { course, degree });
	}

	public CourseEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID,
			String title, int order) {
		super(version, date, classification, approvalstatus, declined, entryID,
				title, order);
		courses = new LinkedList<String[]>();
	}

	public CourseEntry(String title, int order, List<String[]> courses) {
		super(title, order);
		this.courses = courses;
	}

	public CourseEntry(String title, List<String[]> courses) {
		super(title);
		this.courses = courses;
	}

	public CourseEntry(List<String[]> courses) {
		super("Empty Title");
		this.courses = courses;
	}

	public void addCourse(String course, String degree) {
		courses.add(new String[] { course, degree });
	}

	public List<String[]> getCourses() {
		return courses;
	}

	public void setCourses(List<String[]> courses) {
		this.courses = courses;
	}
}
