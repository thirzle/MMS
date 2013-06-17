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
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {

	HttpSession session = request.getSession();
	try {
	    String button = request.getParameter("createModule");
	    if (button != null) {
		if (button.equals("sendModule")) {
		    
		    List<String> subjects = ma.getSubjects();
		    List<Course> courses = ma.getCourses();
		    List<Entry> entryList = (List<Entry>) session.getAttribute("showEntryListFromModule");
		   
		    String obligatoryModulSelect = request.getParameter("obligatoryModulSelect");
		    String voteModuleSelect = request.getParameter("voteModuleSelect");
		    String subject = request.getParameter("subjectSelect");

		    List<Course> obligatory = new ArrayList<Course>();
		    List<Course> vote = new ArrayList<Course>();
		    		
		    for (int i = 0; i < obligatoryModulSelect.length(); i++) {
			obligatory.add(courses.get(Character.getNumericValue(obligatoryModulSelect.charAt(i))));
		    }
		    for (int i = 0; i < obligatoryModulSelect.length(); i++) {
			vote.add(courses.get(Character.getNumericValue(voteModuleSelect.charAt(i))));
		    }
		    
		    entryList.add(new TextualEntry("Fach",5,subjects.get(Character.getNumericValue(subject.charAt(0)))));
		    entryList.add(new CourseEntry("Pflichtmodul",6, obligatory));
		    entryList.add(new CourseEntry("Wahlpflicht",7,vote));

		    long moduleID = Long.parseLong(session.getAttribute("moduleID").toString());
		    int version = Integer.parseInt(session.getAttribute("version").toString());
		    Module module = ma.getModuleByID(moduleID, version);
		    module.setEntryList(entryList);
		    ma.setCoursesToModule(module);
		    session.setAttribute("content", "showModulesForCoordinator");
		    response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		}
		
	    } else {
		long moduleID = Long
			.parseLong(request.getParameter("moduleID"));
		session.setAttribute("modulID", moduleID);
		int version = Integer.parseInt(request.getParameter("version"));
		session.setAttribute("version", version);
		Module module = ma.getModuleByID(moduleID, version);
		System.out.println("########### Eintr�ge ###########");
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
