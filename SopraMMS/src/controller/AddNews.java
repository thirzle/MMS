package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserAdministration;

/**
 * Servlet implementation class CreatNews
 */
@WebServlet("/AddNews")
public class AddNews extends SessionCheck {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddNews() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// checks whether the user is logged
		if(isLoggedIn(request, response)){
			// load variables from request
			String title = request.getParameter("title").trim();
			String content = request.getParameter("content").trim();
			String frontend = request.getParameter("frontend");
			String backend = request.getParameter("backend");
			int visibility;
			
			// check: text and title available
			if (title.equals("") || content.equals("")) {
				request.getSession().setAttribute("content", "createNews");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?newsstatus=addtitleortext&title="
						+ title + "&text=" + content + "");
			// check: visibility available
			} else if (frontend == null && backend == null) {
				request.getSession().setAttribute("content", "createNews");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?newsstatus=choosevisibility&title="
					+ title + "&text=" + content + "");
			// all needed variables are available
			} else {
				visibility = 0;
				
				// defines the visibility status
				if (frontend != null && backend == null) {
					visibility = 1;
				} else if (backend != null && frontend == null) {
					visibility = 2;
				} else {
					visibility = 0;
				}
				
				// sends data to data controller
				String[] data = { title, content };
				uAdmin.addNews(data, visibility);
				
				// send request including info text for the user
				request.getSession().setAttribute("content", "showNews");
				String infoText="Die Nachricht '"+title+"' wurde erfolgreich erstellt.";
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?infotext="+infoText);
			}
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
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
