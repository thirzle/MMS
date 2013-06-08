package controller;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Module;
import management.ModuleAdministration;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class ShowModules
 */
@WebServlet("/ShowModules")
public class ShowModules extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowModules() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ModuleAdministration mAdmin = new ModuleAdministration();
		User user = (User) session.getAttribute("user");
//		TODO getModulesOverview
		LinkedList<Module> moduleList = mAdmin.getModulesByAuthor(user.getLogin());

		session.setAttribute("moduleListForModulemanager", moduleList);
		session.setAttribute("content", "showModules");
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
