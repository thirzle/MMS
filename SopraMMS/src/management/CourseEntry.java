package management;

import java.util.LinkedList;
import java.util.List;

//TODO:
//namen adden bitte, alle die an der Klasse geschafft haben...
/**
 * The CourseEntry class stores all course entries that belong to a {@link Module}.
 * <p>
 * It is an extension of the {@link Entry} class.
 * <p>
 * Several Constructors are given, but only a few are useful.
 * Use only Constructors that are marked as "useful". All other Constructors
 * are used for test purposes.
 * @see Module
 * @author AJ, Teresa, add names here...
 *
 */
public class CourseEntry extends Entry {

	// course --> String[] {courseID, degree}
    	/**
    	 * A List of courses. Consists of string arrays with two entries: course and degree.
    	 */
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
	/**
	 * Creates a CourseEntry with given parameters. (Overloaded)
	 * <p>
	 * Difference: multiple courses needed as parameter.
	 * <p>
	 * useful
	 * @param version		The version of this {@link Entry}-extension. (Used to initialize the super class)
	 * @param date			The modification date. (Used to initialize the super class)
	 * @param classification	Should this entry go to Dezernat 2 ? (Used to initialize the super class)
	 * @param approvalstatus	Is this entry already approved ? (Used to initialize the super class)
	 * @param declined		Is it declined and needs to be rewritten again ? (Used to initialize the super class)
	 * @param entryID		This entry's ID. (Used to initialize the super class)
	 * @param title			The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
	 * @param order			The order of this entry. (Used to initialize the super class)
	 * @param courses		A List of courses. Consists of string arrays with two entries: course and degree.
	 */
	public CourseEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID,
			String title, int order, List<String[]> courses) {
		super(version, date, classification, approvalstatus, declined, entryID,
				title, order);
		this.courses = courses;
	}
	
	/**
	 * Creates a CourseEntry with given parameters. (Overloaded)
	 * <p>
	 * Difference: single course needed as parameter.
	 * <p>
	 * useful
	 * @param version		The version of this {@link Entry}-extension. (Used to initialize the super class)
	 * @param date			The modification date. (Used to initialize the super class)
	 * @param classification	Should this entry go to Dezernat 2 ? (Used to initialize the super class)
	 * @param approvalstatus	Is this entry already approved ? (Used to initialize the super class)
	 * @param declined		Is it declined and needs to be rewritten again ? (Used to initialize the super class)
	 * @param entryID		This entry's ID. (Used to initialize the super class)
	 * @param title			The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
	 * @param order			The order of this entry. (Used to initialize the super class)
	 * @param course		A single course parameter.
	 * @param degree		Is used as degree entry for the single course parameter.
	 */
	public CourseEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID,
			String title, int order, String course, String degree) {
		super(version, date, classification, approvalstatus, declined, entryID,
				title, order);
		courses.add(new String[] { course, degree });
	}

	/**
	 * Creates a CourseEntry with given parameters. (Overloaded)
	 * <p>
	 * Difference: No course parameter, no degree parameter. Add courses with {@link #addCourse(String course, String degree) addCourse}.
	 * <p>
	 * useful
	 * @param version		The version of this {@link Entry}-extension. (Used to initialize the super class)
	 * @param date			The modification date. (Used to initialize the super class)
	 * @param classification	Should this entry go to Dezernat 2 ? (Used to initialize the super class)
	 * @param approvalstatus	Is this entry already approved ? (Used to initialize the super class)
	 * @param declined		Is it declined and needs to be rewritten again ? (Used to initialize the super class)
	 * @param entryID		This entry's ID. (Used to initialize the super class)
	 * @param title			The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
	 * @param order			The order of this entry. (Used to initialize the super class)
	 */
	public CourseEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID,
			String title, int order) {
		super(version, date, classification, approvalstatus, declined, entryID,
				title, order);
		courses = new LinkedList<String[]>();
	}

	/**
	 * Creates a CourseEntry with given parameters. (Overloaded)
	 * <p>
	 * Is used to create new CourseEntries. e.g. a {@link Module} needs to be modified or is created from scratch.
	 * The super class is initialized with default values for new {@link Entry}.
	 * <p>
	 * useful
	 * @param title		The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
	 * @param order		The order of this entry. (Used to initialize the super class)
	 * @param courses	A List of courses. Consists of string arrays with two entries: course and degree.
	 */
	public CourseEntry(String title, int order, List<String[]> courses) {
		super(title, order);
		this.courses = courses;
	}

	/**
	 * Creates a CourseEntry with given parameters. (Overloaded)
	 * <p>
	 * minimalistic Constructor...
	 * <p>
	 * deprecated...
	 * @param title		The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
	 * @param courses	A List of courses. Consists of string arrays with two entries: course and degree.
	 */
	@Deprecated
	public CourseEntry(String title, List<String[]> courses) {
		super(title);
		this.courses = courses;
	}

	/**
	 * Creates a CourseEntry with given parameters. (Overloaded)
	 * <p>
	 * minimalistic Constructor...
	 * <p>
	 * deprecated...
	 * @param courses	A List of courses. Consists of string arrays with two entries: course and degree.
	 */
	@Deprecated
	public CourseEntry(List<String[]> courses) {
		super("Empty Title");
		this.courses = courses;
	}

	/**
	 * Adds this CourseEntry an additional course.
	 * 
	 * @param course	The course which is going to be added.
	 * @param degree	The degree which is stored in relation to the course parameter.
	 */
	public void addCourse(String course, String degree) {
		courses.add(new String[] { course, degree });
	}

	/**
	 * Returns all courses of this CoursEntry object.
	 * @return	List of courses. Consists of string arrays with two entries: course and degree.
	 */
	public List<String[]> getCourses() {
		return courses;
	}

	/**
	 * Overwrites this CourseEntry object's course list.
	 * @param courses	List of courses. Consists of string arrays with two entries: course and degree.
	 */
	public void setCourses(List<String[]> courses) {
		this.courses = courses;
	}
}
