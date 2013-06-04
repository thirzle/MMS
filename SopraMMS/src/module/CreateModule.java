package module;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		System.out.println("(CreateModule.java):"
				+ request.getParameter("addRow"));

		ArrayList<String[]> fieldsTypeA = new ArrayList<>();
		ArrayList<String[]> fieldsTypeB = new ArrayList<>();
		ArrayList<String[]> fieldsTypeC = new ArrayList<>();

		if (session.getAttribute("fieldsTypeA") != null) {
			fieldsTypeA.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeA"));
		} else {
			fieldsTypeA.add(new String[] { "Kürzel", "" });
			fieldsTypeA.add(new String[] { "Titel", "" });
			fieldsTypeA.add(new String[] { "Dauer", "" });
			fieldsTypeA.add(new String[] { "LP", "" });
			fieldsTypeA.add(new String[] { "Turnus", "" });
			fieldsTypeA.add(new String[] { "Sprache", "" });
			fieldsTypeA.add(new String[] { "Prüfungsform", "" });
			fieldsTypeA.add(new String[] { "Notenbildung", "" });
		}
		
		if (session.getAttribute("fieldsTypeB") != null) {
			fieldsTypeB.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeB"));
		} else {

			fieldsTypeB.add(new String[] { "Inhalt", " " });
			fieldsTypeB.add(new String[] { "Lernziele", " " });
			fieldsTypeB.add(new String[] { "Literatur", " " });
		}
		if (session.getAttribute("fieldsTypeC") != null) {
			fieldsTypeC.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeC"));
		}

		for (String[] entry : fieldsTypeA) {
			if(request.getParameter(entry[0] + "Content")!=null){
				entry[1] = request.getParameter(entry[0] + "Content");
			}
		}
		for (String[] entry : fieldsTypeB) {
			if(request.getParameter(entry[0] + "Content")!=null){
				entry[1] = request.getParameter(entry[0] + "Content");
			}
		}
		for (int i = 0; i < fieldsTypeC.size(); i++) {
			String[] entry = fieldsTypeC.get(i);
			entry[0] = request.getParameter(i + "Title");
			if(request.getParameter(entry[0] + "Content")!=null){
				entry[1] = request.getParameter(entry[0] + "Content");
			}
		}

		if (request.getParameter("newModule") == null
				&& request.getParameter("addRow") == null) {

			session.setAttribute("fieldsTypeA", fieldsTypeA);
			session.setAttribute("fieldsTypeB", fieldsTypeB);
			session.setAttribute("fieldsTypeC", fieldsTypeC);

			session.setAttribute("content", "createNewModule");

			response.sendRedirect("/SopraMMS/guiElements/home.jsp");

		} else if (request.getParameter("addRow") != null) {
			fieldsTypeC.add(new String[] { "", "" });

			session.setAttribute("fieldsTypeA", fieldsTypeA);
			session.setAttribute("fieldsTypeB", fieldsTypeB);
			session.setAttribute("fieldsTypeC", fieldsTypeC);
			
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");

		} else if (request.getParameter("saveModule")!=null) {
			//TODO pruefen ob Pflichfelder befuellt sind
			//TODO evtl Pruefansicht
			//TODO letztes selbsterstelltes feld wir d nicht gespeichert
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
