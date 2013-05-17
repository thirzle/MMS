package user;

import java.util.Arrays;
import java.util.List;

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
	private String loginname;
	private String firstName;
	private String lastName;
	private String mail;
	private boolean[] rights;
	private String session;
	private String faculty;
	private String password;
	private List<String> institute;
	private String representative;
	private String supervisor;


	public User() {
	}


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
	 * @return the login
	 */
	public String getLogin() {

		return loginname;
	}


	/**
	 * @param loginname
	 *            the login to set
	 */
	public void setLogin(String loginname) {

		this.loginname = loginname;
	}


	/**
	 * @return the firstName
	 */
	public String getFirstName() {

		return firstName;
	}


	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {

		return lastName;
	}


	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {

		this.lastName = lastName;
	}


	/**
	 * @return the mail
	 */
	public String getMail() {

		return mail;
	}


	/**
	 * @param mail
	 *            the mail to set
	 */
	public void setMail(String mail) {

		this.mail = mail;
	}


	/**
	 * @return the rights
	 */
	public boolean[] getRights() {

		return rights;
	}


	/**
	 * @param rights
	 *            the rights to set
	 */
	public void setRights(boolean[] rights) {

		this.rights = rights;
	}


	/**
	 * @return the session
	 */
	public String getSession() {

		return session;
	}


	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(String session) {

		this.session = session;
	}


	/**
	 * @return the faculty
	 */
	public String getFaculty() {

		return faculty;
	}


	/**
	 * @param faculty
	 *            the faculty to set
	 */
	public void setFaculty(String faculty) {

		this.faculty = faculty;
	}


	/**
	 * @return the institute
	 */
	public List<String> getInstitute() {

		return institute;
	}


	/**
	 * @param institute
	 *            the institute to set
	 */
	public void setInstitute(List<String> institute) {

		this.institute = institute;
	}


	/**
	 * @return the representative
	 */
	public String getRepresentative() {

		return representative;
	}


	/**
	 * @param representative
	 *            the representative to set
	 */
	public void setRepresentative(String representative) {

		this.representative = representative;
	}


	/**
	 * @return the supervisor
	 */
	public String getSupervisor() {

		return supervisor;
	}


	/**
	 * @param supervisor
	 *            the supervisor to set
	 */
	public void setSupervisor(String supervisor) {

		this.supervisor = supervisor;
	}


	public String getPassword() {

		return password;
	}


	public void setPassword(String password) {

		this.password = password;
	}
	
}
