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
		// checks whether the user is logged
		if(isLoggedIn(request, response)) {
			// initialize variables
			ArrayList<String> deleted = new ArrayList<>();
			int number = uAdmin.numberOfNews();
			
			// check whether a news entry should be deleted
			for (int i = 0; i < number; i++) {
				if(request.getParameter("delete"+i)!=null)
				{
					deleted.add(request.getParameter("delete"+i));
				}
			}
			
			// send request to delete to data controller
			for (String title : deleted) {
				uAdmin.deleteNews(title);
			}
			
			// send request including info text for the user
			request.getSession().setAttribute("content", "showNews");
			String infoText="";
			if(deleted.size()==1){
				infoText="Die Nachricht '"+deleted.get(0)+"' wurde erfolgreich gelöscht";
			}else if(deleted.size()>1){
				infoText="Die "+deleted.size()+" Nachricht wurden erfolgreich gelöscht";
			}
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?infotext="+infoText);
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
