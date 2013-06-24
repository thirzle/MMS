package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Course;
import management.Module;
import management.ModuleAdministration;

/**
 * Servlet implementation class ShowEditModuleOverviewForCoordinator
 */
@WebServlet("/ShowEditModuleOverviewForCoordinator")
public class ShowEditModuleOverviewForCoordinator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ShowEditModuleOverviewForCoordinator() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =request.getSession();
		ModuleAdministration ma= new ModuleAdministration();
		
		System.out.println("(ShowEditModuleForCoordinator.java): Bearbeite Modul durch Koordinator");
		
		if (request.getParameter("action") != null) {

			if (request.getParameter("action").equals("editModule")) {
				if (request.getParameter("selectedModule") != null) {
					String[] selectedModule = request.getParameter("selectedModule")
							.split("%");
					response.sendRedirect("/SopraMMS/EditModule?selectedModuleToEdit="
							+ selectedModule[0] + " " + selectedModule[1]);
				} else {
					request.getSession().setAttribute("content",
							"showEditModulesForCoordinator");
					response.sendRedirect("/SopraMMS/guiElements/home.jsp?info=chooseModule");
				}
			} 
		} else {

			List<Module> moduleList = ma.getModules();
			request.getSession().setAttribute(
					"editModuleListForCoordinator", moduleList);
			request.getSession().setAttribute("content",
					"showEditModulesForCoordinator");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
