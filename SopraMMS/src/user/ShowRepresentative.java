package user;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowRepresentative
 */
@WebServlet("/ShowRepresentative")
public class ShowRepresentative extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowRepresentative() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = (User) request.getSession().getAttribute("user");
		String repName = user.getRepresentative();
		User userR = new UserAdministration().getUser(repName);
		request.getSession().setAttribute("generallyMenu", "open");
//		there is no representative
		if(userR == null){
			request.getSession().setAttribute("content", "noRepresentative");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		}
		
		else{
			String firstname = userR.getFirstName();
			String lastname = userR.getLastName();
			String email = userR.getMail();
			String fac = new UserAdministration().getFacultyName(userR);
			LinkedList in = (LinkedList) new UserAdministration().getInstituteNames(userR);
			
			request.getSession().setAttribute("firstnameRep", firstname);
			request.getSession().setAttribute("lastnameRep", lastname);
			request.getSession().setAttribute("emailRep", email);
			request.getSession().setAttribute("facRep", fac);
			request.getSession().setAttribute("inRep", in);
			request.getSession().setAttribute("content", "showRepresentative");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
