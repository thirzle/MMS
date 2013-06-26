package controller;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.icu.text.SimpleDateFormat;

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
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserAdministration uAdmin = new UserAdministration();
		String firstNameRep = request.getParameter("firstNameRep");
		String lastNameRep = request.getParameter("lastNameRep");
		String mailRep = request.getParameter("mailRep");
		String infotext = " ";
		boolean wrongData = false;

		if (firstNameRep.equals(null) || lastNameRep.equals(null)
				|| mailRep.equals(null) || firstNameRep.equals("")
				|| lastNameRep.equals("") || mailRep.equals("")) {
			session.setAttribute("lessDataRep", true);
			session.setAttribute("content", "appointRepresentative");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			return;
		}

		User user = (User) session.getAttribute("user");
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
			session.setAttribute("content", "home");
			infotext = "Ihr Stellvertreter wurde beantragt. Sie bekommen eine Nachricht sobald der Administrator diesen im System registriert hat.";

			// insert into History "Representative appointed"
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date currentTime = new Date();
			String date = formatter.format(currentTime);
			uAdmin.insertHistory(user.getLogin(), date, "Hat " + firstNameRep
					+ " " + lastNameRep + " als Stellvertreter beantragt.");
		}
		// if representative exists
		else {
			if (!userR.getFirstName().equals(firstNameRep)
					|| !userR.getLastName().equals(lastNameRep)) {
				session.setAttribute("wrongDataRep", "wrongName");
				session.setAttribute("content", "appointRepresentative");
				wrongData = true;
			}
			// you can't appoint yourself as representative
			else if (userR.getFirstName().equals(user.getFirstName())
					&& userR.getLastName().equals(user.getLastName())) {
				session.setAttribute("wrongDataRep", "yourselfRep");
				session.setAttribute("content", "appointRepresentative");
				wrongData = true;
			}
			// appoint representative
			else {
				uAdmin.changeRepresentative(user, userR.getLogin());
				request.getSession().setAttribute("content", "home");
				infotext = "Ihr Stellvertreter wurde aktualisiert: "
						+ userR.getFirstName() + " " + userR.getLastName();

				// send e-mail to representative
				String content = user.getFirstName() + " " + user.getLastName()
						+ " hat Sie"
						+ " zu ihrem/seinem Stellvertreter ernannt";
				EmailTelnet mail = new EmailTelnet();
				mail.send_mail("Neuer Stellvertreter", mailRep, content);

				// insert into History "Representative created"
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date currentTime = new Date();
				String date = formatter.format(currentTime);
				uAdmin.insertHistory(user.getLogin(), date, "Hat "
						+ firstNameRep + " " + lastNameRep
						+ " als Stellvertreter ernannt.");
			}
		}
		request.getSession().setAttribute("generallyMenu", "open");
		if (!wrongData) {
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="
					+ infotext);
		} else {
			// Direct Link to Home
			if (session.getAttribute("content").equals("home")) {
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true");
			} else {
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
	}
}
