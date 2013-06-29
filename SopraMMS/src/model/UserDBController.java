package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import com.ibm.icu.text.SimpleDateFormat;

import management.Deadline;

import user.User;

//TODO Bitte alle Kommentare pr��fen und Autoren eintragen
/**
 * The class UserDBController provides a connection to the database.
 * <p>
 * It also provides a number of methods which are used to retrieve, create, edit, and delete contents.
 * 
 * @author Max Reuter, Teresa Hirzle
 *
 */
public class UserDBController {

	private static Statement statement;
	private static String query = null;
	private static PreparedStatement pStatement;

	// local database
//	 private static final String URL = "jdbc:mysql://localhost:3306/mms";
//	 private static final String USER = "root";
//	 private static final String PASSWORD = "";

	// db4free.net database
    	private static final String URL = sysconfig.Config.system_database_url.getValue();
	private static final String USER = sysconfig.Config.system_database_user.getValue();
	private static final String PASSWORD = sysconfig.Config.system_database_password.getValue();
	private static final String DRIVER = sysconfig.Config.system_database_driver.getValue();

	// Tim database
	// private static final String URL = "jdbc:mysql://127.0.0.1:3306/MMS";
	// private static final String USER = "sopramms";
	// private static final String PASSWORD = "sopra13";
	// private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	/**
	 * Number of possible rights a user can own.
	 */
	static final int NUMBEROFRIGHTS = 7;

	// establish connection
	/**
	 * Establishes connection to database.
	 * 
	 * @return		connection object.
	 * @see Connection
	 */
	public Connection connect() {
		Connection connection = null;
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("driver not found");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("connection couldn't be established");
		}
		return connection;
	}

	/**
	 * Gets all users listed in the database.
	 * 
	 * @return		List of all users.
	 * @see User
	 */
	public List<User> getAllUsers() {
		Connection connection = connect();
		List<User> userList = new LinkedList<User>();
		boolean[] rightsArray = new boolean[NUMBEROFRIGHTS];
		List<String> instituteList = new LinkedList<String>();
		try {
			query = "SELECT * FROM user";
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				String loginname = resultSet.getString("loginname");
				rightsArray = getRights(loginname, connection);
				instituteList = getInstitutesByName(loginname, connection);
				User user = new User(loginname,
						resultSet.getString("firstname"),
						resultSet.getString("lastname"),
						resultSet.getString("mail"), rightsArray,
						instituteList, resultSet.getString("representative"));
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get all Users");
		} finally {
			close(connection);
		}
		return userList;
	}


	/**
	 * Gets specified user by loginname.
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @return				User object.
	 * @see User
	 */
	public User getUser(String loginname) {
		Connection connection = connect();
		String firstname, lastname, representative, mail, password, session, faculty, supervisor;
		List<String> institutes = new LinkedList<String>();
		boolean[] rights;
		query = "SELECT * FROM user AS u "
				+ "LEFT OUTER JOIN supervisor AS s ON u.loginname = s.username "
				+ "WHERE u.loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			resultSet.next();
			lastname = resultSet.getString("lastname");
			firstname = resultSet.getString("firstname");
			representative = resultSet.getString("representative");
			mail = resultSet.getString("mail");
			password = resultSet.getString("password");
			session = resultSet.getString("session");
			supervisor = resultSet.getString("supervisor");
			institutes = getAllInstitutesByName(loginname, connection);
			rights = getAllRights(loginname, connection);
			query = "SELECT facultyID FROM institute WHERE instituteID = ?";
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1,
					((LinkedList<String>) institutes).getFirst());
			resultSet = pStatement.executeQuery();
			resultSet.next();
			faculty = resultSet.getString(1);

			close(connection);
			return new User(loginname, firstname, lastname, mail, rights,
					session, faculty, institutes, representative, supervisor,
					password);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get user: " + loginname);
		} finally {
			close(connection);
		}
		return null;
	}

	// tested: check
	// public User getUser(String session, String password) {
	// Connection connection = connect();
	// query = "SELECT * FROM user WHERE session = ? AND password = ?";
	// try {
	// pStatement = connection.prepareStatement(query);
	// pStatement.setString(1, session);
	// pStatement.setString(2, password);
	// ResultSet resultSet = pStatement.executeQuery();
	// if (resultSet.next()) {
	// return new User(resultSet.getString("loginname"),
	// resultSet.getString("firstname"),
	// resultSet.getString("lastname"),
	// resultSet.getString("mail"), password);
	// }
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// System.out.println("couldn't get user by session and password");
	// }
	// return null;
	// }
	// find user by email
	/**
	 * Gets specified user by his email address.
	 * 
	 * @param mail		The user��s email address.
	 * @return			User object.
	 * @see User
	 */
	public User getUserByEmail(String mail) {
		Connection connection = connect();
		query = "SELECT loginname FROM user WHERE mail = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, mail);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				return getUser(resultSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get user by email: " + mail);
		} finally {
			close(connection);
		}
		return null;
	}

	// create new user in database
	/**
	 * Creates a new user.
	 * 
	 * @param user		User object.
	 * @return			<code>true</code> if a new user was created <code>false</code> otherwise.
	 * @see User
	 */
	public boolean createUser(User user) {
		Connection connection = connect();
		query = "INSERT INTO user (loginname, lastname, firstname, mail, password, session) VALUES(?,?,?,?,?,?)";
		try {
			connection.setAutoCommit(false);
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, user.getLogin());
			pStatement.setString(2, user.getLastName());
			pStatement.setString(3, user.getFirstName());
			pStatement.setString(4, user.getMail());
			pStatement.setString(5, user.getPassword());
			pStatement.setString(6, user.getSession());
			pStatement.execute();

			query = "INSERT INTO instituteaffiliation VALUES(?,?)";
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, user.getLogin());
			for (String institute : user.getInstitute()) {
				pStatement.setString(2, institute);
				pStatement.execute();
			}

			query = "INSERT INTO rightsaffiliation VALUES(?,?)";
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, user.getLogin());
			for (int i = 0; i < NUMBEROFRIGHTS; i++) {
				if (user.getRights()[i]) {
					pStatement.setInt(2, i);
					pStatement.execute();
				}
			}
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("User couldn't be created.");
			rollback(connection);
		} finally {
			close(connection);
		}
		return false;

	}

	/**
	 * Changes existing user in the database.
	 * 
	 * @param oldUser		User object of the old user.
	 * @param newUser		User object of the new user.
	 * @return				<code>true</code> if the change was successful <code>false</code> otherwise.
	 * @see User
	 */
	public boolean changeUser(User oldUser, User newUser) {
		Connection connection = connect();
		query = "UPDATE user SET loginname=?, lastname=?, firstname=?, mail=?  WHERE loginname=?";
		try {
			connection.setAutoCommit(false);
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, newUser.getLogin());
			pStatement.setString(2, newUser.getLastName());
			pStatement.setString(3, newUser.getFirstName());
			pStatement.setString(4, newUser.getMail());
			pStatement.setString(5, oldUser.getLogin());
			pStatement.executeUpdate();

			changeRights(oldUser.getLogin(), newUser.getLogin(),
					newUser.getRights(), connection);
			changeInstitutes(oldUser.getLogin(), newUser.getLogin(),
					newUser.getInstitute(), connection);
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(connection);
			System.out.println("User " + oldUser.getLogin()
					+ " couldn't be changed.");
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Deletes a user.
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @return				<code>true</code> if deleting the user was successful <code>false</code> otherwise.
	 * @see User
	 */
	public boolean deleteUser(String loginname) {
		Connection connection = connect();
		query = "DELETE FROM user WHERE loginname=?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("User could not be deleted.");
		} finally {
			close(connection);
		}
		return false;
	}

	/**
	 * Gets the rights of a specified user.
	 * 
	 * @param loginname		The name with which the user uses to log into the system. 
	 * @return				An array set with true and false values ������for the corresponding rights.
	 * @see User
	 */
//	@Deprecated
//	public boolean[] getRights(String loginname) {
//		Connection connection = connect();
//		boolean[] rightsArray = new boolean[NUMBEROFRIGHTS];
//		query = "SELECT rightsID FROM rightsaffiliation WHERE loginname = ?";
//		try {
//			pStatement = connection.prepareStatement(query);
//			pStatement.setString(1, loginname);
//			ResultSet resultSet = pStatement.executeQuery();
//			// set all rights listed in table rightsaffiliation
//			while (resultSet.next()) {
//				rightsArray[resultSet.getInt("rightsID")] = true;
//			}
//			pStatement = connection
//					.prepareStatement("SELECT supervisor FROM supervisor WHERE username = ?");
//			pStatement.setString(1, loginname);
//			resultSet = pStatement.executeQuery();
//			if (resultSet.next()) {
//				loginname = resultSet.getString("supervisor");
//				pStatement = connection.prepareStatement(query);
//				pStatement.setString(1, loginname);
//				resultSet = pStatement.executeQuery();
//				// set all rights of supervisor listed in table
//				// rightsaffiliation
//				while (resultSet.next()) {
//					if(resultSet.getInt("rightsID")!=1){
//						rightsArray[resultSet.getInt("rightsID")] = true;
//					}
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("Couldn't get rights of user: " + loginname);
//		} finally {
//			close(connection);
//		}
//		return rightsArray;
//	}
//	
	
	/**
	 * Gets the rights of a specified user with established connection.
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @param connection	Connection object.
	 * @return				An array set with true and false values ������for the corresponding rights.
	 * @see User
	 */
	public boolean[] getRights(String loginname, Connection connection) {
		boolean[] rightsArray = new boolean[NUMBEROFRIGHTS];
		query = "SELECT rightsID FROM rightsaffiliation WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			// set all rights listed in table rightsaffiliation
			while (resultSet.next()) {
				rightsArray[resultSet.getInt("rightsID")] = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get rights of user: " + loginname);
		}
		return rightsArray;
	}
	

	/**
	 * Gets the rights of a specified user and rights of its supervisor with established connection.
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @param connection	Connection object.
	 * @return				An array set with true and false values for the corresponding rights.
	 * @see User
	 */
	public boolean[] getAllRights(String loginname, Connection connection) {
		boolean[] rightsArray = new boolean[NUMBEROFRIGHTS];
		query = "SELECT rightsID FROM rightsaffiliation WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			// set all rights listed in table rightsaffiliation
			while (resultSet.next()) {
				rightsArray[resultSet.getInt("rightsID")] = true;
			}
			pStatement = connection
					.prepareStatement("SELECT supervisor FROM supervisor WHERE username = ?");
			pStatement.setString(1, loginname);
			resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				loginname = resultSet.getString("supervisor");
				pStatement = connection.prepareStatement(query);
				pStatement.setString(1, loginname);
				ResultSet resultSetRi = pStatement.executeQuery();
				// set all rights of supervisor listed in table
				// rightsaffiliation
				while (resultSetRi.next()) {
					rightsArray[resultSetRi.getInt("rightsID")] = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get rights of user: " + loginname);
		}
		return rightsArray;
	}

	/**
	 * Changes the rights of a specified user.
	 * 
	 * @param user			User object
	 * @param newRights		Array set with true and false values ������for the new rights.
	 * @return				<code>true</code> if changing the rights was successful <code>false</code> otherwise.
	 * @see User
	 */
	public boolean changeRights(User user, boolean[] newRights) {
		Connection connection = connect();
		// delete old rights
		query = "DELETE FROM rightsaffiliation WHERE loginname = ?";
		try {
			connection.setAutoCommit(false);
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, user.getLogin());
			pStatement.execute();

			// insert new rights
			query = "INSERT INTO rightsaffiliation VALUES (?, ?)";
			int i = 0;
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, user.getLogin());
			for (; i < newRights.length; i++) {
				if (newRights[i]) {
					pStatement.setInt(2, i);
					pStatement.execute();
				}
			}
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't change rights of user: "
					+ user.getLogin());
			rollback(connection);
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Changes the rights of a specified user.
	 * 
	 * @param oldLogin		Old loginname.
	 * @param newLogin		New loginname.
	 * @param newRights		Array set with true and false values ������for the new rights.	
	 * @param connection	Connection object.
	 * @return				<code>true</code> if changing the rights was successful <code>false</code> otherwise.
	 * @see User
	 */
	public boolean changeRights(String oldLogin, String newLogin,
			boolean[] newRights, Connection connection) {
		// delete old rights
		query = "DELETE FROM rightsaffiliation WHERE loginname = ?";
		try {
			connection.setAutoCommit(false);
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, oldLogin);
			pStatement.execute();

			// insert new rights
			query = "INSERT INTO rightsaffiliation VALUES (?, ?)";
			int i = 0;
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, newLogin);
			for (; i < newRights.length; i++) {
				if (newRights[i]) {
					pStatement.setInt(2, i);
					pStatement.execute();
				}
			}
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't change rights of user: " + newLogin);
			rollback(connection);
			return false;
		}
	}

	/**
	 * Changes the institute of a specified user.
	 * 
	 * @param user			User object.
	 * @param institutes	List of institutes.
	 * @return				<code>true</code> if changing the institute was successful <code>false</code> otherwise.
	 * @see User
	 */
	public boolean changeInstitutes(User user, List<String> institutes) {
		Connection connection = connect();
		// delete old institutes
		query = "DELETE FROM instituteaffiliation WHERE loginname = ?";
		try {
			connection.setAutoCommit(false);
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, user.getLogin());
			pStatement.execute();

			// insert new institutes
			query = "INSERT INTO instituteaffiliation VALUES (?, ?)";
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, user.getLogin());
			for (String institute : institutes) {
				pStatement.setString(2, institute);
				pStatement.execute();
			}
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't change instututes of user: "
					+ user.getLogin());
			rollback(connection);
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Changes the institute of a specified user.
	 * 
	 * @param oldLogin		Old loginname.
	 * @param newLogin		New loginname.
	 * @param institutes	List of institutes.
	 * @param connection	Connection object.
	 * @return				<code>true</code> if changing the institute was successful <code>false</code> otherwise.
	 * @see User
	 */
	public boolean changeInstitutes(String oldLogin, String newLogin,
			List<String> institutes, Connection connection) {
		// delete old institutes
		query = "DELETE FROM instituteaffiliation WHERE loginname = ?";
		try {
			connection.setAutoCommit(false);
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, oldLogin);
			pStatement.execute();

			// insert new institutes
			query = "INSERT INTO instituteaffiliation VALUES (?, ?)";
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, newLogin);
			for (String institute : institutes) {
				pStatement.setString(2, institute);
				pStatement.execute();
			}
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't change instututes of user: "
					+ newLogin);
			rollback(connection);
			return false;
		}
	}

	/**
	 * Gets all users of a specified institute.
	 * 
	 * @param institute		ID of the institute.
	 * @return				List of users.
	 * @see User
	 */
//	@Deprecated
//	public List<User> getAllUsersFromInstitute(String institute) {
//		Connection connection = connect();
//		List<User> userList = new LinkedList<User>();
//		query = "SELECT loginname FROM instituteaffiliatoin WHERE instituteID = ?";
//		String loginname;
//		ResultSet resultSetUsers = null;
//		try {
//			pStatement = connection.prepareStatement(query);
//			pStatement.setString(1, institute);
//			ResultSet resultSet = pStatement.executeQuery();
//
//			while (resultSet.next()) {
//				loginname = resultSet.getString("loginname");
//				query = "SELECT * FROM user WHERE loginname = ?";
//				pStatement = connection.prepareStatement(query);
//				pStatement.setString(1, loginname);
//				resultSetUsers = pStatement.executeQuery();
//
//				userList.add(new User(resultSetUsers.getString("login"),
//						resultSetUsers.getString("firstName"), resultSetUsers
//								.getString("lastName"), resultSetUsers
//								.getString("mail"), getRights(loginname, connection),
//						resultSetUsers.getString("session"), resultSetUsers
//								.getString("faculty"),
//						getInstitutesByName(loginname, connection), resultSetUsers
//								.getString("representative"), resultSetUsers
//								.getString("supervisor"), resultSetUsers
//								.getString("password")));
//
//			}
//		} catch (SQLException e) {
//			System.out.println("Couldn't get all users from institute: "
//					+ institute);
//			e.printStackTrace();
//		} finally {
//			close(connection);
//		}
//		return userList;
//	}

	/**
	 * Gets instituteID of existing user.
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @return				List of institutes.
	 * @see User
	 */
//	@Deprecated
//	public List<String> getInstitutesByName(String loginname) {
//		Connection connection = connect();
//		LinkedList<String> instituteList = new LinkedList<String>();
//		query = "SELECT instituteID FROM instituteaffiliation WHERE loginname = ?";
//		try {
//			pStatement = connection.prepareStatement(query);
//			pStatement.setString(1, loginname);
//			ResultSet resultSet = pStatement.executeQuery();
//			while (resultSet.next()) {
//				instituteList.add(resultSet.getString("instituteID"));
//			}
//			pStatement = connection
//					.prepareStatement("SELECT supervisor FROM supervisor WHERE username = ?");
//			pStatement.setString(1, loginname);
//			resultSet = pStatement.executeQuery();
//			if (resultSet.next()) {
//				loginname = resultSet.getString("supervisor");
//				pStatement = connection.prepareStatement(query);
//				pStatement.setString(1, loginname);
//				resultSet = pStatement.executeQuery();
//				// get all institutes of supervisor
//				while (resultSet.next()) {
//					if(!instituteList.contains(resultSet.getString("instituteID"))){
//						instituteList.add(resultSet.getString("instituteID"));
//					}
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("Couldn't get list of institutes from user: "
//					+ loginname);
//		} finally {
//			close(connection);
//		}
//		return instituteList;
//	}
	
	
	/**
	 * Gets institute of existing user with established connection
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @param connection	Connection object.
	 * @return				List of institutes.
	 * @see User
	 */
	public List<String> getInstitutesByName(String loginname,
			Connection connection) {
		LinkedList<String> instituteList = new LinkedList<String>();
		query = "SELECT instituteID FROM instituteaffiliation WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				instituteList.add(resultSet.getString("instituteID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get list of institutes from user: "
					+ loginname);
		}
		return instituteList;
	}
	

	/**
	 * Gets institute of existing user and insitutes of its supervisor with established connection
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @param connection	Connection object.
	 * @return				List of institutes.
	 * @see User
	 */
	public List<String> getAllInstitutesByName(String loginname,
			Connection connection) {
		LinkedList<String> instituteList = new LinkedList<String>();
		query = "SELECT instituteID FROM instituteaffiliation WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				instituteList.add(resultSet.getString("instituteID"));
			}
			pStatement = connection
					.prepareStatement("SELECT supervisor FROM supervisor WHERE username = ?");
			pStatement.setString(1, loginname);
			ResultSet sResultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				loginname = sResultSet.getString("supervisor");
				pStatement = connection.prepareStatement(query);
				pStatement.setString(1, loginname);
				ResultSet resultSetIns = pStatement.executeQuery();
				// get all institutes of supervisor
				while (resultSetIns.next()) {
					if(!instituteList.contains(resultSetIns.getString("insituteID"))){
						instituteList.add(resultSetIns.getString("insituteID"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get list of institutes from user: "
					+ loginname);
		}
		return instituteList;
	}

	/**
	 * Gets the institutenames a user belongs to.
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @return				List of institutes.
	 * @see User
	 */
	public List<String[]> getInstituteNames(String loginname) {
		Connection connection = connect();
		LinkedList<String[]> instituteList = new LinkedList<String[]>();
		query = "SELECT i.instituteID, i.name FROM institute AS i JOIN instituteaffiliation AS ia "
				+ "ON i.instituteID = ia.instituteID WHERE ia.loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				instituteList.add(new String[] { resultSet.getString(1),
						resultSet.getString(2) });
			}
			return instituteList;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get names of institutes of user: "
					+ loginname);
		} finally {
			close(connection);
		}
		return instituteList;
	}

	/**
	 * Compares password typed in with the password stored in the database of a specified user.
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @param password		The typed in password.
	 * @return				<code>true</code> if the typed password is correct <code>false</code> otherwise.
	 * @see User
	 */
	public boolean checkPassword(String loginname, String password) {
		Connection connection = connect();
		String correctPassword;
		query = "SELECT password FROM user WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				correctPassword = resultSet.getString(1);
				if (correctPassword.equals(password)) {
					deleteForgotPwd(loginname, connection);
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't check password from user: "
					+ loginname);
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Sets the password of a specified user.
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @param password		The new password.
	 * @return				<code>true</code> if setting the password was successful <code>false</code> otherwise.
	 * @see User
	 */
	public boolean setPassword(String loginname, String password) {
		Connection connection = connect();
		query = "UPDATE user SET password = ? WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, password);
			pStatement.setString(2, loginname);
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't set new password of user: "
					+ loginname);
		} finally {
			close(connection);
		}
		return false;
	}

	/**
	 * Checks if the session of the user is still valid.
	 * 
	 * @param session		Session object.
	 * @return				User object.
	 * @see User
	 */
	public User checkSession(String session) {
		Connection connection = connect();
		User user = null;
		query = "SELECT * FROM user WHERE session = ?";

		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, session);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				user = new User(resultSet.getString("loginname"),
						resultSet.getString("firstname"),
						resultSet.getString("lastName"),
						resultSet.getString("mail"),
						resultSet.getString("password"));
				user.setSession(session);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't check session: " + session);
		} finally {
			close(connection);
		}
		return user;
	}

	/**
	 * Gets all institutes listed in the database.
	 * 
	 * @return		List of institutes.
	 * @see Module
	 */
	public List<String> getInstitutes() {
		Connection connection = connect();
		List<String> instituteList = new LinkedList<String>();
		query = "SELECT name FROM institute";
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				instituteList.add(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get Institutes");
		} finally {
			close(connection);
		}
		return instituteList;
	}

	/**
	 * Gets all instituteIDs listed in the database.
	 * 
	 * @return		List of instituteIDs.
	 * @see Module
	 */
	public List<String> getInstituteID() {
		Connection connection = connect();
		List<String> instituteList = new LinkedList<String>();
		query = "SELECT instituteID FROM institute";
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				instituteList.add(resultSet.getString("instituteID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get InstituteIDs");
		} finally {
			close(connection);
		}
		return instituteList;
	}


	/**
	 * Gets the user��s facultyname.
	 * 
	 * @param user		User object.
	 * @return			The faculty name.
	 * @see User
	 * @see Course
	 */
	public String getFacultyName(User user) {
		Connection connection = connect();
		query = "SELECT f.name FROM faculty AS f JOIN institute AS i ON f.facultyID = i.facultyID "
				+ "JOIN instituteaffiliation AS ia ON ia.instituteID = i.instituteID WHERE ia.loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, user.getLogin());
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get faculty name of user: "
					+ user.getLogin());
		} finally {
			close(connection);
		}
		return null;
	}

	/**
	 * Removes the representative of an user.
	 * 
	 * @param user		User object.
	 * @return			<code>true</code> if removing the representative was successful <code>false</code> otherwise.
	 * @see User
	 */
	public boolean removeRepresentative(User user) {
		Connection connection = connect();
		query = "UPDATE user SET representative = null WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, user.getLogin());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't remove representative of user: "
					+ user.getLogin());
			return false;
		} finally {
			close(connection);
		}
	}
	
	
	public boolean setRepresentative(User user, String representative) {
		Connection connection = connect();
		query = "UPDATE user SET representative = ? WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, representative);
			pStatement.setString(2, user.getLogin());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't set representative of user "+user.getLogin());
			return false;
		} finally {
			close(connection);
		}
	}
	
	//TODO ?
	/**
	 * Sets the password if the user forgets it.
	 * 
	 * @param mail			The user��s email address.
	 * @param forgotPwd		The forgotten password.
	 * @return				<code>true</code> if setting the password was successful <code>false</code> otherwise.
	 * @see User
	 */
	public Boolean setForgotPwdByMail(String mail, String forgotPwd) {
		Connection connection = connect();
		query = "UPDATE user SET forgotpwd = ? WHERE mail = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, forgotPwd);
			pStatement.setString(2, mail);
			pStatement.execute();
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Removes the forgotten password.
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @return				<code>true</code> if removing the password was successful <code>false</code> otherwise.
	 * @see User
	 */
	public Boolean removeForgotPwdByLoginname(String loginname) {
		Connection connection = connect();
		query = "UPDATE user SET forgotpwd = null WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Gets the user by his forgotten password.
	 * 
	 * @param forgotPwd		The user´s forgotten password.
	 * @return				User object.
	 * @see User
	 */
	public User getUserByForgotPwd(String forgotPwd) {
		Connection connection = connect();
		User user = null;
		query = "SELECT * FROM user WHERE forgotpwd = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, forgotPwd);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				user = new User(resultSet.getString("loginname"),
						resultSet.getString("firstname"),
						resultSet.getString("lastname"),
						resultSet.getString("mail"),
						resultSet.getString("password"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return user;
		} finally {
			close(connection);
		}
		return user;
	}

	/**
	 * Gets all faculties.
	 * 
	 * @return		List of faculties.
	 * @see Course
	 */
	public List<String> getFacultiesByName() {
		Connection connection = connect();
		LinkedList<String> facList = new LinkedList<String>();
		query = "SELECT * FROM faculty";
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				facList.add(resultSet.getString("name"));
			}
			return facList;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get names of faculties");
		} finally {
			close(connection);
		}
		return facList;
	}

	/**
	 * Gets all FacultieIDs.
	 * 
	 * @return		List of facultieIDs.
	 * @see Course
	 */
	public List<String> getFacultiesID() {
		Connection connection = connect();
		LinkedList<String> facList = new LinkedList<String>();
		query = "SELECT * FROM faculty";
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				facList.add(resultSet.getString("facultyID"));
			}
			return facList;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get IDs of faculties");
		} finally {
			close(connection);
		}
		return facList;
	}

	/**
	 * Cancels changes made in database if something went wrong.
	 * 
	 * @param connection		Connection object.
	 */
	private void rollback(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets the faculty dependent deadline.
	 * 
	 * @param facultyID		The unique ID of a faculty.
	 * @return				Deadline object.
	 * @see Course
	 */
	public Deadline getDeadlineByFaculty(String facultyID) {
		Connection connection = connect();
		query = "SELECT * FROM deadline where facultyID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, facultyID);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				return new Deadline(resultSet.getDate("deadline"),
						resultSet.getDate("beginremember"),
						resultSet.getString("facultyID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(connection);
		}
		return null;
	}

	/**
	 * Sets the faculty dependent deadline.
	 * 
	 * @param deadline		The point where all changes must be finished.
	 * @return				<code>true</code> if setting the deadline was successful <code>false</code> otherwise.
	 * @see Course
	 */
	public boolean setDeadlineByFaculty(Deadline deadline) {
		Connection connection = connect();
		query = "INSERT INTO deadline VALUES(?,?,?)";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setDate(1, deadline.getDeadline());
			pStatement.setDate(2, deadline.getBeginremember());
			pStatement.setString(3, deadline.getFacultyID());
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Updates the faculty dependent deadline.
	 * 
	 * @param deadline		The point where all changes must be finished.
	 * @return				<code>true</code> if changing the deadline was successful <code>false</code> otherwise.
	 * @see Course
	 */
	public boolean updateDeadlineByFaculty(Deadline deadline) {
		Connection connection = connect();
		query = "UPDATE deadline SET deadline = ?, beginremember = ? WHERE facultyID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setDate(1, deadline.getDeadline());
			pStatement.setDate(2, deadline.getBeginremember());
			pStatement.setString(3, deadline.getFacultyID());
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			close(connection);
		}
	}
	
	/**
	 * Deletes the faculty dependent deadline.
	 * 
	 * @param facutyID		The unique ID of a faculty.
	 * @return				<code>true</code> if deleting the deadline was successful <code>false</code> otherwise.
	 * @see Course
	 */
	public boolean deleteDeadlineByFaculty(String facutyID) {
		Connection connection = connect();
		query = "DELETE FROM deadline WHERE facultyID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, facutyID);
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't delete deadline of faculty "+facutyID);
			return false;
		} finally {
			close(connection);
		}
	}
	
	//TODO
	/**
	 * Gets the latest news.
	 * 
	 * @param type
	 * @return			List of news.
	 */
	public List<String[]> getNews(int type) {
		List<String[]> news = new LinkedList<String[]>();
		Connection connection = connect();
		if (type == 0) {
			query = "SELECT * FROM news ORDER by timestamp DESC";
		} else if (type == 1) {
			query = "SELECT * FROM news WHERE visibility = 0 OR visibility = 1  ORDER by timestamp DESC";
		} else if (type == 2) {
			query = "SELECT * FROM news WHERE visibility = 0 OR visibility = 2  ORDER by timestamp DESC";
		}
		try {
			pStatement = connection.prepareStatement(query);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				Date date = resultSet.getDate("timestamp");
				SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");

				news.add(new String[] { resultSet.getString("title"),
						resultSet.getString("content"),
						dateformat.format(date),
						resultSet.getInt("visibility") + "" });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Couldn't load news");
		} finally {
			close(connection);
		}
		return news;
	}

	/**
	 * Adds new news.
	 * 
	 * @param title		The news heading.
	 * @param content	The news content.
	 * @param type
	 * @return			<code>true</code> if adding news was successful <code>false</code> otherwise.
	 */
	public boolean addNews(String title, String content, int type) {
		Connection connection = connect();
		query = "INSERT INTO news (title,content,visibility) VALUES (?,?,?)";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, title);
			pStatement.setString(2, content);
			pStatement.setInt(3, type);
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't add news");
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Deletes news by their title.
	 * 
	 * @param title		The news heading.
	 * @return			<code>true</code> if deleting news was successful <code>false</code> otherwise.
	 */
	public boolean deleteNews(String title) {
		Connection connection = connect();
		query = "DELETE FROM news WHERE title = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, title);
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Couldn't delete news");
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Gets the number of available news.
	 * 
	 * @return		Number of news.
	 */
	public int numberOfNews() {
		Connection connection = connect();
		query = "SELECT COUNT(title) AS number FROM news";
		try {
			pStatement = connection.prepareStatement(query);
			ResultSet rs = pStatement.executeQuery();
			rs.next();
			return rs.getInt("number");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Couldn't delete news");
			return 0;
		} finally {
			close(connection);
		}
	}
	
	//TODO
	/**
	 * @param instituteName		Name of the institute.
	 * @return					List of instituteIDs.
	 */
	public List<String> convertInstituteToID(List<String> instituteName) {
		Connection connection = connect();
		List<String> instituteID = new LinkedList<String>();
		query = "SELECT instituteID FROM institute WHERE name = ?";
		try {
			pStatement = connection.prepareStatement(query);
			for (String string : instituteName) {
				pStatement.setString(1, string);
				ResultSet resultSet = pStatement.executeQuery();
				if (resultSet.next()) {
					instituteID.add(resultSet.getString("instituteID"));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			close(connection);
		}
		return instituteID;
	}
	
	//TODO Wo verwendung?
	/**
	 * Gets all emails from the users which are available in the database.
	 * 
	 * @param rights		Array set with true and false values for the rights.
	 * @return				String array containing firstname, lastname and mail address.
	 */
	public List<String[]> getEmails(boolean[] rights) {
		Connection connection = connect();
		LinkedList<String[]> mailList = new LinkedList<String[]>();
		String[] string;
		ResultSet resultSet;
		query = "SELECT u.firstname, u.lastname, u.mail "
				+ "FROM user AS u JOIN rightsaffiliation AS r "
				+ "ON u.loginname = r.loginname WHERE r.rightsID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			for (int i = 0; i < rights.length; i++) {
				if (rights[i]) {
					pStatement.setInt(1, i);
					resultSet = pStatement.executeQuery();
					while (resultSet.next()) {
						string = new String[3];
						string[0] = resultSet.getString(1);
						string[1] = resultSet.getString(2);
						string[2] = resultSet.getString(3);
						mailList.add(string);
					}
				}
			}
			return mailList;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get emails of users by rights");
		} finally {
			close(connection);
		}
		return mailList;
	}

	/**
	 * Inserts events into the history.
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @param date			Date the event took place.
	 * @param content		Content of the event.
	 */
	public void insertHistory(String loginname, String date, String content) {
		Connection connection = connect();
		query = "INSERT INTO history (loginname, date, content) VALUES (?,?,?)";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			pStatement.setString(2, date);
			pStatement.setString(3, content);
			pStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't insert history: " + content
					+ " of user: " + loginname);
		} finally {
			close(connection);
		}
	}

	// TODO checkLoginName

	/**
	 * Shows history.
	 * 
	 * @return		List containing loginname, date and content.
	 */
	public List<String[]> showHistory() {
		Connection connection = connect();
		LinkedList<String[]> userList = new LinkedList<String[]>();
		String[] string;
		query = "SELECT loginname, date, content FROM history";
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				string = new String[3];
				string[0] = resultSet.getString(1);
				string[1] = "" + resultSet.getDate(2);
				string[2] = resultSet.getString(3);
				userList.add(string);
			}
			return userList;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't show history");
		} finally {
			close(connection);
		}
		return userList;
	}
	
	/**
	 * Clears the history.
	 * 
	 * @return		<code>true</code> if deleting the history was successful <code>false</code> otherwise.
	 */
	public boolean clearHistory() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-2);
		Date limit = new Date(cal.getTime().getTime());
		Connection connection = connect();
		query = "DELETE FROM history WHERE date < ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setDate(1, limit);
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't clear history");
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Deletes the forgotten password.
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @param connection	Connection object.
	 * @return				<code>true</code> if deleting the forgotten password was successful <code>false</code> otherwise.
	 * @see User
	 */
	public boolean deleteForgotPwd(String loginname, Connection connection) {
		query = "UPDATE user SET forgotpwd = null WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ForgotPassword variable of user " + loginname
					+ " could't be set null.");
			return false;
		}
	}

	/**
	 * Sets the curriculum vitae in form of an url from a specified user.
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @param url			The URL which leads to the user��s curriculum vitae.
	 * @return				<code>true</code> if inserting of the URL was successful <code>false</code> otherwise.
	 * @see User
	 */
	public boolean setCurriculum(String loginname, String url) {
		Connection connection = connect();
		query = "UPDATE user SET curriculum = ? WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, url);
			pStatement.setString(2, loginname);
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't set curriculum for user " + loginname);
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Gets the curriculum vitae of a specified user.
	 * 
	 * @param loginname		The name with which the user uses to log into the system.
	 * @return				The URL which leads to the user��s curriculum vitae.
	 * @see User
	 */
	public String getCurriculum(String loginname) {
		Connection connection = connect();
		String url = null;
		query = "SELECT curriculum FROM user WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				url = resultSet.getString("curriculum");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get curriculum for user " + loginname);
			return null;
		} finally {
			close(connection);
		}
		return url;
	}
	
	/**
	 * Gets the email address by the loginname of the user.
	 * 
	 * @param loginname				The name with which the user uses to log into the system.
	 * @return						The email address.
	 */
	public String getEmailOfUser(String loginname){
		Connection connection = connect();
		query = "SELECT mail FROM user WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			if(resultSet.next()){
				return resultSet.getString(1);
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get email of user: "+loginname);
		}finally {
			close(connection);
		}
		return null;
	}
	
	/**
	 * Checks, if given loginname is already used and returns an unused one.
	 * For exampe: "reuterm" is already taken -> "reuterm-1" will be returned
	 * 
	 * @param loginname				The name with which the user uses to log into the system.
	 * @return						An unused loginname.
	 */
	public String checkLoginname(String loginname) {
		Connection connection = connect();
		query = "SELECT loginname FROM user WHERE loginname = ?";
		int i = 1;
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				loginname = loginname+"-"+i;
				pStatement.setString(1, loginname);
				resultSet = pStatement.executeQuery();
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't generate loginname");
		}
		return loginname;
	}
	

	/**
	 * Closes the connection
	 * 
	 * @param connection		Connection object
	 */
	private void close(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't close connection.");
		}
	}
}
