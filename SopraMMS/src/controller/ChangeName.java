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
 * Servlet implementation class ChangeName
 */
@WebServlet("/ChangeName")
public class ChangeName extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeName() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newFirstname = request.getParameter("newFirstname");
		String newLastname = request.getParameter("newLastname");
		User user = (User) request.getSession().getAttribute("user");
		HttpSession session = request.getSession();
		
		UserAdministration uAdmin = new UserAdministration();
		uAdmin.changeName(user, newFirstname, newLastname);
		String loginname = newLastname.toLowerCase() + newFirstname.toUpperCase().charAt(0);
		uAdmin.changeLoginname(user, loginname);
		
		session.setAttribute("newFirstname", newFirstname);
		session.setAttribute("newLastname", newLastname);
		session.setAttribute("newLoginname", loginname);
		session.setAttribute("content", "changedName");
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
