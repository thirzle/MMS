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

/**
 * Servlet implementation class DeleteUser
 */
@WebServlet("/DeleteUser")
public class DeleteUser extends SessionCheck implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see SessionCheck#SessionCheck()
     */
    public DeleteUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
		if(isLoggedIn(request, response)&&actionGranted(request, 3)) {
	    	User user =(User) session.getAttribute("userToDelete");
	    	ua.deleteUser(user);
			response.sendRedirect("/SopraMMS/LoadTable");
    	} else {
    		session.removeAttribute("userToDelete");
    		session.setAttribute("content","start");
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
