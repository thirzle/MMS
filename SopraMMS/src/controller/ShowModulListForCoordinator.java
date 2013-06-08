package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import management.Module;
import management.ModuleAdministration;

/**
 * Servlet implementation class ShowModulListForCoordinator
 */
@WebServlet("/ShowModulListForCoordinator")
public class ShowModulListForCoordinator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowModulListForCoordinator() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("action") != null) {
			if (request.getParameter("action").equals("editModule")) {

			} else if (request.getParameter("action").equals("enterCourse")) {
				if (request.getParameter("selectedModule") != null) {
					String[] selectedModule = request.getParameter(
							"selectedModule").split("%");
					response.sendRedirect("/SopraMMS/EnterCourseToModule?moduleID="
							+ selectedModule[0]
							+ "&version="
							+ selectedModule[1]);
				} else {
					request.getSession().setAttribute("content",
							"showModulesForCoordinator");
					response.sendRedirect("/SopraMMS/guiElements/home.jsp?info=chooseModule");
				}

			}
		} else {

			ModuleAdministration ma = new ModuleAdministration();
			List<Module> moduleList = ma.getUnfinishedModulesOverview();
			request.getSession().setAttribute(
					"unfinishedModuleListForCoordinator", moduleList);
			request.getSession().setAttribute("content",
					"showModulesForCoordinator");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
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
