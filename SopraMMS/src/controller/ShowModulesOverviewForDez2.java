package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Module;
import management.ModuleAdministration;

/**
 * Servlet implementation class ShowModulesForDez2
 */
@WebServlet("/ShowModulesOverviewForDez2")
public class ShowModulesOverviewForDez2 extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowModulesOverviewForDez2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			//check which button was clicked
			//showVersionsButton was clicked
			if (request.getParameter("showVersionsButton") != null) {
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/ShowVersionsOfModule");
				dispatcher.forward(request, response);
			} else if(request.getParameter("showButton") != null) {
				//TODO
			} else {
				List<Module> moduleList = (LinkedList<Module>) mAdmin.getModules();
				
				session.setAttribute("moduleListForDez2", moduleList);
				session.setAttribute("content", "showModulesForDez2");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	
				session.removeAttribute("fieldsTypeAEdit");
				session.removeAttribute("fieldsTypeBEdit");
				session.removeAttribute("fieldsTypeCEdit");
				session.removeAttribute("fieldsTypeDEdit");
				session.removeAttribute("fieldsTypeEEdit");	
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
