package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.User;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/AddUser")
public class AddUser extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("(AddUser.java): doGet() called");
	    	HttpSession session = request.getSession();
	    	boolean[] rights = {false,false,false,true,false};
		if(isLoggedIn(request, response) && actionGranted(request, rights)) {
		    //TODO
		    //in die Logik auslagern
		    UserDBController controller = new UserDBController();
		    List<String> institutes = controller.getInstitutes();
		    //
		    session.setAttribute("institutes", institutes);
		    session.setAttribute("task", "user");
		    session.setAttribute("addUser", "true");
		    response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		} else {
		    // not logged in or access denied!
		    System.out.println("(Access denied): You are not logged in or permitted");
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
