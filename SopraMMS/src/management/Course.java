package management;

public class Course {

	private String courseID,description,degree,faculty;
	boolean type;
	
	public Course(String courseID, String description, String degree,
			String facult, boolean type) {
		this.courseID = courseID;
		this.description = description;
		this.degree = degree;
		this.faculty = facult;
		this.type = type;
	}

	public Course(String courseID, String description, String degree,
			boolean type) {
		this.courseID = courseID;
		this.description = description;
		this.degree = degree;
		this.type = type;
	}
	
	public String toString() {
		return description+" "+degree;
	}

}
