package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.servlet.jsp.tagext.TryCatchFinally;

import user.User;

import com.sun.xml.internal.bind.v2.TODO;

import management.Module;

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

	public void connect() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Treiber nicht gefunden");
		}
		try {
			connection = DriverManager.getConnection(URL, "root", "");
		} catch (SQLException e1) {
			System.out.println("Keine Verbidnung zu Datenbank moeglich");
			e1.printStackTrace();
		}
	}

	@SuppressWarnings("null")
	public List<Module> getModules() {
		List<Module> modulelist = null;
		query = "SELECT * FROM module";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				modulelist.add(new Module(resultSet.getString("name"),
						resultSet.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules.");
		}
		return modulelist;
	}

	@SuppressWarnings("null")
	public List<Module> getModulesByInstitute(String institute) {
		List<Module> modulelist = null;
		query = "SELECT module.* FROM moduleinstituteaffiliation JOIN module ON moduleinstituteaffiliation.moduleID = module.moduleID WHERE instituteID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, institute);
			resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				modulelist.add(new Module(resultSet.getString("name"),
						resultSet.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules by institute: "
					+ institute);
		}
		return modulelist;
	}

	@SuppressWarnings("null")
	public List<Module> getModulesByCourse(String course, String degree) {
		List<Module> modulelist = null;
		query = "SELECT module.* FROM modulecourseaffiliation JOIN module ON modulecourseaffiliation.moduleID = module.moduleID WHERE courseID = ? AND degree = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, course);
			pStatement.setString(2, degree);
			resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				modulelist.add(new Module(resultSet.getString("name"),
						resultSet.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules by course: " + course
					+ " and degree: " + degree);
		}
		return modulelist;
	}

	@SuppressWarnings("null")
	public List<Module> getModulesByFaculty(String faculty) {
		List<Module> modulelist = null;
		query = "SELECT module.* FROM modulefacultyaffiliation JOIN module ON modulefacultyaffiliation.moduleID = module.moduleID WHERE faulty = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, faculty);
			resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				modulelist.add(new Module(resultSet.getString("name"),
						resultSet.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules by faculty: " + faculty);
		}
		return modulelist;
	}

	@SuppressWarnings("null")
	public List<Module> getModulesByAuthor(String author) {
		List<Module> modulelist = null;
		query = "SELECT * FROM module WHERE author = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
			resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				modulelist.add(new Module(resultSet.getString("name"),
						resultSet.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules by author: " + author);
		}
		return modulelist;
	}

	public Module getModule(int moduleID, String name) {
		query = "SELECT * FROM module WHERE moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, moduleID);
			resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				return new Module(resultSet.getString("name"),
						resultSet.getDate("creationdate"),
						resultSet.getDate("modificationdate"),
						resultSet.getBoolean("approvalstatus"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't get module: " + name);
		}
		return null;
	}

	@SuppressWarnings("null")
	public List<Module> getModifiedModules() {
		List<Module> modulelist = null;
		query = "SELECT module.*" + "FROM module NATURAL JOIN entry"
				+ "WHERE entry.approvalstatus = TRUE AND declined = FALSE;";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				modulelist.add(new Module(resultSet.getString("name"),
						resultSet.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("apprvalstatus")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modified modules.");
		}
		return modulelist;
	}

	@SuppressWarnings("null")
	public List<Module> getModifiedModulesByInstitute(String institute) {
		List<Module> modulelist = null;
		query = "SELECT module.* FROM moduleinstituteaffiliation JOIN module ON moduleinstituteaffiliation.moduleID = module.moduleID WHERE instituteID = ? AND approvalstatus = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, institute);
			pStatement.setBoolean(2, false); // false oder true? TODO
			resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				modulelist.add(new Module(resultSet.getString("name"),
						resultSet.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("false")));// false oder true? TODO
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modified modules by institute: "
					+ institute);
		}
		return modulelist;
	}

	@SuppressWarnings("null")
	public List<Module> getModifiedModulesByAuthor(String author) {
		List<Module> modulelist = null;
		query = "SELECT * FROM module WHERE author = ? AND approvalstatus = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
			pStatement.setBoolean(2, false); // false oder true? TODO
			resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				modulelist.add(new Module(resultSet.getString("name"),
						resultSet.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("false")));// false oder true? TODO
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modified modules by author: "
					+ author);
		}
		return modulelist;

	}

	// TODO
	public List<Module> getRejectedModules() {
		List<Module> modulelist = null;
		return modulelist;
	}

	// TODO
	public List<Module> getRejectedModulesByInstitute(String institute) {
		List<Module> modulelist = null;
		return modulelist;
	}

	// TODO
	public List<Module> getRejectedModulesByAuthor(String author) {
		List<Module> modulelist = null;
		return modulelist;
	}

	public boolean createModule(Module module) {
		int moduleID = module.getModuleID();
		String name = module.getName();
		Date creationDate = module.getCreationDate();
		Date modificationDate = module.getModificationDate();
		boolean approved = module.isApproved();
		//String modulehandbook = module.getModulehandbook();
		String instituteID = module.getInstituteID();

		query = "INSERT INTO module VALUES (?,?,?,?,?,?)";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, moduleID);
			pStatement.setString(2, name);
			pStatement.setDate(3, (java.sql.Date) creationDate);
			pStatement.setDate(4, (java.sql.Date) modificationDate);
			pStatement.setBoolean(5, approved);
//			pStatement.setString(6, modulehandbook);
			pStatement.setString(7, instituteID);
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
//		String modulehandbook = module.getModulehandbook();
		String instituteID = module.getInstituteID();

		query = "UPDATE module"
				+ "SET moduleID = ?, name = ?, creationDate = ?, modificationDate = ?, approved = ?, modulehandbook = ?, instituteID = ?"
				+ "WHERE moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, moduleID);
			pStatement.setString(2, name);
			pStatement.setDate(3, (java.sql.Date) creationDate);
			pStatement.setDate(4, (java.sql.Date) modificationDate);
			pStatement.setBoolean(5, approved);
//			pStatement.setString(6, modulehandbook);
			pStatement.setString(7, instituteID);
			pStatement.setInt(8, moduleIDOld);
			return pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't change Module module: " + moduleID);
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
	public String getModuleManual(String course, String degree, String version) {
		return null;
	}

}
