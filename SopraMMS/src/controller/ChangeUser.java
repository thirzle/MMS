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
    	
		boolean[] requiredRights = {false,false,false,true,false};
		List<String> rootInstitutes = ua.getAllInstitutesByName();
		if(isLoggedIn(request, response) && actionGranted(request, requiredRights)) {
			HttpSession session = request.getSession();
			User origUser = (User) session.getAttribute("userToEdit");
			if(origUser == null) {
				System.out.println("(ChangeUser.java): user is null");
				return;
			}
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
						System.out.println("(ChangeUser.java):RechtNr. "+tmp);
					}
					// Mapping: Integer <=> Institut
					for (char c : splitetInstitutes) {
						int tmp = Character.getNumericValue(c);
						finalInstitutes.add(rootInstitutes.get(tmp));
						System.out.println("(ChangeUser.java.73): add: "+rootInstitutes.get(tmp));
					}
				} catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("(ChangeUser.java): Failed to parseInt(string).");
				}
				System.out.println(!(finalInstitutes.isEmpty()));
 				if(!(finalInstitutes.isEmpty())){
						if(origUser.getFirstName()!= names[1] || origUser.getLastName() != names[2]) {
							ua.changeName(origUser, names[1], names[2]);
						}
						if(names[3] != origUser.getMail()) {
							ua.changeMail(origUser, names[3]);
						}
						if(finalInstitutes != origUser.getInstitute()) {
							ua.changeInstitute(origUser, finalInstitutes);
						}
						if(finalRights != origUser.getRights()) {
							ua.changeRights(origUser, finalRights);
						}
						session.removeAttribute("errormessage");
						session.setAttribute("content","loadTable");
						response.sendRedirect("/SopraMMS/guiElements/home.jsp");
				} else {
					System.out.println("there was an error converting institutes");
					session.setAttribute("errormessage", "an error occurred");
					session.setAttribute("content","loadTable");
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
		// TODO Auto-generated method stub
	}

}
