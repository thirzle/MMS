package testsuite;


import java.util.LinkedList;

import user.User;

import controller.UserDBController;

public class DBtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserDBController controller = new UserDBController();
		boolean [] rights = controller.getRights("lehrd");
		System.out.println("get rights of lehrd: "+rights[1]);
		
		LinkedList<String> stute = (LinkedList) controller.getInstitutesByName("lehrd");
		System.out.println("get institute of lehrd: ");
		for ( String name : stute) {
			System.out.println(name);
		}
		
		LinkedList<User> users = (LinkedList<User>) controller.getAllUsers();
		System.out.println("get all users: ");
		for (User user : users) {
			System.out.println(user.getFirstName());
		}
		
		User user = controller.getUser("lehrd");
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
		instituteT = (LinkedList<String>) controller.getInstitutes();
		for (String string : instituteT) {
			System.out.println(string);
		}
	}

}
