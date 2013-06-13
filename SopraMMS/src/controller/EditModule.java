package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.EffortEntry;
import management.Entry;
import management.Module;
import management.ModuleAdministration;
import management.SelfStudy;
import management.TextualEntry;
import user.User;
import user.UserAdministration;

import com.ibm.icu.text.SimpleDateFormat;

/**
 * Servlet implementation class EditModule
 */
@WebServlet("/EditModule")
public class EditModule extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditModule() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		UserAdministration ua = new UserAdministration();
		ModuleAdministration ma = new ModuleAdministration();
		HttpSession session = request.getSession();
		LinkedList<Entry> entryList = new LinkedList<Entry>();

		// lade Institutsliste durch den Benutzer
		if (session.getAttribute("institutesModuleEntry") == null) {

			List<String[]> institutes = ua.getAllInstitutesByName(session
					.getAttribute("loginname").toString());
			session.setAttribute("institutesModuleEntry", institutes);
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
		System.out.println("(CreateModule.java) selectedInstituteID: "
				+ institute);

		// Fuelle Liste mit Standartwerten falls das Session Attribut noch nicht
		// besteht, anderenfalls kopiere das Session Attribut in das lokale
		// Attribut

		// Modulladen

		Module moduleToEdit = null;

		if (session.getAttribute("editFieldsTypeA") == null
				|| session.getAttribute("editFieldsTypeB") == null
				|| session.getAttribute("editFieldsTypC") == null
				|| session.getAttribute("editFieldsTypeD") == null) {
			String moduleData = request.getParameter("selectedModuleToEdit");
			if (moduleData != null) {
				String[] data = moduleData.split(" ");
				moduleToEdit = ma.getModuleByID(Long.parseLong(data[0]),
						Integer.parseInt(data[1]));
			

				System.out.println("(EditModule.java): Modul (" + data[0] + "-"
						+ data[1] + ") zum bearbeiten aus DB geladen.");
			}
		}

		// Fuer TypA
		if (session.getAttribute("editFieldsTypeA") == null
				&& moduleToEdit != null) {

			List<Entry> list = moduleToEdit.getEntryList();
			moduleToEdit.print();

			for (Entry entry : list) {
				switch (entry.getTitle()) {
				case "Kürzel":
					fieldsTypeA
							.add(new String[] { "Kürzel", entry.getContent() });
					break;

				case "Titel":
					fieldsTypeA
							.add(new String[] { "Titel", entry.getContent() });
					break;
				case "Verantwortlicher":
					fieldsTypeA.add(new String[] { "Verantwortlicher",
							entry.getContent() });
					break;
				case "Turnus":

					fieldsTypeA
							.add(new String[] { "Turnus", entry.getContent() });
					break;
				case "Sprache":
					fieldsTypeA.add(new String[] { "Sprache",
							entry.getContent() });
					break;
				}
			}

			

		} else {
			fieldsTypeA.addAll((ArrayList<String[]>) session
					.getAttribute("editFieldsTypeA"));
		}

		// Fuer TypD
		if (session.getAttribute("editFieldsTypeD") == null) {
			fieldsTypeD.add(new String[] { "Präsenzzeit", "" });
			fieldsTypeD.add(new String[] { "Nacharbeitung", "" });
			fieldsTypeD.add(new String[] { "Übungsaufgaben", "" });
			fieldsTypeD.add(new String[] { "Prüfung", "" });
			fieldsTypeD.add(new String[] { "", "" });

		} else {
			fieldsTypeD.addAll((ArrayList<String[]>) session
					.getAttribute("editFieldsTypeD"));
		}

		// Fuer TypB
		if (session.getAttribute("editFieldsTypeB") == null) {
			fieldsTypeB.add(new String[] { "Prüfungsform", "" });
			fieldsTypeB.add(new String[] { "Notenbildung", "" });
			fieldsTypeB.add(new String[] { "Inhalt", "" });
			fieldsTypeB.add(new String[] { "Lernziele", "" });
			fieldsTypeB.add(new String[] { "Literatur", "" });

			System.out.println("editFieldsTypeB");

		} else {
			fieldsTypeB.addAll((ArrayList<String[]>) session
					.getAttribute("editFieldsTypeB"));
		}

		// Fuer TypC
		if (session.getAttribute("editFieldsTypeC") != null) {
			fieldsTypeC.addAll((ArrayList<String[]>) session
					.getAttribute("editFieldsTypeC"));
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
				int order = 0;
				for (String[] strings : fieldsTypeA) {
					TextualEntry entry = new TextualEntry(strings[0], order++,
							strings[1]);
					module.add(entry);
				}

				// Aufwand speichern
				int pt = Integer.parseInt(fieldsTypeD.get(0)[1]);
				EffortEntry effort = new EffortEntry("Zeitaufwand", order++, pt);
				List<SelfStudy> selfStudyList = new ArrayList<>();

				for (int i = 1; i < fieldsTypeD.size(); i++) {
					String[] entry = fieldsTypeD.get(i);
					if (!(entry[0].equals("") || entry[1].equals(""))) {
						selfStudyList.add(new SelfStudy(entry[0], Integer
								.parseInt(entry[1])));
					}
				}
				effort.setSelfStudyList(selfStudyList);
				module.add(effort);

				// Textfelder speichern
				for (String[] strings : fieldsTypeB) {
					TextualEntry entry = new TextualEntry(strings[0], order++,
							strings[1]);
					module.add(entry);
				}
				for (String[] strings : fieldsTypeC) {
					TextualEntry entry = new TextualEntry(strings[0], order++,
							strings[1]);
					module.add(entry);
				}
				fieldsTypeA = fieldsTypeB = fieldsTypeC = fieldsTypeD = null;
				// TODO leere selbsterstellte felder aussortieren
				// TODO Modul an DB uebertragen
				// Spezifische Felder für Turnus, LP, Aufwand, Studiengang
				ma.createModuleByModuleManager(module,
						((User) session.getAttribute("user")).getLogin(),
						institute);
				// TODO pruefen ob Pflichfelder befuellt sind

				// insert into History "Module created"
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date currentTime = new Date();
				String date = formatter.format(currentTime);
				String title = null;
				for (Entry entry : module) {
					if (entry.getTitle().equals("Kürzel")) {
						title = entry.getContent();
					}
				}

				response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true");
			}
		}
		// Bei Klick entsprechende Zeile loeschen
		else if (request.getParameter("deleteRow") != null) {
			int deleteEntry = Integer.parseInt(request
					.getParameter("deleteRow").replace("Delete", ""));
			fieldsTypeC.remove(deleteEntry);
			session.setAttribute("content", "editExistingModule");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		}
		// Anzeige beim Aufruf von Modul einfuegen
		else if (request.getParameter("newModule") == null) {
			session.setAttribute("content", "editExistingModule");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		}

		session.setAttribute("editFieldsTypeA", fieldsTypeA);
		session.setAttribute("editFieldsTypeB", fieldsTypeB);
		session.setAttribute("editFieldsTypeC", fieldsTypeC);
		session.setAttribute("editFieldsTypeD", fieldsTypeD);
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
