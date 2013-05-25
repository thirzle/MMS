package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String oldPassword = request.getParameter("oldPassword");
		String newPassword1 = request.getParameter("newPassword1");
		String newPassword2 = request.getParameter("newPassword2");
		UserAdministration ua = new UserAdministration();

		request.getSession().setAttribute("generallyMenu", "open");
		if (!newPassword1.equals(newPassword2)) {
			request.getSession().setAttribute("content",
					"changedPwStatusPw12Wrong");

			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		} else {
			User user = (User) (request.getSession().getAttribute("user"));
			if (ua.checkPassword(user.getLogin(), oldPassword.hashCode()+"")) {
				ua.changePassword(user, newPassword1.hashCode()+"");
				request.getSession().setAttribute("content",
						"changedPwStatusdone");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			} else {
				request.getSession().setAttribute("content",
						"changedPwStatusOldPwWrong");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
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
