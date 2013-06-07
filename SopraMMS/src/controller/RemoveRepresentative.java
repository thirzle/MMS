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
 * Servlet implementation class RemoveRepresentative
 */
@WebServlet("/RemoveRepresentative")
public class RemoveRepresentative extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemoveRepresentative() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// remove representative of user
		User user = (User) request.getSession().getAttribute("user");
		UserAdministration uAdmin = new UserAdministration();
		
		String repLoginname = user.getRepresentative();
		User rep = uAdmin.getUser(repLoginname);
		String firstNameRep = rep.getFirstName();
		String lastNameRep = rep.getLastName();
		
		if (uAdmin.removeRepresentative(user)) {
			user.setRepresentative(null);
			request.getSession()
					.setAttribute("content", "removeRepresentative");

			// insert into History "Representative removed"
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date currentTime = new Date();
			String date = formatter.format(currentTime);
			uAdmin.insertHistory(user.getLogin(), date, "Hat " + firstNameRep
					+ " " + lastNameRep + " als Stellvertreter gel&ouml;scht.");

		}
		request.getSession().setAttribute("generallyMenu", "open");
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
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
