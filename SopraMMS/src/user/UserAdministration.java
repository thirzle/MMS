package user;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mail.EmailTelnet;
import management.Deadline;
import model.UserDBController;

public class UserAdministration {

	static UserDBController userDBController = new UserDBController();

	// Constructor
	public UserAdministration() {

	}

	// Creates an object User and send it to the database controller
	public User createUser(String loginname, String firstName, String lastName,
			String mail, boolean[] rights, String session, String faculty,
			List<String> institute, String representative, String supervisor,
			String password) {

		User user = new User(loginname, firstName, lastName, mail, rights,
				session, faculty, institute, representative, supervisor,
				password);
		userDBController.createUser(user);
		return user;
	}

	// Gets an object User and send it to the database controller
	public User createUser(User user) {
	    	// generate random password
	    	user.setPassword(Integer.toString(user.getPassword().hashCode()));
		List<String> institutes = userDBController.convertInstituteToID(user
				.getInstitute());
		user.setInstitute(institutes);
		userDBController.createUser(user);

		return user;
	}

	/*
	 * Gets an object User which contains the loginname to identify the user and
	 * a boolean array which contains the new rights
	 */
	public User changeRights(User user, boolean[] newRights) {

		User newUser = user;
		newUser.setRights(newRights);
		Boolean changeRights = userDBController.changeRights(user, newRights);
		System.err.println("(UserAdministration.java):change rights succesfully="+changeRights);
		return newUser;
	}
	
	public User changeLoginname(User user, String loginname) {
		User newUser = user;
		newUser.setLogin(loginname);
		userDBController.changeUser(user, newUser);
		return newUser;
	}

	/*
	 * Gets an object User which contains the loginname to identify the user and
	 * a String with the new mail address
	 */
	public User changeMail(User user, String mail) {
		User newUser = user;
		newUser.setMail(mail);
		userDBController.changeUser(user, newUser);
		return newUser;
	}

	public User changeFaculty(User user, String faculty) {
		User newUser = user;
		newUser.setFaculty(faculty);
		userDBController.changeUser(user, newUser);
		return newUser;
	}

	/*
	 * Gets an object User which contains the loginname to identify the user and
	 * a list of institute names to identify them
	 */
	public User changeInstitute(User user, List<String> institute) {
		User newUser = user;
		newUser.setInstitute(institute);
		userDBController.changeUser(user, newUser);
		return newUser;
	}

	public User changeRepresentative(User user, String representative) {
		User newUser = user;
		newUser.setRepresentative(representative);
		userDBController.changeUser(user, newUser);
		return newUser;
	}

	public User changeSupervisor(User user, String supervisor) {
		User newUser = user;
		newUser.setRepresentative(supervisor);
		userDBController.changeUser(user, newUser);
		return newUser;
	}

	public User changeName(User user, String firstname, String lastname) {
		User newUser = user;
		newUser.setFirstName(firstname);
		newUser.setLastName(lastname);
		userDBController.changeUser(user, newUser);
		return newUser;
	}

	public User changePassword(User user, String password) {
		userDBController.setPassword(user.getLogin(), password);
		user.setPassword(password);
		return user;
	}

	public User getUser(String loginname) {
		return userDBController.getUser(loginname);
	}

	public User getUserByMail(String mail) {
		return userDBController.getUserByEmail(mail);
	}

	public boolean deleteUser(String loginname) {
		return userDBController.deleteUser(loginname);
	}

	public void sendNewPasswordLink(String email) throws IOException {
		User user = userDBController.getUserByEmail(email);

		String newLink = Math.random() * 1000000000 * Math.PI + ""
				+ Math.random() * 1000000000 * Math.PI + ""
				+ (Math.random() * 1000000000 * Math.PI);

		userDBController.setForgotPwdByMail(email, newLink);

		String url = "http://localhost:8080/SopraMMS/CreateNewPassword?link="
				+ newLink;

		EmailTelnet mail = new EmailTelnet();

		StringBuilder text = new StringBuilder();
		text.append("Sehr geehrte/geehrter Frau/Herr " + user.getLastName()
				+ ",");
		text.append("\n\n");
		text.append("Sie haben soeben ein neues Passwort für Ihren Account (Benutzername: "
				+ user.getLogin() + ") im MMS beantragt.");
		text.append("\n\n");
		text.append("Bitte rufen Sie folgenden Link auf und ändern Sie ihr Passwort.");
		text.append("\n\n");
		text.append(url);
		text.append("\n\n");
		text.append("Sollten Sie kein Passwort für Ihren Account beantragt haben ignorieren Sie diese E-Mail.");
		text.append("\n\n\n");
		text.append("Mit freundlichen Grüßen");
		text.append("\n");
		text.append("MMS-Team");

		mail.send_mail("MMS - Neues Passwort", email, text.toString());
	}
	
	public void sendNewPasswordLinkForNewUser(User user) throws IOException {

		String newLink = Math.random() * 1000000000 * Math.PI + ""
				+ Math.random() * 1000000000 * Math.PI + ""
				+ (Math.random() * 1000000000 * Math.PI);
		String email = user.getMail();
		userDBController.setForgotPwdByMail(email, newLink);

		String url = "http://localhost:8080/SopraMMS/CreateNewPassword?link="
				+ newLink;

		EmailTelnet mail = new EmailTelnet();

		StringBuilder text = new StringBuilder();
		text.append("Sehr geehrte/geehrter Frau/Herr " + user.getLastName()
				+ ",");
		text.append("\n\n");
		text.append("Sie wurden soeben an dem Modul Management System der Universität Ulm registriert.");
		text.append("\n\n");
		text.append("Der Benutzername für Ihren registrierten Account lautet: "+user.getLogin());
		text.append("\n\n");
		text.append("Bitte rufen Sie folgenden Link auf und erstellen Sie ihr Passwort.");
		text.append("\n\n");
		text.append(url);
		text.append("\n\n");
		text.append("Sie haben die Möglichkeit das neu erstellte Passwort zu einem späteren Zeitpunkt zu ändern.");
		text.append("\n\n");
		text.append("Mit freundlichen Grüßen");
		text.append("\n");
		text.append("MMS-Team");

		mail.send_mail("MMS - Neues Passwort", email, text.toString());
	}

	public User checkNewPasswordLink(String link) {
		return userDBController.getUserByForgotPwd(link);
	}

	public void deleteNewPasswordLink(String loginname) {
		userDBController.removeForgotPwdByLoginname(loginname);
	}

	public User checkLogin(String session) {
		// TODO Methode existiert in DB noch nicht
		User user = userDBController.checkSession(session);
		if (user != null) {
			return user;
		}
		return null;
	}

	public User login(String loginname, String password, String session) {

		String pwhash = "" + password.hashCode();
		System.out.println("##### Einloggen #####");
		System.out.println("Loginname: " + loginname);
		System.out.println("Passwort: " + password + " --> " + pwhash);

		if (userDBController.checkPassword(loginname, pwhash)) {

			User user = userDBController.getUser(loginname);
			User newUser = user;
			newUser.setSession(session);
			userDBController.changeUser(user, newUser);

			System.out.println("Login erfolgreich");
			System.out.println("#####################");

			return newUser;
		} else {
			System.out.println("Login fehlgeschlagen");
			System.out.println("#####################");
			return null;
		}
	}

	public boolean removeRepresentative(User user) {
		return userDBController.removeRepresentative(user);
	}

	public void logout(String loginname) {
		User user = userDBController.getUser(loginname);
		if (user != null) {
			user.setSession(null);
			userDBController.changeUser(user, user);
		}
	}

	public Deadline getDeadlinebyFaculty(String facultyID) {
		return userDBController.getDeadlineByFaculty(facultyID);
	}
	
	public void setDeadlinebyFaculty(Deadline deadline){
		userDBController.setDeadlineByFaculty(deadline);
	}
	
	public void updateDeadlinebyFaculty(Deadline deadline){
		userDBController.updateDeadlineByFaculty(deadline);
	}

	public boolean checkPassword(String loginname, String password) {
		return userDBController.checkPassword(loginname, password);
	}

	public String getFacultyName(User user) {
		return userDBController.getFacultyName(user);
	}

	public List<String> getAllFacultiesByName() {
		return userDBController.getFacultiesByName();
	}

	public List<String> getAllFacultiesID() {
		return userDBController.getFacultiesID();
	}

	public List<String[]> getAllInstitutesByName(String loginname) {
		return userDBController.getInstituteNames(loginname);
	}

	public List<String> getAllInstituteID() {
		return userDBController.getInstituteID();
	}
	
	public List<String> getAllInstitute() {
		return userDBController.getInstitutes();
	}

	public List<User> getAllUsers() {
		return userDBController.getAllUsers();
	}

//	get Mail: übergeben: Liste von Rechten, gibt zurück: Liste mit String Array -->Name, Vorname, Email-Adresse
	public List<String[]> getEmails(boolean[] rights){
		return userDBController.getEmails(rights);
	}
	
	public List<String> getInstituteNames(User user) {
		List<String[]> listA = userDBController.getInstituteNames(user.getLogin());
		List<String> listB = new ArrayList<>();
		for (String[] strings : listA) {
			listB.add(strings[1]);
		}
		return listB;
	}

	public List<String> getCoursesByFaculty(String facultyID) {
		return userDBController.getCoursesByFaculty(facultyID);
	}

	// News

	// true -> public
	// false -> private
	public List<String[]> getNewsByType(int type) {
		return userDBController.getNews(type);

	}
	
	public void insertHistory(String loginname, String date, String content){
		userDBController.insertHistory(loginname, date, content);
	}
	
	public List<String[]> showHistory(){
		return userDBController.showHistory();
	}

	public void deleteNews(String title) {
		userDBController.deleteNews(title);
	}

	public void addNews(String[] data, int type) {
		userDBController.addNews(data[0], data[1], type);
	}

	public int numberOfNews() {
		return userDBController.numberOfNews();
	}
}
