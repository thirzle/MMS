package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import management.Course;
import management.CourseEntry;
import management.EffortEntry;
import management.Entry;
import management.Module;
import management.SelfStudy;
import management.TextualEntry;
//TODO Bitte alle Kommentare prüfen und Autoren eintragen
/**
 * @author Max
 *
 */
public class ModuleDBController {

	// local database
	// private static final String URL = "jdbc:mysql://127.0.0.1:3306/mms";
	// private static final String USER = "root";
	// private static final String PASSWORD = "";

	// db4free.net database
	private static final String URL = "jdbc:mysql://db4free.net:3306/sopramms";
	private static final String USER = "teamaccount";
	private static final String PASSWORD = "6lsj7tdm";
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	private static String query = null;
	private static PreparedStatement pStatement = null;
	private static Statement statement = null;

	/**
	 * Establishes connection to database.
	 * 
	 * @return
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
	 * Loads all available modules.
	 * <p>
	 * Gets all available modules from the database and stores them in a {@link List} of modules.
	 * 
	 * @return			List of modules
	 * @see Module
	 */
	public List<Module> getModules() {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT * FROM module AS m JOIN latestmodule AS l " +
				"ON m.moduleID = l.moduleID AND m.version = l.version";

		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"), resultSet
								.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID"), resultSet
								.getString("subject"), resultSet
								.getString("modificationauthor")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules.");
		} finally {
			close(connection);
		}
		return moduleList;
	}

	/**
	 * Loads all entries of a specified module.
	 * <p>
	 * Gets a module and with his "moduleID" and his "module version"
	 * it gets the appropriate entries the module consists of.
	 * These are returned in the form of a {@link Module#entryList}.
	 * 
	 * @param module		The name of the Module.
	 * @return				The entryList of the Module.
	 * @see Module
	 * @see Entry
	 * @see Course
	 * @see CourseEntry
	 * @see SelfStudy
	 * @see TextualEntry
	 */
	public List<Entry> getEntryListOfModule(Module module) {
		Connection connection = connect();
		CourseEntry courses = null;
		List<TextualEntry> textual = new LinkedList<TextualEntry>();
		EffortEntry effort = null;
		List<SelfStudy> selfstudy = new LinkedList<SelfStudy>();
		query = "SELECT ce.courseID, ce.degree, ce.obligatory, c.description, e.* FROM courseentry AS ce JOIN entry "
				+ "AS e on ce.entryID = e.entryID JOIN course AS c ON " +
				"ce.courseID = c.courseID AND ce.degree = c.degree"
				+ " WHERE e.moduleID = ? AND e.moduleversion = ?";
		try {
			// load courses of module
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, module.getModuleID());
			pStatement.setInt(2, module.getVersion());
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				courses = new CourseEntry(resultSet.getDate("date")
						.toString(), resultSet.getBoolean("classification"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getBoolean("declined"),
						resultSet.getLong("entryID"),
						resultSet.getString("title"),
						resultSet.getInt("order"),
						resultSet.getString("courseID"),
						resultSet.getString("description"),
						resultSet.getString("degree"),
						resultSet.getBoolean("obligatory"));
			}
			while (resultSet.next()) {
				courses.addCourse(resultSet.getString("courseID"),
						resultSet.getString("description"), 
						resultSet.getString("degree"),
						resultSet.getBoolean("obligatory"));
			}
			// load textual entries of module
			query = "SELECT e.*, t.text " + "FROM entry AS e "
					+ "JOIN textualentry AS t ON " + "e.entryID = t.entryID "
					+ "WHERE e.moduleID = ? AND e.moduleversion = ?";

			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, module.getModuleID());
			pStatement.setInt(2, module.getVersion());
			resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				textual.add(new TextualEntry(resultSet.getDate("date")
						.toString(), resultSet.getBoolean("classification"),
						resultSet.getBoolean("approvalstatus"), resultSet
								.getBoolean("declined"), resultSet
								.getLong("entryID"), resultSet
								.getString("title"), resultSet.getInt("order"),
						resultSet.getString("text")));
			}

			query = "SELECT e.*,  ef.presencetime "
					+ "FROM entry AS e JOIN effortentry AS ef ON e.entryID = ef.entryID "
					+ "WHERE e.moduleID = ? AND e.moduleversion = ?";
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, module.getModuleID());
			pStatement.setInt(2, module.getVersion());
			resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				effort = new EffortEntry(resultSet.getDate("date").toString(),
						resultSet.getBoolean("classification"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getBoolean("declined"),
						resultSet.getLong("entryID"),
						resultSet.getString("title"),
						resultSet.getInt("order"),
						resultSet.getInt("presencetime"));

				// get selfstudies of effort entry
				query = "SELECT s.selfstudyID, s.time, s.title "
						+ "FROM entry AS e JOIN selfstudy AS s ON e.entryID = s.entryID "
						+ "WHERE e.moduleID = ? AND e.moduleversion = ?";
				pStatement = connection.prepareStatement(query);
				pStatement.setLong(1, module.getModuleID());
				pStatement.setInt(2, module.getVersion());
				resultSet = pStatement.executeQuery();
				while (resultSet.next()) {
					selfstudy.add(new SelfStudy(resultSet
							.getLong("selfstudyID"), resultSet.getInt("time"),
							resultSet.getString("title")));
				}
				effort.setSelfStudyList(selfstudy);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get entries of module: "
					+ module.getName());
		}
		if (courses != null)
			module.addCourseEntry(courses);
		if (!textual.isEmpty())
			module.addTextualEntryList(textual);
		if (effort != null)
			module.addEffortEntry(effort);
		return module.getEntryList();
	}

	
	// tested: check
	/**
	 * Loads all available modules by a chosen institute.
	 * <p>
	 * Gets a module and with the instituteID from the given institute.
	 * 
	 * @param institute		The institute of this Module.
	 * @return				List of modules
	 * @see Module
	 */
	public List<Module> getModulesByInstitute(String institute) {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.* FROM module WHERE instituteID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, institute);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"), resultSet
								.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID"), resultSet
								.getString("subject"), resultSet
								.getString("modificationauthor")));
			}
			for (Module module : moduleList) {
				module.setEntryList(getEntryListOfModule(module));
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

	// 
	// TODO
	/**
	 * Loads all available modules by a chosen course.
	 * 
	 * @param course		Course which belongs to the inquired module
	 * @param degree
	 * @return List of modules
	 * @see Module
	 * @see Course
	 */
	public List<Module> getModulesByCourse(String course, String degree) {
		Connection connection = connect();
		LinkedList<Module> moduleList = new LinkedList<Module>();
		LinkedList<Long> temp = new LinkedList<Long>();
		Module module = null;
		query = "SELECT m.* FROM modulecourseaffiliation AS ma JOIN module AS m "
				+ "ON ma.moduleID = m.moduleID WHERE ma.courseID = ? AND ma.degree = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, course);
			pStatement.setString(2, degree);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				module = new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"),
						resultSet.getString("name"),
						resultSet.getDate("creationdate"),
						resultSet.getDate("modificationdate"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getString("instituteID"),
						resultSet.getString("subject"),
						resultSet.getString("modificationauthor"));
				// check for duplicate
				if (moduleList.isEmpty()) {
					moduleList.add(module);
					temp.add(module.getModuleID());
				} else {
					if (!temp.contains(module.getModuleID())) {
						moduleList.add(module);
						temp.add(module.getModuleID());
					}
				}
			}

			for (Module module1 : moduleList) {
				module1.setEntryList(getEntryListOfModule(module1));
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

	// tested: check
	/**
	 * Loads all available modules by a chosen faculty.
	 * <p>
	 * 
	 * @param facultyID
	 * @return List of modules
	 * @see Module
	 */
	public List<Module> getModulesByFaculty(String facultyID) {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.* FROM module AS m JOIN institute AS i ON "
				+ "m.instituteID = i.institute WHERE i.facultyID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, facultyID);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"), resultSet
								.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID"), resultSet
								.getString("subject"), resultSet
								.getString("modificationauthor")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules by faculty: " + facultyID);
		} finally {
			close(connection);
		}
		return moduleList;
	}


	// tested: check
	/**
	 * Loads all available modules by a chosen author.
	 * <p>
	 * 
	 * @param author		The author of the modules content
	 * @return List of modules
	 * @see Module
	 */
	public List<Module> getModulesOverviewByAuthor(String author) {
		Connection connection = connect();
		LinkedList<Module> moduleList = new LinkedList<Module>();
		query = "SELECT m.* FROM module AS m JOIN latestmodule AS l ON " +
				"m.moduleID = l.moduleID AND m.version = l.version " +
				"WHERE modificationauthor = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"),
						resultSet.getString("name"),
						resultSet.getDate("creationdate"),
						resultSet.getDate("modificationdate"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getString("instituteID"),
						resultSet.getString("subject"),
						resultSet.getString("modificationauthor")));
			}
			moduleList.addAll(getModulesOverviewBySupervisor(author, connection));
			moduleList.addAll(getModulesOverviewByRepresentative(author, connection));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules by author: " + author);
		} finally {
			close(connection);
		}
		return moduleList;
	}
	
	
	/**
	 * Loads modules by the loginname of an supervisor.
	 * 
	 * @param loginname		The loginname of the supervisor
	 * @param connection	Connection to a database.
	 * @return List of modules
	 * @see Module
	 */
	public List<Module> getModulesOverviewBySupervisor(String loginname, Connection connection){
		query = "SELECT supervisor FROM supervisor WHERE username = ?";
		String supervisor = "no supervisor found";
		LinkedList<Module> moduleList = new LinkedList<Module>();
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			if(resultSet.next()){
				supervisor = resultSet.getString("supervisor");
				query = "SELECT m.* FROM module AS m JOIN latestmodule AS l ON " +
						"m.moduleID = l.moduleID AND m.version = l.version " +
						"WHERE modificationauthor = ?";
				pStatement = connection.prepareStatement(query);
				pStatement.setString(1, supervisor);
				resultSet = pStatement.executeQuery();
				while (resultSet.next()) {
					moduleList.add(new Module(resultSet.getLong("moduleID"),
							resultSet.getInt("version"),
							resultSet.getString("name"),
							resultSet.getDate("creationdate"),
							resultSet.getDate("modificationdate"),
							resultSet.getBoolean("approvalstatus"),
							resultSet.getString("instituteID"),
							resultSet.getString("subject"),
							resultSet.getString("modificationauthor")));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
			System.out.println("Couldn't get modules by supervisor: " + supervisor);
		}
			return moduleList;
	}
	
	
	/**
	 * Loads modules by the loginname of an representative.
	 * 
	 * @param loginname		The loginname of the representative.
	 * @param connection	Connection to a database.
	 * @return List of modules
	 * @see Module
	 */
	public List<Module> getModulesOverviewByRepresentative(String loginname, Connection connection){
		query = "SELECT representative FROM user WHERE loginname = ?";
		String representative = "no representative found";
		LinkedList<Module> moduleList = new LinkedList<Module>();
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			if(resultSet.next()){
				representative = resultSet.getString("representative");
				query = "SELECT m.* FROM module AS m JOIN latestmodule AS l ON " +
						"m.moduleID = l.moduleID AND m.version = l.version " +
						"WHERE modificationauthor = ?";
				pStatement = connection.prepareStatement(query);
				pStatement.setString(1, representative);
				resultSet = pStatement.executeQuery();
				while (resultSet.next()) {
					moduleList.add(new Module(resultSet.getLong("moduleID"),
							resultSet.getInt("version"),
							resultSet.getString("name"),
							resultSet.getDate("creationdate"),
							resultSet.getDate("modificationdate"),
							resultSet.getBoolean("approvalstatus"),
							resultSet.getString("instituteID"),
							resultSet.getString("subject"),
							resultSet.getString("modificationauthor")));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
			System.out.println("Couldn't get modules by representative: " + representative);
		}
			return moduleList;
	}
	


	// tested: check
	/**
	 * Gets all versions of specified module.
	 * 
	 * @param moduleID		The unique ID of a module.
	 * @return List of modules
	 * @see Module
	 */
	public List<Module> getAllVersionsOfModule(long moduleID) {
		Connection connection = connect();
		LinkedList<Module> versions = new LinkedList<Module>();
		query = "SELECT * FROM module WHERE moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, moduleID);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				versions.add(new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"),
						resultSet.getString("name"),
						resultSet.getDate("creationdate"),
						resultSet.getDate("modificationdate"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getString("instituteID"),
						resultSet.getString("subject"),
						resultSet.getString("modificationauthor")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't get versions of module: " + moduleID);
		} finally {
			close(connection);
		}
		return versions;
	}

	// tested: check
	/**
	 * Gets specified module.
	 * 
	 * @param moduleID		The unique ID of the module.
	 * @param version		The modules version
	 * @return A module object
	 * @see Module
	 */
	public Module getModule(long moduleID, int version) {
		Connection connection = connect();
		Module module = null;
		query = "SELECT * FROM module WHERE moduleID = ? AND version = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, moduleID);
			pStatement.setInt(2, version);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				module = new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"),
						resultSet.getString("name"),
						resultSet.getDate("creationdate"),
						resultSet.getDate("modificationdate"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getString("instituteID"),
						resultSet.getString("subject"),
						resultSet.getString("modificationauthor"));
			}
			module.setEntryList(getEntryListOfModule(module));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't get module: " + moduleID);
		} finally {
			close(connection);
		}
		return module;
	}

	/**
	 * Gets the latest version of an specified module.
	 * 
	 * @param moduleID		The unique ID of the module.
	 * @return A module object
	 */
	public Module getLatestModule(long moduleID) {
		Connection connection = connect();
		Module module = null;
		int version = -1;
		query = "SELECT version FROM latestmodule WHERE moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, moduleID);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				version = resultSet.getInt("version");
			}
			if (version != -1) {
				query = "SELECT * FROM module WHERE moduleID = ? AND version = ?";
				pStatement = connection.prepareStatement(query);
				pStatement.setLong(1, moduleID);
				pStatement.setInt(2, version);
				resultSet = pStatement.executeQuery();
				if (resultSet.next()) {
					module = new Module(resultSet.getLong("moduleID"),
							resultSet.getInt("version"),
							resultSet.getString("name"),
							resultSet.getDate("creationdate"),
							resultSet.getDate("modificationdate"),
							resultSet.getBoolean("approvalstatus"),
							resultSet.getString("instituteID"),
							resultSet.getString("subject"),
							resultSet.getString("modificationauthor"));
				}
			}
			module.setEntryList(getEntryListOfModule(module));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't get module: " + moduleID);
		} finally {
			close(connection);
		}
		return module;
	}

	// load all modified modules
	// tested: check
	/**
	 * Loads all modules which are modified.
	 * 
	 * @return List of modules.
	 * @see Module
	 */
	public List<Module> getModifiedModules() {
		Connection connection = connect();
		LinkedList<Module> moduleList = new LinkedList<Module>();
		LinkedList<Long> temp = new LinkedList<Long>();
		Module module;
		query = "SELECT module.* FROM module JOIN entry ON module.moduleID = entry.moduleID "
				+ "WHERE entry.approvalstatus = TRUE AND declined = FALSE;";
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				module = new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"),
						resultSet.getString("name"),
						resultSet.getDate("creationdate"),
						resultSet.getDate("modificationdate"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getString("instituteID"),
						resultSet.getString("subject"),
						resultSet.getString("modificationauthor"));
				// check for duplicate
				if (!temp.contains(module.getModuleID())) {
					moduleList.add(module);
					temp.add(module.getModuleID());
				}
			}
			for (Module modules : moduleList) {
				modules.setEntryList(getEntryListOfModule(modules));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modified modules.");
		} finally {
			close(connection);
		}
		return moduleList;
	}

	// tested: check
	/**
	 * Loads all modified modules by a chosen institute.
	 * 
	 * @param instituteID		The unique ID of the institute.
	 * @return List of modules.
	 * @see Module
	 */
	public List<Module> getModifiedModulesByInstitute(String instituteID) {
		Connection connection = connect();
		LinkedList<Module> moduleList = new LinkedList<Module>();
		LinkedList<Long> temp = new LinkedList<Long>();
		Module module;
		query = "SELECT module.* FROM module JOIN entry ON module.moduleID = entry.moduleID "
				+ "WHERE entry.approvalstatus = TRUE AND declined = FALSE AND instituteID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, instituteID);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				module = new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"),
						resultSet.getString("name"),
						resultSet.getDate("creationdate"),
						resultSet.getDate("modificationdate"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getString("instituteID"),
						resultSet.getString("subject"),
						resultSet.getString("modificationauthor"));
				// check for duplicate
				if (!temp.contains(module.getModuleID())) {
					moduleList.add(module);
					temp.add(module.getModuleID());
				}
			}
			for (Module modules : moduleList) {
				modules.setEntryList(getEntryListOfModule(modules));
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

	// tested: check
	/**
	 * Loads all modified modules by a chosen author.
	 * 
	 * @param author		The author of the modules.
	 * @return List of modules
	 * @see Module
	 */
	public List<Module> getModifiedModulesByAuthor(String author) {
		Connection connection = connect();
		LinkedList<Module> moduleList = new LinkedList<Module>();
		LinkedList<Long> temp = new LinkedList<Long>();
		Module module;
		query = "SELECT module.* FROM module JOIN entry ON module.moduleID = entry.moduleID "
				+ "WHERE entry.approvalstatus = TRUE AND declined = FALSE AND author = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				module = new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"),
						resultSet.getString("name"),
						resultSet.getDate("creationdate"),
						resultSet.getDate("modificationdate"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getString("instituteID"),
						resultSet.getString("subject"),
						resultSet.getString("modificationauthor"));
				// check for duplicate
				if (!temp.contains(module.getModuleID())) {
					moduleList.add(module);
					temp.add(module.getModuleID());
				}
			}
			for (Module modules : moduleList) {
				modules.setEntryList(getEntryListOfModule(modules));
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

	// tested: check
	/**
	 * Loads all rejected modules.
	 * 
	 * @return List of modules
	 * @see Module
	 */
	public List<Module> getRejectedModules() {
		Connection connection = connect();
		LinkedList<Module> moduleList = new LinkedList<Module>();
		LinkedList<Long> temp = new LinkedList<Long>();
		query = "SELECT module.* FROM module JOIN entry ON module.moduleID = entry.moduleID "
				+ "WHERE declined = TRUE";
		Module module;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				module = new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"),
						resultSet.getString("name"),
						resultSet.getDate("creationdate"),
						resultSet.getDate("modificationdate"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getString("instituteID"),
						resultSet.getString("subject"),
						resultSet.getString("modificationauthor"));
				// check for duplicate
				if (!temp.contains(module.getModuleID())) {
					moduleList.add(module);
					temp.add(module.getModuleID());
				}
			}
			for (Module modules : moduleList) {
				modules.setEntryList(getEntryListOfModule(modules));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get rejected modules.");
		} finally {
			close(connection);
		}
		return moduleList;
	}

	// tested: check
	/**
	 * Loads all rejected modules by a chosen institute.
	 * 
	 * @param instituteID		The unique ID of the institute.
	 * @return List of modules
	 * @see Module
	 */
	public List<Module> getRejectedModulesByInstitute(String instituteID) {
		Connection connection = connect();
		LinkedList<Module> moduleList = new LinkedList<Module>();
		LinkedList<Long> temp = new LinkedList<Long>();
		Module module;
		query = "SELECT module.*"
				+ "FROM module JOIN entry ON module.moduleID = entry.moduleID "
				+ "WHERE declined = TRUE AND instituteID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, instituteID);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				module = new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"),
						resultSet.getString("name"),
						resultSet.getDate("creationdate"),
						resultSet.getDate("modificationdate"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getString("instituteID"),
						resultSet.getString("subject"),
						resultSet.getString("modificationauthor"));
				// check for duplicate
				if (!temp.contains(module.getModuleID())) {
					moduleList.add(module);
					temp.add(module.getModuleID());
				}
			}
			for (Module modules : moduleList) {
				modules.setEntryList(getEntryListOfModule(modules));
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

	// tested: check
	/**
	 * Loads all rejected modules by a chosen author.
	 * 
	 * @param author		The author of the module
	 * @return List of modules
	 * @see Module
	 */
	public List<Module> getUnapprovedModulesOverviewByAuthor(String author) {
			Connection connection = connect();
			LinkedList<Module> moduleList = new LinkedList<Module>();
			query = "SELECT m.* FROM module AS m WHERE modificationauthor = ? " +
					"AND m.approvalstatus = FALSE;";
			try {
				pStatement = connection.prepareStatement(query);
				pStatement.setString(1, author);
				ResultSet resultSet = pStatement.executeQuery();
				while (resultSet.next()) {
					moduleList.add(new Module(resultSet.getLong("moduleID"),
							resultSet.getInt("version"),
							resultSet.getString("name"),
							resultSet.getDate("creationdate"),
							resultSet.getDate("modificationdate"),
							resultSet.getBoolean("approvalstatus"),
							resultSet.getString("instituteID"),
							resultSet.getString("subject"),
							resultSet.getString("modificationauthor")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Couldn't get unapproved modules by author: " + author);
			} finally {
				close(connection);
			}
			return moduleList;
	}

	/**
	 * Gets the overview of unfinished modules for the coordinator.
	 * 
	 * @return List of modules
	 * @see Module
	 */
	public List<Module> getUnfinishedModulesOverview() {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT DISTINCT * FROM module WHERE subject IS NULL";

		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"), resultSet
								.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID"), null, resultSet
								.getString("modificationauthor")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get unfinished modules.");
		} finally {
			close(connection);
		}
		return moduleList;
	}
	
	/**
	 * Gets an overview of modules for the editor.
	 * 
	 * @param instituteID		The unique ID of the institute.
	 * @return List of modules
	 * @see Module
	 */
	public List<Module> getModuleOverviewForEditor(String instituteID) {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT * FROM module WHERE approvalstatus = 0 AND instituteID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, instituteID);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"), resultSet
								.getString("name"), resultSet
								.getDate("creationDate"), resultSet
								.getDate("modificationDate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID"), resultSet
								.getString("subject"), resultSet
								.getString("modificationauthor")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get modulesoverview for editor");
		} finally {
			close(connection);
		}
		return moduleList;
	}

	// create a new complete module in database
	/**
	 * Creates a new module in the database.
	 * <p>
	 * A module consists of various parameters such as moduleID, version, name ...
	 * Furthermore, it consists of courses and their content.
	 * A study is made up of several modules.
	 * 
	 * @param module		the module which should be created
	 * @return <code>true</code> If a new module was created, <code>false</code> otherwise.
	 * @see Module
	 */
	public boolean createModule(Module module) {
		Connection connection = connect();
		List<Entry> entryList = module.getEntryList();
		CourseEntry courseEntry = null;
		TextualEntry textualEntry = null;
		EffortEntry effortEntry = null;
		try {
			// insert basic module
			int version = getHighestVersionOfModule(module.getModuleID(), connection);
			query = "INSERT INTO module(moduleID, version, name, creationdate,"
					+ " modificationdate, approvalstatus, instituteID, subject,"
					+ " modificationauthor) VALUES (?,?,?,?,?,?,?,?,?)";
			connection.setAutoCommit(false);
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, module.getModuleID());
			pStatement.setInt(2, version);
			pStatement.setString(3, module.getName());
			pStatement.setDate(4, (java.sql.Date) module.getCreationDate());
			pStatement.setDate(5, (java.sql.Date) module.getModificationDate());
			pStatement.setBoolean(6, module.isApproved());
			pStatement.setString(7, module.getInstituteID());
			pStatement.setString(8, module.getSubject());
			pStatement.setString(9, module.getModificationauthor());
			pStatement.execute();

			// insert basic entries
			query = "INSERT INTO entry(entryID, moduleID, moduleversion,"
					+ " author, classification, approvalstatus, declined,"
					+ " title, `order`) VALUES (?,?,?,?,?,?,?,?,?)";
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(2, module.getModuleID());
			pStatement.setInt(3, version);
			pStatement.setString(4, module.getModificationauthor());
			pStatement.setBoolean(5, false);
			pStatement.setBoolean(6, false);
			pStatement.setBoolean(7, false);
			for (Entry entry : entryList) {
				pStatement.setLong(1, entry.getEntryID());
				pStatement.setString(8, entry.getTitle());
				pStatement.setInt(9, entry.getOrder());
				pStatement.execute();
			}

			// insert course entry
			// find the course entry
			for (Entry entry : module.getEntryList()) {
				if (entry.getClass() == CourseEntry.class) {
					courseEntry = (CourseEntry) entry;
				}
			}
			if (courseEntry != null) {
				// insert courses
				query = "INSERT INTO courseentry VALUES (?,?,?,?)";
				pStatement = connection.prepareStatement(query);
				pStatement.setLong(1, courseEntry.getEntryID());
				for (Course course : courseEntry.getCourses()) {
					pStatement.setString(2, course.getCourseID());
					pStatement.setString(3, course.getDegree());
					pStatement.setBoolean(4, course.isObligatory());
					pStatement.execute();
				}
			}

			// insert textual entries
			query = "INSERT INTO textualentry VALUES(?,?)";
			pStatement = connection.prepareStatement(query);
			for (Entry entry : entryList) {
				if (entry.getClass() == TextualEntry.class) {
					textualEntry = (TextualEntry) entry;
					pStatement.setLong(1, textualEntry.getEntryID());
					pStatement.setString(2, textualEntry.getContent());
					pStatement.execute();
				}
			}

			// insert effort entry
			query = "INSERT INTO effortentry VALUES(?,?)";
			pStatement = connection.prepareStatement(query);
			for (Entry entry : entryList) {
				if (entry.getClass() == EffortEntry.class) {
					effortEntry = (EffortEntry) entry;
					pStatement.setLong(1, effortEntry.getEntryID());
					pStatement.setInt(2, effortEntry.getTime());
					pStatement.execute();
				}
			}
			if (effortEntry != null) {
				query = "INSERT INTO selfstudy VALUES(?,?,?,?)";
				pStatement = connection.prepareStatement(query);
				pStatement.setLong(2, effortEntry.getEntryID());
				for (SelfStudy study : effortEntry.getSelfStudyList()) {
					pStatement.setLong(1, study.getSelfstudyID());
					pStatement.setString(3, study.getTitle());
					pStatement.setInt(4, study.getTime());
					pStatement.execute();
				}
			}
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't create module: " + module.getName());
			rollback(connection);
			return false;
		} finally {
			close(connection);
		}
	}
	
	
	/**
	 * Changes a rejected module.
	 * 
	 * @param module		the module that has to be changed
	 * @return <code>true</code> if the module has been changed, <code>false</code> otherwise.
	 * @see Module
	 */
	public boolean editRejectedModule(Module module) {
		Connection connection = connect();
		List<Entry> entryList = module.getEntryList();
		CourseEntry courseEntry = null;
		TextualEntry textualEntry = null;
		EffortEntry effortEntry = null;
		query = "UPDATE module SET name = ?, modificationdate = ?, instituteID = ? WHERE moduleID = ? AND version = ?;";
		try {
			// update basic module
			connection.setAutoCommit(false);
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, module.getName());
			pStatement.setDate(2, (java.sql.Date) module.getModificationDate());
			pStatement.setString(3, module.getInstituteID());
			pStatement.setLong(4, module.getModuleID());
			pStatement.setInt(5, module.getVersion());
			pStatement.executeUpdate();

			// update basic entries
			query = "UPDATE entry SET author = ?, title = ? rejected = FALSE WHERE entryID = ? AND moduleID = ? AND moduleversion = ?";
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, module.getModificationauthor());
			pStatement.setLong(4, module.getModuleID());
			pStatement.setInt(5, module.getVersion());
			for (Entry entry : entryList) {
				pStatement.setString(2, entry.getTitle());
				pStatement.setLong(3, entry.getEntryID());
				pStatement.executeUpdate();
			}

			// update course entry
			// find the course entry
			for (Entry entry : module.getEntryList()) {
				if (entry.getClass() == CourseEntry.class) {
					courseEntry = (CourseEntry) entry;
				}
			}
			if (courseEntry != null) {
				// update courses
				query = "UPDATE courseentry SET courseID = ?, degree = ?, obligatory = ? WHERE entryID = ?";
				pStatement = connection.prepareStatement(query);
				pStatement.setLong(4, courseEntry.getEntryID());
				for (Course course : courseEntry.getCourses()) {
					pStatement.setString(1, course.getCourseID());
					pStatement.setString(2, course.getDegree());
					pStatement.setBoolean(3, course.isObligatory());
					pStatement.executeUpdate();
				}
			}

			// update textual entries
			query = "UPDATE textualentry SET text = ? WHERE entryID = ?";
			pStatement = connection.prepareStatement(query);
			for (Entry entry : entryList) {
				if (entry.getClass() == TextualEntry.class) {
					textualEntry = (TextualEntry) entry;
					pStatement.setString(1, textualEntry.getContent());
					pStatement.setLong(2, textualEntry.getEntryID());
					pStatement.executeUpdate();
				}
			}

			// update effort entry
			query = "UPDATE effortentry SET presencetime = ? WHERE entryID = ?";
			pStatement = connection.prepareStatement(query);
			for (Entry entry : entryList) {
				if (entry.getClass() == EffortEntry.class) {
					effortEntry = (EffortEntry) entry;
					pStatement.setInt(1, effortEntry.getTime());
					pStatement.setLong(2, effortEntry.getEntryID());
					pStatement.executeUpdate();
				}
			}
			if (effortEntry != null) {
				query = "UPDATE selfstudy SET title = ?, time = ? WHERE entryID = ? AND selfstudyID = ?";
				pStatement = connection.prepareStatement(query);
				pStatement.setLong(3, effortEntry.getEntryID());
				for (SelfStudy study : effortEntry.getSelfStudyList()) {
					pStatement.setString(1, study.getTitle());
					pStatement.setInt(2, study.getTime());
					pStatement.setLong(4, study.getSelfstudyID());
					pStatement.executeUpdate();
				}
			}
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't update module: " + module.getName());
			rollback(connection);
			return false;
		} finally {
			close(connection);
		}
	}
	
	
	/**
	 * Gets the courses which belong to a given faculty.
	 * 
	 * @param facultyID		The unique ID of a faculty.
	 * @return List of courses
	 * @see Course
	 */
	public List<String> getCoursesByFaculty(String facultyID) {
		Connection connection = connect();
		List<String> courses = new LinkedList<String>();
		query = "SELECT DISTINCT description FROM course JOIN faculty on course.faculty = faculty.facultyID "
				+ "WHERE faculty = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, facultyID);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				courses.add(resultSet.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get courses of faculty: " + facultyID);
		} finally {
			close(connection);
		}
		return courses;
	}

	/**
	 * Completes a new module.
	 * 
	 * @param module		The module which should be completed.
	 * @return <code>true</code> If a new module was completed, <code>false</code> otherwise.
	 * @see Module
	 */
	public boolean finishNewModule(Module module) {
		Connection connection = connect();
		CourseEntry courseEntry = null;
		query = "UPDATE module SET subject = ? WHERE moduleID = ? AND version = ?";
		try {
			connection.setAutoCommit(false);
			// set subject of unfinished module
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, module.getSubject());
			pStatement.setLong(2, module.getModuleID());
			pStatement.setInt(3, module.getVersion());
			pStatement.execute();

			// find the course entry
			for (Entry entry : module.getEntryList()) {
				if (entry.getClass() == CourseEntry.class) {
					courseEntry = (CourseEntry) entry;
				}
			}
			if (courseEntry != null) {
				// set courses of unfinished module
				// insert basic entry
				query = "INSERT INTO entry (entryID, moduleID, moduleversion,"
						+ " author, classification, approvalstatus, declined,"
						+ " title, `order`) VALUES (?,?,?,?,?,?,?,?,?)";
				pStatement = connection.prepareStatement(query);
				pStatement.setLong(1, courseEntry.getEntryID());
				pStatement.setLong(2, module.getModuleID());
				pStatement.setInt(3, module.getVersion());
				pStatement.setString(4, module.getModificationauthor());
				pStatement.setBoolean(5, false);
				pStatement.setBoolean(6, false);
				pStatement.setBoolean(7, false);
				pStatement.setString(8, courseEntry.getTitle());
				pStatement.setInt(9, courseEntry.getOrder());
				pStatement.execute();

				// insert courses
				query = "INSERT INTO courseentry VALUES (?,?,?,?)";
				pStatement = connection.prepareStatement(query);
				pStatement.setLong(1, courseEntry.getEntryID());
				for (Course course : courseEntry.getCourses()) {
					pStatement.setString(2, course.getCourseID());
					pStatement.setString(3, course.getDegree());
					pStatement.setBoolean(4, course.isObligatory());
					pStatement.execute();
				}
			}
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollback(connection);
			return false;
		} finally {
			close(connection);
		}
		return true;
	}


	/**
	 * Deletes an existing module.
	 * 
	 * @param module		The module which should be deleted
	 * @return <code>true</code> If the module has been deleted, <code>false</code> otherwise.
	 * @see Module
	 */
	public boolean deleteModule(Module module) {
		Connection connection = connect();
		query = "DELETE FROM module WHERE moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, module.getModuleID());
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't delete module: "
					+ module.getModuleID());
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * @param description
	 * @return List of PDFs
	 */
	public List<String[]> getPDFListByCourse(String description) {
		List<String[]> pdfList = new LinkedList<String[]>();
		Connection connection = connect();
		query = "SELECT c.description, c.degree, p.semester, p.url "
				+ "FROM course AS c JOIN pdfmodulemanual AS p ON c.courseID = "
				+ "p.courseID AND c.degree = p.degree WHERE c.description = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, description);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				pdfList.add(new String[] { resultSet.getString("description"),
						resultSet.getString("degree"),
						resultSet.getString("semester"),
						resultSet.getString("url") });
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't find modules");
		} finally {
			close(connection);
		}
		return pdfList;
	}

	// TODO
	/**
	 * Gets the URL of a specified modulemanual
	 * @param courseID		The unique ID of a course.
	 * @param degree
	 * @param version
	 * @return
	 */
	public String getModuleManualURL(String courseID, String degree,
			String version) {
		Connection connection = connect();
		query = "SELECT pdf.url "
				+ "FROM modulemanual AS m JOIN pdfmodulemanual AS pdf ON "
				+ "m.versionnumber = pdf.versionnumber AND m.courseID = pdf.courseID "
				+ "AND m.degree = pdf.degree "
				+ "WHERE m.courseID = ? AND m.degree = ? and m.versionnumber = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, courseID);
			pStatement.setString(2, degree);
			pStatement.setString(3, version);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("url");
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Could't find modulmanual");
		} finally {
			close(connection);
		}
		return null;
	}

	/**
	 * @param courseID
	 * @param degree
	 * @param versionnumber
	 * @return
	 */
	public List<Module> getModuleManualbyCourse(String courseID, String degree,
			String versionnumber) {
		Connection connection = connect();
		List<Module> modulemanual = new LinkedList<Module>();
		query = "SELECT m.* FROM modulemanual AS mm JOIN module AS m ON mm.versionnumber = m.modulemanual "
				+ "WHERE mm.courseID = ? AND mm.degree = ? AND mm.versionnumber = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, courseID);
			pStatement.setString(2, degree);
			pStatement.setString(3, versionnumber);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				modulemanual.add(new Module(resultSet.getLong("moduleID"),
						resultSet.getInt("version"), resultSet
								.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID"), resultSet
								.getString("subject"), resultSet
								.getString("modificationauthor")));
			}
			for (Module module : modulemanual) {
				module.setEntryList(getEntryListOfModule(module));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(connection);
		}
		return modulemanual;
	}

	/**
	 * @param course
	 * @return
	 */
	public String getCourseID(String course) {
		Connection connection = connect();
		query = "SELECT courseID FROM course WHERE description = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, course);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get courseID of course: " + course);
		} finally {
			close(connection);
		}
		return null;
	}

	public String getLastModificationDate(String courseID, String degree) {
		Connection connection = connect();
		query = "SELECT MAX(module.modificationdate) FROM module "
				+ "JOIN modulecourseaffiliation AS ma ON module.moduleID = ma.moduleID "
				+ "WHERE ma.courseID = ? AND ma.degree = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, courseID);
			pStatement.setString(2, degree);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				return "" + resultSet.getDate(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get modificationdates of moduleList");
		} finally {
			close(connection);
		}
		return null;
	}

	/**
	 * @param courseID
	 * @param degree
	 * @return
	 */
	public String getLastModificationAuthor(String courseID, String degree) {
		Connection connection = connect();
		query = "SELECT m.modificationauthor, MAX(m.modificationdate) FROM module AS m "
				+ "JOIN modulecourseaffiliation AS ma ON m.moduleID = ma.moduleID "
				+ "WHERE ma.courseID = ? AND ma.degree = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, courseID);
			pStatement.setString(2, degree);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("couldn't get last modification author of course: "
							+ courseID);
		} finally {
			close(connection);
		}
		return null;
	}

	/**
	 * @param courseID
	 * @param degree
	 * @return
	 */
	public String generateLatestVersionOfModuleManual(String courseID,
			String degree) {
		Connection connection = connect();
		LinkedList<String> versionnumbers = new LinkedList<String>();
		LinkedList<String> sSemesters = new LinkedList<String>();
		LinkedList<String> wSemesters = new LinkedList<String>();
		query = "SELECT versionnumber FROM modulemanual WHERE courseID = ? AND degree = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, courseID);
			pStatement.setString(2, degree);
			ResultSet resultSet = pStatement.executeQuery();
			// get all versionnumbers of module
			while (resultSet.next()) {
				versionnumbers.add(resultSet.getString(1));
			}
			System.out.println("(ModuleDBController) versionnumbers is empty: "
					+ versionnumbers.isEmpty());
			// separate sSemester and wSemester
			for (String version : versionnumbers) {
				if (version.charAt(0) == 's') {
					sSemesters.add(version);
				} else {
					wSemesters.add(version);
				}
			}
			// find latest sSemester version
			System.out.println("(ModuleDBController) sSemesters is empty: "
					+ sSemesters.isEmpty());
			String latestSS = sSemesters.getFirst().substring(4, 8);
			for (String version : sSemesters) {
				if (Integer.parseInt(latestSS) < Integer.parseInt(version
						.substring(4, 8))) {
					latestSS = version.substring(4, 8);
				}
			}
			// find latest wSemester version
			String latestWS = wSemesters.getFirst().substring(8, 12);
			for (String version : wSemesters) {
				if (Integer.parseInt(latestWS) < Integer.parseInt(version
						.substring(8, 12))) {
					latestWS = version.substring(8, 12);
				}
			}
			// if latest sSemester version >= latest wSemester version return
			// new wise
			if (Integer.parseInt(latestSS) >= Integer.parseInt(latestWS)) {
				return "wise" + latestSS + ""
						+ (Integer.parseInt(latestSS) + 1);
			}
			// if latest sSemester version < latest wSemester version return new
			// sose
			else {
				return "sose" + latestWS;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("couldn't get latest version of modulemanual of course: "
							+ courseID);
		} finally {
			close(connection);
		}
		return null;
	}

	/**
	 * @param courseID
	 * @param degree
	 * @return
	 */
	public LinkedList<String> getInstituteList(String courseID, String degree) {
		Connection connection = connect();
		LinkedList<String> instituteIDList = new LinkedList<String>();
		query = "SELECT DISTINCT m.instituteID FROM module AS m "
				+ "JOIN modulecourseaffiliation AS ma ON m.moduleID = ma.moduleID "
				+ "WHERE ma.courseID = ? AND ma.degree = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, courseID);
			pStatement.setString(2, degree);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				instituteIDList.add(resultSet.getString(1));
			}
			return instituteIDList;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("couldn't get institutes of modulemanual of course: "
							+ courseID + " " + degree);
		} finally {
			close(connection);
		}
		return instituteIDList;
	}

	/**
	 * @param instituteID
	 * @return
	 */
	public String getInstituteName(String instituteID) {
		Connection connection = connect();
		query = "SELECT name FROM institute WHERE instituteID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, instituteID);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("couldn't get name of institute: " + instituteID);
		} finally {
			close(connection);
		}
		return null;
	}

	/**
	 * @return
	 */
	public List<String> getSubjects() {
		List<String> subjects = new LinkedList<String>();
		Connection connection = connect();
		query = "SELECT * FROM subjects";
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				subjects.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(connection);
		}
		return subjects;
	}

	/**
	 * @param subject
	 * @return
	 */
	public boolean createSubject(String subject) {
		Connection connection = connect();
		query = "INSERT INTO subjects VALUES (?)";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, subject);
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
	 * @param subject
	 * @return
	 */
	public boolean deleteSubject(String subject) {
		Connection connection = connect();
		query = "DELETE FROM subjects WHERE name = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, subject);
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
	 * @param version
	 * @param courseID
	 * @param degree
	 * @param creationdate
	 * @param modificationdate
	 * @param approvalstatus
	 * @param examregulation
	 * @return
	 */
	public boolean createModuleMaunal(String version, String courseID,
			String degree, String creationdate, String modificationdate,
			boolean approvalstatus, int examregulation) {
		Connection connection = connect();
		query = "INSERT INTO modulemanual (versionnumber, courseID, degree, "
				+ "creationdate, modificationdate, approvalstatus, examregulation) "
				+ "VALUES (?,?,?,?,?,?,?)";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, version);
			pStatement.setString(2, courseID);
			pStatement.setString(3, degree);
			pStatement.setString(4, creationdate);
			pStatement.setString(5, modificationdate);
			pStatement.setBoolean(6, approvalstatus);
			pStatement.setInt(7, examregulation);
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't create modulemanual");
			return false;
		}
	}

	/**
	 * @param moduleID
	 * @return
	 */
	public String getModuleManualByModule(long moduleID) {
		Connection connection = connect();
		query = "SELECT modulemanual FROM module WHERE moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, moduleID);
			ResultSet resultSet = pStatement.executeQuery();
			return resultSet.getString(1);
		} catch (SQLException e) {
			System.err.println("couldn't get modulemanual of module: "
					+ moduleID);
			return "";
		} finally {
			close(connection);
		}
	}

	
	// Setzt das Fach in einem Modul
	/**
	 * @param moduleID
	 * @param version
	 * @param subject
	 * @return
	 */
	public boolean setSubjectToModule(long moduleID,int version, String subject) {
		Connection connection = connect();
		query ="UPDATE module SET subject = ? WHERE moduleID = ? AND version = ?";
		try{
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, subject);
			pStatement.setLong(2, moduleID);
			pStatement.setInt(3, version);
			pStatement.execute();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			System.err.println("Couldn't add Subject To Module");
			return false;
		}finally{
			close(connection);
		}
	}
	
	//get all courses listed in database
	/**
	 * @return
	 */
	public List<Course> getCourses() {
		Connection connection = connect();
		query  = "SELECT * FROM course";
		List<Course> courses = new LinkedList<Course>();
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				courses.add(new Course(resultSet.getString("courseID"),
						resultSet.getString("description"),
						resultSet.getString("degree"),
						resultSet.getString("faculty"), false));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Couldn't get courses");
		} finally {
			close(connection);
		}
		return courses;
	}

	//Setzt Studiengaenge in einem Modul
	// String Array wie oben
	public boolean setCoursesToModule(long moduleID, int version, List<String[]> courses) {
		return false;
	}
	
	
	/**
	 * @param module
	 * @return
	 */
	public boolean approveModuleEntries(Module module){
		Connection connection = connect();
		boolean approveModule = true;
		List<Entry> entryList = module.getEntryList();
		System.out.println("ModuleDBController approveModuleEntries");
		query = "UPDATE entry SET approvalstatus = ?, declined = ? WHERE moduleID = ? AND moduleversion = ?";
		try {
			connection.setAutoCommit(false);
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(3, module.getModuleID());
			pStatement.setInt(4, module.getVersion());
			for (Entry entry : entryList) {
				pStatement.setBoolean(1, entry.isApproved());
				pStatement.setBoolean(2, entry.isRejected());
				pStatement.executeUpdate();
				System.out.println("entry update");
			}
			
			for (Entry entry : entryList) {
				if(!entry.isApproved()){
					approveModule = false;
				}
			}
			if(approveModule){
				query = "UPDATE module SET approvalstatus = ? WHERE moduleID = ? AND version = ?";
				pStatement = connection.prepareStatement(query);	
				pStatement.setBoolean(1, approveModule);
				pStatement.setLong(2, module.getModuleID());
				pStatement.setInt(3, module.getVersion());
				pStatement.executeUpdate();
			}
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(connection);
			System.out.println("Couldn't approve entris of module "+module.getName());
			return false;
		} finally {
			close(connection);
		}
	}
	

	/**
	 * @return
	 */
	public boolean clearDatabase() {
		java.util.Date today = new java.util.Date();
		Date limit = new Date(today.getYear() - 2, today.getMonth(),
				today.getDate());
		Connection connection = connect();
		query = "SELECT moduleID, version FROM module WHERE modificationdate < ? AND (moduleID, version) NOT IN (SELECT * FROM latestmodule)";
		try {
			connection.setAutoCommit(false);
			pStatement = connection.prepareStatement(query);
			pStatement.setDate(1, limit);
			ResultSet resultSet = pStatement.executeQuery();
			query = "DELETE FROM entry WHERE moduleID = ? AND moduleversion = ?";
			while (resultSet.next()) {
				pStatement.setLong(1, resultSet.getLong("moduleID"));
				pStatement.setInt(2, resultSet.getInt("version"));
				pStatement.execute();
			}
			query = "DELETE FROM module WHERE modificationdate < ? AND (moduleID, version) NOT IN (SELECT * FROM latestmodule)";
			pStatement = connection.prepareStatement(query);
			pStatement.setDate(1, limit);
			pStatement.execute();
			connection.commit();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollback(connection);
			System.err.println("Couldn't clear Database");
			return false;
		} finally {
			close(connection);
		}
	}
	
	
	//get new Version of module
	private int getHighestVersionOfModule(long moduleID, Connection connection){
		query = "SELECT MAX(version) FROM module WHERE moduleID = "+moduleID;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				return resultSet.getInt(1)+1;
			} else {
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't detemine highest Version of module: "+moduleID);
			return 1;
		}
	}
	

	// roll back changes made in database if something went wrong
	/**
	 * @param connection
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
	 * @param connection
	 */
	public void close(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't close connection.");
		}
	}

}
