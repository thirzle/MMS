package user;

import java.io.IOException;
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
		//User user = (User) request.getSession().getAttribute("user");
		//String repName = user.getRepresentative();
		String repName = "lehrd";
		User userR = new UserAdministration().getUser(repName);
		System.out.println("**********************************************");
		System.out.println("**********************************************");
		System.out.println(userR == null);
		String firstname = userR.getFirstName();
		String lastname = userR.getLastName();
		String email = userR.getMail();
		String fac = userR.getFaculty();
		//String in = (LinkedList) userR.getInstitute();
		
		request.getSession().setAttribute("firstnameRep", firstname);
		request.getSession().setAttribute("lastnameRep", lastname);
		request.getSession().setAttribute("emailRep", email);
		request.getSession().setAttribute("facRep", fac);
		//request.getSession().setAttribute("inRep", in);
		request.getSession().setAttribute("content", "showRepresentative");
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
