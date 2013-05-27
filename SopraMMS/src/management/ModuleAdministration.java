package management;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import controller.ModuleDBController;

import user.User;

public class ModuleAdministration {
	List<Module> moduleList;
	ModuleDBController moduleDBController = new ModuleDBController();
	
	//TODO
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
		return moduleDBController.getPDFListByCourse(course);
	}
}
