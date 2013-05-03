package management;

import java.util.LinkedList;
import java.util.List;

public class CourseEntry extends Entry{
	
	@SuppressWarnings("unused")
	private String course;
	
	//TODO: Dafuq is selfstudylist and course ???
	private List<SelfStudy> selfStudyList = new LinkedList<SelfStudy>();
	
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
	
	public List<SelfStudy> getCourseEntryList(){
		return selfStudyList;
	}
	
}
