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
		int right = 3; // 3 corresponds: Administrator
		if (isLoggedIn(request, response) && actionGranted(request, right)) {
			HttpSession session = request.getSession();
			User user = createUser(request);
			// checks whether the users login name already exists.
			String loginname = uAdmin.checkLoginname(user.getLogin());
			user.setLogin(loginname);
			uAdmin.createUser(user);
			try {
				// a link is going to be sent to the users email adress.
				uAdmin.sendNewPasswordLinkForNewUser(user);
				session.removeAttribute("emptyInputs");
				System.out
						.println("User successfully transmitted to UserAdministration!");

				// insert into History "User created"
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date currentTime = new Date();
				String date = formatter.format(currentTime);
				uAdmin.insertHistory(user.getLogin(), date, user.getFirstName() + " "
						+ user.getLastName() + " wurde erstellt");
				
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errormessage",
						"Failed to send new password link!");
				System.out
						.println("(SaveUser.java.94): failed to sendNewPasswordLink to email: "
								+ user.getMail());
			} finally {
				// redirect to the user table
				response.sendRedirect("/SopraMMS/LoadTable");
			}
		} else {
				// error caught if the users session timed out.
				String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
		}
	}

	protected User createUser(HttpServletRequest request) {
		List<String> rootInstitutes = uAdmin.getAllInstitute();
		// preparing variables to store parameters
		String[] names = new String[6];
		String[] paras = { "loginCellText", "firstnameCellText",
				"lastnameCellText", "emailCellText", "rightsSelect",
				"instituteSelect" };
		// in names[] are all results stored
		for (int i = 0; i < paras.length; i++) {
			names[i] = request.getParameter(paras[i]);
		}
		// strings are splitet by character
		char[] splitetInstitutes = names[5].toCharArray();
		char[] splitetRights = names[4].toCharArray();
		boolean[] finalRights = new boolean[7];
		List<String> finalInstitutes = new ArrayList<String>();
		try {
			// extract indexes to set rights in a boolean array
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
		// creating user with given parameters
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
