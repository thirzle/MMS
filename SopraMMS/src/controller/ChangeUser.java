package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.User;

/**
 * Servlet implementation class ChangeUser
 */
@WebServlet("/ChangeUser")
public class ChangeUser extends SessionCheck implements Servlet {
	private static final long serialVersionUID = 1L;
	private List<String> emptyInputs = new ArrayList<String>();
	private List<String[]> notEmptyInputs = new ArrayList<String[]>();
       
    /**
     * @see SessionCheck#SessionCheck()
     */
    public ChangeUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
		int right = 3;
		if(isLoggedIn(request, response) && actionGranted(request, right)) {
			User origUser = (User) session.getAttribute("userToEdit");
			if(origUser == null) {
				System.out.println("(ChangeUser.java): user is null");
				return;
			}
			User tmpUser = createUser(request);
			if(emptyInputs.isEmpty()) {
 				if(tmpUser != null){
	 				if(origUser.getFirstName()!= tmpUser.getFirstName() || origUser.getLastName() != tmpUser.getLastName()) {
	 					ua.changeName(origUser, tmpUser.getFirstName(), tmpUser.getLastName());
					}
					if(tmpUser.getMail() != origUser.getMail()) {
						ua.changeMail(origUser, tmpUser.getMail());
					}
					if(tmpUser.getInstitute() != origUser.getInstitute()) {
						ua.changeInstitute(origUser, tmpUser.getInstitute());
					}
					if(tmpUser.getRights() != origUser.getRights()) {
						ua.changeRights(origUser, tmpUser.getRights());
					}
					session.removeAttribute("errormessage");
					response.sendRedirect("/SopraMMS/LoadTable");
 				} else {
					System.out.println("there was an error converting institutes");
					session.setAttribute("errormessage", "an error occurred");
					response.sendRedirect("SopraMMS/LoadTable");
 				}
			} else {
				// Ein oder mehrere Felder waren nicht gefuellt
				session.setAttribute("emptyInputs", emptyInputs);
				session.setAttribute("content", "editUser");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
				System.out.println("some input is missing.");
			}
		} else {
			// Unerlaubter Zugriff / weil nicht eingeloggt oder keine Erlaubnis
			session.setAttribute("content", "start");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			System.out.println("not logged in or access denied.");
		}
		
	} // end doGet()
    
    protected User createUser(HttpServletRequest request) {
		List<String> rootInstitutes = ua.getAllInstitutesByName();
		// Die Liste wird ggf. mit den Feldnamen leeren Feldern gefuellt
		String[] names = new String[6];
		String[] paras = {"loginCellText","firstnameCellText","lastnameCellText","emailCellText","rightsSelect","instituteSelect"};
		//prueft Request Paramater auf null Werte und fuellt entsprechend die 'emptyInputs' Liste
		for (int i = 0; i < paras.length; i++) {
			names[i] = request.getParameter(paras[i]);
			if(names[i].equals(null)||names[i] == "") {
				this.emptyInputs.add(paras[i]);
			} else {
				String[] tmp = {paras[i],names[i]};
				this.notEmptyInputs.add(tmp);
			}
		}		
		// Die erhaltenen Institute und Rechte stehen in einem String gespeichert
		// dieser wird hier getrennt und entsprechend Institute und Rechte extrahiert
		if(this.emptyInputs.isEmpty()) {
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
			System.out.println("create user "+user.toString());
			return user;
		} else {
			return null;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
