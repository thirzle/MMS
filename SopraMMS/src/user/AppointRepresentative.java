package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.EmailApache;

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
		System.out.println("**************************************************");
		System.out.println("**************************************************");
		System.out.println("**************************************************");
		System.out.println("**************************************************");
		System.out.println("**************************************************");

		System.out.println("AppointRepresentative.doGet");
		String firstNameRep = request.getParameter("firstNameRep");
		String lastNameRep = request.getParameter("lastNameRep");
		String mailRep = request.getParameter("mailRep");
		User user = (User) request.getSession().getAttribute("user");
		String content = user.getFirstName() + " " +user.getLastName()+" moechte "+ firstNameRep+" "+lastNameRep +" zu seinem Stellvertreter ernennen";
		EmailApache mail = new EmailApache();
		mail.send_mail("Neuer Stellvertreter", "t.hirzle@web.de", content);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
