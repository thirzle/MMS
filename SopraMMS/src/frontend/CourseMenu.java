package frontend;

import java.util.ArrayList;
import java.util.List;

import model.ModuleDBController;

//TODO Autoren eintragen und kontrollieren
/**
 * The CourseMenu class fills the Menu on the sidebar with the appropriate courses belonging to computer science.
 * 
 * @author ...
 *
 */
public class CourseMenu {

	/**
	 * ModuleDBController object.
	 */
	static ModuleDBController moduleDBController = new ModuleDBController();

	/**
	 * Gets all courses belonging to the subject computer science.
	 * 
	 * @return			List of all courses.
	 * @see Course
	 */
	public List<String> getCourses() {
		ArrayList<String> courses = new ArrayList<String>();
		courses.addAll(moduleDBController.getCoursesByFaculty("in"));
		return courses;
	}


}
