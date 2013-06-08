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

import com.ibm.icu.text.SimpleDateFormat;

import management.EffortEntry;
import management.Entry;
import management.Module;
import management.ModuleAdministration;
import management.SelfStudy;
import management.TextualEntry;
import user.User;
import user.UserAdministration;

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
		LinkedList<Entry> entryList = new LinkedList<Entry>();
		boolean edit = false;

		if (session.getAttribute("edit") != null) {
			edit = true;
			long moduleID = Long.parseLong((request
					.getParameter("selectedModule")));
			Module editModule = ma.getModuleByID(moduleID);
			// entryList ist doppelt
			entryList = (LinkedList<Entry>) ma.getEntryListOfModule(editModule);

		}

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

		// Fuer TypA

		if (session.getAttribute("fieldsTypeA") == null) {
			if (edit && !entryList.isEmpty()) {
				// Type A
				for (Entry entry : entryList) {
					if (entry.getTitle().equals("Kürzel")) {
						fieldsTypeA.add(new String[] { "Kürzel",
								entry.getContent() });
					} else if (entry.getTitle().equals("Titel")) {
						fieldsTypeA.add(new String[] { "Titel",
								entry.getContent() });
					} else if (entry.getTitle().equals("Verantwortlicher")) {
						fieldsTypeA.add(new String[] { "Verantwortlicher",
								entry.getContent() });
					} else if (entry.getTitle().equals("Turnus")) {
						fieldsTypeA.add(new String[] { "Turnus",
								entry.getContent() });
					} else if (entry.getTitle().equals("Sprache")) {
						fieldsTypeA.add(new String[] { "Sprache",
								entry.getContent() });
					} else if (entry.getTitle().equals("Prüfungsform")) {
						fieldsTypeA.add(new String[] { "Prüfungsform",
								entry.getContent() });
					} else if (entry.getTitle().equals("Notenbildung")) {
						fieldsTypeA.add(new String[] { "Notenbildung",
								entry.getContent() });
					}
				}
			}

			else {
				fieldsTypeA.add(new String[] { "Kürzel", "" });
				fieldsTypeA.add(new String[] { "Titel", "" });
				fieldsTypeA.add(new String[] { "Verantwortlicher", "" });
				fieldsTypeA.add(new String[] { "Turnus", "" });
				fieldsTypeA.add(new String[] { "Sprache", "" });
				fieldsTypeA.add(new String[] { "Prüfungsform", "" });
				fieldsTypeA.add(new String[] { "Notenbildung", "" });
			}

			System.out.println("fieldsTypeA");

		} else {
			fieldsTypeA.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeA"));
		}

		// Fuer TypD
		if (session.getAttribute("fieldsTypeD") == null) {
			if (edit && !entryList.isEmpty()) {
				// TODO in EffortEntry sind komische Sachen gespeichert
				for (Entry entry : entryList) {
					if (entry.getTitle().equals("Präsenzzeit")) {
						fieldsTypeD.add(new String[] { "Präsenzzeit",
								entry.getContent() });
					} else if (entry.getTitle().equals("Nacharbeitung")) {
						fieldsTypeD.add(new String[] { "Nacharbeitung",
								entry.getContent() });
					} else if (entry.getTitle().equals("Übungsaufgaben")) {
						fieldsTypeD.add(new String[] { "Übungsaufgaben",
								entry.getContent() });
					} else if (entry.getTitle().equals("Prüfung")) {
						fieldsTypeD.add(new String[] { "Prüfung",
								entry.getContent() });
					}
					fieldsTypeD.add(new String[] { "", "" });

				}
			} else {
				fieldsTypeD.add(new String[] { "Präsenzzeit", "" });
				fieldsTypeD.add(new String[] { "Nacharbeitung", "" });
				fieldsTypeD.add(new String[] { "Übungsaufgaben", "" });
				fieldsTypeD.add(new String[] { "Prüfung", "" });
				fieldsTypeD.add(new String[] { "", "" });
			}

		} else {
			fieldsTypeD.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeD"));
		}

		// Fuer TypB
		if (session.getAttribute("fieldsTypeB") == null) {
			if (session.getAttribute("edit") != null && !entryList.isEmpty()) {
				for (Entry entry : entryList) {
					if (entry.getTitle().equals("Inhalt")) {
						fieldsTypeB.add(new String[] { "Inhalt",
								entry.getContent() });
					} else if (entry.getTitle().equals("Lernziele")) {
						fieldsTypeB.add(new String[] { "Literatur",
								entry.getContent() });
					}
				}
			} else {
				fieldsTypeB.add(new String[] { "Inhalt", "" });
				fieldsTypeB.add(new String[] { "Lernziele", "" });
				fieldsTypeB.add(new String[] { "Literatur", "" });
			}

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

				// Aufwand speichern
				int pt = Integer.parseInt(fieldsTypeD.get(0)[1]);
				EffortEntry effort = new EffortEntry("Präsenzzeit", pt);
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
				// TODO Unterscheiden zwischen neu eingereicht und geändert
				if (edit) {
					ua.insertHistory(
							((User) session.getAttribute("user")).getLogin(),
							date, "Hat ein Modul ge&auml;ndert: " + title);
				} else {
					ua.insertHistory(
							((User) session.getAttribute("user")).getLogin(),
							date, "Hat ein neues Modul eingereicht: " + title);
				}

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
		session.removeAttribute("edit");
		edit = false;
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
