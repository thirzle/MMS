package user;

import java.util.List;

import controller.UserDBController;

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
		User newUser = user;
		newUser.setRepresentative(password);
		userDBController.changeUser(user, newUser);
		return newUser;
	}


	public User getUser(String loginname) {
		// TODO fill Method

		return new User();
	}


	public boolean deleteUser(User user) {
		// TODO fill Method

		return false;
	}


	public boolean sendNewPassword(String loginname) {
		// TODO fill Method

		return false;
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


	public void logout(String loginname) {
		User user = userDBController.getUser(loginname);
		if (user != null) {
			user.setSession(null);
			userDBController.changeUser(user, user);
		}
	}
}
