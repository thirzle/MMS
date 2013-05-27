package frontend;

import java.util.ArrayList;
import java.util.List;

import controller.UserDBController;

public class CourseMenu {

	static UserDBController userDBController = new UserDBController();

	public List<String> getCourses() {
		ArrayList<String> courses = new ArrayList<>();
		courses.addAll(userDBController.getCoursesByFaculty("in"));	
		return courses;
	}

	
	public static void main(String [] args)
	{
		CourseMenu im = new CourseMenu();
		im.getCourses();
	}
}
