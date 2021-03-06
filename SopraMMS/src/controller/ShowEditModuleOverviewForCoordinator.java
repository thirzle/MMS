package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Module;

/**
 * Servlet implementation class ShowEditModuleOverviewForCoordinator
 * 
 * @author Teresa Hirzle
 */
@WebServlet("/ShowEditModuleOverviewForCoordinator")
public class ShowEditModuleOverviewForCoordinator extends SessionCheck {
	private static final long serialVersionUID = 1L;
       

    public ShowEditModuleOverviewForCoordinator() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			HttpSession session =request.getSession();
			if (request.getParameter("showVersionsButton") != null) {
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/ShowVersionsOfModule");
				dispatcher.forward(request, response);
			} else if (request.getParameter("editButton") != null) {
				String editButton = request.getParameter("editButton");
				if (editButton.equals("editButton")||editButton.equals("editUnapprovedModule")) {
					System.out.println("(ShowEditModuleForCoordinator.java): Bearbeite Modul durch Koordinator");
					if (request.getParameter("selectedModuleToEdit") != null) {
						String[] selectedModule = request.getParameter("selectedModuleToEdit")
								.split(" ");
						response.sendRedirect("/SopraMMS/EditModule?editButton=editUnapprovedModule&selectedModuleToEdit="
								+ selectedModule[0] + " " + selectedModule[1]);
					} else {
						request.getSession().setAttribute("content",
								"showEditModulesForCoordinator");
						response.sendRedirect("/SopraMMS/guiElements/home.jsp?info=chooseModule");
					}
				} 
			} else {
				List<Module> moduleList = mAdmin.getUnapprovedModulesOverviewForCoordinator();
				request.getSession().setAttribute(
						"editModuleListForCoordinator", moduleList);
				request.getSession().setAttribute("content",
						"showEditModulesForCoordinator");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?edit=true");
			}
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
