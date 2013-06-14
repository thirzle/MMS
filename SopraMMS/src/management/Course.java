package management;

public class Course {

	private String courseID,description,degree,faculty;
	boolean obligatory;
	
	public Course(String courseID, String description, String degree,
			String facult, boolean type) {
		this.courseID = courseID;
		this.description = description;
		this.degree = degree;
		this.faculty = facult;
		this.obligatory = type;
	}

	public Course(String courseID, String description, String degree,
			boolean type) {
		this.courseID = courseID;
		this.description = description;
		this.degree = degree;
		this.obligatory = type;
	}
	
	public String toString() {
		return description+" "+degree;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public boolean isObligatory() {
		return obligatory;
	}

	public void setObligatory(boolean type) {
		this.obligatory = type;
	}
	
	

}
