package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserAdministration;

/**
 * Servlet implementation class DeleteNews
 */
@WebServlet("/DeleteNews")
public class DeleteNews extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteNews() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			ArrayList<String> deleted = new ArrayList<>();
			int number = uAdmin.numberOfNews();
			for (int i = 0; i < number; i++) {
				if(request.getParameter("delete"+i)!=null)
				{
					deleted.add(request.getParameter("delete"+i));
				}
			}
			
			for (String title : deleted) {
				uAdmin.deleteNews(title);
			}
			request.getSession().setAttribute("content", "showNews");
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
