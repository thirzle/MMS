package management;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import model.ModuleDBController;
import user.User;

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

	public List<Entry> getEntryListOfModule(Module module) {
		ArrayList<Entry> entryList = (ArrayList<Entry>) moduleDBController.getEntryListOfModule(module);
		boolean unsorted = true;
		Entry left, right;
		while (unsorted){
			unsorted = false;
			for (int i=0; i < entryList.size(); i++) {
				left = entryList.get(i);
	         	right = entryList.get(i+1);
	            if (left.getOrder() > right.getOrder()) {                      
	               unsorted = true;
	               entryList.add(i, right);
	               entryList.add(i+1, left);
	            }
			}
		}	
		return entryList;
	}

	public String getCourseID(String course) {
		return moduleDBController.getCourseID(course);
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

	public LinkedList getInstituteListOfModuleManual(String courseID,
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

	public void createModuleByModuleManager(List<Entry> list, String author,
			String institut) {
		String name = list.get(1).getContent();
		Date d = new Date();
		java.sql.Date creationDate = new java.sql.Date(d.getYear(),
				d.getMonth(), d.getDay());
		java.sql.Date modificationDate = new java.sql.Date(d.getYear(),
				d.getMonth(), d.getDay());
		boolean approved = false;
		String insituteID = institut;
		List<Entry> entryList = list;
		String subject = null;
		String modificationauthor = author;
		Module module = new Module(name, creationDate, modificationDate,
				approved, insituteID, entryList, subject, modificationauthor);
		module.print();
		moduleDBController.createModuleByModuleManager(module);
	}
	
	public void createModuleByModuleManager(List<Entry> list, String author,
			String institut, Date creationdate, int version, long moduleID) {
		String name = list.get(1).getContent();
		Date d = new Date();
		java.sql.Date modificationDate = new java.sql.Date(d.getYear(),
				d.getMonth(), d.getDay());
		boolean approved = false;
		String insituteID = institut;
		List<Entry> entryList = list;
		String subject = null;
		String modificationauthor = author;
		Module module = new Module(moduleID, version, name, creationdate, modificationDate, approved, insituteID, entryList, subject, modificationauthor);
		module.print();
		moduleDBController.createModuleByModuleManager(module);
	}

	public Module getModuleByID(long moduleID) {
		return moduleDBController.getLatestModule(moduleID);
	}

	public String getModuleManual(long moduleID) {
		return moduleDBController.getModuleManualByModule(moduleID);
	}

	public List<Module> getModulesByAuthor(String loginname) {
		return moduleDBController.getModulesOverviewByAuthor(loginname);
	}

	public List<Module> getUnfinishedModulesOverview() {
		return moduleDBController.getUnfinishedModulesOverview();
	}
}
