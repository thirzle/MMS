package user;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mail.EmailTelnet;
import management.Deadline;
import model.UserDBController;

/**
 * The UserAdministration class provides easy access to the data that is gathered from the database.
 * 
 * @author David
 * @see User
 */
public class UserAdministration {

	/**
	 * 
	 */
	static UserDBController userDBController = new UserDBController();

	// Constructor
	/**
	 * Default constructor.
	 */
	public UserAdministration() {

	}

	/**
	 * Creates an User object and send it to the database controller.
	 * 
	 * @param loginname			{@link User#getLogin()}
	 * @param firstName			{@link User#getFirstName()}
	 * @param lastName			{@link User#getLastName()}
	 * @param mail				{@link User#getMail()}
	 * @param rights			{@link User#getRights()}
	 * @param session			{@link User#getSession()}
	 * @param faculty			{@link User#getFaculty()}
	 * @param institute			{@link User#getInstitute()}
	 * @param representative	{@link User#getRepresentative()}
	 * @param supervisor		{@link User#getSupervisor()}
	 * @param password			{@link User#getPassword()}
	 * @return					User object.
	 */
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

	/**
	 * Gets an User object and sends it to the database controller.
	 * 
	 * @param user		User object.
	 * @return			Complete User object.
	 */
	public User createUser(User user) {
	    	// generate random password
	    	user.setPassword(user.getLogin());
		List<String> institutes = userDBController.convertInstituteToID(user
				.getInstitute());
		user.setInstitute(institutes);
		userDBController.createUser(user);

		return user;
	}

	/**
	 * Gets an User object which contains the loginname to identify the user and a boolean array which contains the new rights.
	 * @param user			User object.
	 * @param newRights		Boolean array containing the new rights.
	 * @return				Reworked User object.
	 */
	public User changeRights(User user, boolean[] newRights) {

		User newUser = user;
		newUser.setRights(newRights);
		Boolean changeRights = userDBController.changeRights(user, newRights);
		System.err.println("(UserAdministration.java):change rights succesfully="+changeRights);
		return newUser;
	}
	
	/**
	 * Gets an User object which contains the loginname to identify the user and the new loginname.
	 * 
	 * @param user			User object.
	 * @param loginname		{@link User#getLogin()}	
	 * @return				Reworked User object.
	 */
	public User changeLoginname(User user, String loginname) {
		User newUser = user;
		newUser.setLogin(loginname);
		userDBController.changeUser(user, newUser);
		return newUser;
	}


	/**
	 * Gets an User object which contains the loginname to identify the user and
	 * a String with the new mail address.
	 * 
	 * @param user			User object.
	 * @param mail			{@link User#getMail()}
	 * @return				Reworked User object.
	 */
	public User changeMail(User user, String mail) {
		User newUser = user;
		newUser.setMail(mail);
		userDBController.changeUser(user, newUser);
		return newUser;
	}

	/**
	 * Gets an User object which contains the loginname to identify the user and
	 * a String with the new faculty.
	 * 
	 * @param user				User object.
	 * @param faculty			{@link User#getFaculty()}
	 * @return					Reworked User object.
	 */
	public User changeFaculty(User user, String faculty) {
		User newUser = user;
		newUser.setFaculty(faculty);
		userDBController.changeUser(user, newUser);
		return newUser;
	}

	/**
	 * Gets an User object which contains the loginname to identify the user and
	 * a list of institute containing the new ones.
	 * 
	 * @param user			User object.
	 * @param institute		{@link User#getInstitute()}
	 * @return				Reworked User object.
	 */
	public User changeInstitute(User user, List<String> institute) {
		User newUser = user;
		newUser.setInstitute(institute);
		userDBController.changeUser(user, newUser);
		return newUser;
	}

	/**
	 * Gets an User object which contains the loginname to identify the user and the new representative.
	 * @param user				User object.
	 * @param representative	{@link User#getRepresentative()}
	 * @return					Reworked User object.
	 */
	public User changeRepresentative(User user, String representative) {
		user.setRepresentative(representative);
		userDBController.setRepresentative(user, representative);
		return user;
	}

	/**
	 * Gets an User object which contains the loginname to identify the user and the new supervisor.
	 * 
	 * @param user				User object.
	 * @param supervisor		{@link User#getSupervisor()}
	 * @return					Reworked User object.
	 */
	public User changeSupervisor(User user, String supervisor) {
		User newUser = user;
		newUser.setRepresentative(supervisor);
		userDBController.changeUser(user, newUser);
		return newUser;
	}

	/**
	 * Gets an User object which contains the loginname to identify the user and the new name, composed of first and last name.
	 * 
	 * @param user			User object.
	 * @param firstname		{@link User#getFirstName()}
	 * @param lastname		{@link User#getLastName()}
	 * @return				Reworked User object.
	 */
	public User changeName(User user, String firstname, String lastname) {
		User newUser = user;
		newUser.setFirstName(firstname);
		newUser.setLastName(lastname);
		userDBController.changeUser(user, newUser);
		return newUser;
	}

	/**
	 * Gets an User object which contains the loginname to identify the user and the new password.
	 * 
	 * @param user			User object.
	 * @param password		{@link User#getPassword()}
	 * @return				Reworked User object.
	 */
	public User changePassword(User user, String password) {
		userDBController.setPassword(user.getLogin(), password);
		user.setPassword(password);
		return user;
	}

	/**
	 * Gets specified user by loginname.
	 * 
	 * @param loginname			{@link User#getLogin()}
	 * @return					{@link UserDBController#getUser(String)}
	 */
	public User getUser(String loginname) {
		return userDBController.getUser(loginname);
	}

	/**
	 * Gets specified user by email address.
	 * 
	 * @param mail				{@link User#getMail()}
	 * @return					{@link UserDBController#getUserByEmail(String)}
	 */
	public User getUserByMail(String mail) {
		return userDBController.getUserByEmail(mail);
	}

	/**
	 * Deletes specified user by loginname.
	 * 
	 * @param loginname			{@link User#getLogin()}
	 * @return					{@link UserDBController#deleteUser(String)}
	 */
	public boolean deleteUser(String loginname) {
		return userDBController.deleteUser(loginname);
	}
	
	
	/**
	 * @param email
	 * @throws IOException
	 */
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
	
	/**
	 * @param user
	 * @throws IOException
	 */
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

	/**
	 * @param link
	 * @return
	 */
	public User checkNewPasswordLink(String link) {
		return userDBController.getUserByForgotPwd(link);
	}

	/**
	 * @param loginname			{@link User#getLogin()}
	 */
	public void deleteNewPasswordLink(String loginname) {
		userDBController.removeForgotPwdByLoginname(loginname);
	}

	/**
	 * @param session
	 * @return
	 */
	public User checkLogin(String session) {
		// TODO Methode existiert in DB noch nicht
		User user = userDBController.checkSession(session);
		if (user != null) {
			return user;
		}
		return null;
	}

	/**
	 * @param loginname			{@link User#getLogin()}
	 * @param password
	 * @param session
	 * @return
	 */
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

	/**
	 * @param user
	 * @return
	 */
	public boolean removeRepresentative(User user) {
		return userDBController.removeRepresentative(user);
	}

	/**
	 * @param loginname			{@link User#getLogin()}
	 */
	public void logout(String loginname) {
		User user = userDBController.getUser(loginname);
		if (user != null) {
			user.setSession(null);
			userDBController.changeUser(user, user);
		}
	}

	/**
	 * @param facultyID
	 * @return
	 */
	public Deadline getDeadlinebyFaculty(String facultyID) {
		return userDBController.getDeadlineByFaculty(facultyID);
	}
	
	/**
	 * @param deadline
	 */
	public void setDeadlinebyFaculty(Deadline deadline){
		userDBController.setDeadlineByFaculty(deadline);
	}
	
	/**
	 * @param deadline
	 */
	public void updateDeadlinebyFaculty(Deadline deadline){
		userDBController.updateDeadlineByFaculty(deadline);
	}
	
	/**
	 * @param facultyID
	 */
	public void deleteDeadlinebyFaculty(String facultyID) {
		userDBController.deleteDeadlineByFaculty(facultyID);
	}

	/**
	 * @param loginname			{@link User#getLogin()}
	 * @param password
	 * @return
	 */
	public boolean checkPassword(String loginname, String password) {
		return userDBController.checkPassword(loginname, password);
	}

	/**
	 * @param user
	 * @return
	 */
	public String getFacultyName(User user) {
		return userDBController.getFacultyName(user);
	}

	/**
	 * @return
	 */
	public List<String> getAllFacultiesByName() {
		return userDBController.getFacultiesByName();
	}

	/**
	 * @return
	 */
	public List<String> getAllFacultiesID() {
		return userDBController.getFacultiesID();
	}

	/**
	 * @param loginname			{@link User#getLogin()}
	 * @return
	 */
	public List<String[]> getAllInstitutesByName(String loginname) {
		return userDBController.getInstituteNames(loginname);
	}

	/**
	 * @return
	 */
	public List<String> getAllInstituteID() {
		return userDBController.getInstituteID();
	}
	
	/**
	 * @return
	 */
	public List<String> getAllInstitute() {
		return userDBController.getInstitutes();
	}

	/**
	 * @return
	 */
	public List<User> getAllUsers() {
		return userDBController.getAllUsers();
	}

//	get Mail: übergeben: Liste von Rechten, gibt zurück: Liste mit String Array -->Name, Vorname, Email-Adresse
	/**
	 * @param rights
	 * @return
	 */
	public List<String[]> getEmails(boolean[] rights){
		return userDBController.getEmails(rights);
	}
	
	/**
	 * @param user
	 * @return
	 */
	public List<String> getInstituteNames(User user) {
		List<String[]> listA = userDBController.getInstituteNames(user.getLogin());
		List<String> listB = new ArrayList<String>();
		for (String[] strings : listA) {
			listB.add(strings[1]);
		}
		return listB;
	}



	// News

	// true -> public
	// false -> private
	/**
	 * @param type
	 * @return
	 */
	public List<String[]> getNewsByType(int type) {
		return userDBController.getNews(type);

	}
	
	
	/**
	 * @param loginname			{@link User#getLogin()}
	 * @return
	 */
	public String getCurriculum(String loginname){
		return userDBController.getCurriculum(loginname);
	}
	
	
	/**
	 * @param loginname			{@link User#getLogin()}
	 * @param date
	 * @param content
	 */
	public void insertHistory(String loginname, String date, String content){
		userDBController.insertHistory(loginname, date, content);
	}
	
	/**
	 * @return
	 */
	public List<String[]> showHistory(){
		return userDBController.showHistory();
	}
	
	/**
	 * 
	 */
	public void clearHistory() {
		userDBController.clearHistory();
	}
	
	/**
	 * @param loginname			{@link User#getLogin()}
	 * @param url
	 */
	public void setCurriculum(String loginname, String url){
		userDBController.setCurriculum(loginname, url);
	}
	

	/**
	 * @param title
	 */
	public void deleteNews(String title) {
		userDBController.deleteNews(title);
	}

	/**
	 * @param data
	 * @param type
	 */
	public void addNews(String[] data, int type) {
		userDBController.addNews(data[0], data[1], type);
	}

	/**
	 * @return
	 */
	public int numberOfNews() {
		return userDBController.numberOfNews();
	}
	
	public String getEmailOfUser(String loginname){
		return userDBController.getEmailOfUser(loginname);
	}
}
