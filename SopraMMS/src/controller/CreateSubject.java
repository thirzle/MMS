package controller;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Course;
import management.ModuleAdministration;
import user.User;

/**
 * Servlet implementation class CreateSubject
 */
@WebServlet("/CreateSubject")
public class CreateSubject extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateSubject() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			//check if menuentry or submit brought you here
			if(request.getParameter("name")==null){
				session.setAttribute("content", "createNewSubject");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			} else {
				LinkedList<String> subjectList = (LinkedList<String>) mAdmin.getSubjects();
				String subject = request.getParameter("name");
				boolean wrongData = false;
				for(String string : subjectList){
					if(!wrongData && subject.equals(string)){
						session.setAttribute("wrongDataCreateSubject", "wrongData");
						session.setAttribute("content", "createNewSubject");
						response.sendRedirect("/SopraMMS/guiElements/home.jsp");
						wrongData = true;
					}
				}
				if(!wrongData){
					mAdmin.addSubject(subject);
					String infoText = "Das Fach '"+subject+"' wurde erfolgreich angelegt.";
					response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="+infoText);	
				}
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
