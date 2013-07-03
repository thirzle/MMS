package management;

import java.util.LinkedList;
import java.util.List;

/**
 * The CourseEntry class stores all course entries that belong to a {@link Module}.
 * <p>
 * It is an extension of the {@link Entry} class.
 * <p>
 * Several Constructors are given, but only a few are useful.
 * Use only Constructors that are marked as "useful". All other Constructors
 * are used for test purposes.
 * @see Module
 * @see Entry
 * @see Course
 * @author Johann Albach, Teresa Hirzle, David Lehr, Max Reuter
 *
 */
public class CourseEntry extends Entry {


    @Override
    public String toString() {
	return "CourseEntry [courses=" + courses + "]";
    }

	/**
    	 * A List of courses. Consists of {@link Course} objects.
    	 */
	private List<Course> courses = new LinkedList<Course>();

	/**
	 * Creates a CourseEntry with given parameters. (Overloaded)
	 * <p>
	 * Difference: multiple {@link Course}s needed as parameter. Used to create an Entry from already loaded data.
	 * <p>
	 * useful
	 * 
	 * @see Course
	 * 
	 * @param date			The modification date. (Used to initialize the super class)
	 * @param classification	Should this entry go to Dezernat 2 ? (Used to initialize the super class)
	 * @param approvalstatus	Is this entry already approved ? (Used to initialize the super class)
	 * @param declined		Is it declined and needs to be rewritten again ? (Used to initialize the super class)
	 * @param entryID		This entry's ID. (Used to initialize the super class)
	 * @param title			The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
	 * @param order			The order of this entry. (Used to initialize the super class)
	 * @param courses		A List of {@link Course}s.
	 */
	public CourseEntry(String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID,
			String title, int order, List<Course> courses) {
		super(date, classification, approvalstatus, declined, entryID,
				title, order);
		this.courses = courses;
	}
	
	/**
	 * Creates a CourseEntry with given parameters. (Overloaded)
	 * <p>
	 * Difference: single {@link Course}'s attributes needed as parameters. Used to create an {@link Entry} from already loaded data.
	 * <p>
	 * useful
	 * 
	 * @see Course
	 * 
	 * @param date			The modification date. (Used to initialize the super class)
	 * @param classification	Should this entry go to Dezernat 2 ? (Used to initialize the super class)
	 * @param approvalstatus	Is this entry already approved ? (Used to initialize the super class)
	 * @param declined		Is it declined and needs to be rewritten again ? (Used to initialize the super class)
	 * @param entryID		This entry's ID. (Used to initialize the super class)
	 * @param title			The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
	 * @param order			The order of this entry. (Used to initialize the super class)
	 * @param courseID		The course's ID.
	 * @param description		Name of the course.
	 * @param degree		Is used as degree entry for the course.
	 * @param obligatory		true: module necessary false: module voluntarily
	 */
	public CourseEntry(String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID,
			String title, int order, String courseID, String description, String degree, boolean obligatory) {
		super(date, classification, approvalstatus, declined, entryID,
				title, order);
		courses.add(new Course(courseID, description, degree, obligatory));
	}

	/**
	 * Creates a CourseEntry with given parameters. (Overloaded)
	 * <p>
	 * Difference: No courses, no degree parameter. Add courses with {@link #addCourse(String courseID, String  description,String degree, boolean obligatory) addCourse}.
	 * Used to create an Entry from already loaded data.
	 * <p>
	 * useful
	 * 
	 * @param date			The modification date. (Used to initialize the super class)
	 * @param classification	Should this entry go to Dezernat 2 ? (Used to initialize the super class)
	 * @param approvalstatus	Is this entry already approved ? (Used to initialize the super class)
	 * @param declined		Is it declined and needs to be rewritten again ? (Used to initialize the super class)
	 * @param entryID		This entry's ID. (Used to initialize the super class)
	 * @param title			The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
	 * @param order			The order of this entry. (Used to initialize the super class)
	 */
	public CourseEntry(String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID,
			String title, int order) {
		super(date, classification, approvalstatus, declined, entryID,
				title, order);
		courses = new LinkedList<Course>();
	}

	/**
	 * Creates a CourseEntry with given parameters. (Overloaded)
	 * <p>
	 * Is used to create new CourseEntries. e.g. a {@link Module} needs to be modified or is created from scratch.
	 * The super class is initialized with default values for new {@link Entry}.
	 * <p>
	 * useful
	 * 
	 * @see Course
	 * 
	 * @param title		The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
	 * @param order		The order of this entry. (Used to initialize the super class)
	 * @param courses	A List of {@link Course}s.
	 */
	public CourseEntry(String title, int order, List<Course> courses) {
		super(title, order);
		this.courses = courses;
	}

	/**
	 * Creates a CourseEntry with given parameters. (Overloaded)
	 * <p>
	 * minimalistic Constructor...
	 * <p>
	 * deprecated...
	 * 
	 * @see Course
	 * 
	 * @param title		The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
	 * @param courses	A List of {@link Course}s.
	 */
	@Deprecated
	public CourseEntry(String title, List<Course> courses) {
		super(title);
		this.courses = courses;
	}

	/**
	 * Creates a CourseEntry with given parameters. (Overloaded)
	 * <p>
	 * minimalistic Constructor...
	 * <p>
	 * deprecated...
	 * 
	 * @see Course
	 * 
	 * @param courses	A List of {@link Course}s.
	 */
	@Deprecated
	public CourseEntry(List<Course> courses) {
		super("Empty Title");
		this.courses = courses;
	}

	/**
	 * Adds this CourseEntry an additional course.
	 * 
	 * @param courseID		The course which is going to be added.
	 * @param degree		The degree which is stored in relation to the course parameter.
	 * @param description		Name of the Course
	 * @param obligatory		The type. true: module necessary false: module voluntarily
	 */
	public void addCourse(String courseID, String  description,String degree, boolean obligatory) {
		courses.add(new Course(courseID, description, degree, obligatory));
	}

	/**
	 * Returns all courses of this CoursEntry object.
	 * 
	 * @see Course
	 * 
	 * @return	List of {@link Course}s.
	 */
	public List<Course> getCourses() {
		return courses;
	}

	/**
	 * Overwrites this CourseEntry object's course list.
	 * 
	 * @see Course
	 * 
	 * @param courses	List of {@link Course}s.
	 */
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
	/**
	 * Returns a string with all obligatory {@link Course}s divided by "\n".
	 * 
	 * @see Course
	 * 
	 * @return		All obligatory {@link Course}s.
	 */
	public String obligatoryCoursesToString(){
		StringBuilder sb = new StringBuilder();
		for (Course c : courses) {
			if(c.isObligatory()){
				sb.append(c.toString()+"\n");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Returns a string with all necessary {@link Course}s divided by "\n".
	 * 
	 * @see Course
	 * 
	 * @return		All necessary {@link Course}s.
	 */
	public String necessaryCoursesToString(){
		StringBuilder sb = new StringBuilder();
		for (Course c : courses) {
			if(!c.isObligatory()){
				sb.append(c.toString()+"\n");
			}
		}
		return sb.toString();
	}
	
	
	public String getContent(){
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		for (Course c : courses) {
			if(c.isObligatory()){
				sb1.append("- "+c.toString()+"\n");
			}
			else{
				sb2.append("- "+c.toString()+"\n");
			}
		}
		return "Plichtmodul\n"+sb2.toString()+"\nWahlpflichtmodul\n"+sb1.toString();
	}
}
