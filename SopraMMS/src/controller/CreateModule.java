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
import management.SelfStudy;
import management.TextualEntry;
import user.User;

/**
 * Servlet implementation class CreateModule
 */
@WebServlet("/CreateModule")
public class CreateModule extends SessionCheck {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateModule() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			LinkedList<Entry> entryList = new LinkedList<Entry>();

			if (session.getAttribute("institutesModuleEntry") == null) {

				List<String[]> institutes = uAdmin
						.getAllInstitutesByName(session.getAttribute(
								"loginname").toString());
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
			// Fuelle Liste mit Standartwerten falls das Session Attribut noch
			// nicht
			// besteht, anderenfalls kopiere das Session Attribut in das lokale
			// Attribut

			// Fuer TypA

			if (session.getAttribute("fieldsTypeA") == null) {

				fieldsTypeA.add(new String[] { "K�rzel", "" });
				fieldsTypeA.add(new String[] { "Titel", "" });
				fieldsTypeA.add(new String[] { "Verantwortlicher", "" });
				fieldsTypeA.add(new String[] { "Turnus", "" });
				fieldsTypeA.add(new String[] { "Sprache", "" });
				fieldsTypeA.add(new String[] { "Pr�fungsform", "" });

				System.out.println("fieldsTypeA");

			} else {
				fieldsTypeA.addAll((ArrayList<String[]>) session
						.getAttribute("fieldsTypeA"));
			}

			// Fuer TypD
			if (session.getAttribute("fieldsTypeD") == null) {
				fieldsTypeD.add(new String[] { "Pr�senzzeit", "" });
				fieldsTypeD.add(new String[] { "Nacharbeitung", "" });
				fieldsTypeD.add(new String[] { "�bungsaufgaben", "" });
				fieldsTypeD.add(new String[] { "Pr�fung", "" });
				fieldsTypeD.add(new String[] { "", "" });

			} else {
				fieldsTypeD.addAll((ArrayList<String[]>) session
						.getAttribute("fieldsTypeD"));
			}

			// Fuer TypB
			if (session.getAttribute("fieldsTypeB") == null) {
				fieldsTypeB.add(new String[] { "Notenbildung", "" });
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

			// Lese Parameter aus der URL und fuege Sie den jeweiligen Listen
			// hinzu
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
				// Modul f�r Sitzung Speichern
				else if (request.getParameter("createModule").equals(
						"saveModule")) {
					System.out
							.println("(CreateModule.java): Modul f�r Sitzung gespeichert");
					response.sendRedirect("/SopraMMS/guiElements/home.jsp");
				}
				// Bei Klick Module Speichern
				else if (request.getParameter("createModule").equals(
						"sendModule")) {

					ArrayList<Entry> module = new ArrayList<>();
					int order = 0;
					for (String[] strings : fieldsTypeA) {
						TextualEntry entry = new TextualEntry(strings[0],
								order++, strings[1]);
						module.add(entry);
					}
					order += 2;
					// Aufwand speichern
					int pt = Integer.parseInt(fieldsTypeD.get(0)[1]);
					EffortEntry effort = new EffortEntry("Zeitaufwand",
							order++, pt);
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
								order++, strings[1]);
						module.add(entry);
					}
					for (String[] strings : fieldsTypeC) {
						TextualEntry entry = new TextualEntry(strings[0],
								order++, strings[1]);
						module.add(entry);
					}
					fieldsTypeA = fieldsTypeB = fieldsTypeC = fieldsTypeD = null;
					// TODO leere selbsterstellte felder aussortieren
					// TODO Modul an DB uebertragen
					// Spezifische Felder für Turnus, LP, Aufwand, Studiengang
					mAdmin.createModuleByModuleManager(module,
							((User) session.getAttribute("user")).getLogin(),
							institute);

					// insert into History "Module created"
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd");
					Date currentTime = new Date();
					String date = formatter.format(currentTime);

					String title = null;
					for (Entry entry : module) {
						if (entry.getTitle().equals("Titel")) {
							title = entry.getContent();
						}
					}

					uAdmin.insertHistory(
							((User) session.getAttribute("user")).getLogin(),
							date, "Hat folgendes Modul erstellt: " + title);

					String infotext = "Das Modul '"
							+ title
							+ "' wurde erfolgreich erstellt und zur Freigabe weitergeleitet.";
					response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="
							+ infotext);
				}
			}
			// Bei Klick entsprechende Zeile loeschen
			else if (request.getParameter("deleteRow") != null) {
				int deleteEntry = Integer.parseInt(request.getParameter(
						"deleteRow").replace("Delete", ""));
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
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="
					+ error);
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
