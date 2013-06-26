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
 * Servlet implementation class ViewModule
 */
@WebServlet("/ViewModule")
public class ViewModule extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewModule() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			if(isLoggedIn(request, response)) {
				HttpSession session = request.getSession();
				String selectedModule = request.getParameter("selectedModuleToEdit");
				long moduleID;
				int version;
				String instituteID;
				String institute = null;
				Module module = null;
				String[] parts;
				if(selectedModule != null){
					parts = selectedModule.split(" ");
					moduleID = Long.parseLong(parts[0]);
					version = Integer.parseInt(parts[1]);
					module = mAdmin.getModuleByID(moduleID, version);
					instituteID = module.getInstituteID();
					institute = mAdmin.getInstituteName(instituteID);
				}
				
				System.out.println("ViewModule: module == null "+(module==null));
				
				session.setAttribute("instituteForViewModule", institute);
				session.setAttribute("moduleForViewModule", module);
				session.setAttribute("content", "viewModule");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			}
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
