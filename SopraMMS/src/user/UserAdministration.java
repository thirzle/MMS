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
	 * Sends an email if the user has forgotten his password.
	 * <p>
	 * By getting an email address, it searches for the user who belongs to it
	 * and sends him an email with an link he can click on to change the password.
	 * 
	 * @param email				{@link User#getMail()}
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
	 * Sends a new registered user an email with an link allowing him to change his password.
	 * 
	 * @param user				User object.
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
	
	//TODO
	/**
	 * @param link
	 * @return
	 */
	public User checkNewPasswordLink(String link) {
		return userDBController.getUserByForgotPwd(link);
	}
	
	//TODO
	/**
	 * @param loginname			{@link User#getLogin()}
	 */
	public void deleteNewPasswordLink(String loginname) {
		userDBController.removeForgotPwdByLoginname(loginname);
	}

	/**
	 * Checks if the session of the user is still valid.
	 * 
	 * @param session		{@link User#getSession()}
	 * @return				User object.
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
	 * Performs the login by comparing the password typed in with the password stored in the database of a specified user.
	 * 
	 * @param loginname			{@link User#getLogin()}
	 * @param password			{@link User#getPassword()}
	 * @param session			{@link User#getSession()}
	 * @return					User object or <code>null</code> if the login wasn´t successful.
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
	 * Removes the representative of an user.
	 * 
	 * @param user		User object.
	 * @return			<code>true</code> if removing the representative was successful <code>false</code> otherwise.
	 */
	public boolean removeRepresentative(User user) {
		return userDBController.removeRepresentative(user);
	}

	/**
	 * Performs the logout.
	 * 
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
	 * Gets the faculty dependent deadline.
	 * 
	 * @param facultyID		The unique ID of a faculty.
	 * @return				Deadline object.
	 */
	public Deadline getDeadlinebyFaculty(String facultyID) {
		return userDBController.getDeadlineByFaculty(facultyID);
	}
	
	/**
	 * Sets the faculty dependent deadline.
	 * 
	 * @param deadline		The point where all changes must be finished.
	 */
	public void setDeadlinebyFaculty(Deadline deadline){
		userDBController.setDeadlineByFaculty(deadline);
	}
	
	/**
	 * Updates the faculty dependent deadline.
	 * 
	 * @param deadline		The point where all changes must be finished.
	 */
	public void updateDeadlinebyFaculty(Deadline deadline){
		userDBController.updateDeadlineByFaculty(deadline);
	}
	
	/**
	 * Deletes the faculty dependent deadline.
	 * 
	 * @param facultyID		The unique ID of a faculty.
	 */
	public void deleteDeadlinebyFaculty(String facultyID) {
		userDBController.deleteDeadlineByFaculty(facultyID);
	}

	/**
	 * Compares password typed in with the password stored in the database of a specified user.
	 * 
	 * @param loginname			{@link User#getLogin()}
	 * @param password			{@link User#getPassword()}
	 * @return					<code>true</code> if the typed password is correct <code>false</code> otherwise.
	 */
	public boolean checkPassword(String loginname, String password) {
		return userDBController.checkPassword(loginname, password);
	}

	/**
	 * Gets the faculty the user belongs to.
	 * 
	 * @param user		User object.
	 * @return			The faculty name.
	 */
	public String getFacultyName(User user) {
		return userDBController.getFacultyName(user);
	}

	/**
	 * Gets all available faculties.
	 * 
	 * @return			List of faculties.
	 */
	public List<String> getAllFacultiesByName() {
		return userDBController.getFacultiesByName();
	}

	/**
	 * Gets all FacultieIDs.
	 * 
	 * @return			List of facultieIDs.
	 */
	public List<String> getAllFacultiesID() {
		return userDBController.getFacultiesID();
	}

	/**
	 * Gets all institutes by their name.
	 * 
	 * @param loginname			{@link User#getLogin()}
	 * @return					List of institutes.
	 */
	public List<String[]> getAllInstitutesByName(String loginname) {
		return userDBController.getInstituteNames(loginname);
	}

	/**
	 * Gets all instituteIDs listed in the database.
	 * 
	 * @return				List of institutesIDs.
	 */
	public List<String> getAllInstituteID() {
		return userDBController.getInstituteID();
	}
	
	/**
	 * Gets all institutes listed in the database.
	 * 
	 * @return				List of institutes.
	 */
	public List<String> getAllInstitute() {
		return userDBController.getInstitutes();
	}

	/**
	 * Gets all Users listed in the database.
	 * 
	 * @return				List of all users.
	 */
	public List<User> getAllUsers() {
		return userDBController.getAllUsers();
	}

//	get Mail: übergeben: Liste von Rechten, gibt zurück: Liste mit String Array -->Name, Vorname, Email-Adresse
	/**
	 * Gets all emails from the users which are available in the database.
	 * 
	 * @param rights		{@link User#getRights()}
	 * @return				String array containing firstname, lastname and mail address.
	 */
	public List<String[]> getEmails(boolean[] rights){
		return userDBController.getEmails(rights);
	}
	
	/**
	 * Gets the institutenames a user belongs to.
	 * 
	 * @param user			User object.
	 * @return				List of institutes.
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
	//TODO
	/**
	 * @param type
	 * @return				List of news.
	 */
	public List<String[]> getNewsByType(int type) {
		return userDBController.getNews(type);

	}
	
	
	/**
	 * Gets the curriculum vitae of a user.
	 * 
	 * @param loginname			{@link User#getLogin()}
	 * @return					The URL which leads to the user´s curriculum vitae.
	 */
	public String getCurriculum(String loginname){
		return userDBController.getCurriculum(loginname);
	}
	
	
	/**
	 * Inserts events into the history.
	 * 
	 * @param loginname			{@link User#getLogin()}
	 * @param date				Date the event took place.
	 * @param content			Content of the event.
	 */
	public void insertHistory(String loginname, String date, String content){
		userDBController.insertHistory(loginname, date, content);
	}
	
	/**
	 * Shows the history.
	 * 
	 * @return					List containing loginname, date and content.
	 */
	public List<String[]> showHistory(){
		return userDBController.showHistory();
	}
	
	/**
	 * Clears the history.
	 */
	public void clearHistory() {
		userDBController.clearHistory();
	}
	
	/**
	 * Sets the curriculum vitae in form of an url from a specified user.
	 * 
	 * @param loginname			{@link User#getLogin()}
	 * @param url				The URL which leads to the user´s curriculum vitae.
	 */
	public void setCurriculum(String loginname, String url){
		userDBController.setCurriculum(loginname, url);
	}
	

	/**
	 * Deletes news by their title.
	 * 
	 * @param title				The news heading.
	 */
	public void deleteNews(String title) {
		userDBController.deleteNews(title);
	}
	
	//TODO
	/**
	 * Adds new News.
	 * 
	 * @param data
	 * @param type
	 */
	public void addNews(String[] data, int type) {
		userDBController.addNews(data[0], data[1], type);
	}

	/**
	 * Gets the number of available news.
	 * 
	 * @return				Number of news.
	 */
	public int numberOfNews() {
		return userDBController.numberOfNews();
	}
	
	/**
	 * Gets the email address by the loginname of the user.
	 * 
	 * @param loginname		{@link User#getLogin()}
	 * @return				The email address.
	 */
	public String getEmailOfUser(String loginname){
		return userDBController.getEmailOfUser(loginname);
	}
}
