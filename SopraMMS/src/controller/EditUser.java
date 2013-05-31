package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDBController;
import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class EditUser
 */
@WebServlet("/EditUser")
public class EditUser extends SessionCheck implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see SessionCheck#SessionCheck()
     */
    public EditUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();	
    	UserAdministration ua = new UserAdministration();
    	List<String> institutes = ua.getAllInstitutes();
    	if(institutes != null && !institutes.isEmpty()) {
    		session.setAttribute("institutes", institutes);
    	} else {
    		System.out.println("(EditUser.java): null value");
    	}
    	String loginname = request.getParameter("selectedRow").toString();
    	if(loginname != "" ) {
    		User user = ua.getUser(loginname);
    		if(user != null) {
    			session.setAttribute("user", user);
    			session.setAttribute("content", "editUser");
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
