package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.CourseEntry;
import management.EffortEntry;
import management.Entry;
import management.Module;
import management.ModuleAdministration;
import management.SelfStudy;
import management.TextualEntry;
import user.UserAdministration;

/**
 * Servlet implementation class editModuleForModulmanager
 */
@WebServlet("/EditModule")
public class EditModule extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession session = null;

	UserAdministration uAdmin = new UserAdministration();
	ModuleAdministration mAdmin = new ModuleAdministration();

	long moduleID;
	int moduleVersion;

	LinkedList<TextualEntry> textualEntrys = new LinkedList<>();
	EffortEntry effortEntry = null;
	CourseEntry courseEntry = null;

	Module module = null;
	List<Entry> entryList = null;

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

		if (request.getParameter("selectedModuleToEdit") != null) {
			String selectedModule = request
					.getParameter("selectedModuleToEdit");
			String[] selectedModuleArray = selectedModule.split(" ");
			moduleID = Long.parseLong(selectedModuleArray[0]);
			moduleVersion = Integer.parseInt(selectedModuleArray[1]);
			session.setAttribute("selectedModuleIDToEdit", moduleID);
			session.setAttribute("selectedVersionToEdit", moduleVersion);
			firstRun = true;
		} else {
			moduleID = (long) session.getAttribute("selectedModuleIDToEdit");
			moduleVersion = (int) session.getAttribute("selectedVersionToEdit");		
		}

		module = mAdmin.getModuleByID(moduleID, moduleVersion);
		entryList = mAdmin.sortModuleEntryListByOrder(module);

		for (Entry entry : entryList) {
			if (entry.getClass().equals(EffortEntry.class)) {
				effortEntry = (EffortEntry) entry;
			} else if (entry.getClass().equals(CourseEntry.class)) {
				courseEntry = (CourseEntry) entry;
			} else {
				textualEntrys.add((TextualEntry) entry);
			}
		}
		System.out.println("(EditModule.java): Initialisierung abgeschlossen");

		setContentToFields();

		loadFieldsOnSession();
		session.setAttribute("content", "editModule");
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	
		System.out.println("###############################");
		for (String[] s : fieldsTypeA) {
			if(s[1].length()<5)
			System.out.println(s[0]+" : xxx");
			System.out.println(s[0]+" : "+s[1].substring(0,	4));
		}
		for (String[] s : fieldsTypeB) {
			if(s[1].length()<5)
			System.out.println(s[0]+" : xxx");
			System.out.println(s[0]+" : "+s[1].substring(0,	4));
		}
		for (String[] s : fieldsTypeC) {
			if(s[1].length()<5)
			System.out.println(s[0]+" : xxx");
			System.out.println(s[0]+" : "+s[1].substring(0,	4));
		}
		for (String[] s : fieldsTypeD) {
			System.out.println(s[0]+" : xxx");
		}
		for (String[] s : fieldsTypeE) {
			if(s[1].length()<5)
			System.out.println(s[0]+" : xxx");
			System.out.println(s[0]+" : "+s[1].substring(0,	4));
		}
		System.out.println("###############################");
	
	}

	private void setContentToFields() {
		if (firstRun) {
			loadFieldsA();
			loadFieldsB();
			loadFieldsC();
			loadFieldsD();
			loadFieldsE();
			//remove sessions
		} else {

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

		System.out.println("(EditModule.java): Felder in Session geladen");
	}

	private void loadFieldsA() {
		for (TextualEntry entry : textualEntrys) {
			if (entry.getTitle().equals("Kürzel")) {
				fieldsTypeA.add(new String[] { "Kürzel", entry.getContent() });
			} else if (entry.getTitle().equals("Titel")) {
				fieldsTypeA.add(new String[] { "Titel", entry.getContent() });
			} else if (entry.getTitle().equals("Verantwortlicher")) {
				fieldsTypeA.add(new String[] { "Verantwortlicher",
						entry.getContent() });
			} else if (entry.getTitle().equals("Turnus")) {
				fieldsTypeA.add(new String[] { "Turnus", entry.getContent() });
			} else if (entry.getTitle().equals("Sprache")) {
				fieldsTypeA.add(new String[] { "Sprache", entry.getContent() });
			} else if (entry.getTitle().equals("Prüfungsform")) {
				fieldsTypeB.add(new String[] { "Prüfungsform",
						entry.getContent() });
			}
		}
		System.out.println("(EditModule.java): Felder von A geladen");
	}

	private void loadFieldsB() {
		for (TextualEntry entry : textualEntrys) {
			if (entry.getTitle().equals("Inhalt")) {
				fieldsTypeB.add(new String[] { "Inhalt", entry.getContent() });
			} else if (entry.getTitle().equals("Lernziele")) {
				fieldsTypeB
						.add(new String[] { "Lernziele", entry.getContent() });
			} else if (entry.getTitle().equals("Literatur")) {
				fieldsTypeB
						.add(new String[] { "Literatur", entry.getContent() });
			} else if (entry.getTitle().equals("Notenbildung")) {
				fieldsTypeB.add(new String[] { "Notenbildung",
						entry.getContent() });
			}
		}

		System.out.println("(EditModule.java): Felder von B geladen");
	}

	private void loadFieldsC() {

		System.out
				.println("(EditModule.java): Felder von C geladen (NICHT NICHT IMPLEMENTIERT)");
	}

	private void loadFieldsD() {
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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
