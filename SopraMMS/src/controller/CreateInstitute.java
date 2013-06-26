package controller;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.ModuleAdministration;

import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class CreateInstitute
 */
@WebServlet("/CreateInstitute")
public class CreateInstitute extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateInstitute() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ModuleAdministration mAdmin = new ModuleAdministration();
		UserAdministration uAdmin = new UserAdministration();
		String infotext = "";
		LinkedList<String> instituteList = (LinkedList<String>) uAdmin.getAllInstitute();
		LinkedList<String> instituteIDList = (LinkedList<String>) uAdmin.getAllInstituteID();
		boolean wrongData = false;

		String instituteID = request.getParameter("instituteID");
		String instituteName = request.getParameter("instituteName");
		
		for(String string : instituteList){
			if(instituteName.equals(string)){
				session.setAttribute("wrongDataNewInst", "wrongName");
				session.setAttribute("content", "createNewInstitute");
				wrongData = true;
			}
		}
		if(!wrongData){
			for(String string : instituteIDList){
				if(instituteID.equals(string)){
					session.setAttribute("wrongDataNewInst", "wrongID");
					session.setAttribute("content", "createNewInstitute");
					wrongData = true;
				}
			}	
		}
		
		if(!wrongData){
			if (mAdmin.createInstitute(instituteID, instituteName, "in")) {
				infotext = "Das Institut " + instituteName
						+ " wurde erfolgreich erstellt.";
				session.setAttribute("content", "home");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="+infotext);		
			}
		}
		
		else {
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
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
