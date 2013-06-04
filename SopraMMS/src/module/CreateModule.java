package module;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

		HttpSession session = request.getSession();

		// TypA --> Vordefinierte Pflichfelder Feld
		// TypB --> Vordefinierte Pflichfelder Textarea
		// TypC --> Selbstdefinierte Felder Textarea
		ArrayList<String[]> fieldsTypeA = new ArrayList<>();
		ArrayList<String[]> fieldsTypeB = new ArrayList<>();
		ArrayList<String[]> fieldsTypeC = new ArrayList<>();

		// Fuelle Liste mit Standartwerten falls das Session Attribut noch nicht
		// besteht, anderenfalls kopiere das Session Attribut in das lokale
		// Attribut

		// Fuer TypA
		if (session.getAttribute("fieldsTypeA") == null) {
			fieldsTypeA.add(new String[] { "K�rzel", "" });
			fieldsTypeA.add(new String[] { "Titel", "" });
			fieldsTypeA.add(new String[] { "Dauer", "" });
			fieldsTypeA.add(new String[] { "LP", "" });
			fieldsTypeA.add(new String[] { "Turnus", "" });
			fieldsTypeA.add(new String[] { "Sprache", "" });
			fieldsTypeA.add(new String[] { "Pr�fungsform", "" });
			fieldsTypeA.add(new String[] { "Notenbildung", "" });
			
			System.out.println("fieldsTypeA");
			
		} else {
			fieldsTypeA.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeA"));
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
		for (String[] entry : fieldsTypeA) {
			if (request.getParameter(entry[0] + "Content") != null) {
				entry[1] = request.getParameter(entry[0] + "Content").trim();
			}
		}
		// Fuer TypB
		for (String[] entry : fieldsTypeB) {
			if (request.getParameter(entry[0] + "Content") != null) {
				entry[1] = request.getParameter(entry[0] + "Content").trim();
			}
		}
		// Fuer TypC
		for (int i = 0; i < fieldsTypeC.size(); i++) {
			String[] entry = fieldsTypeC.get(i);
			entry[0] = request.getParameter(i + "Title").trim();
			if (request.getParameter(i + "Content") != null) {
				entry[1] = request.getParameter(i + "Content").trim();
			}
			fieldsTypeC.set(i, entry);
		}

		
		// Bei Klick Zeile hinzufuegen
		if (request.getParameter("addRow") != null) {

			fieldsTypeC.add(new String[] { "", ""});
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		} 
		// Bei Klick entsprechende Zeile loeschen
		else if (request.getParameter("deleteRow") != null) {
			int deleteEntry = Integer.parseInt(request.getParameter("deleteRow").replace(
					"Delete", ""));
			fieldsTypeC.remove(deleteEntry);
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		}
		// Bei Klick Module Speichern
		else if (request.getParameter("saveModule") != null) {
			// TODO pruefen ob Pflichfelder befuellt sind
			// TODO evtl Pruefansicht
			
			ModuleAdministration ma = new ModuleAdministration();
			
			ArrayList<Entry> module = new ArrayList<>();
			
			for (String[] strings : fieldsTypeA) {
				TextualEntry entry = new TextualEntry(strings[0], strings[1]);
				module.add(entry);
			}
			for (String[] strings : fieldsTypeB) {
				TextualEntry entry = new TextualEntry(strings[0], strings[1]);
				module.add(entry);
			}
			for (String[] strings : fieldsTypeC) {
				TextualEntry entry = new TextualEntry(strings[0], strings[1]);
				module.add(entry);
			}
			fieldsTypeA=fieldsTypeB=fieldsTypeC=null;
			
			
			System.out.println("############ Modul #############");
			for (Entry entry : module) {
				System.out.println("-> "+entry.getTitle());
				System.out.println("     "+entry.getContent());
			}
			System.out.println("################################");
			
			//TODO Modul an DB uebertragen
			//Spezifische Felder f�r Turnus, LP, Aufwand, Studiengang 
			
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true");			
		}
		// Anzeige beim Aufruf von Modul einfuegen
		else if (request.getParameter("newModule") == null) {
			session.setAttribute("content", "createNewModule");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		}

		session.setAttribute("fieldsTypeA", fieldsTypeA);
		session.setAttribute("fieldsTypeB", fieldsTypeB);
		session.setAttribute("fieldsTypeC", fieldsTypeC);

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
