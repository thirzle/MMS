package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class ShowRepresentative
 */
@WebServlet("/ShowRepresentative")
public class ShowRepresentative extends SessionCheck {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowRepresentative() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
	//		there is no representative
			if(user.getRepresentative() == null){
				session.setAttribute("content", "noRepresentative");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			}
			else{
				User userR = new UserAdministration().getUser(user.getRepresentative());
				session.setAttribute("generallyMenu", "open");
				String firstnameRep = userR.getFirstName();
				String lastnameRep = userR.getLastName();
				String emailRep = userR.getMail();
				String facRep = uAdmin.getFacultyName(userR);
				ArrayList instituteListRep = (ArrayList) new UserAdministration().getInstituteNames(userR);
				String[] userArray = new String[4];
				
				userArray[0] = firstnameRep;
				userArray[1] = lastnameRep;
				userArray[2] = emailRep;
				userArray[3] = facRep;
				session.setAttribute("repUserArray", userArray);
				session.setAttribute("instituteListRep", instituteListRep);
				session.setAttribute("content", "showRepresentative");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
				
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
