package controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mail.EmailTelnet;

import com.ibm.icu.text.SimpleDateFormat;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class RemoveRepresentative
 * 
 * @author Teresa Hirzle
 */
@WebServlet("/RemoveRepresentative")
public class RemoveRepresentative extends SessionCheck {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemoveRepresentative() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			String repLoginname = user.getRepresentative();
			User rep = uAdmin.getUser(repLoginname);
			String firstNameRep = rep.getFirstName();
			String lastNameRep = rep.getLastName();
			String infotext = " ";
			
			// remove representative of user
			if (uAdmin.removeRepresentative(user)) {
				user.setRepresentative(null);
				String mailRep = (String) session.getAttribute("mailRep");
				session.setAttribute("content", "home");
				
				//send mail to deleted representative
				String content = user.getFirstName() + " " + user.getLastName()
						+ " hat Sie" + " als Stellvertreter entfernt";
				EmailTelnet mail = new EmailTelnet();
				mail.send_mail("Stellvertreter", mailRep, content);
				
				infotext = "Ihr Stellvertreter wurde entfernt. Erneuern Sie diesen bitte demnächst!";
				
				// insert into History "Representative removed"
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date currentTime = new Date();
				String date = formatter.format(currentTime);
				uAdmin.insertHistory(user.getLogin(), date, "Hat " + firstNameRep
						+ " " + lastNameRep + " als Stellvertreter gel&ouml;scht.");
	
			}
			session.setAttribute("generallyMenu", "open");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="+infotext);
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
		}
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
