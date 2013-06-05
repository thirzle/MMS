package controller;

import java.io.IOException;
import java.util.Date;
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
		System.out.println("(DeleteUser.java):doGet() called");
    	HttpSession session = request.getSession();
		if(isLoggedIn(request, response)&&actionGranted(request, 3)) {
			String loginname = request.getParameter("selectedRowID");
	    	User user = ua.getUser(loginname);
	    	System.out.println("(DeleteUser.java):user:"+user);
	    	// insert into History "User removed"
			Date currentTime = new Date();
			java.sql.Date date = new java.sql.Date(currentTime.getYear(),
					currentTime.getMonth(), currentTime.getDay()+2);
			ua.insertHistory(user.getLogin(), date, "Wurde geloescht");
			
	    	ua.deleteUser(user);
	    	session.setAttribute("task", "edit");
			response.sendRedirect("/SopraMMS/LoadTable");
			
    	} else {
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
