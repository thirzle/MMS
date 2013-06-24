package management;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import model.ModuleDBController;
import user.User;

/**
 * The ModuleAdministration class provides easy access to the data that is gathered
 * from the database.
 * 
 * @author name here...
 *
 */
public class ModuleAdministration {
	List<Module> moduleList;
	ModuleDBController moduleDBController = new ModuleDBController();

	// TODO:
	// - Modulhandbuch Titelseite Daten:
	// - Fakultaet
	// - Bachelor/Master Sudiengang(name)
	// - Pruefungsordnung
	// - letzte Aenderungen
	// - Name der Person die zuletzt bearbeitet hat...
	// - Semester des Modulhandbuchs
	// - basierend auf Revision ???

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

	public List<String[]> getModuleManualPdfByCourse(String course) {
		List<String[]> list = moduleDBController.getPDFListByCourse(course);
		for (String[] strings : list) {
			System.out.println("-> " + strings[1] + " " + strings[2] + " "
					+ strings[3] + " " + strings[0] + " ");
		}
		return list;
	}

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

	public List<Entry> getEntryListOfModule(Module module) {
		return moduleDBController.getEntryListOfModule(module);
	}

	public String getCourseID(String course) {
		return moduleDBController.getCourseID(course);
	}

	public List<String> getCoursNamesByFacultyID(String id) {
		return moduleDBController.getCoursesByFaculty(id);
	}

	public List<Module> getModules() {
		return moduleDBController.getModules();
	}

	public List<Module> getModulesByCourse(String course, String degree) {
		return moduleDBController.getModulesByCourse(course, degree);
	}

	public String getLastModificationDateOfModuleManual(String courseID,
			String degree) {
		return moduleDBController.getLastModificationDate(courseID, degree);
	}

	public String getLastModificationAuthor(String courseID, String degree) {
		return moduleDBController.getLastModificationAuthor(courseID, degree);
	}

	public String getLatestVersionOfModuleManual(String courseID, String degree) {
		return moduleDBController.generateLatestVersionOfModuleManual(courseID,
				degree);
	}

	public LinkedList<String> getInstituteListOfModuleManual(String courseID,
			String degree) {
		return moduleDBController.getInstituteList(courseID, degree);
	}

	public String getInstituteName(String instituteID) {
		return moduleDBController.getInstituteName(instituteID);
	}

	public void createModuleManual(String version, String courseID,
			String degree, String creationdate, String modificationdate,
			boolean approvalstatus, int examregulation) {
		moduleDBController.createModuleMaunal(version, courseID, degree,
				creationdate, modificationdate, approvalstatus, examregulation);
	}

	// TODO
	/*
	 * Beispiel um den veralteten Konstruktor zu umgehen....
	 * 
	 * Calendar cal = Calendar.getInstance(); cal.set(Calendar.YEAR, 1988);
	 * cal.set(Calendar.MONTH, 1); cal.set(Calendar.DAY_OF_MONTH, 1); Date
	 * dateRepresentation = cal.getTime();
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

	public Module getModuleByID(long moduleID, int version) {
		Module module = moduleDBController.getModule(moduleID, version);
		module.setEntryList(sortModuleEntryListByOrder(module));
		return module;
	}

	public String getModuleManual(long moduleID) {
		return moduleDBController.getModuleManualByModule(moduleID);
	}

	public List<Module> getModulesByAuthor(String loginname) {
		return moduleDBController.getModulesOverviewByAuthor(loginname);
	}

	public List<Module> getUnapprovedModulesByAuthor(String loginname) {
		return moduleDBController
				.getUnapprovedModulesOverviewByAuthor(loginname);
	}

	public List<Module> getVersionsOfModule(long moduleID) {
		return moduleDBController.getAllVersionsOfModule(moduleID);
	}

	public List<Module> getUnfinishedModulesOverview() {
		return moduleDBController.getUnfinishedModulesOverview();
	}

	public List<Module> getLatestModulesOverview() {
		return moduleDBController.getModules();
	}

	public List<Module> getModuleOverviewForEditor(String instituteID) {
		return moduleDBController.getModuleOverviewForEditor(instituteID);
	}

	public List<String> getSubjects() {
		return moduleDBController.getSubjects();
	}

	public void addSubject(String subject) {
		moduleDBController.createSubject(subject);
	}

	public void addCourse(Course course) {
		moduleDBController.createCourse(course);
	}

	public void setSubjectToModule(long moduleID, int version, String subject) {
		moduleDBController.setSubjectToModule(moduleID, version, subject);

	}

	public List<Course> getCourses() {
		return moduleDBController.getCourses();
	}

	public void setCoursesToModule(Module module) {
		moduleDBController.finishNewModule(module);
	}

	public void changeEntryListOfModule(Module module) {
		moduleDBController.approveModuleEntries(module);
	}

	public void deactivateModule(Module module) {
		moduleDBController.deactivateModule(module);
	}

	public void clearDatabase() {
		moduleDBController.clearDatabase();
	}
		
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
}
