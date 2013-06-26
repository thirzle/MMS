package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import management.Module;
import management.ModuleAdministration;

/**
 * Servlet implementation class DeleteModule
 */
@WebServlet("/DeleteModule")
public class DeleteModule extends SessionCheck {
	private static final long serialVersionUID = 1L;

	private ArrayList<Module> moduleList = new ArrayList<>();

	public DeleteModule() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			if (request.getParameter("delete") == null) {
				moduleList.clear();
				moduleList.addAll(mAdmin.getAllModules());
				request.getSession().setAttribute("listOfAllModules", moduleList);
	
				request.getSession().setAttribute("content", "deleteModule");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			} else {
				for (int i = 0; i < moduleList.size(); i++) {
					if (request.getParameter("moduleDelete" + i) != null) {
						String[] module = request.getParameter("moduleDelete" + i)
								.split(" ");
						long moduleID = Long.parseLong(module[0]);
						int moduleVersion = Integer.parseInt(module[1]);
						mAdmin.deleteModule(moduleID, moduleVersion);
						System.out.println("(DeleteModule.java): Das Modul '"
								+ moduleID + " - " + moduleVersion
								+ "' wurde gel�scht");
					}
	
				}
				String infoText = "Die ausgew�hlten Module wurden entg�ltig aus dem Modul Management System gel�scht.";
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="
						+ infoText);
			}
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
