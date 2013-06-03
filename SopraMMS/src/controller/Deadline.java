package controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserAdministration;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Servlet implementation class NewDeadline
 */
@WebServlet("/Deadline")
public class Deadline extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deadline() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(request.getParameter("deadline")==null&&request.getParameter("rememberbegin")==null){
			if (session.getAttribute("deadline")==null) {
				session.setAttribute("content", "newDeadline");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?changeDeadline=true");
			} else {
				session.setAttribute("content", "showDeadline");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?changeDeadline=true");
			}
		} else {
			UserAdministration ua = new UserAdministration();
			String [] date = request.getParameter("deadline").split(".");
			session.setAttribute("deadline", new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])));
			date = request.getParameter("deadline").split(".");
			session.setAttribute("rememberbegin",  new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])));
			//ua.
			
		}	
	} 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
