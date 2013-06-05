package controller;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.User;
import user.UserAdministration;

import mail.EmailApache;
import mail.EmailTelnet;

/**
 * Servlet implementation class AppointRepresentative
 */
@WebServlet("/AppointRepresentative")
public class AppointRepresentative extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AppointRepresentative() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserAdministration uAdmin = new UserAdministration();
		String firstNameRep = request.getParameter("firstNameRep");
		String lastNameRep = request.getParameter("lastNameRep");
		String mailRep = request.getParameter("mailRep");

		if (firstNameRep.equals(null) || lastNameRep.equals(null)
				|| mailRep.equals(null) || firstNameRep.equals("")
				|| lastNameRep.equals("") || mailRep.equals("")) {
			request.getSession().setAttribute("lessData", true);
			request.getSession().setAttribute("content",
					"appointRepresentative");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			return;
		}

		User user = (User) request.getSession().getAttribute("user");
		User userR = uAdmin.getUserByMail(mailRep);

		// if representative doesn't exist
		if (userR == null) {
			String content = user.getFirstName() + " " + user.getLastName()
					+ " moechte " + firstNameRep + " " + lastNameRep
					+ " (E-Mail Adresse: " + mailRep + ") "
					+ " zu ihrem/seinem Stellvertreter ernennen"
					+ "\nhttp://localhost:8080/SopraMMS/";

			EmailTelnet mail = new EmailTelnet();
			boolean[] rights = { false, false, false, false, false, false, true };
			LinkedList<String[]> adminMails = (LinkedList) uAdmin
					.getEmails(rights);
			for (String[] strings : adminMails) {
				mail.send_mail("Neuer Stellvertreter", strings[2], content);
			}
			request.getSession().setAttribute("content", "applyRepresentative");
			
			// insert into History "Representative appointed"
			Date currentTime = new Date();
			java.sql.Date date = new java.sql.Date(currentTime.getYear(),
					currentTime.getMonth(), currentTime.getDay()+2);
			uAdmin.insertHistory(user.getLogin(), date, "Hat "+firstNameRep+" "+lastNameRep+" als Stellvertreter beantragt.");
		}
		// if representative exists
		else {
			if (!userR.getFirstName().equals(firstNameRep)
					|| !userR.getLastName().equals(lastNameRep)) {
				request.getSession().setAttribute("wrongData", true);
				request.getSession().setAttribute("content",
						"appointRepresentative");
			} else {
				uAdmin.changeRepresentative(user, userR.getLogin());
				request.getSession().setAttribute("content",
						"createdRepresentative");
				// insert into History "Representative created"
				Date currentTime = new Date();
				java.sql.Date date = new java.sql.Date(currentTime.getYear(),
						currentTime.getMonth(), currentTime.getDay()+2);
				uAdmin.insertHistory(user.getLogin(), date, "Hat "+firstNameRep+" "+lastNameRep+" als Stellvertreter ernannt.");
			}
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
