package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.tomcat.jni.OS;

import user.User;

import management.CourseEntry;
import management.EffortEntry;
import management.Entry;
import management.Module;
import management.SelfStudy;
import management.TextualEntry;

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

	// load all available modules
	public List<Module> getModules() {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		List<Entry> entryList = new LinkedList<Entry>();
		query = "SELECT * FROM module";

		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			// get all entries except of course entries
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getInt("moduleID"), 
						resultSet.getInt("version"),
						resultSet.getString("name"), resultSet
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
		for (Module module : moduleList) {
			module.setEntryList(getEntryListOfModule(module));
		}
		return moduleList;
	}

	// load all entries of a specified module
	public List<Entry> getEntryListOfModule(Module module) {
		CourseEntry course = getCourseEntryByModule(module);
		List<TextualEntry> textual = getTextualEntryByModule(module);
		EffortEntry effort = getEffortEntryByModule(module);
		if (course != null)
			module.addCourseEntry(getCourseEntryByModule(module));
		if (!textual.isEmpty())
			module.addTextualEntryList(getTextualEntryByModule(module));
		if (effort != null)
			module.addEffortEntry(getEffortEntryByModule(module));
		return module.getEntryList();
	}

	// load all available modules by a chosen institute
	// tested: check
	public List<Module> getModulesByInstitute(String institute) {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.* FROM module WHERE instituteID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, institute);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getInt("moduleID"), 
						resultSet.getInt("version"),
						resultSet.getString("name"), resultSet
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

	// load all available modules by a chosen course
	// TODO
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
				module = new Module(resultSet.getInt("moduleID"), 
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

	// load all available modules by a chosen faculty
	// tested: check
	public List<Module> getModulesByFaculty(String faculty) {
		Connection connection = connect();
		List<Module> moduleList = new LinkedList<Module>();
		query = "SELECT module.* FROM modulefacultyaffiliation JOIN module "
				+ "ON modulefacultyaffiliation.moduleID = module.moduleID WHERE facultyID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, faculty);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				moduleList.add(new Module(resultSet.getInt("moduleID"),
						resultSet.getInt("version"),
						resultSet.getString("name"), resultSet
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
			System.out.println("Couldn't get modules by faculty: " + faculty);
		} finally {
			close(connection);
		}
		return moduleList;
	}

	// load all available modules by a chosen author
	// tested: check
	public List<Module> getModulesByAuthor(String author) {
		Connection connection = connect();
		LinkedList<Module> moduleList = new LinkedList<Module>();
		LinkedList<Long> temp = new LinkedList<Long>();
		Module module;
		query = "SELECT module.*, entry.author FROM module JOIN entry ON module.moduleID = entry.moduleID"
				+ " WHERE entry.author = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				module = new Module(resultSet.getInt("moduleID"),
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
			for (Module modules : moduleList) {
				modules.setEntryList(getEntryListOfModule(modules));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't get modules by author: " + author);
		} finally {
			close(connection);
		}
		return moduleList;
	}

	// get a specified module
	// tested: check
	public Module getModule(int moduleID) {
		Connection connection = connect();
		Module module = null;
		query = "SELECT * FROM module WHERE moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, moduleID);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				module = new Module(resultSet.getInt("moduleID"),
						resultSet.getInt("version"),
						resultSet.getString("name"),
						resultSet.getDate("creationdate"),
						resultSet.getDate("modificationdate"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getString("instituteID"),
						resultSet.getString("subject"),
						resultSet.getString("modificationauthor"));
			}
			if (module != null)
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
				module = new Module(resultSet.getInt("moduleID"),
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

	// load all modified modules by a chosen institute
	// tested: check
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
				module = new Module(resultSet.getInt("moduleID"),
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

	// load all modified modules by a chosen author
	// tested: check
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
				module = new Module(resultSet.getInt("moduleID"),
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

	// load all rejected modules
	// tested: check
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
				module = new Module(resultSet.getInt("moduleID"),
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

	// load all rejected modules by a chosen institute
	// tested: check
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
				module = new Module(resultSet.getInt("moduleID"),
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

	// load all rejected modules (entries) by a chosen author
	// tested: check
	public List<Module> getRejectedModulesByAuthor(String author) {
		Connection connection = connect();
		LinkedList<Module> moduleList = new LinkedList<Module>();
		LinkedList<Long> temp = new LinkedList<Long>();
		Module module;
		query = "SELECT module.* FROM module JOIN entry ON module.moduleID = entry.moduleID "
				+ "WHERE declined = TRUE AND author = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				module = new Module(resultSet.getInt("moduleID"),
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
			for (Module modules : moduleList) {
				modules.setEntryList(getEntryListOfModule(modules));
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
	//TODO
	// create a new module in database
	public boolean createModuleByModuleManager(Module module) {
		Connection connection = connect();
		List<Entry> entryList = new LinkedList<Entry>();
		query = "INSERT INTO module(moduleID, version, name, creationdate," +
				" modificationdate, approvalstatus, instituteID," +
				" modificationauthor) VALUES (?,?,?,?,?,?,?,?);";
		try {
			//insert basic module
			connection.setAutoCommit(false);
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, module.getModuleID());
			pStatement.setInt(2, module.getVersion());
			pStatement.setString(3, module.getName());
			pStatement.setDate(4, (java.sql.Date) module.getCreationDate());
			pStatement.setDate(5, (java.sql.Date) module.getModificationDate());
			pStatement.setBoolean(6, module.isApproved());
			pStatement.setString(7, module.getInstituteID());
			pStatement.setString(8, module.getModificationauthor());
			pStatement.execute();
			
			
			//insert basic entries
			query = "INSERT INTO entry(entryID, version, moduleID, moduleversion," +
					" author, date, classification, approvalstatus, declined," +
					" title) VALUES (?,?,?,?,?,?,?,?)";
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(2, 1);
			pStatement.setLong(3, module.getModuleID());
			pStatement.setInt(4, module.getVersion());
			pStatement.setString(5, module.getModificationauthor());
			pStatement.setDate(6, (Date) module.getCreationDate());
			pStatement.setBoolean(7, false);
			pStatement.setBoolean(8, false);
			pStatement.setBoolean(9, false);
			for (Entry entry : entryList) {
				pStatement.setLong(1, entry.getEntryID());
				pStatement.setString(10, entry.getTitle());
				pStatement.execute();
			}
			
			//insert textual entries
			TextualEntry te;
			query = "INSERT INTO textualentry VALUES(?,?,?)";
			pStatement = connection.prepareStatement(query);
			for (Entry entry : entryList) {
				if(entry.getClass()==TextualEntry.class){
					te = (TextualEntry) entry;
					pStatement.setLong(1, te.getEntryID());
					pStatement.setInt(2, te.getVersion());
					pStatement.setString(3, te.getContent());
					pStatement.execute();
				}
			}
			
			//insert effort entry
			EffortEntry ee = null;
			query = "INSERT INTO effortentry VALUES(?,?,?)";
			pStatement = connection.prepareStatement(query);
			for (Entry entry : entryList) {
				if(entry.getClass()==EffortEntry.class){
					ee = (EffortEntry) entry;
					pStatement.setLong(1, ee.getEntryID());
					pStatement.setInt(2, ee.getVersion());
					pStatement.setInt(3, ee.getTime());
				}
			}
			query = "INSERT INTO selfstudy VALUES(?,?,?,?,?)";
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(2, ee.getEntryID());
			pStatement.setInt(3, ee.getVersion());
			for (SelfStudy study : ee.getSelfStudyList()) {
				pStatement.setLong(1, study.getSelfstudyID());
				pStatement.setString(4, study.getTitle());
				pStatement.setInt(5, study.getTime());
				pStatement.execute();
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

	// change an existing module
	public boolean changeModule(Module module, int moduleIDOld) {
		Connection connection = connect();
		long moduleID = module.getModuleID();
		String name = module.getName();
		Date creationDate = (Date) module.getCreationDate();
		Date modificationDate = (Date) module.getModificationDate();
		boolean approved = module.isApproved();
		String instituteID = module.getInstituteID();

		query = "UPDATE module"
				+ "SET moduleID = ?, name = ?, creationDate = ?, modificationDate = ?, approved = ?, instituteID = ?"
				+ "WHERE moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, moduleID);
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
			return false;
		} finally {
			close(connection);
		}

	}

	// delete an existing module
	public boolean deleteModule(Module module) {
		Connection connection = connect();
		query = "DELETE FROM module WHERE moduleID = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, module.getModuleID());
			return pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't delete module: "
					+ module.getModuleID());
			return false;
		} finally {
			close(connection);
		}
	}

	public List<TextualEntry> getTextualEntryByModule(Module m) {
		List<TextualEntry> entries = new LinkedList<TextualEntry>();
		Connection connection = connect();
		query = "SELECT e.*, t.text "
				+ "FROM entry AS e JOIN latestentry AS l ON e.entryID = l.entryID "
				+ "AND e.version = l.version JOIN textualentry AS t ON "
				+ "e.entryID = t.entryID AND e.version = t.version "
				+ "WHERE moduleID = ? AND modulversion = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, m.getModuleID());
			pStatement.setInt(2, m.getVersion());
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				entries.add(new TextualEntry(resultSet.getInt("version"),
						resultSet.getDate("date").toString(), resultSet
								.getBoolean("classification"), resultSet
								.getBoolean("approvalstatus"), resultSet
								.getBoolean("declined"), resultSet
								.getInt("entryID"), resultSet
								.getString("title"), resultSet
								.getString("text")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't load textual entries");
		} finally {
			close(connection);
		}
		return entries;
	}

	public CourseEntry getCourseEntryByModule(Module m) {
		CourseEntry courses = null;
		Connection connection = connect();
		query = "SELECT c.courseID, e.*"
				+ " FROM courseentry AS c JOIN latestentry AS l"
				+ " ON c.entryID = l.entryID AND c.version = l.version JOIN entry "
				+ "AS e on c.entryID = e.entryID AND c.version = e.version"
				+ " WHERE e.moduleID = ? AND e.moduleversion = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, m.getModuleID());
			pStatement.setInt(2, m.getVersion());
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				courses = new CourseEntry(resultSet.getInt("version"),
						resultSet.getTimestamp("timestamp").toString(),
						resultSet.getBoolean("classification"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getBoolean("declined"),
						resultSet.getInt("entryID"),
						resultSet.getString("title"),
						resultSet.getString("courseID"));
			}
			while (resultSet.next()) {
				courses.addCourse(resultSet.getString("courseID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't get courseentry by module: " + m);
		} finally {
			close(connection);
		}

		return courses;
	}

	public EffortEntry getEffortEntryByModule(Module m) {
		EffortEntry effort = null;
		List<SelfStudy> selfstudy = null;
		Connection connection = connect();
		query = "SELECT e.*,  ef.presencetime "
				+ "FROM entry AS e JOIN effortentry AS ef ON e.entryID = ef.entryID"
				+ " AND e.version = ef.version JOIN latestentry AS l ON "
				+ "ef.entryID = l.entryID AND ef.version = l.version " +
				"WHERE e.moduleID = ? AND e.moduleversion = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setLong(1, m.getModuleID());
			pStatement.setInt(2, m.getVersion());
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				effort = new EffortEntry(resultSet.getInt("version"), resultSet
						.getDate("date").toString(),
						resultSet.getBoolean("classification"),
						resultSet.getBoolean("approvalstatus"),
						resultSet.getBoolean("declined"),
						resultSet.getInt("entryID"),
						resultSet.getString("title"),
						resultSet.getInt("presencetime"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't load effortentry");
		}
		if (effort != null) {
			query = "SELECT s.selfstudyID, s.time, s.title "
					+ "FROM entry AS e JOIN selfstudy AS s ON e.entryID = s.entryID"
					+ " AND e.version = s.version JOIN latestentry AS l ON "
					+ "s.entryID = l.entryID AND s.version = l.version "
					+ "WHERE e.moduleID = ?";
			try {
				pStatement = connection.prepareStatement(query);
				pStatement.setLong(1, m.getModuleID());
				ResultSet resultSet = pStatement.executeQuery();
				selfstudy = new LinkedList<SelfStudy>();
				while (resultSet.next()) {
					selfstudy.add(new SelfStudy(
							resultSet.getInt("selfstudyID"), resultSet
									.getInt("time"), resultSet
									.getString("title")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Couldn't load selfstudies");
			}
			close(connection);
			effort.setSelfStudyList(selfstudy);
		}
		return effort;
	}

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
	// get URL of a specified modulemanual
	public String getModuleManualURL(String courseID, String degree, String version) {
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
	
	
	public List<Module> getModuleManualbyCourse(String courseID, String degree, String versionnumber){
		Connection connection = connect();
		List<Module> modulemanual = new LinkedList<Module>();
		query = "SELECT m.* FROM modulemanual AS mm JOIN module AS m ON mm.versionnumber = m.modulemanual " +
				"WHERE mm.courseID = ? AND mm.degree = ? AND mm.versionnumber = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, courseID);
			pStatement.setString(2, degree);
			pStatement.setString(3, versionnumber);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				modulemanual.add(new Module(resultSet.getInt("moduleID"), 
						resultSet.getInt("version"),
						resultSet.getString("name"), 
						resultSet.getDate("creationdate"), 
						resultSet.getDate("modificationdate"), 
						resultSet.getBoolean("approvalstatus"), 
						resultSet.getString("instituteID"), 
						resultSet.getString("subjectname"), 
						resultSet.getString("modificationauthor")));
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
			System.out.println("(ModuleDBController) versionnumbers is empty: "+ versionnumbers.isEmpty());
			// separate sSemester and wSemester
			for (String version : versionnumbers) {
				if (version.charAt(0) == 's') {
					sSemesters.add(version);
				} else {
					wSemesters.add(version);
				}
			}
			// find latest sSemester version
			System.out.println("(ModuleDBController) sSemesters is empty: "+ sSemesters.isEmpty());
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

	public LinkedList getInstituteList(String courseID, String degree) {
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
	
	
	public boolean createSubject(String subject){
		Connection connection = connect();
		query = "INSERT INTO subjects VALUES (?)";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, subject);
			return pStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			close(connection);
		}
	}
	
	
	public boolean deleteSubject(String subject){
		Connection connection = connect();
		query = "DELETE FROM subjects WHERE name = ?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, subject);
			return pStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			close(connection);
		}
	}
	

	public void createModuleMaunal(String version, String courseID,
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
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("couldn't create modulemanual");
		}
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
	

	public void close(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't close connection.");
		}
	}

}
