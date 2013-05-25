package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import user.User;

public class UserDBController {

	private static Statement statement;
	private static ResultSet resultSet;
	private static String query = null;
	private static PreparedStatement pStatement;

	// Lokale Datenbank
	private static final String URL = "jdbc:mysql://localhost:3306/mms";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	// Datenbank auf db4free.net
	// private static final String URL =
	// "jdbc:mysql://db4free.net:3306/sopramms";
	// private static final String USER = "teamaccount";
	// private static final String PASSWORD = "6lsj7tdm";
	//
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	static final int NUMBEROFRIGHTS = 5;

	// establish connection
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

	// get all user listed in database
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
				rightsArray = getRights(loginname);
				instituteList = getInstitutesByName(loginname);
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

	// find specified user by loginname
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
			institutes = getInstitutesByName(loginname);
			rights = getRights(loginname);
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
	public User getUser(String session, String password) {
		Connection connection = connect();
		query = "SELECT * FROM user WHERE session = ? AND password = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, session);
			pStatement.setString(2, password);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				return new User(resultSet.getString("loginname"),
						resultSet.getString("firstname"),
						resultSet.getString("lastname"),
						resultSet.getString("mail"), password);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get user by session and password");
		}
		return null;
	}

	// create new user in database
	public boolean createUser(User user) {
		Connection connection = connect();
		query = "INSERT INTO User VALUES(?,?,?,?,?,?,?)";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, user.getLogin());
			pStatement.setString(2, user.getLastName());
			pStatement.setString(3, user.getFirstName());
			pStatement.setString(4, user.getRepresentative());
			pStatement.setString(5, user.getMail());
			pStatement.setString(6, user.getPassword());
			pStatement.setString(7, user.getSession());
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

			System.out.println("User created!");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("User couldn't be created.");
		} finally {
			close(connection);
		}
		return false;

	}

	// change existing user in database
	public boolean changeUser(User oldUser, User newUser) {
		Connection connection = connect();
		String loginname = oldUser.getLogin();
		query = "SELECT * FROM user WHERE loginname=?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			resultSet = pStatement.executeQuery();
			// checks if user exists in database
			if (resultSet.next()) {
				query = "UPDATE user SET loginname=?, lastname=?, firstname=?, mail=?, password=?, session=? WHERE loginname=?";
				pStatement = connection.prepareStatement(query);
				pStatement.setString(1, newUser.getLogin());
				pStatement.setString(2, newUser.getLastName());
				pStatement.setString(3, newUser.getFirstName());
				pStatement.setString(4, newUser.getMail());
				pStatement.setString(5, newUser.getPassword());
				pStatement.setString(6, newUser.getSession());
				pStatement.setString(7, loginname);
				pStatement.executeUpdate();
				return true;
			} else {
				System.out.println("That user was not found!");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("User " + loginname + " couldn't be changed.");
		} finally {
			close(connection);
		}
		return false;
	}

	// deletes User
	public boolean deleteUser(String loginname) {
		Connection connection = connect();
		query = "DELETE FROM Uuer WHERE loginname=?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			return pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("User could not be deleted.");
		} finally {
			close(connection);
		}
		return false;
	}

	// get rights of specified user
	public boolean[] getRights(String loginname) {
		Connection connection = connect();
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

	// change rights of specified user
	public boolean changeRights(User user, boolean[] newRights) {
		Connection connection = connect();
		String loginname = user.getLogin();
		// delete old rights
		query = "DELETE FROM rightsaffiliation WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			pStatement.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Couldn't delete rights of user: " + loginname);
			return false;
		}
		// insert new rights
		query = "INSERT INTO rightsaffiliation VALUES (?, ?)";
		int i = 0;
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			for (; i < newRights.length; i++) {
				if (newRights[i]) {
					pStatement.setInt(2, i);
					return pStatement.execute();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't insert right: " + i + " of user: "
					+ loginname);
		} finally {
			close(connection);
		}
		return false;
	}

	// get all user of specified institute
	public List<User> getAllUsersFromInstitute(String institute) {
		Connection connection = connect();
		List<User> userList = new LinkedList<User>();
		query = "SELECT loginname FROM instituteaffiliatoin WHERE instituteID = ?";
		String loginname;
		ResultSet resultSetUsers = null;
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, institute);
			ResultSet resultSet = pStatement.executeQuery();

			while (resultSet.next()) {
				loginname = resultSet.getString("loginname");
				query = "SELECT * FROM user WHERE loginname = ?";
				pStatement = connection.prepareStatement(query);
				pStatement.setString(1, loginname);
				resultSetUsers = pStatement.executeQuery();

				userList.add(new User(resultSetUsers.getString("login"),
						resultSetUsers.getString("firstName"), resultSetUsers
								.getString("lastName"), resultSetUsers
								.getString("mail"), getRights(loginname),
						resultSetUsers.getString("session"), resultSetUsers
								.getString("faculty"),
						getInstitutesByName(loginname), resultSetUsers
								.getString("representative"), resultSetUsers
								.getString("supervisor"), resultSetUsers
								.getString("password")));

			}
		} catch (SQLException e) {
			System.out.println("Couldn't get all users from institute: "
					+ institute);
			e.printStackTrace();
		} finally {
			close(connection);
		}
		return userList;
	}

	// get institute of existing user
	public List<String> getInstitutesByName(String loginname) {
		Connection connection = connect();
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
		} finally {
			close(connection);
		}
		return instituteList;
	}

	// get institutenames of user
	public List<String> getInstituteNames(String loginname) {
		Connection connection = connect();
		LinkedList<String> instituteList = new LinkedList<String>();
		query = "SELECT i.name FROM institute AS i JOIN instituteaffiliation AS ia " +
				"ON i.instituteID = ia.instituteID WHERE ia.loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				instituteList.add(resultSet.getString(1));
			}
			return instituteList;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get names of institutes of user: "
					+ loginname);
		}
		return instituteList;
	}

	// compare hashed password typed in with password in database of specified
	// user
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
				close(connection);
				return correctPassword.equals(password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't check password from user: "
					+ loginname);
		} finally {
			close(connection);
		}
		return false;
	}

	// change password of specified user
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

	// check if session of user is still valid
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

	// get all institutes listed in databse
	public List<String> getInstitutes() {
		Connection connection = connect();
		List<String> instituteList = new LinkedList<String>();
		query = "SELECT name FROM institute";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				instituteList.add(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get Institutes");
		}
		return instituteList;
	}

	// get facultyname of user
	public String getFacultyName(User user) {
		Connection connection = connect();
		query = "SELECT f.name FROM faculty AS f JOIN institute AS i ON f.facultyID = i.facultyID "
				+ "JOIN instituteaffiliation AS ia ON ia.instituteID = i.instituteID WHERE ia.loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, user.getLogin());
			resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get faculty name of user: "
					+ user.getLogin());
		}
		return null;
	}

	// close connection
	private void close(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't close connection.");
		}
	}
}
