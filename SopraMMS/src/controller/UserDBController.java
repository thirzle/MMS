package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import management.User;

public class UserDBController {

	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;
	private static String query = null;
	private static PreparedStatement pStatement;
	// CONNECTIONSTRING anpassen!!
	private static final String URL = "jdbc:mysql://localhost:3306/mms";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	static final int NUMBEROFRIGHTS = 4;

	public UserDBController() {

		super();
		connect();

	}

	private void connect() {

		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<User> getAllUsers() throws SQLException {

		ResultSet resultSetRights = null, resultSetInstitutes = null;
		List<User> userList = null;
		query = "SELECT * FROM user";
		statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			boolean[] rightsArray = new boolean[NUMBEROFRIGHTS];
			List<String> instituteList = null;
			String loginname = resultSet.getString("loginname");

			rightsArray = getRights(loginname);
			instituteList = getInstitute(loginname);

			User user = new User(loginname, resultSet.getString("firstname"),
					resultSet.getString("lastname"),
					resultSet.getString("mail"), rightsArray,
					resultSet.getString("session"),
					resultSet.getString("faculty"), instituteList,
					resultSet.getString("represantative"),
					resultSet.getString("supervisor"),
					resultSet.getString("password"));
			userList.add(user);
		}
		close();
		return userList;
	}

	public Boolean createUser(User user) {

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
		}
		close();
		return true;

	}

	public Boolean changeUser(User oldUser, User newUser) {

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
		}
		close();
		return true;
	}

	// deletes User
	public boolean deleteUser(String loginname) {
		query = "DELETE FROM User WHERE loginname=?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("User could not be deleted.");
		}
		close();
		return true;

	}

	@SuppressWarnings("null")
	public boolean[] getRights(String loginname) {

		boolean[] rightsArray = null;
		query = "SELECT rightsID FROM rightsaffiliation WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				rightsArray[resultSet.getInt("rightsID")] = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get rights of user: " + loginname);
		}
		close();
		return rightsArray;
	}

	public void changeRights(User user, boolean[] newRights) {

		String loginname = user.getLogin();
		query = "DELETE FROM rightsaffiliation WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			pStatement.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Couldn't delete rights of user: " + loginname);
		}
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
		close();
	}

	@SuppressWarnings("null")
	public List<User> getAllUsersFromInstitute(String institute) { // String institute = instituteID

		List<User> userList = null;
		query = "SELECT loginname FROM instituteaffiliatoin WHERE instituteID = ?";
		String loginname;
		ResultSet resultSetUsers = null;
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, institute);
			resultSet = pStatement.executeQuery();

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
			System.out.println("Couldn't get all users from institute: " + institute);
			e.printStackTrace();
		}
		close();
		return userList;
	}

	public List<String> getInstitute(String loginname) {
		List<String> instituteList = null;
		query = "SELECT instituteID FROM instituteaffiliation WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				instituteList.add(resultSet.getString("instituteID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get list of institutes from user: " + loginname);
		}
		close();
		return instituteList;
	}

	private void close() {

		try {
			pStatement.close();
			statement.close();
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't close connection.");
		}
	}
}
