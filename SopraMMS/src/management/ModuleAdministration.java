package management;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.ModuleDBController;


import user.User;

public class ModuleAdministration {
	List<Module> moduleList;
	ModuleDBController moduleDBController = new ModuleDBController();
	
	//TODO:
	// - Modulhandbuch Titelseiten Daten:
	//	- Fakultaet
	//	- Bachelor/Master Sudiengang(name)
	//	- Pruefungsordnung
	//	- letzte Aenderungen
	//	- Name der Person die zuletzt bearbeitet hat...
	//	- Semester des Modulhandbuchs
	//	- basierend auf Revision ???
	
	
	public List<Module> getModifiedModules(User user){
		List<Module> modules = new LinkedList<Module>();
		List<Entry> entries = new LinkedList<Entry>();
		for (Module module : moduleList) {
			entries = module.getEntryList();
			for (Entry entry : entries) {
				if(entry.isApproved()&&!entry.isRejected()){
					modules.add(module);
					break;
				}
			}
		}
		return modules;
	}
	
	public List<String[]> getModuleManualPdfByCourse(String course)
	{
		List<String[]> list = moduleDBController.getPDFListByCourse(course);
		for (String[] strings : list) {
			System.out.println("-> "+strings[1]+" "+strings[2]+" "+strings[3]+" "+strings[0]+" ");
		}
		return list;
	}
	
	public List<Entry> getEntryListOfModule(Module module){
		return moduleDBController.getEntryListOfModule(module);
	}
	
	public String getCourseID(String course){
		return moduleDBController.getCourseID(course);
	}
	
	public List<Module> getModulesByCourse(String course, String degree){
		return moduleDBController.getModulesByCourse(course, degree);
	}
}
