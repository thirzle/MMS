package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.User;

/**
 * Servlet implementation class SaveUser
 */
@WebServlet("/SaveUser")
public class SaveUser extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveUser() {
        super();
        // TODO Auto-generated constructor stub
        // 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginname = request.getParameter("loginCellText");
		String firstname = request.getParameter("firstnameCellText");
		String lastname = request.getParameter("lastnameCellText");
		String email = request.getParameter("emailCellText");
		String rights = request.getParameter("rightsSelect");
		String institute = request.getParameter("instituteSelect");
		session.setAttribute("task", "usertable");
		System.out.println("(addUser.jsp) loginname: "+loginname);
		System.out.println("(addUser.jsp) firstname: "+firstname);
		System.out.println("(addUser.jsp) lastname: "+lastname);
		System.out.println("(addUser.jsp) email: "+email);
		System.out.println("(addUser.jsp) rights: "+rights);
		System.out.println("(addUser.jsp) institute: "+institute);
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
