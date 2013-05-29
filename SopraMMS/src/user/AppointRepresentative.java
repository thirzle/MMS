package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.EmailApache;
import mail.EmailTelnet;

/**
 * Servlet implementation class AppointRepresentative
 */
@WebServlet("/AppointRepresentative")
public class AppointRepresentative extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AppointRepresentative() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String firstNameRep = request.getParameter("firstNameRep");
		String lastNameRep = request.getParameter("lastNameRep");
		String mailRep = request.getParameter("mailRep");
		System.out.println(mailRep == ""+"''''''''''''''''''''''''''''''''''''''''''");
		if(firstNameRep.equals(null) || lastNameRep.equals(null)  || mailRep.equals(null) || firstNameRep.equals("") || lastNameRep.equals("") || mailRep.equals("")){
			request.getSession().setAttribute("lessData", true);
			request.getSession().setAttribute("content", "appointRepresentative");
		}
		
		User user = (User) request.getSession().getAttribute("user");
		User userR = new UserAdministration().getUserByMail(mailRep);

		//if representative doesn't exist
		if(userR == null){
			String content = user.getFirstName() + " " + user.getLastName()
					+ " (E-Mail Adresse: " + mailRep + ") " + " moechte "
					+ firstNameRep + " " + lastNameRep
					+ " zu seinem Stellvertreter ernennen";
			
			EmailTelnet mail = new EmailTelnet();
			mail.send_mail("Neuer Stellvertreter", "teresa.hirzle@uni-ulm.de",
					content);
			request.getSession().setAttribute("content", "applyRepresentative");
		}
		//if representative exists
		else{
			if(!userR.getFirstName().equals(firstNameRep) || !userR.getLastName().equals(lastNameRep)){
				System.out.println("falsche Daten**************************************");
				request.getSession().setAttribute("wrongData", true);
				request.getSession().setAttribute("content", "appointRepresentative");
			}
			else{
				new UserAdministration().changeRepresentative(user, userR.getLogin());
				request.getSession().setAttribute("content", "createdRepresentative");
			}
		}
		request.getSession().setAttribute("generallyMenu", "open");
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
