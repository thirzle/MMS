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
		if (isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			try {
				String button = request.getParameter("createModule");
				if (button != null) {
					if (button.equals("sendModule")) {
						System.out.println("getParameter('obligatoryModulSelect')"+request.getParameter("obligatoryModulSelect"));
						System.out.println("getParameter('voteModuleSelect')"+request.getParameter("voteModuleSelect"));
						new ArrayList<Course>();
						new ArrayList<Course>();
						List<String> subjects = mAdmin.getSubjects();
						List<Course> courses = mAdmin.getCourses();
						List<Course> tobeholdcourses = new ArrayList<Course>();
						List<Entry> entryList = (List<Entry>) session
								.getAttribute("showEntryListFromModule");
						String obligatoryModulSelect = (request
								.getParameter("obligatoryModulSelect"))
								.replace("empty", "");
						String voteModuleSelect = (request
								.getParameter("voteModuleSelect")).replace(
								"empty", "");
						String subject = request.getParameter("subjectSelect");
						System.out.println("obligatoryModulSelect: "+obligatoryModulSelect);
						System.out.println("voteModuleSelect: "+voteModuleSelect);
						if (!obligatoryModulSelect.isEmpty()) {
							for (int i = 0; i < obligatoryModulSelect.length(); i++) {
								Course tmp = courses.get(Character.getNumericValue(obligatoryModulSelect.charAt(i)));
								tmp.setObligatory(false);
								tobeholdcourses.add(tmp);
							}
						}
						if (!voteModuleSelect.isEmpty()) {
							for (int i = 0; i < voteModuleSelect.length(); i++) {
								Course tmp = courses.get(Character.getNumericValue(voteModuleSelect.charAt(i)));
								tmp.setObligatory(true);
								tobeholdcourses.add(tmp);
							}
						}
						entryList.add(new TextualEntry("Fach", 5, subjects
								.get(Character.getNumericValue(subject
										.charAt(0)))));
						entryList.add(new CourseEntry("Studiengänge", 6,
								tobeholdcourses));

						long moduleID = Long.parseLong(session.getAttribute(
								"moduleID").toString());
						int version = Integer.parseInt(session.getAttribute(
								"version").toString());

						Module module = mAdmin.getModuleByID(moduleID, version);
						module.setEntryList(entryList);
						System.out.println(subjects.get(Character
								.getNumericValue(subject.charAt(0))));
						module.setSubject(subjects.get(Character
								.getNumericValue(subject.charAt(0))));
						mAdmin.setCoursesToModule(module);

						// response.sendRedirect("/SopraMMS/ShowModuleOverviewForCoordinator");
						String infoText = "Die ausgewählten Studiengänge und das Fach wurden zu dem Modul '"
								+ module.getName() + "' hinzugfügt.";
						response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="+infoText);
					}
				} else {

					long moduleID = Long.parseLong(request
							.getParameter("moduleID"));
					session.setAttribute("moduleID", moduleID);

					int version = Integer.parseInt(request
							.getParameter("version"));
					session.setAttribute("version", version);

					Module module = mAdmin.getModuleByID(moduleID, version);
					System.out.println("########### Einträge ###########");
					if (module != null) {
						for (Entry entry : module.getEntryList()) {
							System.out.println(entry.toString());
						}
						System.out.println("################################");
						List<Entry> entry = module.getEntryList();
						session.setAttribute(
								"showModificationauthorFromModule",
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
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="
					+ error);
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
