package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.User;

/**
 * Servlet implementation class LoadUser
 */
@WebServlet("/LoadUser")
public class LoadUser extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see SessionCheck#SessionCheck()
     */
    public LoadUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("(LoadUsers.java): doGet called");
	    	HttpSession session = request.getSession();	    	
		String[] status = {"Modulver.","Redakteur","Administrator","right[3]","right[4]","right[5]"};
		UserDBController controller = new UserDBController();
		List<User> users = controller.getAllUsers();
		System.out.println("(LoadUser.java): users = "+users);
		session.setAttribute("users", users);
		session.setAttribute("content", "user");
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
