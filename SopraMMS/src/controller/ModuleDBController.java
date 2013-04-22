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

import management.Module;
//
public class ModuleDBController {

	private static final String URL = "jdbc:mysql://127.0.0.1:3306/mms";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static Connection connection = null;
	private static String query = null;
	private static ResultSet resultSet = null;
	private static PreparedStatement pStatement = null;
	private static Statement statement = null;


	public ModuleDBController() {
		connect();
	}


//	establish connection to database
	public void connect() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("driver not found");
		}
		try {
			connection = DriverManager.getConnection(URL, "root", "");
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("connection couldn't be established");
		}
	}

//	load all available modules
	public List<Module> getModules() {
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT * FROM module";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
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
			System.out.println("Couldn't get modules.");
		}
		return moduleList;
	}

//	load all available modules by a chosen institute
	public List<Module> getModulesByInstitute(String institute) {
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.* FROM moduleinstituteaffiliation JOIN module ON moduleinstituteaffiliation.moduleID = module.moduleID WHERE instituteID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, institute);
			resultSet = pStatement.executeQuery();
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
		}
		return moduleList;
	}

//	load all available modules by a chosen course
//	TODO
	public List<Module> getModulesByCourse(String course, String degree) {
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.* FROM modulecourseaffiliation JOIN module ON modulecourseaffiliation.moduleID = module.moduleID WHERE courseID = ? AND degree = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, course);
			pStatement.setString(2, degree);
			resultSet = pStatement.executeQuery();
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
		}
		return moduleList;
	}


	public List<Module> getModulesByFaculty(String faculty) {
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.* FROM modulefacultyaffiliation JOIN module ON modulefacultyaffiliation.moduleID = module.moduleID WHERE faulty = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, faculty);
			resultSet = pStatement.executeQuery();
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
		}
		return moduleList;
	}


	public List<Module> getModulesByAuthor(String author) {
		List<Module> modueList = new LinkedList<Module>();
		query = "SELECT * FROM module WHERE author = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
			resultSet = pStatement.executeQuery();
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
		}
		return modueList;
	}


	public Module getModule(int moduleID, String name) {
		query = "SELECT * FROM module WHERE moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, moduleID);
			resultSet = pStatement.executeQuery();
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
		}
		return null;
	}


	public List<Module> getModifiedModules() {
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.*" + "FROM module NATURAL JOIN entry"
				+ "WHERE entry.approvalstatus = TRUE AND declined = FALSE;";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
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
		}
		return moduleList;
	}


	public List<Module> getModifiedModulesByInstitute(String instituteID) {
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.*" + "FROM module NATURAL JOIN entry"
				+ "WHERE entry.approvalstatus = TRUE AND declined = FALSE"
				+ "AND instituteID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, instituteID);
			resultSet = pStatement.executeQuery();
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
		}
		return moduleList;
	}


	public List<Module> getModifiedModulesByAuthor(String author) {
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.*" + "FROM module NATURAL JOIN entry"
				+ "WHERE entry.approvalstatus = TRUE AND declined = FALSE"
				+ "AND author = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
			resultSet = pStatement.executeQuery();
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
		}
		return moduleList;

	}


	public List<Module> getRejectedModules() {
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.*" + "FROM module NATURAL JOIN entry"
				+ "WHERE declined = TRUE";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
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
		}
		return moduleList;
	}


	public List<Module> getRejectedModulesByInstitute(String instituteID) {
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.*" + "FROM module NATURAL JOIN entry"
				+ "WHERE declined = TRUE AND instituteID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, instituteID);
			resultSet = pStatement.executeQuery();
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
		}
		return moduleList;
	}


	public List<Module> getRejectedModulesByAuthor(String author) {
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.*" + "FROM module NATURAL JOIN entry"
				+ "WHERE declined = TRUE AND author = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
			resultSet = pStatement.executeQuery();
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
		}
		return moduleList;
	}


	public boolean createModule(Module module) {
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
		}
		return false;
	}


	public boolean changeModule(Module module, int moduleIDOld) {
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
		}
		return false;

	}


	public boolean deleteModule(Module module) {
		query = "DELETE FROM module WHERE moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, module.getModuleID());
			return pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't delete module: "
					+ module.getModuleID());
		}
		return false;
	}


	// TODO
	public String getModuleManual(String courseID, String degree, String version) {

		return null;
	}


	public void close() {
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
