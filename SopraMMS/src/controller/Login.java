package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

		UserAdministration userAdmin = new UserAdministration();

		if (session.getAttribute("loginname") == null) {
			// login
			// Here you put the check on the username and password
			User user = userAdmin.login(loginname, password, sessionID);
			if (user != null) {
				session.setAttribute("user", user);
				session.setAttribute("loginname", loginname);
				session.setAttribute("rights", user.getRights());
				session.setAttribute("email", user.getMail());
				session.setAttribute("task", "home");

				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			} else {
				System.out.println("Invalid loginname and password");
				//TODO in bestehenden Content oeffnen und nicht neue Seite oeffnen
				response.sendRedirect("/SopraMMS/guiElements/error.jsp");
			}
		} else if (userAdmin.checkLogin(sessionID).getLogin()
				.equals(session.getAttribute("loginname").toString())) {
			System.out
					.println("User ist eingelogt Seite kann neu geladen werden");
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
