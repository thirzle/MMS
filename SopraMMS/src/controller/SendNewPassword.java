package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserAdministration;

/**
 * Servlet implementation class SendNewPassword
 */
@WebServlet("/SendNewPassword")
public class SendNewPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendNewPassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		UserAdministration ua = new UserAdministration();
		try {
			ua.sendNewPasswordLink(email);
			String infotext= "Es wurde eine Mail an Ihre E-Mail Adresse versand.";
			session.setAttribute("content", "start");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?infotext="+infotext);
		} catch (Exception e) {
			e.printStackTrace();
			String errortext= "Die angegebene E-Mail Adresse ist im System nicht hinterlegt.";
			session.setAttribute("content", "start");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?errortext="+errortext);
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
