package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Deadline;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginname = request.getParameter("loginname");
		String password = request.getParameter("password");
		String sessionID = session.getId();


		if (session.getAttribute("loginname") == null) {
			// login
			// Here you put the check on the username and password
			User user = uAdmin.login(loginname, password, sessionID);
			if (user != null) {
				Deadline deadline = uAdmin.getDeadlinebyFaculty(user.getFaculty());
				session.setAttribute("user", user);
				session.setAttribute("loginname", loginname);
				session.setAttribute("rights", user.getRights());
				session.setAttribute("email", user.getMail());
				session.setAttribute("content", "home");
				if(deadline!=null) {
					session.setAttribute("deadline", deadline.getDeadline());
					session.setAttribute("beginremember", deadline.getBeginremember());
				}
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			} else {
				System.out.println("Invalid loginname or password");
				//TODO in bestehenden Content oeffnen und nicht neue Seite oeffnen
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?loginStatus=failed");
			}
		} else if (uAdmin.checkLogin(sessionID).getLogin()
				.equals(session.getAttribute("loginname").toString())) {
			System.out.println("User ist eingelogt Seite kann neu geladen werden");
			session.setAttribute("content", "home");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		} else {
			System.out
					.println("User ist nicht eingeloggt oder SessionID stimmt nicht");
			response.sendRedirect("/SopraMMS/guiElements/error.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
