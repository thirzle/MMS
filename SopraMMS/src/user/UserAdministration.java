package user;


import java.util.List;


import controller.UserDBController;

public class UserAdministration {

    static UserDBController userDBController = new UserDBController();


    public UserAdministration() {

	// Constructor
    }


    public User createUser(String login, String firstName, String lastName,
	    String mail, boolean[] rights, String session, String faculty,
	    List<String> institute, String representative, String supervisor,
	    String password) {

	User user = new User(login, firstName, lastName, mail, rights, session,
		faculty, institute, representative, supervisor, password);
	userDBController.createUser(user);
	return user;
    }


    public User changeRights(User user, boolean[] newRights) {

	User newUser = user;
	newUser.setRights(newRights);
	userDBController.changeUser(user, newUser);
	return newUser;
    }
    
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
    
    public User changeInstitute(User user, List<String> institute) {
	User newUser = user;
	newUser.setInstitute(institute);
	userDBController.changeUser(user, newUser);
	return newUser;
    }
    
    public User changeRepresentative(User user, String representative)  {
	User newUser = user;
	newUser.setRepresentative(representative);
	userDBController.changeUser(user, newUser);
	return newUser;
    }
    
    public User changeSupervisor(User user, String supervisor)  {
	User newUser = user;
	newUser.setRepresentative(supervisor);
	userDBController.changeUser(user, newUser);
	return newUser;
    }
    
    public User changeName(User user, String firstname, String lastname)  {
	User newUser = user;
	newUser.setFirstName(firstname);
	newUser.setLastName(lastname);
	userDBController.changeUser(user, newUser);
	return newUser;
    }
    
    public User changePassword(User user, String password)  {
	User newUser = user;
	newUser.setRepresentative(password);
	userDBController.changeUser(user, newUser);
	return newUser;
    }
    
    //weiter machen...
}
