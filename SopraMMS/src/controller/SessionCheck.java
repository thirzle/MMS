package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.User;

/**
 * Servlet implementation class SessionCeck
 */
@WebServlet("/SessionCeck")
public class SessionCheck extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionCheck() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	protected boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null) {
			return true;
		}
		return false;
	}
	
	protected boolean actionGranted(HttpServletRequest request, boolean[] rights) {
	    HttpSession session = request.getSession();
	    User user = (User) session.getAttribute("user");
	    boolean[] userRights = user.getRights();
	    boolean isGranted = true;
	    for (int i = 0; i < rights.length; i++) {
		if(rights[i]) {
		    if(userRights[i]!=rights[i]) {
			isGranted = false;
		    }
		}
	    }
	    return isGranted;
	}
}
