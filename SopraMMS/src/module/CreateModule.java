package module;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

/**
 * Servlet implementation class CreateModule
 */
@WebServlet("/CreateModule")
public class CreateModule extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateModule() {
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
		
		if (request.getParameter("newModule") == null) {
			ArrayList<String> requiredFieldsTypeA = new ArrayList<>();
			requiredFieldsTypeA.add("Kürzel");
			requiredFieldsTypeA.add("Titel");
			requiredFieldsTypeA.add("Dauer");
			requiredFieldsTypeA.add("LP");
			requiredFieldsTypeA.add("Turnus");
			requiredFieldsTypeA.add("Sprache");
			requiredFieldsTypeA.add("Prüfungsform");
			requiredFieldsTypeA.add("Notenbildung");
			
			ArrayList<String> requiredFieldsTypeB = new ArrayList<>();
			requiredFieldsTypeB.add("Inhalt");
			requiredFieldsTypeB.add("Lernziele");
			requiredFieldsTypeB.add("Literatur");
			
			ArrayList<String> fieldsTypeC = new ArrayList<>();
					
			session.setAttribute("fieldsTypeA", requiredFieldsTypeA);
			session.setAttribute("fieldsTypeB", requiredFieldsTypeB);
			session.setAttribute("fieldsTypeC", fieldsTypeC);
			
			session.setAttribute("content", "createNewModule");
			
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");

		} else if (request.getParameter("newModule").equals("newRow")) {
		} else if (request.getParameter("newModule").equals("missingField")) {
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?newModule=missingField");
		} else {

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
