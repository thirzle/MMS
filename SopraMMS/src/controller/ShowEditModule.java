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
 * Servlet implementation class ShowEditModule
 */
@WebServlet("/ShowEditModule")
public class ShowEditModule extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowEditModule() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		UserAdministration uAdmin = new UserAdministration();
		ModuleAdministration mAdmin = new ModuleAdministration();
		HttpSession session = request.getSession();
		LinkedList<Entry> entryList = new LinkedList<Entry>();

		long moduleID = 0;
		// selectedModuleToEdit == null --> Aufruf von Buttons
		if (request.getParameter("selectedModuleToEdit") == null) {
			moduleID = (long) session.getAttribute("selectedModuleIDToEdit");
		}
		// erster Aufruf von showModules
		else {
			moduleID = Long.parseLong((request
					.getParameter("selectedModuleToEdit")));
			session.setAttribute("selectedModuleIDToEdit", moduleID);
		}

		Module editModule = mAdmin.getModuleByID(moduleID);
		entryList = (LinkedList<Entry>) mAdmin.getEntryListOfModule(editModule);

		if (session.getAttribute("institutesModuleEntry") == null) {

			List<String[]> institutes = uAdmin.getAllInstitutesByName(session
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
		System.out.println("(ShowEditModule.java) selectedInstituteID: "
				+ institute);
		// Fuelle Liste mit Standartwerten falls das Session Attribut noch nicht
		// besteht, anderenfalls kopiere das Session Attribut in das lokale
		// Attribut

		// Fuer TypA

		if (session.getAttribute("fieldsTypeAEdit") == null) {
			// Type A
			for (Entry entry : entryList) {
				if (entry.getTitle().equals("Kürzel")) {
					fieldsTypeA
							.add(new String[] { "Kürzel", entry.getContent() });
				} else if (entry.getTitle().equals("Titel")) {
					fieldsTypeA
							.add(new String[] { "Titel", entry.getContent() });
				} else if (entry.getTitle().equals("Verantwortlicher")) {
					fieldsTypeA.add(new String[] { "Verantwortlicher",
							entry.getContent() });
				} else if (entry.getTitle().equals("Turnus")) {
					fieldsTypeA
							.add(new String[] { "Turnus", entry.getContent() });
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

			System.out.println("fieldsTypeAEdit");

		} else {
			fieldsTypeA.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeAEdit"));
		}

		// Fuer TypD
		if (session.getAttribute("fieldsTypeDEdit") == null) {
			for (Entry entry : entryList) {
				if (entry.getClass() == EffortEntry.class
						&& entry.getTitle().equals("Zeitaufwand")) {
					EffortEntry effortEntry = (EffortEntry) entry;
					LinkedList<SelfStudy> selfStudyList = (LinkedList<SelfStudy>) effortEntry
							.getSelfStudyList();

					for (SelfStudy selfStudy : selfStudyList) {
						if (selfStudy.getTitle().equals("Präsenzzeit")) {
							fieldsTypeD.add(new String[] { "Präsenzzeit",
									"" + selfStudy.getTime() });
						} else if (selfStudy.getTitle().equals("Nacharbeitung")) {
							fieldsTypeD.add(new String[] { "Nacharbeitung",
									"" + selfStudy.getTime() });
						} else if (selfStudy.getTitle()
								.equals("Übungsaufgaben")) {
							fieldsTypeD.add(new String[] { "Übungsaufgaben",
									"" + selfStudy.getTime() });
						} else if (selfStudy.getTitle().equals("Prüfung")) {
							fieldsTypeD.add(new String[] { "Prüfung",
									"" + selfStudy.getTime() });
						}
					}
				}
			}
			fieldsTypeD.add(new String[] { "", "" });

		} else {
			fieldsTypeD.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeDEdit"));
		}

		// Fuer TypB
		if (session.getAttribute("fieldsTypeB") == null) {
			for (Entry entry : entryList) {
				if (entry.getTitle().equals("Inhalt")) {
					fieldsTypeB
							.add(new String[] { "Inhalt", entry.getContent() });
				} else if (entry.getTitle().equals("Lernziele")) {
					fieldsTypeB.add(new String[] { "Literatur",
							entry.getContent() });
				}
			}

			System.out.println("fieldsTypeBEdit");

		} else {
			fieldsTypeB.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeBEdit"));
		}

		// Fuer TypC
		if (session.getAttribute("fieldsTypeCEdit") != null) {
			fieldsTypeC.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeCEdit"));
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

				ArrayList<Entry> entryListForNewModule = new ArrayList<>();

				for (String[] strings : fieldsTypeA) {
					TextualEntry entry = new TextualEntry(strings[0],
							strings[1]);
					entryListForNewModule.add(entry);
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
				entryListForNewModule.add(effort);

				// Textfelder speichern
				for (String[] strings : fieldsTypeB) {
					TextualEntry entry = new TextualEntry(strings[0],
							strings[1]);
					entryListForNewModule.add(entry);
				}
				for (String[] strings : fieldsTypeC) {
					TextualEntry entry = new TextualEntry(strings[0],
							strings[1]);
					entryListForNewModule.add(entry);
				}
				fieldsTypeA = fieldsTypeB = fieldsTypeC = fieldsTypeD = null;
				// TODO leere selbsterstellte felder aussortieren

				// Spezifische Felder für Turnus, LP, Aufwand, Studiengang
				// Versionsnummer von Modul aktualisieren
				java.sql.Date creationdate = (java.sql.Date) editModule.getCreationDate();
				int version = editModule.getVersion()+1;
				mAdmin.createModuleByModuleManager(entryListForNewModule,
						((User) session.getAttribute("user")).getLogin(),
						institute, creationdate, version, moduleID);
				// TODO pruefen ob Pflichfelder befuellt sind

				// insert into History "Module created"
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date currentTime = new Date();
				String date = formatter.format(currentTime);
				String title = null;
				for (Entry entry : entryListForNewModule) {
					if (entry.getTitle().equals("Kürzel")) {
						title = entry.getContent();
					}
				}
				uAdmin.insertHistory(
						((User) session.getAttribute("user")).getLogin(), date,
						"Hat ein Modul ge&auml;ndert: " + title);

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
		else {
			session.setAttribute("content", "editModule");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		}

		session.setAttribute("fieldsTypeAEdit", fieldsTypeA);
		session.setAttribute("fieldsTypeBEdit", fieldsTypeB);
		session.setAttribute("fieldsTypeCEdit", fieldsTypeC);
		session.setAttribute("fieldsTypeDEdit", fieldsTypeD);
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
