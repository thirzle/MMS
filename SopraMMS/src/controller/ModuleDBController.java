package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import management.CourseEntry;
import management.Entry;
import management.Module;

//
public class ModuleDBController {

	private static final String URL = "jdbc:mysql://127.0.0.1:3306/mms";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static String query = null;
	private static PreparedStatement pStatement = null;
	private static Statement statement = null;


	// establish connection to database
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

	//TODO
	// load all available modules
	public List<Module> getModules() {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		List<Entry> entryList = new LinkedList<Entry>();
		query = "SELECT moduleID FROM module";

		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			//get all entries except of course entries
			while(rs.next()){
				query = "SELECT e.* FROM entry AS e JOIN latestentry as l " +
						"on e.entryID = l.entryID AND e.version = l.version WHERE" +
						" e.moduleID = ? AND e.type = 0 AND" +
						" e.approvalstatus = false";
				pStatement = connection.prepareStatement(query);
				pStatement.setString(1, rs.getString(1));
				ResultSet resultSet = pStatement.executeQuery();
				List<String> courses;
				while (resultSet.next()) {
					
				}
				moduleList.add(new Module(resultSet.getInt("moduleID"),
						resultSet.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID"), entryList));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules.");
		} finally {
			close(connection);
		}
		return moduleList;
	}

	//TODO
	// load all available modules by a chosen institute
	public List<Module> getModulesByInstitute(String institute) {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.* FROM moduleinstituteaffiliation JOIN module ON moduleinstituteaffiliation.moduleID = module.moduleID WHERE instituteID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, institute);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getInt("moduleID"),
						resultSet.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules by institute: "
					+ institute);
		} finally {
			close(connection);
		}
		return moduleList;
	}


	// load all available modules by a chosen course
	// TODO
	public List<Module> getModulesByCourse(String course, String degree) {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.* FROM modulecourseaffiliation JOIN module ON modulecourseaffiliation.moduleID = module.moduleID WHERE courseID = ? AND degree = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, course);
			pStatement.setString(2, degree);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getInt("moduleID"),
						resultSet.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules by course: " + course
					+ " and degree: " + degree);
		} finally {
			close(connection);
		}
		return moduleList;
	}

	//TODO
	public List<Module> getModulesByFaculty(String faculty) {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.* FROM modulefacultyaffiliation JOIN module ON modulefacultyaffiliation.moduleID = module.moduleID WHERE faulty = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, faculty);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getInt("moduleID"),
						resultSet.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules by faculty: " + faculty);
		} finally {
			close(connection);
		}
		return moduleList;
	}

	//TODO
	// load all available modules by a chosen author
	public List<Module> getModulesByAuthor(String author) {
		Connection connection = connect();
		List<Module> modueList = new LinkedList<Module>();
		query = "SELECT module.*, entry.author FROM module JOIN entry on module.moduleID = entry.moduleID WHERE entry.author = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				modueList.add(new Module(resultSet.getInt("moduleID"),
						resultSet.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules by author: " + author);
		} finally {
			close(connection);
		}
		return modueList;
	}

	//TODO
	// get a specified module
	public Module getModule(int moduleID, String name) {
		Connection connection = connect();
		query = "SELECT * FROM module WHERE moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, moduleID);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				return new Module(resultSet.getInt("moduleID"),
						resultSet.getString("name"),
						resultSet.getDate("creationdate"),
						resultSet.getDate("modificationdate"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getString("instituteID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't get module: " + name);
		} finally {
			close(connection);
		}
		return null;
	}

	//TODO
	// load all modified modules
	public List<Module> getModifiedModules() {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.*" + "FROM module NATURAL JOIN entry"
				+ "WHERE entry.approvalstatus = TRUE AND declined = FALSE;";
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getInt("moduleID"),
						resultSet.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modified modules.");
		} finally {
			close(connection);
		}
		return moduleList;
	}

	//TODO
	// load all modified modules by a chosen institute
	public List<Module> getModifiedModulesByInstitute(String instituteID) {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.*" + "FROM module NATURAL JOIN entry"
				+ "WHERE entry.approvalstatus = TRUE AND declined = FALSE"
				+ "AND instituteID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, instituteID);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getInt("moduleID"),
						resultSet.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modified modules by institute: "
					+ instituteID);
		} finally {
			close(connection);
		}
		return moduleList;
	}

	//TODO
	// load all modified modules by a chosen author
	public List<Module> getModifiedModulesByAuthor(String author) {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.*" + "FROM module NATURAL JOIN entry"
				+ "WHERE entry.approvalstatus = TRUE AND declined = FALSE"
				+ "AND author = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getInt("moduleID"),
						resultSet.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modified modules by author: "
					+ author);
		} finally {
			close(connection);
		}
		return moduleList;

	}

	//TODO
	// load all rejected modules
	public List<Module> getRejectedModules() {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.*" + "FROM module NATURAL JOIN entry"
				+ "WHERE declined = TRUE";
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getInt("moduleID"),
						resultSet.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get rejected modules.");
		} finally {
			close(connection);
		}
		return moduleList;
	}

	//TODO
	// load all rejected modules by a chosen institute
	public List<Module> getRejectedModulesByInstitute(String instituteID) {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.*" + "FROM module NATURAL JOIN entry"
				+ "WHERE declined = TRUE AND instituteID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, instituteID);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getInt("moduleID"),
						resultSet.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get rejected modules by institute: "
					+ instituteID);
		} finally {
			close(connection);
		}
		return moduleList;
	}

	//TODO
	// load all rejected modules by a chosen author
	public List<Module> getRejectedModulesByAuthor(String author) {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.*" + "FROM module NATURAL JOIN entry"
				+ "WHERE declined = TRUE AND author = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getInt("moduleID"),
						resultSet.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get rejected modules by author: "
					+ author);
		} finally {
			close(connection);
		}
		return moduleList;
	}
	
	public CourseEntry getCoursesbyModule(String moduleID){
		CourseEntry courseEntry = null;
		query = "SELECT e.*, c.courseID FROM entry AS e "
				+ "JOIN latestentry as l on e.entryID = l.entryID AND e.version = l.version " +
				"JOIN courseentry AS c ON e.entryID = c.entryID AND e.version = c.version " +
				"WHERE e.moduleID = ? AND e.type = 0 AND e.approval = false";
		try {
			Connection connection = connect();
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, moduleID);
			ResultSet resultSet = pStatement.executeQuery();
			List<String> courses = new LinkedList<String>();
			resultSet.next();
			courseEntry = new CourseEntry(resultSet.getInt("version"), resultSet.getDate("date").toString(), 
					resultSet.getBoolean("classification"), resultSet.getBoolean("approvalstatus"),
					resultSet.getBoolean("declined"), resultSet.getString("caption"));
			courseEntry.addCourse(resultSet.getString("courseID"));
			while (resultSet.next()) {
				courseEntry.addCourse(resultSet.getString("courseID"));
			}
		}catch(SQLException e){
			
		}		
		return courseEntry;
	}


	// create a new module in database
	public boolean createModule(Module module) {
		Connection connection = connect();
		int moduleID = module.getModuleID();
		String name = module.getName();
		Date creationDate = module.getCreationDate();
		Date modificationDate = module.getModificationDate();
		boolean approved = module.isApproved();
		String instituteID = module.getInstituteID();

		query = "INSERT INTO module VALUES (?,?,?,?,?,?);";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, moduleID);
			pStatement.setString(2, name);
			pStatement.setDate(3, (java.sql.Date) creationDate);
			pStatement.setDate(4, (java.sql.Date) modificationDate);
			pStatement.setBoolean(5, approved);
			pStatement.setString(6, instituteID);
			return pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't create module: " + name);
		} finally {
			close(connection);
		}
		return false;
	}


	// change an existing module
	public boolean changeModule(Module module, int moduleIDOld) {
		Connection connection = connect();
		int moduleID = module.getModuleID();
		String name = module.getName();
		Date creationDate = module.getCreationDate();
		Date modificationDate = module.getModificationDate();
		boolean approved = module.isApproved();
		String instituteID = module.getInstituteID();

		query = "UPDATE module"
				+ "SET moduleID = ?, name = ?, creationDate = ?, modificationDate = ?, approved = ?, instituteID = ?"
				+ "WHERE moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, moduleID);
			pStatement.setString(2, name);
			pStatement.setDate(3, (java.sql.Date) creationDate);
			pStatement.setDate(4, (java.sql.Date) modificationDate);
			pStatement.setBoolean(5, approved);
			pStatement.setString(6, instituteID);
			pStatement.setInt(7, moduleIDOld);
			return pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't change module: " + moduleID);
		} finally {
			close(connection);
		}
		return false;

	}


	// delete an existing module
	public boolean deleteModule(Module module) {
		Connection connection = connect();
		query = "DELETE FROM module WHERE moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, module.getModuleID());
			return pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't delete module: "
					+ module.getModuleID());
		} finally {
			close(connection);
		}
		return false;
	}
	
	//TODO
	public CourseEntry getCoursesByModule(Module m){
		CourseEntry courses = null;
		Connection connection = connect();
		query = "SELECT c.courseID e.* FROM courseentry AS c JOIN latestentry AS l" +
				" ON c.entryID = l.entryID AND c.version = l.version JOIN entry " +
				"AS e on c.entryID = e.entryID AND c.version = e.version" +
				" WHERE e.moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, m.getModuleID());
			ResultSet resultSet = pStatement.executeQuery();
			if(resultSet.next()){
				courses = new CourseEntry(resultSet.getInt("version"),
						resultSet.getTimestamp("timestamp").toString(), 
						resultSet.getBoolean("classification"), 
						resultSet.getBoolean("approvedstatus"), 
						resultSet.getBoolean("declined"), 
						resultSet.getString("caption"), 
						resultSet.getString("courseID"));
			}
			while (resultSet.next()) {
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return courses;
	}
	// TODO
	// get a specified modulemanual
	public String getModuleManual(String courseID, String degree, String version) {
		Connection connection = connect();
		
		close(connection);
		return null;
	}


	public void close(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't close connection.");
		}
	}

}
