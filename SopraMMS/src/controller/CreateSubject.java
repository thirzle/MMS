package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Course;
import management.ModuleAdministration;
import user.User;

/**
 * Servlet implementation class CreateSubject
 */
@WebServlet("/CreateSubject")
public class CreateSubject extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateSubject() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			//check if menuentry or submit brought you here
			if(request.getParameter("name")==null){
				session.setAttribute("content", "createNewSubject");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			} else {
				mAdmin.addSubject(request.getParameter("name"));
				session.setAttribute("subjectCreated", true);
				session.setAttribute("content", "createNewSubject");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
