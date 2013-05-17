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
	private static final String URL = "jdbc:mysql://localhost:3306/mms";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	static final int NUMBEROFRIGHTS = 4;


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
				instituteList = getInstitute(loginname);
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
			close(connection);
			institutes = getInstitute(loginname);
			rights = getRights(loginname);
			connect();
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


	// create new user in database
	public Boolean createUser(User user) {
		Connection connection = connect();
		query = "INSERT INTO User VALUES(?,?,?,?,?,?)";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, user.getLogin());
			pStatement.setString(2, user.getLastName());
			pStatement.setString(3, user.getFirstName());
			pStatement.setString(4, user.getMail());
			pStatement.setString(5, user.getPassword());
			pStatement.setString(6, user.getSession());

			pStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("User couldn't be created.");
		} finally {
			close(connection);
		}
		System.out.println("User created!");
		return true;

	}


	// change existing user in database
	public Boolean changeUser(User oldUser, User newUser) {
		Connection connection = connect();
		String loginname = oldUser.getLogin();
		query = "SELECT * FROM User WHERE loginname=?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			resultSet = pStatement.executeQuery();
			// checks if user exists in database
			if (resultSet.next()) {
				query = "UPDATE User SET loginname=?, lastname=?, firstname=?, mail=?, password=?, session=? WHERE loginname=?";
				pStatement = connection.prepareStatement(query);
				pStatement.setString(1, newUser.getLogin());
				pStatement.setString(2, newUser.getLastName());
				pStatement.setString(3, newUser.getFirstName());
				pStatement.setString(4, newUser.getMail());
				pStatement.setString(5, newUser.getPassword());
				pStatement.setString(6, newUser.getSession());
				pStatement.setString(7, loginname);

				pStatement.executeUpdate();
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
		return true;
	}


	// deletes User
	public boolean deleteUser(String loginname) {
		Connection connection = connect();
		query = "DELETE FROM User WHERE loginname=?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("User could not be deleted.");
			return false;
		} finally {
			close(connection);
		}
		return true;
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
	public void changeRights(User user, boolean[] newRights) {
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
		}
		// insert new rights
		query = "INSERT INTO rightsaffiliation VALUES (?, ?)";
		for (int i = 0; i < newRights.length; i++) {
			if (newRights[i]) {
				try {
					pStatement = connection.prepareStatement(query);
					pStatement.setString(1, loginname);
					pStatement.setInt(2, i);
					pStatement.execute();
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Couldn't insert right: " + i
							+ " of user: " + loginname);
				}
			}
		}
		close(connection);
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
								.getString("faculty"), getInstitute(loginname),
						resultSetUsers.getString("representative"),
						resultSetUsers.getString("supervisor"), resultSetUsers
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
	public List<String> getInstitute(String loginname) {
		Connection connection = connect();
		List<String> instituteList = new LinkedList<String>();
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