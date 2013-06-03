package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class NewDeadline
 */
@WebServlet("/Deadline")
public class Deadline extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//check if you visit site the first time or a submit brought you there
		if(request.getParameter("deadline")==null&&request.getParameter("beginremember")==null){
			// check if there is a deadline stored in the session
			if (session.getAttribute("deadline")==null) {
				session.setAttribute("content", "newDeadline");
				session.setAttribute("existingDeadline", false);
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?existingDeadline=false");
			} else {
				session.setAttribute("content", "showDeadline");
				session.setAttribute("existingDeadline", true);
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?existingDeadline=true");
			}
		//Deadline submitted
		} else {
			UserAdministration userAdmin = new UserAdministration();
			Date deadline, beginremember;
			String temp = request.getParameter("deadline");
			int[] input = new int[3];
			StringTokenizer strtok = new StringTokenizer(temp, ".");
			for (int i = 0; strtok.hasMoreTokens(); i++) {
				input[i] = Integer.parseInt(strtok.nextToken());
			}
			deadline = new Date(input[0], input[1], input[2]);
			session.setAttribute("deadline", deadline);
			beginremember = new Date(input[0], input[1], input[2]);
			session.setAttribute("beginremember",  beginremember);
			User user = (User) session.getAttribute("user");
			System.out.println(session.getAttribute("existingDeadline"));
//			if(session.getAttribute("existingDeadline").){
//				userAdmin.updateDeadlinebyFaculty(new management.Deadline(deadline, beginremember, user.getFaculty()));
//			} else {
//				userAdmin.setDeadlinebyFaculty(new management.Deadline(deadline, beginremember, user.getFaculty()));
//			}
		}	
		session.removeAttribute("existingDeadline");
	} 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
