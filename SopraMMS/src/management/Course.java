package management;

//TODO:
//namen adden bitte, alle die an der Klasse geschafft haben...
/**
 * The Course class stores attributes of a single course, that belongs to a {@link CourseEntry}.
 * <p>
 * It contains a few constructors for the necessary initializations of attributes.
 * 
 * @see Module
 * @see Entry
 * @see CourseEntry
 * @author David
 * 
 */
public class Course {

    /**
     * The course's ID.
     */
    private String courseID;
    /**
     * The course's description.
     */
    private String description;
    /**
     * The course's degree.
     */
    private String degree;
    /**
     * The course's faculty.
     */
    private String faculty;
    /**
     * Is this course obligatory or necessary to visit ?
     */
    boolean obligatory;

    /**
     * Creates a Course with given parameters. (Overloaded)
     * <p>
     * Difference: All attributes need to be set.
     * <p>
     * Useful if you load data from database.
     * 
     * @param courseID		The course's ID.
     * @param description	The course's description.
     * @param degree		The course's degree.
     * @param faculty		The course's faculty.
     * @param type		Is this course obligatory or necessary to visit ?
     */
    public Course(String courseID, String description, String degree, String faculty, boolean type) {
	this.courseID = courseID;
	this.description = description;
	this.degree = degree;
	this.faculty = faculty;
	this.obligatory = type;
    }

    /**
     * Creates a Course with given parameters. (Overloaded)
     * <p>
     * Difference: {@link #faculty} attribute does not need to be set.
     * <p>
     * Useful if {@link #faculty} attribute can not be provided during creation.
     * 
     * @param courseID		The course's ID.
     * @param description	The course's description.
     * @param degree		The course's degree.
     * @param type		Is this course obligatory or necessary to visit ?
     */
    public Course(String courseID, String description, String degree, boolean type) {
	this.courseID = courseID;
	this.description = description;
	this.degree = degree;
	this.obligatory = type;
    }

    /**
     * Creates a Course with given parameters. (Overloaded)
     * <p>
     * Difference: {@link #obligatory} attribute does not need to be set.
     * <p>
     * Useful if {@link #obligatory} attribute  can not be provided during creation.
     * 
     * @param courseID		The course's ID.
     * @param description	The course's description.
     * @param degree		The course's degree.
     * @param faculty		The course's faculty.
     */
    public Course(String courseID, String description, String degree, String faculty) {
	this.courseID = courseID;
	this.description = description;
	this.degree = degree;
	this.faculty = faculty;
    }

    /**
     * Returns the description and degree attribute as a single String.
     * 
     * @return The {@link #description} and {@link #degree}.
     */
    public String toString() {
	return description + " " + degree;
    }

    /**
     * Returns the {@link #courseID}.
     * 
     * @return The {@link #courseID}.
     */
    public String getCourseID() {
	return courseID;
    }

    /**
     * Sets the {@link #courseID}.
     * 
     * @param courseID	The {@link #courseID}.
     */
    public void setCourseID(String courseID) {
	this.courseID = courseID;
    }

    /**
     * Returns the {@link #description}.
     * 
     * @return The {@link #description}.
     */
    public String getDescription() {
	return description;
    }

    /**
     * Sets the {@link #description}.
     * 
     * @param description	The {@link #description}.
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * Returns the {@link #degree}.
     * 
     * @return The {@link #degree}.
     */
    public String getDegree() {
	return degree;
    }

    /**
     * Sets the {@link #degree}.
     * 
     * @param degree	The {@link #degree}.
     */
    public void setDegree(String degree) {
	this.degree = degree;
    }

    /**
     * Returns the {@link #faculty}.
     * 
     * @return The {@link #faculty}.
     */
    public String getFaculty() {
	return faculty;
    }

    /**
     * Sets the {@link #faculty}.
     * 
     * @param faculty	The {@link #faculty}.
     */
    public void setFaculty(String faculty) {
	this.faculty = faculty;
    }

    /**
     * Returns whether this course is {@link #obligatory}.
     * 
     * @return Is this course {@link #obligatory}?
     */
    public boolean isObligatory() {
	return obligatory;
    }

    /**
     * Sets this course's {@link #obligatory} status.
     * 
     * @param obligatory	Is this course {@link #obligatory}.
     */
    public void setObligatory(boolean type) {
	this.obligatory = type;
    }

}
