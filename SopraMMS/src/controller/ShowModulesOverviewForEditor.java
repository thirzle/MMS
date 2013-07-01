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
import user.User;

/**
 * Servlet implementation class ShowModulesOverviewForEditor
 * 
 * @author Teresa Hirzle, David Lehr
 */
@WebServlet("/ShowModulesOverviewForEditor")
public class ShowModulesOverviewForEditor extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowModulesOverviewForEditor() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			LinkedList<String> instituteIDListOfUser = (LinkedList<String>) user.getInstitute();
			LinkedList<Module> moduleListForEditor = new LinkedList<Module>();
							
			if(request.getParameter("editorMenu").equals("checkModule")){
					moduleListForEditor.addAll(mAdmin.getModuleOverviewForEditor(instituteIDListOfUser));
					session.setAttribute("moduleListForEditor", moduleListForEditor);
					session.setAttribute("content", "showApproveModulesOverviewForEditor");
			}			
			else if(request.getParameter("editorMenu").equals("editModule")){
				moduleListForEditor = (LinkedList<Module>) mAdmin.getModules();
				session.setAttribute("moduleListForEditor", moduleListForEditor);
				session.setAttribute("content", "showEditModulesOverviewForEditor");
			}	
			
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
