package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Module;
import management.ModuleAdministration;

/**
 * Servlet implementation class DisableModule
 */
@WebServlet("/DisableModule")
public class DisableModule extends SessionCheck {
	private static final long serialVersionUID = 1L;
	private Module module = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DisableModule() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			if (request.getParameter("action") == null) {
	
				HttpSession session = request.getSession();
	
				String selectedModule = request
						.getParameter("selectedModuleToEdit");
				long moduleID;
				int version;
				String instituteID;
				String institute = null;
				String[] parts;
				if (selectedModule != null) {
					parts = selectedModule.split(" ");
					moduleID = Long.parseLong(parts[0]);
					version = Integer.parseInt(parts[1]);
					module = mAdmin.getModuleByID(moduleID, version);
					instituteID = module.getInstituteID();
					institute = mAdmin.getInstituteName(instituteID);
				}
	
				System.out
						.println("ViewModule: module == null " + (module == null));
	
				session.setAttribute("instituteForViewModule", institute);
				session.setAttribute("moduleForViewModule", module);
				session.setAttribute("content", "disableModule");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			} else {
				if (request.getParameter("action").equals("disable")) {
					mAdmin.deactivateModule(module);
					String infotext = "Das Modul '"
							+ module.getName()
							+ "' wurde zurückgesetzt und muss erst wieder feigegeben werden um in einem neuen Modulhandbuch aufzutauchen.";
					response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="
							+ infotext);
				} else if (request.getParameter("action").equals("ok")) {
					String infotext = "Das Modul '"
							+ module.getName()
							+ "' wurde akzeptieren und wird im neuen Modulhandbuch veröffentlicht.";
					response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="
							+ infotext);
				}
			}
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
