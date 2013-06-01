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
public class AddNews extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddNews() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title").trim();
		String content = request.getParameter("content").trim();
		String frontend = request.getParameter("frontend");
		String backend = request.getParameter("backend");
		int visibility = -1;

		if (title.equals("") || content.equals("")) {
			request.getSession().setAttribute("content", "createNews");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?newsstatus=addtitleortext&title="
					+ title + "&text=" + content + "");
		} else if (frontend == null && backend == null) {
			request.getSession().setAttribute("content", "createNews");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?newsstatus=choosevisibility&title="
					+ title + "&text=" + content + "");
		} else {
			visibility = 0;

			if (frontend != null) {
				visibility = 1;
			} else if (backend != null) {
				visibility = 2;
			} else {
				visibility = 0;
			}
			UserAdministration ua = new UserAdministration();
			String[] data = { title, content };
			ua.addNews(data, visibility);
			request.getSession().setAttribute("content", "showNews");
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
