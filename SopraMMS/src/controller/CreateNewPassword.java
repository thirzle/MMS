package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class CreateNewPassword
 */
@WebServlet("/CreateNewPassword")
public class CreateNewPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserAdministration ua = new UserAdministration();
		try {
			User user = ua.checkNewPasswordLink(request.getParameter("link"));
			if (user!=null) {
				request.getSession().setAttribute("userCreatNewPassword", user);
				request.getSession().setAttribute("content", "createNewPassword");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			}else{
				response.sendRedirect("/SopraMMS/guiElements/error.jsp");
			}
		
		} catch (Exception e) {
			response.sendRedirect("/SopraMMS/guiElements/error.jsp");
		}	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
