package controller;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Module;
import management.ModuleAdministration;

import user.UserAdministration;

/**
 * Servlet implementation class ShowEntries
 */
@WebServlet("/ShowEntries")
public class ShowEntries extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowEntries() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		ModuleAdministration mAdmin = new ModuleAdministration();
		UserAdministration uAdmin = new UserAdministration();
		LinkedList<String> facListID = (LinkedList) uAdmin.getAllFacultiesID();
		LinkedList<String> courses = (LinkedList) uAdmin.getCoursesByFaculty(facListID.getFirst());
		
//		alle Kurse der fakultät in courseArray[] gespeichert
		String[] courseArray = new String[2*courses.size()];
		for (int i = 0; i < courses.size(); i++) {
			courseArray[i] = "Bachelor "+courses.get(i);
		}
		for (int j = 0; j < courses.size(); j++) {
			courseArray[j+courses.size()] = "Master "+courses.get(j);
		}
		
		String fullCourse = request.getParameter("course");
		String course = courseArray[Integer.parseInt(fullCourse)];
		String[] splitCourse = course.split(" ");
//		alle Module die zu einem Studiengang gehören laden
		LinkedList<Module> moduleList= (LinkedList) mAdmin.getModulesByCourse(splitCourse[1], splitCourse[0]);
		for (Module module : moduleList) {
			System.out.println("Module: "+module);
		}
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
