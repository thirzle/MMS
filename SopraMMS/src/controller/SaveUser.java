package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.icu.text.SimpleDateFormat;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class SaveUser
 */
@WebServlet("/SaveUser")
public class SaveUser extends SessionCheck {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveUser() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int right = 3; // 3 entspricht dem Recht: Administrator
		if (isLoggedIn(request, response) && actionGranted(request, right)) {
			HttpSession session = request.getSession();
			User user = createUser(request);
			String loginname = ua.checkLoginname(user.getLogin());
			user.setLogin(loginname);
			ua.createUser(user);
			try {
				ua.sendNewPasswordLinkForNewUser(user);
				session.removeAttribute("emptyInputs");
				System.out
						.println("User successfully transmitted to UserAdministration!");

				// insert into History "User created"
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date currentTime = new Date();
				String date = formatter.format(currentTime);
				ua.insertHistory(user.getLogin(), date, user.getFirstName() + " "
						+ user.getLastName() + " wurde erstellt");
				
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errormessage",
						"Failed to send new password link!");
				System.out
						.println("(SaveUser.java.94): failed to sendNewPasswordLink to email: "
								+ user.getMail());
			} finally {
				response.sendRedirect("/SopraMMS/LoadTable");
			}
		} else {
			// Unerlaubter Zugriff / weil nicht eingeloggt oder keine Erlaubnis
			HttpSession session = request.getSession();
			session.setAttribute("content", "start");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			System.out.println("not logged in or access denied.");
		}
	}

	protected User createUser(HttpServletRequest request) {
		List<String> rootInstitutes = ua.getAllInstitute();
		// Die Liste wird ggf. mit den Feldnamen leeren Feldern gefuellt
		String[] names = new String[6];
		String[] paras = { "loginCellText", "firstnameCellText",
				"lastnameCellText", "emailCellText", "rightsSelect",
				"instituteSelect" };
		// Die erhaltenen Institute und Rechte stehen in einem String
		// gespeichert
		for (int i = 0; i < paras.length; i++) {
			names[i] = request.getParameter(paras[i]);
		}
		// dieser wird hier getrennt und entsprechend Institute und Rechte
		// extrahiert
		char[] splitetInstitutes = names[5].toCharArray();
		char[] splitetRights = names[4].toCharArray();
		boolean[] finalRights = new boolean[7];
		List<String> finalInstitutes = new ArrayList<String>();
		try {
			// Extrahiere Integer Werte um das Rechte boolean[] zu setzten
			for (char c : splitetRights) {
				int tmp = Character.getNumericValue(c);
				finalRights[tmp] = true;
				System.out.println("(SaveUser.java):RechtNr. " + tmp);
			}
			// Mapping: Integer <=> Institut
			for (char c : splitetInstitutes) {
				int tmp = Character.getNumericValue(c);
				finalInstitutes.add(rootInstitutes.get(tmp));
				System.out.println("(SaveUser.java): add: "
						+ rootInstitutes.get(tmp));
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("(SaveUser.java): Failed to parseInt(string).");
		}
		// Der Benutzer mit den erhaltenen Attributen kann erzeugt werden
		User user = new User(names[0], names[1], names[2], names[3],
				finalRights, finalInstitutes, "");
		user.setPassword(names[0]);
		System.out.println("create user " + user.toString());

		return user;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost " + request.getParameter("rights"));
	}
}
