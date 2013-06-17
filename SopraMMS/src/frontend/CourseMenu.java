package frontend;

import java.util.ArrayList;
import java.util.List;

import model.ModuleDBController;


public class CourseMenu {

	static ModuleDBController moduleDBController = new ModuleDBController();

	public List<String> getCourses() {
		ArrayList<String> courses = new ArrayList<String>();
		courses.addAll(moduleDBController.getCoursesByFaculty("in"));
		return courses;
	}


}
