package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class SaveUser
 */
@WebServlet("/SaveUser")
public class SaveUser extends SessionCheck {
	private static final long serialVersionUID = 1L;
    private UserAdministration ua;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveUser() {
        super();
        ua = new UserAdministration();
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
		session.setAttribute("content", "editUser");
		
		// TODO
		/*
		 * Der User kann initialisiert werden und gespeichert werden.
		 */
		System.out.println("(SaveUser.java) loginname: "+loginname);
		System.out.println("(SaveUser.java) firstname: "+firstname);
		System.out.println("(SaveUser.java) lastname: "+lastname);
		System.out.println("(SaveUser.java) email: "+email);
		System.out.println("(SaveUser.java) rights: "+rights);
		System.out.println("(SaveUser.java) institute: "+institute);
		/*
		 * 
		 * 
		 */
		response.sendRedirect("/SopraMMS/EditUser");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
