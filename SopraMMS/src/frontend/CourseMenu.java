package frontend;

import java.util.ArrayList;
import java.util.List;

import model.UserDBController;


public class CourseMenu {

	static UserDBController userDBController = new UserDBController();

	public List<String> getCourses() {
		ArrayList<String> courses = new ArrayList<String>();
		courses.addAll(userDBController.getCoursesByFaculty("in"));	
		return courses;
	}

	
	public static void main(String [] args)
	{
		CourseMenu im = new CourseMenu();
		im.getCourses();
	}
}
