package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.User;

import management.Module;

/**
 * Servlet implementation class DeleteModule
 */
@WebServlet("/DeleteModule")
public class DeleteModule extends SessionCheck {
	private static final long serialVersionUID = 1L;

	private ArrayList<Module> moduleList = new ArrayList<>();

	public DeleteModule() {
		super();
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
						Module moduleName = mAdmin.getModuleByID(moduleID, moduleVersion);
						
						mAdmin.deleteModule(moduleID, moduleVersion);
						System.out.println("(DeleteModule.java): Das Modul '"
								+ moduleID + " - " + moduleVersion
								+ "' wurde gelöscht");
						
						
						// insert into History "Module deleted"
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						Date currentTime = new Date();
						String date = formatter.format(currentTime);
						
						uAdmin.insertHistory(
								((User) request.getSession().getAttribute("user")).getLogin(),
								date,
								"Hat folgendes Modul gelöscht: "
										+ moduleName.getName());
					}
				}
				String infoText = "Die ausgewählten Module wurden entgültig aus dem Modul Management System gelöscht.";
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="
						+ infoText);
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
	}

}
