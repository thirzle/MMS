package management;

public class CourseEntry extends Entry{
	
	@SuppressWarnings("unused")
	private String course;
	
	public CourseEntry(int version, String timestamp, boolean classification, boolean approved, boolean rejected, String title, String course){
		super(version, timestamp, classification, approved, rejected, title);
		this.course = course;
	}
	
	public CourseEntry(String title, String course){
		super(title);
		this.course = course;
	}
	
	public CourseEntry(String course){
		super("Empty Title");
		this.course = course;
	}
	
	public String toString(){
		return "to do...";
	}
	
	//to do getCourseEntryList()...
	
}
