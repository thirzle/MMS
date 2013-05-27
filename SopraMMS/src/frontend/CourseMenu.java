package frontend;

import java.util.ArrayList;
import java.util.List;

import controller.UserDBController;

public class CourseMenu {

	static UserDBController userDBController = new UserDBController();

	public List<String> getCourses() {
		ArrayList<String> courses = new ArrayList<>();

		// Muss noch durch .getCourses(fakultaet) erstetzt werden
		courses.addAll(userDBController.getInstitutes());

		for (String string : courses) {
			System.out.println(string);
		}
		
		return courses;
	}

	
	public static void main(String [] args)
	{
		CourseMenu im = new CourseMenu();
		im.getCourses();
	}
}
