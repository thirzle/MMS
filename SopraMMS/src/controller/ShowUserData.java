package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class ShowUserData
 */
@WebServlet("/ShowUserData")
public class ShowUserData extends SessionCheck {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowUserData() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			String[] userArray = new String[7];
			boolean[] rights = user.getRights();
			ArrayList<String> userRights = new ArrayList<String>();
			ArrayList<String> instituteList = new ArrayList<String>();
	
			userArray[0] = user.getLogin();
			userArray[1] = user.getFirstName() + " " + user.getLastName();
			userArray[2] = user.getMail();
			if (user.getRepresentative() != null)
				userArray[3] = uAdmin.getUser(user.getRepresentative())
						.getFirstName()
						+ " "
						+ uAdmin.getUser(user.getRepresentative()).getLastName();
			else
				userArray[3] = "- Kein Stellvertreter vorhanden -";
			if (user.getSupervisor() != null)
				userArray[4] = uAdmin.getUser(user.getSupervisor()).getFirstName()
						+ " " + uAdmin.getUser(user.getSupervisor()).getLastName();
			else
				userArray[4] = "- Nicht in der Position eines Stellvertreters -";
			userArray[5] = uAdmin.getFacultyName(user).substring(9);
			userArray[6] = uAdmin.getCurriculum(userArray[0]);
			if(userArray[6]==null){
				userArray[6]="";
			}
			
			instituteList = (ArrayList<String>) uAdmin.getInstituteNames(user);
	
			if (rights[1])
				userRights.add("Dozent");
			if (rights[2])
				userRights.add("Modulverantwortlicher");
			if (rights[3])
				userRights.add("Dezernat 2");
			if (rights[4])
				userRights.add("Koordinator");
			if (rights[5])
				userRights.add("Freigabeberechtigter");
			if (rights[6])
				userRights.add("Administrator");
	
			session.setAttribute("rightsForShowUserData", userRights);
			session.setAttribute("showUserDataArray", userArray);
			session.setAttribute("instituteListForUserData", instituteList);
			session.setAttribute("content", "showUserData");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
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
