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
		List<User> users = ua.getAllUsers();
		session.setAttribute("users", users);
		session.removeAttribute("content");
		String task = "";
		try {
			task = request.getParameter("task").toString();
			if(task == "mail") {
				// TODO Lisa bitte noch den content value angeben, also welche seite in contentBox geladen werden soll...
				session.setAttribute("content", ""); // <----- hier setzten
											//   ^------ hier setzten
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
