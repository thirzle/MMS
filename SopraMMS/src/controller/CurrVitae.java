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
 * 
 * @author Lisa
 */
@WebServlet("/CurrVitae")
public class CurrVitae extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CurrVitae() {
        super();
    }

	/**
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			if(request.getParameter("first")!=null)
			{
				String url=uAdmin.getCurriculum(user.getLogin());
				if(url==null){
					url="";
				}
				session.setAttribute("currVitae", url);
				session.setAttribute("content", "currVitae");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			}
			else{
				if(request.getParameter("action")!=null){
					if(request.getParameter("action").equals("delete")){
						uAdmin.setCurriculum(user.getLogin(), null);
						String infotext = "Ihr Lebenslauf wurde von Ihrem Benutzerprofil gel�scht.";
						response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="+infotext);
					}else if(request.getParameter("action").equals("add")){
						uAdmin.setCurriculum(user.getLogin(), request.getParameter("currurl"));
						String infotext = "Ihr Lebenslauf wurde in Ihrem Benutzerprofil ge�ndert.";
						response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="+infotext);
					}
				}
			}
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
