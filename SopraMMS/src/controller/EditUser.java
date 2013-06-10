package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDBController;
import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class EditUser
 */
@WebServlet("/EditUser")
public class EditUser extends SessionCheck implements Servlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see SessionCheck#SessionCheck()
     */
    public EditUser() {
	super();
	// TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	System.out.println("(EditUser.java): doGet() called");
	HttpSession session = request.getSession();
	if (isLoggedIn(request, response) && actionGranted(request, 3)) {
	    String loginname = "";
	    UserAdministration ua = new UserAdministration();
	    List<String> institutes = ua.getAllInstituteID();
	    List<String> instituteNames = ua.getAllInstitute();
	    if (institutes != null) {
		session.setAttribute("institutes", institutes);
		session.setAttribute("instituteNames", instituteNames);
		session.removeAttribute("errormessage");
	    } else {
		System.out.println("(EditUser.java): institute has null value");
		session.setAttribute("errormessage", "institute is null");
		session.setAttribute("content", "loadTable");
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	    }
	    try {
		loginname = request.getParameter("selectedRowID").toString();
		System.out.println("(EditUser.java):selectedRowID: " + loginname);
		User user = ua.getUser(loginname);
		if (user != null) {
		    session.removeAttribute("emptyInputs");
		    session.setAttribute("userToEdit", user);
		    session.setAttribute("content", "editUser");
		} else {
		    session.setAttribute("errormessage", "no user was selected");
		    session.setAttribute("content", "loadTable");
		}
	    } catch (NullPointerException e) {
		System.out.println("Parameter: selectedRowID has null value.");
		session.setAttribute("errormessage", "no user was selected.");
		session.setAttribute("content", "loadTable");
	    } finally {
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	    }
	} else {
	    System.out.println("not logged in or access denied.");
	    session.setAttribute("content", "start");
	    response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
    }

}
