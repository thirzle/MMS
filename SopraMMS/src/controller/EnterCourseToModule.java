package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Entry;
import management.Module;
import management.ModuleAdministration;

/**
 * Servlet implementation class EnterCourseToModule
 */
@WebServlet("/EnterCourseToModule")
public class EnterCourseToModule extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EnterCourseToModule() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ModuleAdministration ma = new ModuleAdministration();
		HttpSession session = request.getSession();

		long moduleID = 137060731287371l;
		Module module = ma.getModuleByID(moduleID);
		if (module != null) {
			List<Entry> entry = module.getEntryList();

			session.setAttribute("showModificationauthorFromModule",
					module.getModificationauthor());
			session.setAttribute("showModificationDateFromModule",
					module.getModificationDate());
			session.setAttribute("showCreationDateFromModule",
					module.getCreationDate());
			session.setAttribute("showEntryListFromModule", entry);
		}
		session.setAttribute("content", "enterCourseToModule");
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");

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
