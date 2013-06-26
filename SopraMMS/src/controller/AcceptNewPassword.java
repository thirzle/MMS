package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class AcceptNewPassword
 */
@WebServlet("/AcceptNewPassword")
public class AcceptNewPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AcceptNewPassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String newPassword1 = request.getParameter("newPassword1");
		String newPassword2 = request.getParameter("newPassword2");
		UserAdministration ua = new UserAdministration();

		if (!newPassword1.equals(newPassword2)) {
			request.getSession().setAttribute("content", "ceateNewPwNotEqual");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		} else {
			User user = (User) (request.getSession()
					.getAttribute("userCreatNewPassword"));
			ua.changePassword(user, newPassword1.hashCode() + "");

			ua.deleteNewPasswordLink(user.getLogin());

			String infoText = "Ihr Passwort wurde erfolgreich geändert. Absofort können Sie sich mit Ihrem neuen Passwort einloggen.";
			request.getSession().removeAttribute("userCreatNewPassword");
			request.getSession().setAttribute("content", "start");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?infotext="
					+ infoText);

		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
