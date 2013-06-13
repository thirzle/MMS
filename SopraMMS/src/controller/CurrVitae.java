package controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.icu.text.SimpleDateFormat;

import mail.EmailTelnet;
import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class CurrVitae
 */
@WebServlet("/CurrVitae")
public class CurrVitae extends SessionCheck implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CurrVitae() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserAdministration uAdmin = new UserAdministration();
		User user = (User) session.getAttribute("user");
		if ( user != null ){
			String loginname = user.getLogin();
			String currurl = request.getParameter("currurl");

			uAdmin.setCurriculum(loginname, currurl);

			session.setAttribute("currurl", currurl);

			// insert into History "New CurricullumVitae"
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date currentTime = new Date();
			String date = formatter.format(currentTime);
			uAdmin.insertHistory(loginname, date, "Lebenslauf angelegt");
		}
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	}

}
