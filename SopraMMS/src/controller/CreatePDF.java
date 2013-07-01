package controller;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Course;
import management.ModuleAdministration;

import user.UserAdministration;

/**
 * Servlet implementation class CreatePDF
 */
@WebServlet("/CreatePDF")
public class CreatePDF extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     * 
     * @author Teresa Hirzle
     */
    public CreatePDF() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			
			LinkedList<String> facListNames = (LinkedList) uAdmin.getAllFacultiesByName();
			LinkedList<String> facListID = (LinkedList) uAdmin.getAllFacultiesID();
			LinkedList<Course> courses = (LinkedList<Course>) mAdmin.getCoursesExistModules();
			
			 
			session.setAttribute("faculty", facListNames.getFirst());
			session.setAttribute("coursesForPDF", courses);
			
	
			session.setAttribute("content", "generatePDF");
		    response.sendRedirect("/SopraMMS/guiElements/home.jsp");
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
