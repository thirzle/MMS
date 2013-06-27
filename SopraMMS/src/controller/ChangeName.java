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
 * Servlet implementation class ChangeName
 */
@WebServlet("/ChangeName")
public class ChangeName extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeName() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			String newFirstname = request.getParameter("newFirstname");
			String newLastname = request.getParameter("newLastname");
			User user = (User) request.getSession().getAttribute("user");
			HttpSession session = request.getSession();
			String infotext;
			String content;
			String address;
			
			uAdmin.changeName(user, newFirstname, newLastname);
			
			// Insert into History "Name changed"
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date currentTime = new Date();
			String date = formatter.format(currentTime);
			uAdmin.insertHistory(user.getLogin(), date, "Name ge&auml;ndert");
			
			infotext = "Ihr Name wurde erfolgreich zu "+newFirstname+" "+newLastname+" geändert. ";
			session.setAttribute("content", "home");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="+infotext);
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
