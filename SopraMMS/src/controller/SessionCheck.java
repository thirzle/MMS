package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.ModuleAdministration;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class SessionCeck
 */
@WebServlet("/SessionCeck")
public class SessionCheck extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	/**
	 * An object representing an interface to the database.
	 * @see User
	 */
	protected UserAdministration ua;
	/**
	 * An object representing an interface to the database.
	 */
	protected ModuleAdministration ma;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionCheck() {
        super();
        ua = new UserAdministration();
        ma = new ModuleAdministration();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * checks with the help of the session if the attriubute "user" is set, and thus whether he is logged.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @see User
	 */
	protected boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks whether the user has the necessary rights for an action.
	 * 
	 * @param request
	 * @param rights		the rights the user owns within the system.
	 * @return				<code>true</code> if the necessary rights are available<br>
	 * 						<code>false</code> otherwise.
	 * @see User
	 */
	protected boolean actionGranted(HttpServletRequest request, boolean[] rights) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		boolean[] userRights = user.getRights();
		boolean isGranted = true;
		for (int i = 0; i < rights.length; i++) {
			if (rights[i]) {
				if (userRights[i] != rights[i]) {
					isGranted = false;
				}
			}
		}
		return isGranted;
	}

	/**
	 * Checks whether a user has the rights over the given status.
	 * 
	 * @param request
	 * @param status		the users status
	 * @return				<code>true</code> if the necessary rights are available<br>
	 * 						<code>false</code> otherwise.
	 */
	protected boolean actionGranted(HttpServletRequest request, int status) {
	    HttpSession session = request.getSession();
	    User user = (User) session.getAttribute("user");
	    boolean[] userRights = user.getRights();
	    return userRights[status];
	}
}
