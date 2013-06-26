package testsuite;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import com.ibm.icu.text.SimpleDateFormat;

import management.Deadline;

import management.Entry;
import management.Module;
import model.ModuleDBController;
import model.UserDBController;

import user.User;


public class DBtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserDBController uController = new UserDBController();
		ModuleDBController mController = new ModuleDBController();
//		uController.connect();
//		for (User user : uController.getAllUsers()) {
//			System.out.println(user);
//		}
//		uController.getRights
//		boolean [] rights = uController.getRights("lehrd");
//		System.out.println("get rights of lehrd: "+rights[1]);		
//		
////		uController.getInstitutesByName
//		LinkedList<String> stute = (LinkedList) uController.getInstitutesByName("lehrd");
//		System.out.println("get institute of lehrd: ");
//		for ( String name : stute) {
//			System.out.println(name);
//		}
//		
////		uController.getAllUsers
//		LinkedList<User> users = (LinkedList<User>) uController.getAllUsers();
//		System.out.println("get all users: ");
//		for (User user : users) {
//			System.out.println(user.getFirstName());
//		}
//		
////		uController.getUser
//		User user = uController.getUser("lehrd");
//		System.out.println("get user (lastname) lehrd: "+user.getLastName());
//		
////		uController.getUser
////		user = uController.getUser("5BA1F9C933DA6ECAE7F6EB2F02790A4F", "926186098");
////		System.out.println("get user hirzlet: "+ user.getFirstName());
//		
////		uController.getUser
//		user = uController.getUserByEmail("teresa.hirzle@uni-ulm.de");
//		System.out.println("get user by email: "+user.getLogin());
//		
//		boolean[] rightsT = {true, true, false, false, false};
//		LinkedList<String> instituteT = new LinkedList<String>();
//		instituteT.add("mi");
////		boolean b = controller.deleteUser("hirzlet");
////		User teresaOld = new User("hirzlet", "Teresa", "Hirzle", "teresa.hirzle@uni-ulm.de", rightsT, null, null, instituteT, null, null, "hirzlet".hashCode()+"");
////		User teresaNew = new User("wursth", "Hans", "Wurst", "hans.wurst@uni-ulm.de", rightsT, null, null, instituteT, null, null, "wursth".hashCode()+"");
////		boolean b = controller.createUser(teresaOld);
////		boolean b = controller.changeUser(teresaNew, teresaOld);
////		boolean [] rightsN = {true, false, true, false, true};
////		controller.changeRights(teresaOld, rightsN);
////		System.out.println("changed user hirzlet: "+b);
//		
////		uController.getInstitutes
//		instituteT = (LinkedList<String>) uController.getInstitutes();
//		for (String string : instituteT) {
//			System.out.println(string);
//		}
//		
////		mController.getRejectedModulesByAuthor
//		LinkedList<Module> rejectedModuleList = (LinkedList) mController.getRejectedModulesByAuthor("hirzlet");
//		System.out.println("all rejected modules by author hirzlet: ");
//		for (Module module : rejectedModuleList) {
//			System.out.print(module.getName()+" ");
//		}
//		System.out.println();
//		
////		mController.getRejectedModulesByInstitute
//		rejectedModuleList = (LinkedList) mController.getRejectedModulesByInstitute("mi");
//		System.out.println("all rejected modules by institute mi: ");
//		for (Module module : rejectedModuleList) {
//			System.out.print(module.getName()+" ");
//		}
//		System.out.println();
//		
////		mController.getRejectedModules
//		rejectedModuleList = (LinkedList) mController.getRejectedModules();
//		System.out.println("all rejected modules: ");
//		for (Module module : rejectedModuleList) {
//			System.out.print(module.getName()+" ");
//		}
//		System.out.println();
//		
////		mController.getModifiedModulesByAuthor
//		LinkedList<Module> modifiedModulesList = (LinkedList) mController.getModifiedModulesByAuthor("hirzlet");
//		System.out.println("modifiedModules by author: hirzlet");
//		for (Module module : modifiedModulesList) {
//			System.out.println(module.getName()+" ");
//		}
//		System.out.println();
//		
////		mController.getModifiedModulesByInstitute
//		modifiedModulesList = (LinkedList) mController.getModifiedModulesByInstitute("mi");
//		System.out.println("modifiedModules by institute: mi");
//		for (Module module : modifiedModulesList) {
//			System.out.println(module.getName()+" ");
//		}
//		System.out.println();
//		
////		mController.getModifiedModules
//		modifiedModulesList = (LinkedList) mController.getModifiedModules();
//		System.out.println("all modifiedModules: ");
//		for (Module module : modifiedModulesList) {
//			System.out.println(module.getName()+" ");
//		}
//		System.out.println();
//		
////		mController.getModule
//		Module module = mController.getModule(42);
//		System.out.println("get module: Lineare Algebra");
//		System.out.println(module.getModuleID()+" "+module.getName()+" "+module.getInstituteID()+"\n");
//		
////		mController.getModuleByAuthor
//		LinkedList<Module> moduleList = (LinkedList) mController.getModulesByAuthor("lehrd");
//		System.out.println("get modules by author: lehrd");
//		for (Module module2 : moduleList) {
//			System.out.println(module2.getName());
//		}
//		System.out.println();
//		
////		mController.getModulesByFaculty
//		moduleList = (LinkedList) mController.getModulesByFaculty("in");
//		System.out.println("get modules by faculty: in");
//		for (Module module2 : moduleList) {
//			System.out.println(module2.getName());
//		}
//		System.out.println();
//
////		mController.getModulesByInstitute
//		moduleList = (LinkedList) mController.getModulesByInstitute("theo");
//		System.out.println("get modules by intitute: theo");
//		for (Module module2 : moduleList) {
//			System.out.println(module2.getName());
//		}
//		System.out.println();
//		
////		mController.getModules
//		moduleList = (LinkedList) mController.getModules();
//		System.out.println("get all modules:");
//		for (Module module2 : moduleList) {
//			System.out.println(module2.getName());
//		}
//		System.out.println();
		
//		mController.generateLatestVersionOfModuleManual
//		String latestVersion = mController.generateLatestVersionOfModuleManual("inf", "Bachelor");
//		System.out.println("get latest version of course inf "+latestVersion);
//		
////	mController.createModuleManual
//		mController.createModuleMaunal(latestVersion, "mi", "Bachelor", "2013-06-01", "2013-06-01", true, 2012);
	
//		uController.getEmails
		
//		User basti = uController.getUser("nigschb");
//
//		uController.changeRights(basti, new boolean[]{true, false, false, false, true, true, false});
//		System.out.println("done");+

		//System.out.println(mController.clearDatabase());
		System.out.println(uController.checkLoginname("reuterm"));


	}

}
