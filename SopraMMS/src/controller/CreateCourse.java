package controller;

import java.io.IOException;
import java.util.LinkedList;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			ModuleAdministration mAdmin = new ModuleAdministration();
			// check if menuentry or submit brought you here
			if (request.getParameter("courseID") == null
					&& request.getParameter("description") == null
					&& request.getParameter("degree") == null) {
				session.setAttribute("content", "createNewCourse");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			} else {
				User user = (User) session.getAttribute("user");
				int tmp = Integer.parseInt(request.getParameter("degree"));
				String degree = null;
				boolean wrongData = false;
				String courseID = request.getParameter("courseID");
				String courseDescription = request.getParameter("description");
				LinkedList<Course> courseList = (LinkedList<Course>) mAdmin
						.getCourses();
				// check if courseID already exists
				for (Course course : courseList) {
					if (!wrongData && courseID.equals(course.getCourseID())) {
						session.setAttribute("wrongDataCreateCourse",
								"wrongCourseID");
						session.setAttribute("content", "createNewCourse");
						response.sendRedirect("/SopraMMS/guiElements/home.jsp");
						wrongData = true;
					} else if (!wrongData
							&& courseDescription
									.equals(course.getDescription())) {
						session.setAttribute("wrongDataCreateCourse",
								"wrongCourseDescription");
						session.setAttribute("content", "createNewCourse");
						response.sendRedirect("/SopraMMS/guiElements/home.jsp");
						wrongData = true;
					}
				}

				if (!wrongData) {
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
					Course course = new Course(courseID, courseDescription,
							degree, user.getFaculty());
					mAdmin.addCourse(course);
					String infoText = "Der Studiengang '" + course.toString()
							+ "' wurde erfolgreich zum System hinzugefügt.";
					response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="
							+ infoText);
				}

			}
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="
					+ error);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
