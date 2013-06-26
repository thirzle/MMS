package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Module;
import management.ModuleAdministration;
import user.User;

/**
 * Servlet implementation class ShowModules
 */
@WebServlet("/ShowModulesOverviewForModulemanager")
public class ShowModulesOverviewForModulemanager extends SessionCheck {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowModulesOverviewForModulemanager() {

		super();
		System.out.println("ShowModulesOverviewForModulemanager");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			System.out.println("ShowModulesOverviewForModulemanager");
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
	
			List<Module> moduleList = mAdmin.getModulesByAuthor(user.getLogin());
	
			session.setAttribute("moduleListForModulemanager", moduleList);
			session.setAttribute("content", "showModulesForModulemanager");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	
			session.removeAttribute("fieldsTypeAEdit");
			session.removeAttribute("fieldsTypeBEdit");
			session.removeAttribute("fieldsTypeCEdit");
			session.removeAttribute("fieldsTypeDEdit");
			session.removeAttribute("fieldsTypeEEdit");
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
