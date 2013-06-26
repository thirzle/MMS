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
public class CreateNewPassword extends HttpServlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserAdministration uAdmin = new UserAdministration();
	
			try {
				User user = uAdmin.checkNewPasswordLink(request.getParameter("link"));
				System.out.println(user);
				if (user!=null) {
					request.getSession().setAttribute("userCreatNewPassword", user);
					request.getSession().setAttribute("content", "createNewPassword");
					System.out.println("(CreateNewPassword.java): "+request.getSession().getAttribute("content"));
					response.sendRedirect("/SopraMMS/guiElements/home.jsp");
				}else{
					String errorText = "Dieser Link um ein neues Passwort zu erstellen existiert nicht.";
					response.sendRedirect("/SopraMMS/guiElements/home.jsp?errortext="+errorText);
				}
			
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("/SopraMMS/guiElements/error.jsp");
			}	
		}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
