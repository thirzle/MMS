package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		User user = ua.checkNewPasswordLink(request.getParameter("link"));
		if(user==null)
		{
			response.sendRedirect("/SopraMMS/guiElements/error.jsp");
		}
		else{
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("contentWelcome", "createNewPassword");
			response.sendRedirect("/SopraMMS/welcome.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
