package management;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import model.ModuleDBController;
import user.User;

/**
 * The ModuleAdministration class provides easy access to the data that is
 * gathered from the database.
 *  
 * @author David Lehr, Teresa Hirzle, Max Reuter
 *
 */
public class ModuleAdministration {
    
    	/**
    	 * A list of modules.
    	 */
	private List<Module> moduleList;
	
	/**
	 * The controller of the Module-database.
	 */
	private ModuleDBController moduleDBController = new ModuleDBController();

	// TODO:
	// - Modulhandbuch Titelseite Daten:
	// - Fakultaet
	// - Bachelor/Master Sudiengang(name)
	// - Pruefungsordnung
	// - letzte Aenderungen
	// - Name der Person die zuletzt bearbeitet hat...
	// - Semester des Modulhandbuchs
	// - basierend auf Revision ???

	/**
	 * Returns a list of {@link Module}s the {@link User} modified.
	 * 
	 * @see Module
	 * @see User
	 * 
	 * @param user	The {@link User} who modified the {@link Module}s.
	 * @return	The modified {@link Module}s.
	 */
	public List<Module> getModifiedModules(User user) {
		List<Module> modules = new LinkedList<Module>();
		List<Entry> entries = new LinkedList<Entry>();
		for (Module module : moduleList) {
			entries = module.getEntryList();
			for (Entry entry : entries) {
				if (entry.isApproved() && !entry.isRejected()) {
					modules.add(module);
					break;
				}
			}
		}
		return modules;
	}

	//TODO:
	/**
	 * 
	 * @param course
	 * @return
	 */
	public List<String[]> getModuleManualPdfByCourse(String course) {
		System.out.println("(ModuleAdministration): getModuleManualPdf");
		List<String[]> list = moduleDBController.getPDFListByCourse(course);
		for (String[] strings : list) {
			int year = Integer.parseInt(strings[4]);
			String semester = strings[2];
			if (semester.equals("sose")) {
				strings[2] = "SoSe " + year;
			} else if (semester.equals("wise")) {
				strings[2] = "WiSe " + year+"/"+(year+1);
			} else {
				strings [2] = "Fehler";
			}
			System.out.println("-> " + strings[1] + " " + strings[2] + " "
					+ strings[3] + " " + strings[0] + " ");
		}
		return list;
	}

	/**
	 * This functions sorts the entries of a {@link Module} by their order and returns a list
	 * of sorted entries.
	 * <p>
	 * The database returns the entries unsorted so this method can fix the order.
	 * (Used during PDF generation...)
	 * 
	 * @see Module
	 * @see Entry
	 * 
	 * @param module	The module that needs to be sorted
	 * @return		A list of sorted entries.
	 */
	public List<Entry> sortModuleEntryListByOrder(Module module) {
		LinkedList<Entry> entryList = (LinkedList<Entry>) module.getEntryList();
		Collections.sort(entryList, new Comparator<Entry>() {
			@Override
			public int compare(Entry o1, Entry o2) {
				if (o1.getOrder() < o2.getOrder()) {
					return -1;
				} else if (o1.getOrder() > o2.getOrder()) {
					return 1;
				} else {
					return 0;
				}
			}
		});
		return entryList;
	}

	/**
	 * Retrieves the entries of a module.
	 * 
	 * @see Module
	 * @see Entry
	 * 
	 * @param module	The module that should have the entries.
	 * @return		The entries of the module.
	 */
	public List<Entry> getEntryListOfModule(Module module) {
		return moduleDBController.getEntryListOfModule(module);
	}

	/**
	 * Retrieves the course ID from the course name.
	 * @param course	The course name.
	 * @return		The course ID.
	 */
	public String getCourseID(String course) {
		return moduleDBController.getCourseID(course);
	}

	/**
	 * Retrieves the names of courses, that belong to the faculty ID.
	 * @param id	The faculty ID.
	 * @return	A list of course names.
	 */
	public List<String> getCoursNamesByFacultyID(String id) {
		return moduleDBController.getCoursesByFaculty(id);
	}

	/**
	 * Retrieves all modules.
	 * @see Module
	 * @return	A list of all Modules.
	 */
	public List<Module> getModules() {
		return moduleDBController.getModules();
	}

	/**
	 * Retrieves all modules of a given course of the specified degree.
	 * @param course	The course name.
	 * @param degree	The degree.
	 * @return		A list of modules of the course.
	 */
	public List<Module> getModulesByCourse(String course, String degree) {
		return moduleDBController.getModulesByCourse(course, degree);
	}

	// public String getLastModificationDateOfModuleManual(String courseID,
	// String degree) {
	// return moduleDBController.getLastModificationDate(courseID, degree);
	// }

	// public String getLatestVersionOfModuleManual(String courseID, String
	// degree) {
	// return moduleDBController.generateLatestVersionOfModuleManual(courseID,
	// degree);
	// }

	// public LinkedList<String> getInstituteListOfModuleManual(String courseID,
	// String degree) {
	// return moduleDBController.getInstituteList(courseID, degree);
	// }

	/**
	 * Retrieves the institute name.
	 * @param instituteID	The institute ID.
	 * @return		The institute name.
	 */
	public String getInstituteName(String instituteID) {
		return moduleDBController.getInstituteName(instituteID);
	}
	
	//TODO:
	//mehr info (was das teil hier macht) ist nich schlecht...

	/**
	 * Creates a module manual.
	 * 
	 * @param version		The version.
	 * @param url			The url.
	 * @param courseID		The course ID.
	 * @param degree		The degree.
	 * @param creationdate		The creation date.
	 * @param modificationdate	The modification date.
	 * @param semester		The semester.
	 * @param examregulation	The exam regulation.
	 */
	public void createModuleManual(String version, String url, String courseID,
			String degree, java.sql.Date creationdate,
			java.sql.Date modificationdate, String semester, int examregulation) {
		moduleDBController.createModuleMaunal(version, url, courseID, degree,
				creationdate, modificationdate, semester, examregulation);
	}

	// TODO
	/*
	 * Beispiel um den veralteten Konstruktor zu umgehen....
	 * 
	 * Calendar cal = Calendar.getInstance(); cal.set(Calendar.YEAR, 1988);
	 * cal.set(Calendar.MONTH, 1); cal.set(Calendar.DAY_OF_MONTH, 1); Date
	 * dateRepresentation = cal.getTime();
	 */

	//TODO:
	//deprecation fix noch machen...
	
	/**
	 * Creates the module of the given author.
	 * 
	 * @see Module
	 * @see Entry
	 * 
	 * @param list		The list of module entries.
	 * @param author	The author.
	 * @param institut	The institute.
	 */
	public void createModuleByModuleManager(List<Entry> list, String author,
			String institut) {
		String name = list.get(1).getContent();
		Date d = new Date();
		java.sql.Date creationDate = new java.sql.Date(d.getYear(),
				d.getMonth(), d.getDate());
		java.sql.Date modificationDate = new java.sql.Date(d.getYear(),
				d.getMonth(), d.getDate());
		boolean approved = false;
		String insituteID = institut;
		List<Entry> entryList = list;
		String subject = null;
		String modificationauthor = author;
		Module module = new Module(name, creationDate, modificationDate,
				approved, insituteID, entryList, subject, modificationauthor);
		module.print();
		moduleDBController.createModule(module);
	}

	
	//TODO:
	//deprecation fix noch machen...
	/**
	 * Creates the module of the given module manager.
	 * 
	 * @see Module
	 * 
	 * @param module	The module.
	 * @param author	The author/module manager.
	 * @param creationdate	The creation date.
	 * @param version	The version.
	 */
	public void createModuleByModuleManager(Module module, String author,
			Date creationdate, int version) {
		System.out.println("(ModulAdministration.java): Subject 3: "
				+ module.getSubject());
		String name = module.getEntryList().get(1).getContent();
		Date d = new Date();
		java.sql.Date modificationDate = new java.sql.Date(d.getYear(),
				d.getMonth(), d.getDate());
		boolean approved = false;
		String insituteID = module.getInstituteID();
		List<Entry> entryList = module.getEntryList();
		for (Entry entry : entryList) {
			if (entry.getClass().equals(CourseEntry.class)) {
				entry.setNewEntryID();
			}
		}
		String subject = module.getSubject();
		String modificationauthor = author;
		Module moduleFinished = new Module(module.getModuleID(), version, name,
				creationdate, modificationDate, approved, insituteID,
				entryList, subject, modificationauthor);
		moduleFinished.print();
		System.out.println("(ModulAdministration.java): Subject 4: "
				+ moduleFinished.getSubject());
		moduleDBController.createModule(moduleFinished);
	}

	/**
	 * Retrieves the module by a given ID.
	 * 
	 * @see Module
	 * 
	 * @param moduleID	The module ID.
	 * @param version	The version of the module.
	 * @return		The module.
	 */
	public Module getModuleByID(long moduleID, int version) {
		Module module = moduleDBController.getModule(moduleID, version);
		module.setEntryList(sortModuleEntryListByOrder(module));
		return module;
	}

	// public String getModuleManual(long moduleID) {
	// return moduleDBController.getModuleManualByModule(moduleID);
	// }

	/**
	 * Retrieves all modules of a given author.
	 * 
	 * @see Module
	 * 
	 * @param loginname	The login name of the author.
	 * @return		A list of modules the author is responsible for.
	 */
	public List<Module> getModulesByAuthor(String loginname) {
		return moduleDBController.getModulesOverviewByAuthor(loginname);
	}

	/**
	 * Retrieves all unapproved modules of a given author.
	 * 
	 * @see Module
	 * 
	 * @param loginname	The login name of the author.
	 * @return		A list of all unapproved modules the author is responsible for.
	 */
	public List<Module> getUnapprovedModules(String loginname) {
		return moduleDBController.getUnapprovedModulesOverview(loginname);
	}

	/**
	 * Retrieves a list of modules filled with all different versions of the module.
	 * 
	 * @see Module
	 * 
	 * @param moduleID	The module ID.
	 * @return		A list of all module versions.
	 */
	public List<Module> getVersionsOfModule(long moduleID) {
		return moduleDBController.getAllVersionsOfModule(moduleID);
	}

	//TODO:
	//not sure if ...
	/**
	 * Retrieves unfinished overviewed/reviewed modules as a list.
	 * 
	 * @see Module
	 * 
	 * @return	A list with unfinished overviewed/reviewed modules.
	 */
	public List<Module> getUnfinishedModulesOverview() {
		return moduleDBController.getUnfinishedModulesOverview();
	}

	//TODO:
	//not sure if ...
	/**
	 * Retrieves latest overviewed/reviewed modules as a list.
	 * 
	 * @Module
	 * 
	 * @return	A list of the latest overviewed/reviewed modules.
	 */
	public List<Module> getLatestModulesOverview() {
		return moduleDBController.getModules();
	}

	/**
	 * Retrieves a list of modules for the overview/review editor.
	 * 
	 * @see Module
	 * 
	 * @param instituteList		A list of institutes.
	 * @return			A list of modules for the overview/review editor
	 */
	public List<Module> getModuleOverviewForEditor(List<String> instituteList) {
		return moduleDBController.getModuleOverviewForEditor(instituteList);
	}

	/**
	 * Retrieves a list of all subjects.
	 * 
	 * @return A list of all subjects.
	 */
	public List<String> getSubjects() {
		return moduleDBController.getSubjects();
	}

	/**
	 * Adds a subject to the database.
	 * 
	 * @param subject 	The subject that is going to be added to the database.
	 */
	public void addSubject(String subject) {
		moduleDBController.createSubject(subject);
	}

	/**
	 * Adds a course to the database.
	 * 
	 * @see Course
	 * 
	 * @param course	The course that is going to be added to the database.
	 */
	public void addCourse(Course course) {
		moduleDBController.createCourse(course);
	}

	/**
	 * Adds the given subject to the module.
	 * 
	 * @param moduleID	The module ID.
	 * @param version	The version.
	 * @param subject	The subject.
	 */
	public void setSubjectToModule(long moduleID, int version, String subject) {
		moduleDBController.setSubjectToModule(moduleID, version, subject);
	}

	/**
	 * Retrieves all courses as a list.
	 * 
	 * @see Course
	 * 
	 * @return 	A list of all courses.
	 */
	public List<Course> getCourses() {
		return moduleDBController.getCourses();
	}

	//TODO:
	//dafuq does it do.
	/**
	 * 
	 * @param module
	 */
	public void setCoursesToModule(Module module) {
		moduleDBController.finishNewModule(module);
	}

	//TODO:
	//i have no idea what it's doing ...
	/**
	 * 
	 * @param module
	 */
	public void changeEntryListOfModule(Module module) {
		moduleDBController.approveModuleEntries(module);
	}

	/**
	 * Deactivates the given module.
	 * 
	 * @see Module
	 * 
	 * @param module A module.
	 */
	public void deactivateModule(Module module) {
		moduleDBController.deactivateModule(module);
	}

	/**
	 * Clears the entire database.
	 */
	public void clearDatabase() {
		moduleDBController.clearDatabase();
	}
		
	/**
	 * Retrieves a list of modules of the specified subject and degree with sorted entries.
	 * 
	 * @see Module
	 * @see Entry
	 * 
	 * @param subject	The subject.
	 * @param degree	The degree.
	 * @return		A list of modules of the specified subject and degree with sorted entries.
	 */
	public List<Module> getSortedModulesByCourse(String subject, String degree){   
	    List<Module> module_list =  moduleDBController.getModulesByCourse(subject, degree);
	    
	    List<Module> module_list_final = new LinkedList<Module>();
	    for(int i = 0; i < module_list.size(); i++){
		Module m = module_list.get(i);
		m.setEntryList(sortModuleEntryListByOrder(m));	
		module_list_final.add(m);
	    }
	    
	    return module_list_final;
	}
	
	/**
	 * Retrieves all modules as a list.
	 * 
	 * @see Module
	 * 
	 * @return	All modules as a list.
	 */
	public List<Module> getAllModules(){
		return moduleDBController.getAllModules();
	}
	
	/**
	 * Deletes a module in the database.
	 * 
	 * @see Module
	 * 
	 * @param moduleID	The module ID.
	 * @param version	the version.
	 */
	public void deleteModule(long moduleID,int version)
	{
		moduleDBController.deleteModule(moduleID, version);
	}
	
	//TODO:
	//no idea...
	/**
	 * 
	 * @param module
	 */
	public void editModule(Module module){
		moduleDBController.editRejectedModule(module);
	}
	
	/**
	 * Creates a new institute entry in the database.
	 * 
	 * @param instituteID		The institute ID.
	 * @param managerLoginname	The manager's loginname.
	 * @param facultyID		The faculty ID.
	 * @return			A boolean set to true if successfully created ???????
	 */
	public boolean createInstitute(String instituteID, String managerLoginname, String facultyID){
		return moduleDBController.createInstitute(instituteID, managerLoginname, facultyID);
	}
}
