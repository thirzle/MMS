package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDBController;

import user.User;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/NewUser")
public class NewUser extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("(NewUser.java): doGet() called");
	    HttpSession session = request.getSession();
		if(isLoggedIn(request, response) && actionGranted(request, 3)) {
		    List<String> institutes = ua.getAllInstitute();
		    session.removeAttribute("institutes");
    		session.removeAttribute("instituteNames");
    		session.removeAttribute("emptyInputs");
		    session.setAttribute("institutes", institutes);
		    session.setAttribute("content", "newUser");
		    response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		} else {
		    // not logged in or access denied!
		    System.out.println("(Access denied): You are not logged in or permitted");
		    session.setAttribute("content", "start");
		    response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
