package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.icu.util.Calendar;

import management.CourseEntry;
import management.EffortEntry;
import management.Entry;
import management.Module;
import management.ModuleAdministration;
import management.SelfStudy;
import management.TextualEntry;
import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class editModuleForModulmanager
 */
@WebServlet("/EditModule")
public class EditModule extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// TODO Institut mit Aktuallisieren

	HttpSession session = null;

	UserAdministration uAdmin = new UserAdministration();
	ModuleAdministration mAdmin = new ModuleAdministration();

	long moduleID;
	int moduleVersion;

	LinkedList<TextualEntry> textualEntrys = new LinkedList<>();
	EffortEntry effortEntry = null;
	CourseEntry courseEntry = null;
	String institute = null;
	Module module = null;
	List<Entry> entryList = null;
	private boolean editUnapprovedModule = false;
	private String editUnapprovedModuleUrl ="";
	private boolean firstRun = false;

	// TypeA --> predefined mandatory fields Feld
	// TypeB --> predefined mandatory fields Textarea
	// TypeC --> self defined fields Textarea
	// TypeD --> predefined mandatory field Aufwand
	// TypeE --> text field for Course and Subject
	ArrayList<String[]> fieldsTypeA = new ArrayList<>();
	ArrayList<String[]> fieldsTypeB = new ArrayList<>();
	ArrayList<String[]> fieldsTypeC = new ArrayList<>();
	ArrayList<String[]> fieldsTypeD = new ArrayList<>();
	ArrayList<String[]> fieldsTypeE = new ArrayList<>();

	public EditModule() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		editUnapprovedModule = false;
		editUnapprovedModuleUrl="";
		System.out.println("+++++ "+request.getParameter("editButton"));
		if (request.getParameter("editButton") != null) {
			if (request.getParameter("editButton").equals(
					"editUnapprovedModule")) {
				editUnapprovedModule = true;
				editUnapprovedModuleUrl="?editButton=editUnapprovedModule";
				System.out.println("(EditModule.java): Unbestätigtes Modul wird bearbeitet");
			}
		}
		
		if (request.getParameter("showVersionsButton") != null) {
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/ShowVersionsOfModule");
			dispatcher.forward(request, response);
		} else if (request.getParameter("showButton") != null) {
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/ViewModule");
			dispatcher.forward(request, response);
		}

		else {

			if (request.getParameter("selectedModuleToEdit") != null) {

				String selectedModule = request
						.getParameter("selectedModuleToEdit");
				String[] selectedModuleArray = selectedModule.split(" ");
				moduleID = Long.parseLong(selectedModuleArray[0]);
				moduleVersion = Integer.parseInt(selectedModuleArray[1]);
				session.setAttribute("selectedModuleIDToEdit", moduleID);
				session.setAttribute("selectedVersionToEdit", moduleVersion);
				firstRun = true;

				module = mAdmin.getModuleByID(moduleID, moduleVersion);
				System.out.println("(EditModule.java): Subject 1: "
						+ module.getSubject());
				entryList = mAdmin.sortModuleEntryListByOrder(module);

				if (fieldsTypeA != null)
					fieldsTypeA.clear();
				if (fieldsTypeB != null)
					fieldsTypeB.clear();
				if (fieldsTypeC != null)
					fieldsTypeC.clear();
				if (fieldsTypeD != null)
					fieldsTypeD.clear();
				if (fieldsTypeE != null)
					fieldsTypeE.clear();
				if (textualEntrys != null)
					textualEntrys.clear();

				for (Entry entry : entryList) {
					if (entry.getClass().equals(EffortEntry.class)) {
						effortEntry = (EffortEntry) entry;
					} else if (entry.getClass().equals(CourseEntry.class)) {
						courseEntry = (CourseEntry) entry;
					} else {
						textualEntrys.add((TextualEntry) entry);
					}
				}
				System.out
						.println("(EditModule.java): Initialisierung abgeschlossen");
				firstRun = true;

			} else {
				moduleID = (long) session
						.getAttribute("selectedModuleIDToEdit");
				moduleVersion = (int) session
						.getAttribute("selectedVersionToEdit");
				firstRun = false;
			}

			

			setContentToFields(request);

			loadFieldsOnSession();

			handleButtonEvents(request, response);
		}

	}

	private void setContentToFields(HttpServletRequest request) {
		if (firstRun) {
			session.removeAttribute("fieldsTypeAEdit");
			session.removeAttribute("fieldsTypeBEdit");
			session.removeAttribute("fieldsTypeCEdit");
			session.removeAttribute("fieldsTypeDEdit");
			session.removeAttribute("fieldsTypeEEdit");
			// session.removeAttribute("selectedInstitute");
			loadFieldsA();
			loadFieldsB();
			loadFieldsC();
			loadFieldsD();
			loadFieldsE();
			institute = module.getInstituteID();
			System.out
					.println("(EditModule.java): Setze Inhalt - Erster Aufruf");
		} else {
			reloadFieldsA(request);
			reloadFieldsB(request);
			reloadFieldsC(request);
			reloadFieldsD(request);
			module.setInstituteID(request.getParameter("selectedInstitute"));
			
			System.out.println("(EditModule.java): Setze Inhalt");
		}

		System.out
				.println("(EditModule.java): Inhalt der Module in tempListen geladen");
	}

	private void loadFieldsOnSession() {
		session.setAttribute("fieldsTypeAEdit", fieldsTypeA);
		session.setAttribute("fieldsTypeBEdit", fieldsTypeB);
		session.setAttribute("fieldsTypeCEdit", fieldsTypeC);
		session.setAttribute("fieldsTypeDEdit", fieldsTypeD);
		session.setAttribute("fieldsTypeEEdit", fieldsTypeE);
		session.setAttribute("moduleEdit", module);
		// session.setAttribute("selectedInstitute",
		// mAdmin.getInstituteName(module.getInstituteID()));

		System.out.println("(EditModule.java): Felder in Session geladen");
	}

	private void loadFieldsA() {
		fieldsTypeA = new ArrayList<>();
		for (TextualEntry entry : textualEntrys) {
			if (entry.getTitle().equals("Kürzel")) {
				fieldsTypeA.add(new String[] { "Kürzel", entry.getContent() });
				entry.setPredefined(true);
			} else if (entry.getTitle().equals("Titel")) {
				fieldsTypeA.add(new String[] { "Titel", entry.getContent() });
				entry.setPredefined(true);
			} else if (entry.getTitle().equals("Verantwortlicher")) {
				fieldsTypeA.add(new String[] { "Verantwortlicher",
						entry.getContent() });
				entry.setPredefined(true);
			} else if (entry.getTitle().equals("Turnus")) {
				fieldsTypeA.add(new String[] { "Turnus", entry.getContent() });
				entry.setPredefined(true);
			} else if (entry.getTitle().equals("Sprache")) {
				fieldsTypeA.add(new String[] { "Sprache", entry.getContent() });
				entry.setPredefined(true);
			} else if (entry.getTitle().equals("Prüfungsform")) {
				fieldsTypeA.add(new String[] { "Prüfungsform",
						entry.getContent() });
				entry.setPredefined(true);
			}
		}
		System.out.println("(EditModule.java): Felder von A geladen");
	}

	private void loadFieldsB() {
		fieldsTypeB = new ArrayList<>();
		for (TextualEntry entry : textualEntrys) {
			if (entry.getTitle().equals("Inhalt")) {
				fieldsTypeB.add(new String[] { "Inhalt", entry.getContent() });
				entry.setPredefined(true);
			} else if (entry.getTitle().equals("Lernziele")) {
				fieldsTypeB
						.add(new String[] { "Lernziele", entry.getContent() });
				entry.setPredefined(true);
			} else if (entry.getTitle().equals("Literatur")) {
				fieldsTypeB
						.add(new String[] { "Literatur", entry.getContent() });
				entry.setPredefined(true);
			} else if (entry.getTitle().equals("Notenbildung")) {
				fieldsTypeB.add(new String[] { "Notenbildung",
						entry.getContent() });
				entry.setPredefined(true);
			}
		}

		System.out.println("(EditModule.java): Felder von B geladen");
	}

	private void loadFieldsC() {
		fieldsTypeC = new ArrayList<>();
		for (TextualEntry entry : textualEntrys) {
			if (!entry.isPredefinedField()) {
				fieldsTypeC.add(new String[] { entry.getTitle(),
						entry.getContent() });
			}
		}
		System.out.println("(EditModule.java): Felder von C geladen");
	}

	private void loadFieldsD() {
		fieldsTypeD = new ArrayList<>();
		LinkedList<SelfStudy> selfStudyList = (LinkedList<SelfStudy>) effortEntry
				.getSelfStudyList();

		int selfStudySize = selfStudyList.size();
		fieldsTypeD.add(new String[] { "Präsenzzeit",
				effortEntry.getPresenceTime() + "" });
		for (int i = 0; i < selfStudySize; i++) {
			SelfStudy selfStudy = selfStudyList.get(i);
			fieldsTypeD.add(new String[] { selfStudy.getTitle(),
					selfStudy.getTime() + "" });
		}
		if (selfStudySize < 4) {
			for (int i = selfStudySize; i < 4; i++) {
				fieldsTypeD.add(new String[] { "", "" });
			}
		}
		System.out.println("(EditModule.java): Felder von D geladen");
	}

	private void loadFieldsE() {
		fieldsTypeE = new ArrayList<>();
		if (module.getSubject() != null) {
			fieldsTypeE.add(new String[] { "Fach", module.getSubject() });
		}
		if (courseEntry != null) {
			fieldsTypeE.add(new String[] { "Plichtmodul",
					courseEntry.necessaryCoursesToString() });
			fieldsTypeE.add(new String[] { "Wahlplichtmodul",
					courseEntry.obligatoryCoursesToString() });
		}
		System.out.println("(EditModule.java): Felder von E geladen");
	}

	private void reloadFieldsA(HttpServletRequest request) {
		for (int i = 0; i < fieldsTypeA.size(); i++) {
			String[] entry = fieldsTypeA.get(i);
			if (request.getParameter(i + "ContentA") != null) {
				entry[1] = request.getParameter(i + "ContentA");
			}
			fieldsTypeA.set(i, entry);
		}
	}

	private void reloadFieldsB(HttpServletRequest request) {
		for (int i = 0; i < fieldsTypeB.size(); i++) {
			String[] entry = fieldsTypeB.get(i);
			if (request.getParameter(i + "ContentB") != null) {
				entry[1] = request.getParameter(i + "ContentB");
			}
			fieldsTypeB.set(i, entry);
		}

	}

	private void reloadFieldsC(HttpServletRequest request) {
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
	}

	private void reloadFieldsD(HttpServletRequest request) {
		for (int i = 0; i < fieldsTypeD.size(); i++) {
			String[] entry = fieldsTypeD.get(i);
			if (request.getParameter(i + "TitleD") != null) {
				entry[0] = request.getParameter(i + "TitleD").trim();
			}
			if (request.getParameter(i + "ContentD") != null) {
				entry[1] = request.getParameter(i + "ContentD").trim();
			}
		}
	}

	private void handleButtonEvents(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (request.getParameter("editModule") != null) {
			if (request.getParameter("editModule").equals("addRow")) {
				fieldsTypeC.add(new String[] { "", "" });
				session.setAttribute("content", "editModule");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp"+editUnapprovedModuleUrl);

				System.out
						.println("(EditModule.java): Reihe zum Modul hinzugefügt");
			} else if (request.getParameter("editModule").equals("saveModule")) {
				System.out
						.println("(EditModule.java): Modul für Sitzung gespeichert");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp"+editUnapprovedModuleUrl);
			} else if (request.getParameter("editModule").equals("sendModule")) {

				LinkedList<Entry> entryListForNewModule = new LinkedList<>();
				// entryListForNewModule.addAll(entryList);
				int order = 0;
				for (String[] strings : fieldsTypeA) {
					TextualEntry entry = new TextualEntry(strings[0], order++,
							strings[1]);
					entryListForNewModule.add(entry);
				}

				// save effort
				int pt = Integer.parseInt(fieldsTypeD.get(0)[1]);
				EffortEntry effort = new EffortEntry("Präsenzzeit", order++, pt);
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
				entryListForNewModule.add(courseEntry);

				// save textfields
				for (String[] strings : fieldsTypeB) {
					TextualEntry entry = new TextualEntry(strings[0], order++,
							strings[1]);
					entryListForNewModule.add(entry);
				}
				for (String[] strings : fieldsTypeC) {
					TextualEntry entry = new TextualEntry(strings[0], order++,
							strings[1]);
					entryListForNewModule.add(entry);
				}
				fieldsTypeA = fieldsTypeB = fieldsTypeC = fieldsTypeD = null;
				// TODO leere selbsterstellte felder aussortieren

				// Spezifische Felder für Turnus, LP, Aufwand, Studiengang
				// Versionsnummer von Modul aktualisieren
				java.sql.Date creationdate = (java.sql.Date) module
						.getCreationDate();
				int newVersion = module.getVersion() + 1;
				System.out.println("(EditModule.java): Subject 2: "
						+ module.getSubject());

				module.setEntryList(entryListForNewModule);
				if (!editUnapprovedModule) {
					System.out.println("+++++++++");
					System.out.println("(EditModule.java): Bearbeitetes Modul --> Neues Modul");
					System.out.println(editUnapprovedModule+"  url: "+editUnapprovedModuleUrl);
					System.out.println("+++++++++");
					mAdmin.createModuleByModuleManager(module,
							((User) session.getAttribute("user")).getLogin(),
							module.getCreationDate(), newVersion);
				} else {
					System.out.println("+++++++++");
					System.out.println("(EditModule.java): Bearbeitetes Modul --> Überschreibt Modul");
					System.out.println(editUnapprovedModule+"  url: "+editUnapprovedModuleUrl);
					System.out.println("+++++++++");
					for (Entry entry : module.getEntryList()) {
						System.out.println(entry.getTitle() + "");
					}
					module.setAuthor(((User) session.getAttribute("user"))
							.getLogin());
					java.util.Calendar cal = new GregorianCalendar();
					module.setModificationDate(new java.sql.Date(cal.getTime()
							.getTime()));
					mAdmin.editModule(module);
					editUnapprovedModule = false;
				}

				// insert into History "Module changed"
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date currentTime = new Date();
				String date = formatter.format(currentTime);
				uAdmin.insertHistory(
						((User) session.getAttribute("user")).getLogin(),
						date,
						"Hat folgendes Modul ge&auml;ndert: "
								+ module.getName());

				String infotext = "Das Modul '" + module.getName()
						+ "' wurde zur Freigabe weitergeleitet.";
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="
						+ infotext);
			}

		} else if (request.getParameter("deleteRow") != null) {
			int deleteEntry = Integer.parseInt(request
					.getParameter("deleteRow").replace("Delete", ""));
			fieldsTypeC.remove(deleteEntry);
			System.out.println("(EditModule.java): Reihe gelöscht");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp"+editUnapprovedModuleUrl);

		} else {
			session.setAttribute("content", "editModule");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp"+editUnapprovedModuleUrl);

		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
