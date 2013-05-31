package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.LinkedList;
import java.util.List;

import com.ibm.icu.text.SimpleDateFormat;

import management.Deadline;

import user.User;

public class UserDBController {

	private static Statement statement;
	private static String query = null;
	private static PreparedStatement pStatement;

	// local database
	// private static final String URL = "jdbc:mysql://localhost:3306/mms";
	// private static final String USER = "root";
	// private static final String PASSWORD = "";

	// db4free.net database
	private static final String URL = "jdbc:mysql://db4free.net:3306/sopramms";
	private static final String USER = "teamaccount";
	private static final String PASSWORD = "6lsj7tdm";
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

			System.out.println("User created!");
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

	// change existing user in database
	public boolean changeUser(User oldUser, User newUser) {
		Connection connection = connect();
		String loginname = oldUser.getLogin();
		query = "SELECT * FROM user WHERE loginname=?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
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
		} finally {
			close(connection);
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
			connection.setAutoCommit(false);
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			pStatement.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Couldn't delete rights of user: " + loginname);
			rollback(connection);
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
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't insert right: " + i + " of user: "
					+ loginname);
			rollback(connection);
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
		query = "SELECT i.name FROM institute AS i JOIN instituteaffiliation AS ia "
				+ "ON i.instituteID = ia.instituteID WHERE ia.loginname = ?";
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
		} finally {
			close(connection);
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
	
	
	public List<String> getCoursesByFaculty(String facultyID){
		Connection connection = connect();
		List<String> courses = new LinkedList<String>();
		query = "SELECT DISTINCT description FROM course JOIN faculty on course.faculty = faculty.facultyID " +
				"WHERE faculty = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1,facultyID);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				courses.add(resultSet.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get courses of faculty: "+facultyID);
		}
		finally{
			close(connection);
		}
		return courses;
	}
	

	// get facultyname of user
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

	// remove representative of user
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
			System.out.println("couldn't remove representative of user: "
					+ user.getLogin());
		} finally {
			close(connection);
		}
		return false;
	}
	
	
	public Boolean setForgotPwdByMail(String mail, String forgotPwd){
		Connection connection = connect();
		query = "UPDATE user SET forgotpwd = ? WHERE mail = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, forgotPwd);
			pStatement.setString(2, mail);
			return pStatement.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			close(connection);
		}
	}
	
	
	public Boolean removeForgotPwdByLoginname(String loginname){
		Connection connection = connect();
		query = "UPDATE user SET forgotpwd = null WHERE loginname = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			return pStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			close(connection);
		}
	}
	
	
	public User getUserByForgotPwd(String forgotPwd){
		Connection connection = connect();
		User user = null;
		query = "SELECT * FROM user WHERE forgotpwd = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, forgotPwd);
			ResultSet resultSet = pStatement.executeQuery();
			if(resultSet.next()){
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
		}finally{
			close(connection);
		}
		return user;
	}
	
	public List<String> getFacultiesByName(){
		Connection connection = connect();
		LinkedList<String> facList = new LinkedList<String>();
		query = "SELECT * FROM faculty";
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()){
				facList.add(resultSet.getString("name"));
			}
			return facList;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get names of faculties");
		}
		finally{
			close(connection);
		}
		return facList;
	}
	
	public List<String> getFacultiesID(){
		Connection connection = connect();
		LinkedList<String> facList = new LinkedList<String>();
		query = "SELECT * FROM faculty";
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()){
				facList.add(resultSet.getString("facultyID"));
			}
			return facList;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get IDs of faculties");
		}
		finally{
			close(connection);
		}
		return facList;
	}
	

	// roll back changes made in database if something went wrong
	private void rollback(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Deadline getDeadlineByFaculty(String facultyID){
		Connection connection = connect();
		query = "SELECT * FROM deadline where facultyID = ?";
		
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, facultyID);
			ResultSet resultSet = pStatement.executeQuery();
			if(resultSet.next()){
				return new Deadline(resultSet.getDate("deadline"),
						resultSet.getDate("beginremember"),
						resultSet.getInt("tolerance"), 
						resultSet.getString("facultyID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public List<String[]> getNews(int type){
		List<String[]> news = new LinkedList<String[]>();
		Connection connection = connect();
		query = "SELECT * FROM news WHERE visibility = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, type);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				Date date= resultSet.getDate("timestamp");
				SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
				
				news.add(new String[]{resultSet.getString("title"), 
						resultSet.getString("content"), 
						dateformat.format(date)});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Couldn't load news");
		}finally{
			close(connection);
		}
		return news;
	}
	
	public boolean addNews(String title, String content, String author, int type){
		Connection connection = connect();
		query = "INSERT INTO news VALUES (?,?,?,?)";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, title);
			pStatement.setString(2, content);
			pStatement.setString(3, author);
			pStatement.setInt(4, type);
			return pStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Couldn't add news");
			return false;
		} finally{
			close(connection);
		}
	}
	
	
	public boolean deleteNews(String title){
		Connection connection = connect();
		query = "DELETE FROM news WHERE title = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, title);
			return pStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Couldn't delete news");
			return false;
		} finally{
			close(connection);
		}
	}
	
	
	public List<String> convertInstituteToID(List<String> instituteName){
		Connection connection = connect();
		List<String> instituteID = new LinkedList<String>();
		query = "SELECT instituteID FROM institute WHERE name = ?";
		try {
			pStatement = connection.prepareStatement(query);
			for (String string : instituteName) {
				pStatement.setString(1, string);
				ResultSet resultSet = pStatement.executeQuery();
				if(resultSet.next()){
					instituteID.add(resultSet.getString("instituteID"));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally{
			close(connection);
		}
		return instituteID;
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
