package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.icu.text.SimpleDateFormat;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
	int right = 3;
	if (isLoggedIn(request, response) && actionGranted(request, right)) {
	    User origUser = (User) session.getAttribute("userToEdit");
	    String loginname = origUser.getLogin();
	    User tmpUser = createUser(request);
	    if (origUser.getFaculty() != null) {
		tmpUser.setFaculty(origUser.getFaculty());
	    }
	    if (origUser.getRepresentative() != null) {
		tmpUser.setRepresentative(origUser.getRepresentative());
	    }
	    if (origUser.getSupervisor() != null) {
		tmpUser.setSupervisor(origUser.getSupervisor());
	    }
	    tmpUser.setPassword(origUser.getPassword());
	    System.out.println("(ChangeUser.java):origUser=" + origUser);
	    System.out.println("(ChangeUser.java):tmpUser=" + tmpUser);

	    if (origUser.getFirstName() != tmpUser.getFirstName() || origUser.getLastName() != tmpUser.getLastName()) {
		ua.changeName(origUser, tmpUser.getFirstName(), tmpUser.getLastName());
	    }
	    if (tmpUser.getMail() != origUser.getMail()) {
		ua.changeMail(origUser, tmpUser.getMail());
	    }
	    if (tmpUser.getInstitute() != origUser.getInstitute()) {
		ua.changeInstitute(origUser, tmpUser.getInstitute());
	    }
	    System.out.println(tmpUser.getRights() != origUser.getRights());
	    if (tmpUser.getRights() != origUser.getRights()) {
		ua.changeRights(origUser, tmpUser.getRights());
	    }

	    // insert into History "User changed"
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    Date currentTime = new Date();
	    String date = formatter.format(currentTime);
	    ua.insertHistory(loginname, date, "Wurde ge&auml;ndert");

	    session.removeAttribute("errormessage");
	    response.sendRedirect("/SopraMMS/LoadTable");
	} else {
	    // Unerlaubter Zugriff / weil nicht eingeloggt oder keine Erlaubnis
	    session.setAttribute("content", "start");
	    response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	    System.out.println("not logged in or access denied.");
	}

    } // end doGet()

    protected User createUser(HttpServletRequest request) {
	List<String> rootInstitutes = ua.getAllInstituteID();
	// Die Liste wird ggf. mit den Feldnamen leeren Feldern gefuellt
	String[] names = new String[6];
	String[] paras = { "loginCellText", "firstnameCellText", "lastnameCellText", "emailCellText", "rightsSelect", "instituteSelect" };
	// prueft Request Paramater auf null Werte und fuellt entsprechend die 'emptyInputs' Liste
	for (int i = 0; i < paras.length; i++) {
	    names[i] = request.getParameter(paras[i]);
	    System.out.println("(ChangeUser.java):names[]" + names[i]);
	}
	// Die erhaltenen Institute und Rechte stehen in einem String gespeichert
	// dieser wird hier getrennt und entsprechend Institute und Rechte extrahiert
	char[] splitetInstitutes = names[5].toCharArray();
	char[] splitetRights = names[4].toCharArray();
	boolean[] finalRights = new boolean[7];
	List<String> finalInstitutes = new ArrayList<String>();
	try {
	    // Extrahiere Integer Werte um das Rechte boolean[] zu setzten
	    for (char c : splitetRights) {
		int tmp = Character.getNumericValue(c);
		finalRights[tmp] = true;
		System.out.println("(ChangeUser.java):RechtNr. " + tmp);
	    }
	    // Mapping: Integer <=> Institut
	    for (char c : splitetInstitutes) {
		int tmp = Character.getNumericValue(c);
		finalInstitutes.add(rootInstitutes.get(tmp));
		System.out.println("(ChangeUser.java.70): add: " + rootInstitutes.get(tmp));
	    }
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.out.println("(ChangeUser.java): Failed to parseInt(string).");
	}
	// Der Benutzer mit den erhaltenen Attributen kann erzeugt werden
	User user = new User(names[0], names[1], names[2], names[3], finalRights, finalInstitutes, "");
	System.out.println("(ChangeUser.java):create user " + user.toString());
	return user;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
    }

}
