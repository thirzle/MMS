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
 * Servlet implementation class EditUser
 */
@WebServlet("/LoadTable")
public class LoadTable extends SessionCheck implements Servlet {
	
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 8802119366159398073L;

	/**
     * @see SessionCheck#SessionCheck()
     */
    public LoadTable() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();	
    	long x = System.currentTimeMillis();
		List<User> users = ua.getAllUsers();
		long y = System.currentTimeMillis();
		System.out.println("(LoadTable.java) LoadUser Time: "+(y-x));
		
		
		session.setAttribute("users", users);
		try {
			session.setAttribute("task", request.getParameter("task").toString());
		} catch(NullPointerException e){
			session.setAttribute("task", "edit");
		} finally {
			session.setAttribute("content", "loadTable");
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
