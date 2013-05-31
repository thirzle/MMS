package controller;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		String[] courseArray = new String[2*courses.size()];
		for (int i = 0; i < courses.size().; i++) {
			courseArray[i] = "Bachelor-"+courses.get(i);
		}
		for (int j = courses.size(); j < courseArray.length; j++) {
			courseArray[j] = 
		}
		
		String fullCourse = request.getParameter("course");		
		System.out.println(fullCourse+"****************************");
		String[] course = fullCourse.split("-");
		System.out.println("***************course: " + course[0] +" "+course[1]);
//		alle Module die zu einem Studiengang gehören laden
//		LinkedList<Module> = (LinkedList) mAdmin.getModulesByCourse(course[1], course[0]);
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
