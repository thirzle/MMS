package controller;

import java.io.IOException;
import java.util.LinkedList;
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
 * Servlet implementation class ShowModulesOverviewForEditor
 */
@WebServlet("/ShowModulesOverviewForEditor")
public class ShowModulesOverviewForEditor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowModulesOverviewForEditor() {
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
		LinkedList<String> instituteIDListOfUser = (LinkedList) user.getInstitute();
		LinkedList<Module> moduleListForEditor = new LinkedList();
						
		if(request.getParameter("editorMenu").equals("checkModule")){
			for (String instituteID : instituteIDListOfUser) {
				moduleListForEditor.addAll(mAdmin.getModuleOverviewForEditor(instituteID));
				session.setAttribute("moduleListForEditor", moduleListForEditor);
				session.setAttribute("content", "showApproveModulesOverviewForEditor");
			}
		}			
		else if(request.getParameter("editorMenu").equals("editModule")){
			moduleListForEditor = (LinkedList) mAdmin.getModules();
			session.setAttribute("moduleListForEditor", moduleListForEditor);
			session.setAttribute("content", "showEditModulesOverviewForEditor");
		}	
		
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
