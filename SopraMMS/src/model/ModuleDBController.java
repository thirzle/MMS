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

import management.Course;
import management.CourseEntry;
import management.EffortEntry;
import management.Entry;
import management.Module;
import management.SelfStudy;
import management.TextualEntry;

/**
 * The class ModuleDBController provides a connection to the database.
 * <p>
 * It also provides a number of methods which are used to retrieve, create,
 * edit, and delete contents like modules, courses and their entries.
 * 
 * @author Max Reuter, Teresa Hirzle
 * 
 */
public class ModuleDBController {

//	// local database
//	 private static final String URL = "jdbc:mysql://127.0.0.1:3306/mms";
//	 private static final String USER = "root";
//	 private static final String PASSWORD = "";

	// db4free.net database
	private static final String URL = sysconfig.Config.system_database_url
			.getValue();
	private static final String USER = sysconfig.Config.system_database_user
			.getValue();
	private static final String PASSWORD = sysconfig.Config.system_database_password
			.getValue();
	private static final String DRIVER = sysconfig.Config.system_database_driver
			.getValue();


	private static String query = null;
	private static PreparedStatement pStatement = null;
	private static Statement statement = null;

	/**
	 * Establishes connection to database.
	 * 
	 * @return connection object.
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
	 * Loads all approved modules.
	 * <p>
	 * Gets all approved modules from the database and stores them in a
	 * {@link List} of modules.
	 * 
	 * @return List of modules.
	 * @see Module
	 */
	public List<Module> getModules() {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT * FROM module AS m JOIN latestmodule AS l "
				+ "ON m.moduleID = l.moduleID AND m.version = l.version";

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
	 * Gets a module and with his "moduleID" and his "version" it gets the
	 * appropriate entries the module consists of. These are returned in the
	 * form of a {@link Module#entryList}.
	 * 
	 * @param module
	 *            The name of the Module.
	 * @return The entryList of the Module.
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
				+ "AS e on ce.entryID = e.entryID JOIN course AS c ON "
				+ "ce.courseID = c.courseID AND ce.degree = c.degree"
				+ " WHERE e.moduleID = ? AND e.moduleversion = ?";
		try {
			// load courses of module
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, module.getModuleID());
			pStatement.setInt(2, module.getVersion());
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				courses = new CourseEntry(resultSet.getDate("date").toString(),
						resultSet.getBoolean("classification"),
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
		} finally {
			close(connection);
		}
		if (courses != null)
			module.addCourseEntry(courses);
		if (!textual.isEmpty())
			module.addTextualEntryList(textual);
		if (effort != null)
			module.addEffortEntry(effort);
		return module.getEntryList();
	}

	/**
	 * Loads all entries of a specified module with an established connection.
	 * <p>
	 * Gets a module and with his "moduleID" and his "module version" it gets
	 * the appropriate entries the module consists of. These are returned in the
	 * form of a {@link Module#entryList}.
	 * 
	 * @param module
	 *            The name of the Module.
	 * @param connection
	 *            An established Connection.
	 * @return The entryList of the Module.
	 * @see Module
	 * @see Entry
	 * @see Course
	 * @see CourseEntry
	 * @see SelfStudy
	 * @see TextualEntry
	 */
	public List<Entry> getEntryListOfModule(Module module, Connection connection) {
		CourseEntry courses = null;
		List<TextualEntry> textual = new LinkedList<TextualEntry>();
		EffortEntry effort = null;
		List<SelfStudy> selfstudy = new LinkedList<SelfStudy>();
		query = "SELECT ce.courseID, ce.degree, ce.obligatory, c.description, e.* FROM courseentry AS ce JOIN entry "
				+ "AS e on ce.entryID = e.entryID JOIN course AS c ON "
				+ "ce.courseID = c.courseID AND ce.degree = c.degree"
				+ " WHERE e.moduleID = ? AND e.moduleversion = ?";
		try {
			// load courses of module
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, module.getModuleID());
			pStatement.setInt(2, module.getVersion());
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				courses = new CourseEntry(resultSet.getDate("date").toString(),
						resultSet.getBoolean("classification"),
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
	 * @param institute
	 *            The institute of this Module.
	 * @return List of modules.
	 * @see Module
	 */

	/**
	 * Loads all approved modules by a chosen course.
	 * 
	 * @param course
	 *            Course which belongs to the inquired module
	 * @param degree
	 * @return List of modules.
	 * @see Module
	 * @see Course
	 */
	public List<Module> getModulesByCourse(String course, String degree) {
		Connection connection = connect();
		LinkedList<Module> moduleList = new LinkedList<Module>();
		query = "SELECT m.* FROM latestmodule AS l JOIN module AS m "
				+ "ON l.moduleID = m.moduleID AND l.version = m.version "
				+ "JOIN entry AS e ON m.moduleID = e.moduleID "
				+ "AND m.version = e.moduleversion JOIN courseentry AS ce "
				+ "ON e.entryID = ce.entryID WHERE ce.courseID = ? AND ce.degree = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, course);
			pStatement.setString(2, degree);
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
				module.setEntryList(getEntryListOfModule(module, connection));
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
	 *            The unique ID of a course.
	 * @return List of modules.
	 * @see Module
	 */

	// tested: check
	/**
	 * Loads all approved modules by a chosen author.
	 * <p>
	 * 
	 * @param author
	 *            The author of the modules content.
	 * @return List of modules.
	 * @see Module
	 */
	public List<Module> getModulesOverviewByAuthor(String author) {
		Connection connection = connect();
		LinkedList<Module> moduleList = new LinkedList<Module>();
		query = "SELECT m.* FROM module AS m JOIN latestmodule AS l ON "
				+ "m.moduleID = l.moduleID AND m.version = l.version "
				+ "WHERE modificationauthor = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
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
			moduleList
					.addAll(getModulesOverviewBySupervisor(author, connection));
			moduleList.addAll(getModulesOverviewByRepresentative(author,
					connection));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules by author: " + author);
		} finally {
			close(connection);
		}
		return moduleList;
	}

	/**
	 * Loads modules by the loginname of a supervisor.
	 * 
	 * @param loginname
	 *            The loginname of the supervisor.
	 * @param connection
	 *            Connection to a database.
	 * @return List of modules.
	 * @see Module
	 */
	public List<Module> getModulesOverviewBySupervisor(String loginname,
			Connection connection) {
		query = "SELECT supervisor FROM supervisor WHERE username = ?";
		String supervisor = "no supervisor found";
		LinkedList<Module> moduleList = new LinkedList<Module>();
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				supervisor = resultSet.getString("supervisor");
				query = "SELECT m.* FROM module AS m JOIN latestmodule AS l ON "
						+ "m.moduleID = l.moduleID AND m.version = l.version "
						+ "WHERE modificationauthor = ?";
				pStatement = connection.prepareStatement(query);
				pStatement.setString(1, supervisor);
				ResultSet resultSetSup = pStatement.executeQuery();
				while (resultSetSup.next()) {
					moduleList.add(new Module(resultSetSup.getLong("moduleID"),
							resultSetSup.getInt("version"), resultSetSup
									.getString("name"), resultSetSup
									.getDate("creationdate"), resultSetSup
									.getDate("modificationdate"), resultSetSup
									.getBoolean("approvalstatus"), resultSetSup
									.getString("instituteID"), resultSetSup
									.getString("subject"), resultSetSup
									.getString("modificationauthor")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules by supervisor: "
					+ supervisor);
		}
		return moduleList;
	}

	/**
	 * Loads modules by the loginname of a representative.
	 * 
	 * @param loginname
	 *            The loginname of the representative.
	 * @param connection
	 *            Connection to a database.
	 * @return List of modules.
	 * @see Module
	 */
	public List<Module> getModulesOverviewByRepresentative(String loginname,
			Connection connection) {
		query = "SELECT representative FROM user WHERE loginname = ?";
		String representative = "no representative found";
		LinkedList<Module> moduleList = new LinkedList<Module>();
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				representative = resultSet.getString("representative");
				query = "SELECT m.* FROM module AS m JOIN latestmodule AS l ON "
						+ "m.moduleID = l.moduleID AND m.version = l.version "
						+ "WHERE modificationauthor = ?";
				pStatement = connection.prepareStatement(query);
				pStatement.setString(1, representative);
				resultSet = pStatement.executeQuery();
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules by representative: "
					+ representative);
		}
		return moduleList;
	}

	// tested: check
	/**
	 * Gets all versions of specified module.
	 * 
	 * @param moduleID
	 *            The unique ID of a module.
	 * @return List of modules.
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
						resultSet.getInt("version"), resultSet
								.getString("name"), resultSet
								.getDate("creationdate"), resultSet
								.getDate("modificationdate"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getString("instituteID"), resultSet
								.getString("subject"), resultSet
								.getString("modificationauthor")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't get versions of module: " + moduleID);
		} finally {
			close(connection);
		}
		return versions;
	}

	/**
	 * Loads all modules.
	 * <p>
	 * Gets all modules from the database and stores them in a {@link List} of
	 * modules.
	 * 
	 * @return List of modules.
	 * @see Module
	 */
	public List<Module> getAllModules() {
		List<Module> moduleList = new LinkedList<Module>();
		Connection connection = connect();
		query = "SELECT * FROM module";
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

	// tested: check
	/**
	 * Gets specified module.
	 * 
	 * @param moduleID
	 *            The unique ID of the module.
	 * @param version
	 *            The modules version
	 * @return A module object.
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
			module.setEntryList(getEntryListOfModule(module, connection));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't get module: " + moduleID);
		} finally {
			close(connection);
		}
		return module;
	}


	// tested: check
	/**
	 * Loads all unapproved modules by a chosen author.
	 * 
	 * @param author
	 *            The author of the module
	 * @return List of modules.
	 * @see Module
	 */
	public List<Module> getUnapprovedModulesOverview(String author) {
		Connection connection = connect();
		LinkedList<Module> moduleList = new LinkedList<Module>();
		query = "SELECT m.* FROM module AS m WHERE m.approvalstatus = FALSE AND m.modificationauthor = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
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
			moduleList.addAll(getUnapprovedModulesOverviewBySupervisor(author,
					connection));
			moduleList.addAll(getUnapprovedModulesOverviewByRepresentative(
					author, connection));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get unapproved modules");
		} finally {
			close(connection);
		}
		return moduleList;
	}

	/**
	 * Loads unapproved modules by the loginname of a supervisor.
	 * 
	 * @param loginname
	 *            The loginname of the supervisor.
	 * @param connection
	 *            Connection to a database.
	 * @return List of modules.
	 * @see Module
	 */
	public List<Module> getUnapprovedModulesOverviewBySupervisor(
			String loginname, Connection connection) {
		query = "SELECT supervisor FROM supervisor WHERE username = ?";
		String supervisor = "no supervisor found";
		LinkedList<Module> moduleList = new LinkedList<Module>();
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				supervisor = resultSet.getString("supervisor");
				query = "SELECT m.* FROM module AS m WHERE modificationauthor = ? "
						+ "AND m.approvalstatus = FALSE";
				pStatement = connection.prepareStatement(query);
				pStatement.setString(1, supervisor);
				ResultSet resultSetSup = pStatement.executeQuery();
				while (resultSet.next()) {
					moduleList.add(new Module(resultSetSup.getLong("moduleID"),
							resultSetSup.getInt("version"), resultSetSup
									.getString("name"), resultSetSup
									.getDate("creationdate"), resultSetSup
									.getDate("modificationdate"), resultSetSup
									.getBoolean("approvalstatus"), resultSetSup
									.getString("instituteID"), resultSetSup
									.getString("subject"), resultSetSup
									.getString("modificationauthor")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("Couldn't get unapproved modules by supervisor: "
							+ supervisor);
		}
		return moduleList;
	}

	/**
	 * Loads unapproved modules by the loginname of a representative.
	 * 
	 * @param loginname
	 *            The loginname of the representative.
	 * @param connection
	 *            Connection to a database.
	 * @return List of modules.
	 * @see Module
	 */
	public List<Module> getUnapprovedModulesOverviewByRepresentative(
			String loginname, Connection connection) {
		query = "SELECT representative FROM user WHERE loginname = ?";
		String representative = "no representative found";
		LinkedList<Module> moduleList = new LinkedList<Module>();
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, loginname);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				representative = resultSet.getString("representative");
				query = "SELECT m.* FROM module AS m WHERE modificationauthor = ? "
						+ "AND m.approvalstatus = FALSE";
				pStatement = connection.prepareStatement(query);
				pStatement.setString(1, representative);
				resultSet = pStatement.executeQuery();
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("Couldn't get unapproved modules by representative: "
							+ representative);
		}
		return moduleList;
	}
	
	
	/**
	 * Loads all unapproved modules.
	 * 
	 * @return List of modules.
	 * @see Module
	 */
	public List<Module> getUnapprovedModulesOverviewForCoordinator() {
		Connection connection = connect();
		LinkedList<Module> moduleList = new LinkedList<Module>();
		query = "SELECT m.* FROM module AS m WHERE m.approvalstatus = FALSE";
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
			System.out.println("Couldn't get unapproved modules");
		} finally {
			close(connection);
		}
		return moduleList;
	}
	

	/**
	 * Gets the overview of unfinished modules for the coordinator.
	 * 
	 * @return List of modules.
	 * @see Module
	 */
	public List<Module> getUnfinishedModulesOverview() {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT * FROM module WHERE subject IS NULL AND approvalstatus = FALSE";

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
	 * Gets the overview of already finished modules for the coordinator.
	 * 
	 * @return List of modules.
	 * @see Module
	 */
	public List<Module> getFinishedModulesOverview() {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT * FROM module WHERE subject IS NOT NULL AND approvalstatus = FALSE";

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
			System.out.println("Couldn't get finished modules.");
		} finally {
			close(connection);
		}
		return moduleList;
	}
	

	/**
	 * Gets an overview of modules for the editor.
	 * 
	 * @param instituteID
	 *            The unique ID of the institute.
	 * @return List of modules.
	 * @see Module
	 */
	public List<Module> getModuleOverviewForEditor(List<String> instituteList) {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT * FROM module WHERE approvalstatus = FALSE AND subject IS NOT NULL AND instituteID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			for (String instituteID : instituteList) {
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
	 * A module consists of various parameters such as moduleID, version, name
	 * ... Furthermore, it consists of courses and their content. A study is
	 * made up of several modules.
	 * 
	 * @param module
	 *            the module which should be created.
	 * @return <code>true</code> If a new module was created, <code>false</code>
	 *         otherwise.
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
			int version = getHighestVersionOfModule(module.getModuleID(),
					connection);
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
	 * @param module
	 *            the module that has to be changed.
	 * @return <code>true</code> if the module has been changed,
	 *         <code>false</code> otherwise.
	 * @see Module
	 */
	public boolean editRejectedModule(Module module) {
		Connection connection = connect();
		List<Entry> entryList = module.getEntryList();
		CourseEntry courseEntry = null;
		TextualEntry textualEntry = null;
		EffortEntry effortEntry = null;
		query = "UPDATE module SET name = ?, modificationdate = ?, instituteID = ? WHERE moduleID = ? AND version = ?";
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

			// delete old entries
			query = "DELETE FROM entry WHERE moduleID = ? AND moduleversion = ?";
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, module.getModuleID());
			pStatement.setInt(2, module.getVersion());
			pStatement.execute();

			// insert new entries
			// insert basic entries
			query = "INSERT INTO entry(entryID, moduleID, moduleversion,"
					+ " author, classification, approvalstatus, declined,"
					+ " title, `order`) VALUES (?,?,?,?,?,?,?,?,?)";
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(2, module.getModuleID());
			pStatement.setInt(3, module.getVersion());
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
	 * @param facultyID
	 *            The unique ID of a faculty.
	 * @return List of courses.
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
	 * @param module
	 *            The module which should be completed.
	 * @return <code>true</code> If a new module was completed,
	 *         <code>false</code> otherwise.
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
			e.printStackTrace();
			rollback(connection);
			return false;
		} finally {
			close(connection);
		}
		return true;
	}

	/**
	 * Gets a list of PDFs which belong to a course.
	 * 
	 * @param description
	 * @return List of PDFs.
	 * @see Course
	 */
	public List<String[]> getPDFListByCourse(String description) {
		List<String[]> pdfList = new LinkedList<String[]>();
		Connection connection = connect();
		Calendar cal = new GregorianCalendar();
		query = "SELECT c.description, c.degree, p.semester, p.versionnumber, p.creationdate "
				+ "FROM course AS c JOIN modulemanual AS p ON c.courseID = "
				+ "p.courseID AND c.degree = p.degree WHERE c.description = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, description);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				cal.setTime(resultSet.getDate("creationdate"));
				String url = sysconfig.Config.system_pdf_path.getValue() + ""
						+ resultSet.getString("versionnumber") + ".pdf";
				System.out.println("(ModuleDBController): PDF-URL: " + url);
				pdfList.add(new String[] { resultSet.getString("description"),
						resultSet.getString("degree"),
						resultSet.getString("semester"), url,
						cal.get(cal.YEAR) + "" });
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't find modules");
		} finally {
			close(connection);
		}
		return pdfList;
	}

	

	/**
	 * Gets the course ID belonging to a specified course.
	 * 
	 * @param course
	 *            The name of the course.
	 * @return The courseID.
	 * @see Course
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



	/**
	 * Gets the name of an institute refering to an unique ID.
	 * 
	 * @param instituteID
	 *            The unique ID of a institute.
	 * @return The name of the institute.
	 * @see Module
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
	 * Gets all available subjects.
	 * 
	 * @return List of subjects.
	 * @see Module
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
			e.printStackTrace();
			System.out.println("Couldn't get subjects.");
		} finally {
			close(connection);
		}
		return subjects;
	}

	/**
	 * Creates a new subject.
	 * 
	 * @param subject
	 *            The subject a module is grouped by.
	 * @return <code>true</code> if a new subject was created <code>false</code>
	 *         otherwise.
	 * @see Module
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
			e.printStackTrace();
			System.out.println("Couldn't create subject: " + subject);
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Deletes a subject.
	 * 
	 * @param subject
	 *            The subject a module is grouped by.
	 * @return <code>true</code> if the subject was deleted <code>false</code>
	 *         otherwise.
	 * @see Module
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
			e.printStackTrace();
			System.out.println("couldn't delete subject: " + subject);
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Creates a new course.
	 * 
	 * @param course
	 *            Course object.
	 * @return <code>true</code> if a new course was created <code>false</code>
	 *         otherwise.
	 * @see Course
	 */
	public boolean createCourse(Course course) {
		Connection connection = connect();
		query = "INSERT INTO course VALUES(?,?,?,?)";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, course.getCourseID());
			pStatement.setString(2, course.getDescription());
			pStatement.setString(3, course.getDegree());
			pStatement.setString(4, course.getFaculty());
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't create course "
					+ course.getDescription());
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Deletes a course.
	 * 
	 * @param course
	 *            Course object.
	 * @return <code>true</code> if the course was deleted <code>false</code>
	 *         otherwise.
	 * @see Course
	 */
	public boolean deleteCourse(Course course) {
		Connection connection = connect();
		query = "DELETE FROM course WHERE courseID = ? AND degree = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, course.getCourseID());
			pStatement.setString(2, course.getDegree());
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't delete course: "
					+ course.getDescription());
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Creates a new ModuleManual.
	 * 
	 * @param version
	 *            The module manuals version.
	 * @param courseID
	 *            The unique ID of a course.
	 * @param degree
	 *            The degree the module manual refers to.
	 * @param creationdate
	 *            The date the module manual was created.
	 * @param modificationdate
	 *            The date the module manual was changed.
	 * @param approvalstatus
	 *            The status of approval.
	 * @param examregulation
	 *            The exam regulation the module manual belongs to.
	 * @return <code>true</code> if a new module manual was created
	 *         <code>false</code> otherwise.
	 * @see Course
	 * @see Module
	 * @see ModuleAdministration
	 */
	public boolean createModuleMaunal(String version, String url,
			String courseID, String degree, Date creationdate,
			Date modificationdate, String semester, int examregulation) {
		Connection connection = connect();
		query = "INSERT INTO modulemanual (versionnumber, url, courseID, degree, "
				+ "creationdate, modificationdate, semester, examregulation) "
				+ "VALUES (?,?,?,?,?,?,?,?)";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, version);
			pStatement.setString(2, url);
			pStatement.setString(3, courseID);
			pStatement.setString(4, degree);
			pStatement.setDate(5, creationdate);
			pStatement.setDate(6, modificationdate);
			pStatement.setString(7, semester);
			pStatement.setInt(8, examregulation);
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't create modulemanual");
			return false;
		}
	}

	/**
	 * Sets the subject in a module.
	 * 
	 * @param moduleID
	 *            The unique ID of a module.
	 * @param version
	 *            The version number of this Module.
	 * @param subject
	 *            The subject a module is grouped by.
	 * @return <code>true</code> if the subject was set <code>false</code>
	 *         otherwise.
	 * @see Module
	 */
	public boolean setSubjectToModule(long moduleID, int version, String subject) {
		Connection connection = connect();
		query = "UPDATE module SET subject = ? WHERE moduleID = ? AND version = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, subject);
			pStatement.setLong(2, moduleID);
			pStatement.setInt(3, version);
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't add Subject To Module");
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Gets all available courses in the database.
	 * 
	 * @return List of courses.
	 * @see Course
	 */
	public List<Course> getCourses() {
		Connection connection = connect();
		query = "SELECT * FROM course";
		List<Course> courses = new LinkedList<Course>();
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				courses.add(new Course(resultSet.getString("courseID"),
						resultSet.getString("description"), resultSet
								.getString("degree"), resultSet
								.getString("faculty"), false));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get courses");
		} finally {
			close(connection);
		}
		return courses;
	}

	/**
	 * Gets all courses with module manuals
	 * 
	 * @return List of courses.
	 * @see Course
	 */
	public List<Course> getCoursesExistModules() {
		Connection connection = connect();
		query = "SELECT DISTINCT c.courseID, c.description, c.degree, c.faculty FROM latestmodule lm "
				+ "join entry e on lm.moduleID=e.moduleID and lm.version=e.moduleversion "
				+ "join courseentry ce on e.entryID=ce.entryID "
				+ "join course c on ce.courseID=c.courseID and ce.degree=c.degree";
		List<Course> courses = new LinkedList<Course>();
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				courses.add(new Course(resultSet.getString("courseID"),
						resultSet.getString("description"), resultSet
								.getString("degree"), resultSet
								.getString("faculty"), false));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get courses");
		} finally {
			close(connection);
		}
		return courses;
	}

	/**
	 * Approves the module entries.
	 * 
	 * @param module
	 *            Module object.
	 * @return <code>true</code> if the entries were approved <code>false</code>
	 *         otherwise.
	 * @see Module
	 */
	public boolean approveModuleEntries(Module module) {
		Connection connection = connect();
		boolean approveModule = true;
		List<Entry> entryList = module.getEntryList();
		query = "UPDATE entry SET approvalstatus = ?, declined = ? WHERE moduleID = ? AND moduleversion = ? AND entryID = ?";
		try {
			connection.setAutoCommit(false);
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(3, module.getModuleID());
			pStatement.setInt(4, module.getVersion());
			for (Entry entry : entryList) {
				pStatement.setBoolean(1, entry.isApproved());
				pStatement.setBoolean(2, entry.isRejected());
				pStatement.setLong(5, entry.getEntryID());
				pStatement.executeUpdate();
			}

			for (Entry entry : entryList) {
				if (!entry.isApproved()) {
					approveModule = false;
				}
			}
			if (approveModule) {
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
			System.out.println("Couldn't approve entris of module "
					+ module.getName());
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Unapproves a whole module.
	 * 
	 * @param module
	 *            Module object.
	 * @return <code>true</code> if the module was deactivated
	 *         <code>false</code> otherwise.
	 * @see Module
	 */
	public boolean deactivateModule(Module module) {
		Connection connection = connect();
		query = "UPDATE module SET approvalstatus = FALSE WHERE moduleID = ? AND version = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, module.getModuleID());
			pStatement.setInt(2, module.getVersion());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't deactivate module " + module.getName()
					+ "-" + module.getVersion());
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Deletes an existing module.
	 * 
	 * @param moduleID
	 *            The unique ID of the module.
	 * @param version
	 *            The modules version
	 * @return <code>true</code> If the module has been deleted,
	 *         <code>false</code> otherwise.
	 * @see Module
	 */
	public boolean deleteModule(long moduleID, int version) {
		Connection connection = connect();
		query = "DELETE FROM module WHERE moduleID = ? AND version = ?";
		try {
			connection.setAutoCommit(false);
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, moduleID);
			pStatement.setInt(2, version);
			pStatement.execute();

			query = "DELETE FROM entry WHERE moduleID = ? AND moduleversion = ?";
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, moduleID);
			pStatement.setInt(2, version);
			pStatement.execute();
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't deletet module " + moduleID + "-"
					+ version);
			rollback(connection);
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Clears content of the database, which is older than two years and unused
	 * and unused in current modulemanuals.
	 * 
	 * @return <code>true</code> if the content was cleared <code>false</code>
	 *         otherwise.
	 */
	public boolean clearDatabase() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 2);
		Date limit = new Date(cal.getTime().getTime());
		Connection connection = connect();
		query = "SELECT moduleID, version FROM module WHERE modificationdate < ? AND "
				+ "(moduleID, version) NOT IN (SELECT * FROM latestmodule)";
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
			e.printStackTrace();
			rollback(connection);
			System.out.println("Couldn't clear Database");
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Determines the highest version of a given moduleID and returns a higher
	 * one.
	 * 
	 * @param moduleID
	 *            The unique ID of a module.
	 * @param connection
	 *            Connection object.
	 * @return
	 * @see Module
	 */
	private int getHighestVersionOfModule(long moduleID, Connection connection) {
		query = "SELECT MAX(version) FROM module WHERE moduleID = " + moduleID;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				return resultSet.getInt(1) + 1;
			} else {
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't detemine highest Version of module: "
					+ moduleID);
			return 1;
		}
	}

	/**
	 * Creates a new institute
	 * 
	 * @param instituteID
	 *            The unique ID of a institute
	 * @param instituteName
	 *            Name of the institute
	 * @param facultyID
	 *            The unique ID of a faculty
	 */
	public boolean createInstitute(String instituteID, String instituteName,
			String facultyID) {
		Connection connection = connect();
		query = "INSERT INTO institute (instituteID, name, facultyID) VALUES (?,?,?)";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, instituteID);
			pStatement.setString(2, instituteName);
			pStatement.setString(3, facultyID);
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't create institute: " + instituteID);
			return false;
		} finally {
			close(connection);
		}
	}

	/**
	 * Cancels changes made in database if something went wrong.
	 * 
	 * @param connection
	 *            Connection object.
	 */
	private void rollback(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't rollback");
		}
	}

	/**
	 * Closes the database connection.
	 * 
	 * @param connection
	 *            Connection object.
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
