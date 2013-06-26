package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import backend.TaskManager;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class NewDeadline
 */
@WebServlet("/Deadline")
public class Deadline extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			//check if you visit site the first time or a submit brought you there
			if(request.getParameter("deadline")==null&&request.getParameter("beginremember")==null){
				// check if there is a deadline stored in the session
				if (session.getAttribute("deadline")==null) {
					session.setAttribute("content", "newDeadline");
					session.setAttribute("existingDeadline", false);
					response.sendRedirect("/SopraMMS/guiElements/home.jsp");
				} else {
					session.setAttribute("content", "showDeadline");
					session.setAttribute("existingDeadline", true);
					response.sendRedirect("/SopraMMS/guiElements/home.jsp");
				}
			//Deadline submitted
			} else {
				Date deadline, beginremember;
				String temp = request.getParameter("deadline");
				int[] input = new int[3];
				StringTokenizer strtok = new StringTokenizer(temp, ".");
				for (int i = 0; strtok.hasMoreTokens(); i++) {
					input[i] = Integer.parseInt(strtok.nextToken());
				}
				Calendar cal = new GregorianCalendar();
				cal.set(input[2], input[1]-1, input[0]);
				deadline = new Date(cal.getTime().getTime());
				temp = request.getParameter("beginremember");
				strtok = new StringTokenizer(temp, ".");
				for (int i = 0; strtok.hasMoreTokens(); i++) {
					input[i] = Integer.parseInt(strtok.nextToken());
				}
				cal.set(input[2], input[1]-1, input[0]);
				beginremember = new Date(cal.getTime().getTime());
				if(deadline.after(beginremember)&&deadline.after(new java.util.Date())){
					session.setAttribute("deadline", deadline);
					session.setAttribute("beginremember",  beginremember);
					User user = (User) session.getAttribute("user");
					if((boolean)session.getAttribute("existingDeadline")){
						uAdmin.updateDeadlinebyFaculty(new management.Deadline(deadline, 
								beginremember, user.getFaculty()));
					} else {
						uAdmin.setDeadlinebyFaculty(new management.Deadline(deadline, 
								beginremember, user.getFaculty()));
					}
					TaskManager taskMananer = new TaskManager(new management.Deadline(deadline, 
							beginremember, user.getFaculty()));
					session.removeAttribute("existingDeadline");
					session.setAttribute("content", "showDeadline");
					response.sendRedirect("/SopraMMS/guiElements/home.jsp?submitDeadline=done");
				} else{
					if ((boolean)session.getAttribute("existingDeadline")) {
						session.setAttribute("content", "showDeadline");
						if(deadline.before(beginremember))
							response.sendRedirect("/SopraMMS/guiElements/home.jsp?submitDeadline=deadBeforeRemem");
						else
							response.sendRedirect("/SopraMMS/guiElements/home.jsp?submitDeadline=deadBeforeToday");
						
					} else {
						session.setAttribute("content", "newDeadline");
						if(deadline.before(beginremember))
							response.sendRedirect("/SopraMMS/guiElements/home.jsp?submitDeadline=deadBeforeRemem");
						else
							response.sendRedirect("/SopraMMS/guiElements/home.jsp?submitDeadline=deadBeforeToday");
					}
				}
				
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
