package user;

import java.util.Arrays;
import java.util.List;

import management.CourseEntry;
import management.EffortEntry;
import management.Entry;
import management.TextualEntry;

//TODO Kommentare überprüfen und Autoren eintragen
/**
 * The User class contains all informations which belong to a user of the system.
 * <p>
 * There are several constructors available.
 * <p>
 * There are different kind of users which is shown by the rights they own.
 * The different rights give them the possibility to insert, change and confirm content in the system.
 * 
 * @author David Lehr
 *
 */
public class User {

	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

	return "User [loginname=" + loginname + ", firstName=" + firstName
		+ ", lastName=" + lastName + ", mail=" + mail + ", rights="
		+ Arrays.toString(rights) + ", session=" + session
		+ ", faculty=" + faculty + ", password=" + password
		+ ", institute=" + institute + ", representative="
		+ representative + ", supervisor=" + supervisor + "]";
    }


	//
	/**
	 * The name that is used to login to the system.
	 */
	private String loginname;
	/**
	 * The user´s first name.
	 */
	private String firstName;
	/**
	 * The user´s last name.
	 */
	private String lastName;
	/**
	 * The user´s email address which he needs to exchange messages with other users of the system,
	 * and to get notified by the system.
	 */
	private String mail;
	/**
	 * A Array of rights.
	 * "True" is set if the user owns the right.
	 */
	private boolean[] rights;
	/**
	 * The session is a temporary data type which contains information about the logged in user.
	 */
	private String session;
	/**
	 * The faculty a user belongs to.
	 */
	private String faculty;
	/**
	 * The password a user needs to log into the system.
	 */
	private String password;
	/**
	 * A list filled with all institutes a user belongs to.
	 */
	private List<String> institute;
	/**
	 * A user's representative is another user of the systems who owns the same rights and represents the user if he is not available.
	 */
	private String representative;
	/**
	 * The supervisor is a user who is hierarchically above the logged in user.
	 * The user is the supervisor´s representative.
	 */
	private String supervisor;


	/**
	 * Default constructor.
	 */
	public User() {
	}


	/**
	 * Constructor for the User object with all available parameters.
	 * 
	 * @param loginname			{@link #loginname}	
	 * @param firstName			{@link #firstName}
	 * @param lastName			{@link #lastName}
	 * @param mail				{@link #mail}
	 * @param rights			{@link #rights}	
	 * @param session			{@link #session}
	 * @param faculty			{@link #faculty}
	 * @param institute			{@link #institute}
	 * @param representative	{@link #representative}	
	 * @param supervisor		{@link #supervisor}
	 * @param password			{@link #password}
	 */
	public User(String loginname, String firstName, String lastName,
			String mail, boolean[] rights, String session, String faculty,
			List<String> institute, String representative, String supervisor,
			String password) {

		super();
		this.loginname = loginname;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.rights = rights;
		this.session = session;
		this.faculty = faculty;
		this.password = password;
		this.institute = institute;
		this.representative = representative;
		this.supervisor = supervisor;
	}


	// alternative constructor for User object
	/**
	 * Constructor for the User object with not all available parameters.
	 * 
	 * @param loginname		{@link #loginname}
	 * @param firstName		{@link #firstName}
	 * @param lastName		{@link #lastName}
	 * @param mail			{@link #mail}
	 * @param password		{@link #password}
	 */
	public User(String loginname, String firstName, String lastName,
			String mail, String password) {

		this.loginname = loginname;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.password = password;
		this.rights = null;
		this.session = null;
		this.faculty = null;
		this.institute = null;
		this.representative = null;
		this.supervisor = null;
	}
	
	/**
	 * Constructor for the User object with not all available parameters.
	 * 
	 * @param loginname			{@link #loginname}
	 * @param firstName			{@link #firstName}
	 * @param lastName			{@link #lastName}
	 * @param mail				{@link #mail}
	 * @param rights			{@link #rights}
	 * @param institute			{@link #institute}
	 * @param representative	{@link #representative}
	 */
	public User(String loginname, String firstName, String lastName,
			String mail, boolean[] rights, List<String> institute, String representative) {

		this.loginname = loginname;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.password = null;
		this.rights = rights;
		this.session = null;
		this.faculty = null;
		this.institute = institute;
		this.representative = representative;
		this.supervisor = null;
	}



	/**
	 * Gets the user´s {@link #loginname}.
	 * 
	 * @return {@link #loginname}
	 */
	public String getLogin() {

		return loginname;
	}


	/**
	 * Sets the user´s {@link #loginname}.
	 * 
	 * @param {@link #loginname}
	 */
	public void setLogin(String loginname) {

		this.loginname = loginname;
	}


	/**
	 * Gets the user´s {@link #firstName}.
	 * 
	 * @return {@link #firstName}
	 */
	public String getFirstName() {

		return firstName;
	}


	/**
	 * Sets the user´s {@link #firstName}.
	 * 
	 * @param firstName {@link #firstName}
	 */
	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}


	/**
	 * Gets the user´s {@link #lastName}.
	 * 
	 * @return {@link #lastName}
	 */
	public String getLastName() {

		return lastName;
	}


	/**
	 * Sets the user´s {@link #lastName}.
	 * 
	 * @param {@link #lastName}
	 */
	public void setLastName(String lastName) {

		this.lastName = lastName;
	}


	/**
	 * Gets the user´s {@link #mail}.
	 * 
	 * @return {@link #mail}
	 */
	public String getMail() {

		return mail;
	}


	/**
	 * Sets the user´s {@link #mail}.
	 * 
	 * @param {@link #mail}
	 */
	public void setMail(String mail) {

		this.mail = mail;
	}


	/**
	 * Gets the user´s {@link #rights}.
	 * 
	 * @return {@link #rights}
	 */
	public boolean[] getRights() {

		return rights;
	}


	/**
	 * Sets the user´s {@link #rights}.
	 * 
	 * @param {@link #rights}
	 */
	public void setRights(boolean[] rights) {

		this.rights = rights;
	}


	/**
	 * Gets the user´s {@link #session}.
	 * 
	 * @return {@link #session}
	 */
	public String getSession() {

		return session;
	}


	/**
	 * Sets the user´s {@link #session}.
	 * 
	 * @param {@link #session}
	 */
	public void setSession(String session) {

		this.session = session;
	}


	/**
	 * Gets the user´s {@link #faculty}.
	 * 
	 * @return {@link #faculty}
	 */
	public String getFaculty() {

		return faculty;
	}


	/**
	 * Sets the user´s {@link #faculty}
	 * 
	 * @param {@link #faculty}
	 */
	public void setFaculty(String faculty) {

		this.faculty = faculty;
	}


	/**
	 * Gets the user´s {@link #institute}.
	 * 
	 * @return {@link #institute}
	 */
	public List<String> getInstitute() {

		return institute;
	}


	/**
	 * Sets the user´s {@link #institute}.
	 * 
	 * @param {@link #institute}
	 */
	public void setInstitute(List<String> institute) {

		this.institute = institute;
	}


	/**
	 * Gets the user´s {@link #representative}.
	 * 
	 * @return {@link #representative}
	 */
	public String getRepresentative() {

		return representative;
	}


	/**
	 * Sets the user´s {@link #representative}.
	 * 
	 * @param {@link #representative}
	 */
	public void setRepresentative(String representative) {

		this.representative = representative;
	}


	/**
	 * Gets the user´s {@link #supervisor}.
	 * 
	 * @return {@link #supervisor}
	 */
	public String getSupervisor() {

		return supervisor;
	}


	/**
	 * Sets the user´s {@link #supervisor}.
	 * 
	 * @param {@link #supervisor}
	 */
	public void setSupervisor(String supervisor) {

		this.supervisor = supervisor;
	}


	/**
	 * Gets the user´s {@link #password}.
	 * 
	 * @return {@link #password}
	 */
	public String getPassword() {

		return password;
	}


	/**
	 * Sets the user´s {@link #password}.
	 * 
	 * @param {@link #password}
	 */
	public void setPassword(String password) {

		this.password = password;
	}
	
}
