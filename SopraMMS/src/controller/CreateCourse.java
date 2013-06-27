package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.User;

import management.Course;
import management.ModuleAdministration;

/**
 * Servlet implementation class CreateCourse
 */
@WebServlet("/CreateCourse")
public class CreateCourse extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCourse() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			//check if menuentry or submit brought you here
			if(request.getParameter("courseID")==null&&request.getParameter("description")==null&&request.getParameter("degree")==null){
				session.setAttribute("content", "createNewCourse");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			} else {
				User user = (User) session.getAttribute("user");
				int tmp = Integer.parseInt(request.getParameter("degree"));
				String degree = null;
				switch (tmp) {
				case 0:
					degree = "Bachelor";
					break;
				case 1:
					degree = "Master";
					break;
				case 2:
					degree = "Lehramt";
				}
				Course course = new Course(request.getParameter("courseID"), 
						request.getParameter("description"), 
						degree, 
						user.getFaculty());
				mAdmin.addCourse(course);
				String infoText = "Der Studiengang '"+course.toString()+"' wurde erfolgreich zum System hinzugefügt.";
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="+infoText);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
