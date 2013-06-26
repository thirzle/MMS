package controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.icu.text.SimpleDateFormat;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends SessionCheck {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Checks whether the creation of a new password is allowed.
	 * <p>
	 * Reads the old as well as the two new passwords. Checks the equality of the two new passwords.
	 * If they match, the old password is checked and the new password gets stored.
	 * The event is recorded in the history.
	 * <p>
	 * 
	 * @see UserAdministration
	 * @see User
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
		String oldPassword = request.getParameter("oldPassword");
		String newPassword1 = request.getParameter("newPassword1");
		String newPassword2 = request.getParameter("newPassword2");

		request.getSession().setAttribute("generallyMenu", "open");
		if (!newPassword1.equals(newPassword2)) {
				request.getSession().setAttribute("content", "changedPw");
	
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?changePwStatus=changedPwStatusPw12Wrong");
			} else {
				User user = (User) (request.getSession().getAttribute("user"));
				if (uAdmin.checkPassword(user.getLogin(), oldPassword.hashCode() + "")) {
					uAdmin.changePassword(user, newPassword1.hashCode() + "");
	
					// Insert into History "Password changed"
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date currentTime = new Date();
					String date = formatter.format(currentTime);
					uAdmin.insertHistory(user.getLogin(), date, "Passwort ge&auml;ndert");
	
					request.getSession().setAttribute("content", "changedPw");
					response.sendRedirect("/SopraMMS/guiElements/home.jsp?changePwStatus=changedPwStatusdone");
				} else {
					request.getSession().setAttribute("content", "changedPw");
					response.sendRedirect("/SopraMMS/guiElements/home.jsp?changePwStatus=changedPwStatusOldPwWrong");
				}
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}
}
