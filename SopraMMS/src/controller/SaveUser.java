package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class SaveUser
 */
@WebServlet("/SaveUser")
public class SaveUser extends SessionCheck {
	private static final long serialVersionUID = 1L;
    private UserAdministration ua;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveUser() {
        super();
        ua = new UserAdministration();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean[] requiredRights = {false,false,false,true,false};
		List<String> rootInstitutes = ua.getAllInstitutesByName();
		if(isLoggedIn(request, response) && actionGranted(request, requiredRights)) {
			HttpSession session = request.getSession();
			// Die Liste wird ggf. mit den Feldnamen leeren Feldern gefuellt
			List<String> emptyInputs = new ArrayList<String>();
			List<String[]> notEmptyInputs = new ArrayList<String[]>();
			String[] names = new String[6];
			String[] paras = {"loginCellText","firstnameCellText","lastnameCellText","emailCellText","rightsSelect","instituteSelect"};
			//prueft Request Paramater auf null Werte und fuellt entsprechend die 'emptyInputs' Liste
			for (int i = 0; i < paras.length; i++) {
				names[i] = request.getParameter(paras[i]);
				if(names[i].equals(null)||names[i] == "") {
					emptyInputs.add(paras[i]);
				} else {
					String[] tmp = {paras[i],names[i]};
					notEmptyInputs.add(tmp);
				}
			}		
			
			// Die erhaltenen Institute und Rechte stehen in einem String gespeichert
			// dieser wird hier getrennt und entsprechend Institute und Rechte extrahiert
			if(emptyInputs.isEmpty()) {
				char[] splitetInstitutes = names[5].toCharArray();
				char[] splitetRights = names[4].toCharArray();
				boolean[] finalRights = {false,false,false,false,false};
				List<String> finalInstitutes = new ArrayList<String>();
				try {
					// Extrahiere Integer Werte um das Rechte boolean[] zu setzten
					for (char c : splitetRights) {
						int tmp = Character.getNumericValue(c);
						finalRights[tmp] = true;
						System.out.println("(SaveUser.java):RechtNr. "+tmp);
					}
					// Mapping: Integer <=> Institut
					for (char c : splitetInstitutes) {
						int tmp = Character.getNumericValue(c);
						finalInstitutes.add(rootInstitutes.get(tmp));
						System.out.println("(SaveUser.java.70): add: "+rootInstitutes.get(tmp));
					}
				} catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("(SaveUser.java): Failed to parseInt(string).");
				}
				// Der Benutzer mit den erhaltenen Attributen kann erzeugt werden
				User user = new User(names[0],names[1],names[2],names[3],finalRights,finalInstitutes,"");
				user.setPassword("hartwig");
				System.out.println("create user "+user.toString());
				if(!finalInstitutes.isEmpty()){
					ua.createUser(user);
					session.removeAttribute("emptyInputs");
					session.setAttribute("content", "editUser");
					response.sendRedirect("/SopraMMS/EditUser");
					System.out.println("User successfully transmitted to UserAdministration!");
				} else {
					System.out.println("there was an error converting institutes");
				}
			} else {
				// Ein oder mehrere Felder waren nicht gefuellt
				session.setAttribute("notEmptyInputs", notEmptyInputs);
				session.setAttribute("emptyInputs", emptyInputs);
				session.setAttribute("content", "newUser");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
				System.out.println("some input is missing.");
			}
		} else {
			// Unerlaubter Zugriff / weil nicht eingeloggt oder keine Erlaubnis
			HttpSession session = request.getSession();
			session.setAttribute("content", "start");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			System.out.println("not logged in or access denied.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost "+request.getParameter("rights"));
	}
}
