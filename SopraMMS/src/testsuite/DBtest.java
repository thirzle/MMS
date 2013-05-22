package testsuite;


import java.util.LinkedList;

import management.Module;

import user.User;

import controller.ModuleDBController;
import controller.UserDBController;

public class DBtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserDBController uController = new UserDBController();
		ModuleDBController mController = new ModuleDBController();
		
//		uController.getRights
		boolean [] rights = uController.getRights("lehrd");
		System.out.println("get rights of lehrd: "+rights[1]);
		
//		uController.getInstitutesByName
		LinkedList<String> stute = (LinkedList) uController.getInstitutesByName("lehrd");
		System.out.println("get institute of lehrd: ");
		for ( String name : stute) {
			System.out.println(name);
		}
		
//		uController.getAllUsers
		LinkedList<User> users = (LinkedList<User>) uController.getAllUsers();
		System.out.println("get all users: ");
		for (User user : users) {
			System.out.println(user.getFirstName());
		}
		
//		uController.getUser
		User user = uController.getUser("lehrd");
		System.out.println("get user (lastname) lehrd: "+user.getLastName());
		
		boolean[] rightsT = {true, true, false, false, false};
		LinkedList<String> instituteT = new LinkedList<String>();
		instituteT.add("mi");
//		boolean b = controller.deleteUser("hirzlet");
//		User teresaOld = new User("hirzlet", "Teresa", "Hirzle", "teresa.hirzle@uni-ulm.de", rightsT, null, null, instituteT, null, null, "hirzlet".hashCode()+"");
//		User teresaNew = new User("wursth", "Hans", "Wurst", "hans.wurst@uni-ulm.de", rightsT, null, null, instituteT, null, null, "wursth".hashCode()+"");
//		boolean b = controller.createUser(teresaOld);
//		boolean b = controller.changeUser(teresaNew, teresaOld);
//		boolean [] rightsN = {true, false, true, false, true};
//		controller.changeRights(teresaOld, rightsN);
//		System.out.println("changed user hirzlet: "+b);
		
//		uController.getInstitutes
		instituteT = (LinkedList<String>) uController.getInstitutes();
		for (String string : instituteT) {
			System.out.println(string);
		}
		
//		mController.getRejectedModulesByAuthor
		LinkedList<Module> rejectedModuleList = (LinkedList) mController.getRejectedModulesByAuthor("hirzlet");
		System.out.println("all rejected modules: ");
		for (Module module : rejectedModuleList) {
			System.out.print(module.getName()+" ");
		}
		System.out.println();
		
//		mController.getRejectedModulesByInstitute
		rejectedModuleList = (LinkedList) mController.getRejectedModulesByInstitute("mi");
		System.out.println("all rejected modules: ");
		for (Module module : rejectedModuleList) {
			System.out.print(module.getName()+" ");
		}
		System.out.println();
		
//		mController.getRejectedModules
		rejectedModuleList = (LinkedList) mController.getRejectedModules();
		System.out.println("all rejected modules: ");
		for (Module module : rejectedModuleList) {
			System.out.print(module.getName()+" ");
		}
		System.out.println();
		
	}

}
