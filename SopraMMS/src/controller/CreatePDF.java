package controller;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserAdministration;

/**
 * Servlet implementation class CreatePDF
 */
@WebServlet("/CreatePDF")
public class CreatePDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePDF() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserAdministration uAdmin = new UserAdministration();
		HttpSession session = request.getSession();
		
		LinkedList<String> facListNames = (LinkedList) uAdmin.getAllFacultiesByName();
		LinkedList<String> facListID = (LinkedList) uAdmin.getAllFacultiesID();
		LinkedList<String> courses = (LinkedList) uAdmin.getCoursesByFaculty(facListID.getFirst());
		 
		session.setAttribute("faculty", facListNames.getFirst());
		session.setAttribute("courses", courses);
		

		session.setAttribute("content", "generatePDF");
	    response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
