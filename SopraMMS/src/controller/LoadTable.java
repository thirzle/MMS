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
		if(isLoggedIn(request, response)) {
	    	HttpSession session = request.getSession();	
	    	
	    	
	    	// just for debug reasons####################################
	    	long x = System.currentTimeMillis();
			List<User> users = uAdmin.getAllUsers();
			long y = System.currentTimeMillis();
			System.out.println("(LoadTable.java) LoadUser Time: "+(y-x));
			//###########################################################
			
			session.setAttribute("users", users);
			session.removeAttribute("content");
			String task = "";
			try {
				task = request.getParameter("task").toString();
				if(task.equals("mail")) {
					session.setAttribute("content", "newMessage"); 
				} else if (task.equals("curr")) {
					session.setAttribute("content", "CurrVitae");
				} else if( task.equals("edit") ) {
					session.setAttribute("task", "edit");
					session.setAttribute("content", "loadTable");
				} else if( task.equals("delete") ) {
					session.setAttribute("task", "delete");
					session.setAttribute("content", "loadTable");
				} else {
					session.setAttribute("task", "edit");
					session.setAttribute("content", "loadTable");
				}
			} catch(NullPointerException e){
				session.setAttribute("task", "edit");
				session.setAttribute("content", "loadTable");
			} finally {
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			}
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
