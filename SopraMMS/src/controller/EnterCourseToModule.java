package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Course;
import management.CourseEntry;
import management.Entry;
import management.Module;
import management.ModuleAdministration;
import management.TextualEntry;

/**
 * Servlet implementation class EnterCourseToModule
 */
@WebServlet("/EnterCourseToModule")
public class EnterCourseToModule extends SessionCheck {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
		    String button = request.getParameter("createModule");
		    if (button != null) {
				if (button.equals("sendModule")) {
				    
				    new ArrayList<Course>();
				    new ArrayList<Course>();
				    List<String> subjects = ma.getSubjects();
				    List<Course> courses = ma.getCourses();
				    List<Entry> entryList = (List<Entry>) session.getAttribute("showEntryListFromModule");
				    String obligatoryModulSelect = request.getParameter("obligatoryModulSelect");
				    String voteModuleSelect = request.getParameter("voteModuleSelect");
				    String subject = request.getParameter("subjectSelect");
	
				    for (int i = 0; i < obligatoryModulSelect.length(); i++) {
					courses.get(Character.getNumericValue(obligatoryModulSelect.charAt(i))).setObligatory(true);
				    }
				    for (int i = 0; i < voteModuleSelect.length(); i++) {
					courses.get(Character.getNumericValue(voteModuleSelect.charAt(i))).setObligatory(false);
				    }
				    
				    entryList.add(new TextualEntry("Fach",5,subjects.get(Character.getNumericValue(subject.charAt(0)))));
				    entryList.add(new CourseEntry("Studiengänge",6, courses));
					
				    long moduleID = Long.parseLong(session.getAttribute("moduleID").toString());
				    int version = Integer.parseInt(session.getAttribute("version").toString());
				    
				    Module module = ma.getModuleByID(moduleID, version);
				    module.setEntryList(entryList);
				    System.out.println(subjects.get(Character.getNumericValue(subject.charAt(0))));
				    module.setSubject(subjects.get(Character.getNumericValue(subject.charAt(0))));
				    ma.setCoursesToModule(module);
				  
				    response.sendRedirect("/SopraMMS/ShowModuleOverviewForCoordinator");
				}
		    } else {
		    	
				long moduleID = Long.parseLong(request.getParameter("moduleID"));
				session.setAttribute("moduleID", moduleID);
				
				int version = Integer.parseInt(request.getParameter("version"));
				session.setAttribute("version", version);
				
				Module module = ma.getModuleByID(moduleID, version);
				System.out.println("########### Einträge ###########");
				if (module != null) {
				    for (Entry entry : module.getEntryList()) {
					System.out.println(entry.toString());
				    }
				    System.out.println("################################");
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
		} catch (NullPointerException e) {
		    System.err.println(e);
		    System.err.println(e.getMessage());
		} catch (StringIndexOutOfBoundsException e) {
			System.err.println(e);
		    System.err.println(e.getMessage());
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
