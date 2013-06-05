package module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserAdministration;

import management.Entry;
import management.ModuleAdministration;
import management.TextualEntry;

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

		UserAdministration ua = new UserAdministration();
		ModuleAdministration ma = new ModuleAdministration();
		HttpSession session = request.getSession();

		if (session.getAttribute("institutes") == null) {

			List<String[]> institutes = ua.getAllInstitutesByName(session
					.getAttribute("loginname").toString());
			session.setAttribute("institutes", institutes);
		}

		// TypA --> Vordefinierte Pflichfelder Feld
		// TypB --> Vordefinierte Pflichfelder Textarea
		// TypC --> Selbstdefinierte Felder Textarea
		// TypD --> Vordefiniertes Feld Aufwand
		ArrayList<String[]> fieldsTypeA = new ArrayList<>();
		ArrayList<String[]> fieldsTypeB = new ArrayList<>();
		ArrayList<String[]> fieldsTypeC = new ArrayList<>();
		ArrayList<String[]> fieldsTypeD = new ArrayList<>();
		
		String institute = request.getParameter("selectedInstitute");
		System.out.println("(CreateModule.java) selectedInstituteID: "+institute);
		// Fuelle Liste mit Standartwerten falls das Session Attribut noch nicht
		// besteht, anderenfalls kopiere das Session Attribut in das lokale
		// Attribut

		// Fuer TypA
		if (session.getAttribute("fieldsTypeA") == null) {
			fieldsTypeA.add(new String[] { "K&uuml;rzel", "" });
			fieldsTypeA.add(new String[] { "Titel", "" });
			fieldsTypeA.add(new String[] { "Verantwortlicher", "" });
			fieldsTypeA.add(new String[] { "Turnus", "" });
			fieldsTypeA.add(new String[] { "Sprache", "" });
			fieldsTypeA.add(new String[] { "Pr&uuml;fungsform", "" });
			fieldsTypeA.add(new String[] { "Notenbildung", "" });

			System.out.println("fieldsTypeA");

		} else {
			fieldsTypeA.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeA"));
		}

		// Fuer TypD
		if (session.getAttribute("fieldsTypeD") == null) {
			fieldsTypeD.add(new String[] { "Pr&auml;senzzeit", "" });
			fieldsTypeD.add(new String[] { "Nacharbeitung", "" });
			fieldsTypeD.add(new String[] { "&Uuml;bungsaufgaben", "" });
			fieldsTypeD.add(new String[] { "Pr&uuml;fung", "" });
			fieldsTypeD.add(new String[] { "", "" });
		} else {
			fieldsTypeD.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeD"));
		}

		// Fuer TypB
		if (session.getAttribute("fieldsTypeB") == null) {

			fieldsTypeB.add(new String[] { "Inhalt", "" });
			fieldsTypeB.add(new String[] { "Lernziele", "" });
			fieldsTypeB.add(new String[] { "Literatur", "" });

			System.out.println("fieldsTypeB");

		} else {
			fieldsTypeB.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeB"));
		}

		// Fuer TypC
		if (session.getAttribute("fieldsTypeC") != null) {
			fieldsTypeC.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeC"));
		}

		// Lese Parameter aus der URL und fuege Sie den jeweiligen Listen hinzu
		// Fuer TypA
		for (int i = 0; i < fieldsTypeA.size(); i++) {
			String[] entry = fieldsTypeA.get(i);
			if (request.getParameter(i + "ContentA") != null) {
				entry[1] = request.getParameter(i + "ContentA").trim();
			}
		}
		// Fuer TypD
		for (int i = 0; i < fieldsTypeD.size(); i++) {
			String[] entry = fieldsTypeD.get(i);
			if (request.getParameter(i + "TitleD") != null) {
				entry[0] = request.getParameter(i + "TitleD").trim();
			}
			if (request.getParameter(i + "ContentD") != null) {
				entry[1] = request.getParameter(i + "ContentD").trim();
			}
		}

		// Fuer TypB
		for (int i = 0; i < fieldsTypeB.size(); i++) {
			String[] entry = fieldsTypeB.get(i);
			if (request.getParameter(i + "ContentB") != null) {
				entry[1] = request.getParameter(i + "ContentB").trim();
			}
		}
		// Fuer TypC
		for (int i = 0; i < fieldsTypeC.size(); i++) {
			String[] entry = fieldsTypeC.get(i);
			if (request.getParameter(i + "TitleC") != null) {
				entry[0] = request.getParameter(i + "TitleC").trim();
			}
			if (request.getParameter(i + "ContentC") != null) {
				entry[1] = request.getParameter(i + "ContentC").trim();
			}
			fieldsTypeC.set(i, entry);
		}

		if (request.getParameter("createModule") != null) {
			// Bei Klick Zeile hinzufuegen
			if (request.getParameter("createModule").equals("addRow")) {
				fieldsTypeC.add(new String[] { "", "" });
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			}
			// Modul für Sitzung Speichern
			else if (request.getParameter("createModule").equals("saveModule")) {
				System.out
						.println("(CreateModule.java): Modul für Sitzung gespeichert");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			}
			// Bei Klick Module Speichern
			else if (request.getParameter("createModule").equals("sendModule")) {

				ArrayList<Entry> module = new ArrayList<>();

				for (String[] strings : fieldsTypeA) {
					TextualEntry entry = new TextualEntry(strings[0],
							strings[1]);
					module.add(entry);
				}
				for (String[] strings : fieldsTypeB) {
					TextualEntry entry = new TextualEntry(strings[0],
							strings[1]);
					module.add(entry);
				}
				for (String[] strings : fieldsTypeC) {
					TextualEntry entry = new TextualEntry(strings[0],
							strings[1]);
					module.add(entry);
				}
				fieldsTypeA = fieldsTypeB = fieldsTypeC = fieldsTypeD = null;				
			
				// TODO Modul an DB uebertragen
				// Spezifische Felder für Turnus, LP, Aufwand, Studiengang
				ma.createModule(module, session.getAttribute("loginname").toString(), institute);
				// TODO pruefen ob Pflichfelder befuellt sind

				response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true");
			}
		}
		// Bei Klick entsprechende Zeile loeschen
		else if (request.getParameter("deleteRow") != null) {
			int deleteEntry = Integer.parseInt(request
					.getParameter("deleteRow").replace("Delete", ""));
			fieldsTypeC.remove(deleteEntry);
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		}
		// Anzeige beim Aufruf von Modul einfuegen
		else if (request.getParameter("newModule") == null) {
			session.setAttribute("content", "createNewModule");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		}

		session.setAttribute("fieldsTypeA", fieldsTypeA);
		session.setAttribute("fieldsTypeB", fieldsTypeB);
		session.setAttribute("fieldsTypeC", fieldsTypeC);
		session.setAttribute("fieldsTypeD", fieldsTypeD);
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
