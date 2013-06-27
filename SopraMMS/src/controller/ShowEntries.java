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
public class ShowEntries extends SessionCheck {
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
		if(isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			LinkedList<String> facListID = (LinkedList) uAdmin.getAllFacultiesID();
			LinkedList<String> courses = (LinkedList) mAdmin.getCourses();
			
	//		all courses of faculty in courseArray[]
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
	//		load all modules of course
			LinkedList<Module> moduleList= (LinkedList) mAdmin.getModulesByCourse(mAdmin.getCourseID(splitCourse[1]), splitCourse[0]);
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
