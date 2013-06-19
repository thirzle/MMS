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
 * Servlet implementation class ShowUnapprovedModulesOverview
 */
@WebServlet("/ShowUnapprovedModulesOverview")
public class ShowUnapprovedModulesOverview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowUnapprovedModulesOverview() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ShowUnapprovedModulesOverview");
		HttpSession session = request.getSession();
		ModuleAdministration mAdmin = new ModuleAdministration();
		User user = (User) session.getAttribute("user");

		// TODO getModulesOverview
		List<Module> moduleList = mAdmin.getUnapprovedModulesByAuthor(user.getLogin());

		session.setAttribute("moduleListForModulemanager", moduleList);
		session.setAttribute("content", "showModulesForModulemanager");
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");

		session.removeAttribute("fieldsTypeAEdit");
		session.removeAttribute("fieldsTypeBEdit");
		session.removeAttribute("fieldsTypeCEdit");
		session.removeAttribute("fieldsTypeDEdit");
		session.removeAttribute("fieldsTypeEEdit");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
