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
public class CreateCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//check if menuentry or submit brought you here
		if(request.getParameter("courseID")==null&&request.getParameter("description")==null&&request.getParameter("degree")==null){
			session.setAttribute("content", "createNewCourse");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		} else {
			User user = (User) session.getAttribute("user");
			ModuleAdministration mAdministration = new ModuleAdministration();
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
			mAdministration.addCourse(course);
			session.setAttribute("courseCreated", true);
			session.setAttribute("content", "createNewCourse");
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
