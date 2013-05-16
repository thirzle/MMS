package testsuite;

import user.User;

public class TempKlasse {

	
	public User[] getUsers() {
		String[] Loginname = {"admin","kopetri","herbert12","Tombat"};
		String[] Firstname = {"Peter","Sebastian","Herbert","Kevin"};
		String[] Lastname = {"Griffin","Hartwig","Pan","Mantz"};
		String[] Email = {"peter.griffin@uni-ulm.de","sebastian.hartwig@uni-ulm.de","herbert.pan@uni-ulm.de","kevin.mantz@uni-ulm.de"};
		String[] Rights = {"Administrator","Administrator","Redakteur","Modulverantwortlicher"};
		String[] Institute = {"Eingebettete Systeme","Compilerbau","Neuroinformatik","Theoretische Informatik"};
		String[] Supervisor = {"keinen","keinen","Jochen Wittmann","Berndt Kappler"};
		User[] users = new User[Loginname.length];
		for (int i = 0; i < Loginname.length; i++) {
			User user = new User();
			user.setLogin(Loginname[i]);
			user.setFirstName(Firstname[i]);
			user.setLastName(Lastname[i]);
			user.setMail(Email[i]);
			user.setSupervisor(Supervisor[i]);
			users[i] = user;
		}
		return users;
	}
	/*<% for(int i=0; i<users.length;i++) { %>
	<tr>
		<td><%=users[i].getLogin() %></td>
		<td><%=users[i].getFirstName() %></td>
		<td><%=users[i].getLastName() %></td>
		<td><%=users[i].getMail() %></td>
		<td><%=users[i].getSupervisor() %></td>
	</tr>
<%} %>*/
}
