package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String oldPassword = request.getParameter("oldPassword");
		String newPassword1 = request.getParameter("newPassword1");
		String newPassword2 = request.getParameter("newPassword2");
		System.out.println("oldPw"+oldPassword);
		UserAdministration ua= new UserAdministration();
		
		if (!newPassword1.equals(newPassword2)) {
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?generallyMenu=open&content=changedPwStatusPw12Wrong");
		}
		else{
			User user = ua.checkPasswordBySession(request.getSession().toString(), oldPassword);
			if(user==null){
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?generallyMenu=open&content=changedPwStatusOldOwWrong");
			}
			else{
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?generallyMenu=open&content=changedPwStatusdone");
			}
		}
	}

}