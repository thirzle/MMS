package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShowEditModuleForEditor
 */
@WebServlet("/ShowEditModuleForEditor")
public class ShowEditModuleForEditor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowEditModuleForEditor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		long moduleID = 0;
		int version = 0;
		// selectedModuleToApprove == null --> Aufruf von Buttons
		if (request.getParameter("selectedModuleToApprove") == null) {
			moduleID = (long) session.getAttribute("selectedModuleIDToApprove");
			version = (int) session.getAttribute("selectedVersionToApprove");

		}
		
		session.setAttribute("content", "showEditModuleForEditor");
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
