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
	static final String CONNECTIONSTRING = "jdbc:mysql://localhost/test?user=monty&password=greatsqldb";
	static final int NUMBEROFRIGHTS = 5;

	public UserDBController() {

		super();
		connect();
		resultSet = null;
		try {
			connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void connect() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(CONNECTIONSTRING);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		query = "SELECT * FROM User";
		resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			boolean[] rightsArray = new boolean[NUMBEROFRIGHTS];
			List<String> instituteList = null;
			String loginname = resultSet.getString("loginname");

			rightsArray = getRights(loginname);
			instituteList = getInstitute(loginname);

//			User user = new User(loginname, resultSet.getString("firstname"),
//					resultSet.getString("lastname"),
//					resultSet.getString("mail"), rightsArray,
//					resultSet.getString("session"),
//					resultSet.getString("faculty"), instituteList,
//					resultSet.getString("represantative"),
//					resultSet.getString("supervisor"),
//					resultSet.getString("password"));
//			userList.add(user);
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
			return false;
		}
		return true;

	}

	public Boolean changeUser(User oldUser, User newUser) {

		String login = oldUser.getLogin();
		query = "SELECT * FROM User WHERE login=?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, login);
			resultSet = pStatement.executeQuery();
			// checks if user exists in database
			if (resultSet.next()) {
				query = "UPDATE User SET loginname=?, lastname=?, lastname=?, mail=?, password=?, session=? WHERE loginname=?";
				pStatement = connection.prepareStatement(query);
				pStatement.setString(1, newUser.getLogin());
				pStatement.setString(2, newUser.getLastName());
				pStatement.setString(3, newUser.getFirstName());
				pStatement.setString(4, newUser.getMail());
				pStatement.setString(5, newUser.getPassword());
				pStatement.setString(6, newUser.getSession());
				pStatement.setString(7, login);

				pStatement.executeUpdate();
			} else {
				System.out.println("That user was not found!");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//loescht User
	public boolean deleteUser(String loginname){
		return false;
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
				rightsArray[resultSet.getInt("rightsID") - 1] = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rightsArray;
	}

	public void changeRights(User user, boolean[] newRights) {

		String loginname = user.getLogin();
		query = "UPDATE rightsaffiliation WHERE loginname = ? SET rightsID = ?";
		for (int i = 0; i < newRights.length; i++) {
			if (newRights[i]) {
				try {
					pStatement = connection.prepareStatement(query);
					pStatement.setString(1, loginname);
					pStatement.setInt(2, i + 1);
					pStatement.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public List<User> getAllUsersFromInstitute(String institute) {

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
//				userList.add(new User(resultSetUsers.getString("login"),
//						resultSetUsers.getString("firstName"), resultSetUsers
//								.getString("lastName"), resultSetUsers
//								.getString("mail"), getRights(loginname),
//						resultSetUsers.getString("sessoin"), resultSetUsers
//								.getString("faculty"), getInstitute(loginname),
//						resultSetUsers.getString("representative"),
//						resultSetUsers.getString("supervisor"), resultSetUsers
//								.getString("password")));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void close() {

		try {
			statement.close();
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
