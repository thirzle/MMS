package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.icu.text.SimpleDateFormat;

import model.UserDBController;
import user.User;

/**
 * Servlet implementation class DeleteUser
 */
@WebServlet("/DeleteUser")
public class DeleteUser extends SessionCheck implements Servlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see SessionCheck#SessionCheck()
     */
    public DeleteUser() {
	super();
	// TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("(DeleteUser.java):doGet() called");
		HttpSession session = request.getSession();
		if (isLoggedIn(request, response) && actionGranted(request, 3)) {
		    String loginname = request.getParameter("selectedRowID");
		    String thisloginname = ((User) session.getAttribute("user")).getLogin();
		    if((!loginname.equals(thisloginname))) {
			    System.out.println("(DeleteUser.java):user:" + loginname);
			    // insert into History "User removed"
			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			    Date currentTime = new Date();
			    String date = formatter.format(currentTime);
			    uAdmin.insertHistory(loginname, date, "Wurde geloescht");
		
			    uAdmin.deleteUser(loginname);
			    session.setAttribute("task", "edit");
			    response.sendRedirect("/SopraMMS/LoadTable");
		    } else {
		    	 session.setAttribute("errormessage", "Sie k�nnen sich nicht selbst l�schen! Bitte kontaktieren Sie einen anderen Administrator.");
		    	 session.setAttribute("task", "edit");
				 response.sendRedirect("/SopraMMS/LoadTable");
		    }
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
		}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
    }

}
