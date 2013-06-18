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
 * Servlet implementation class ShowEditModuleForEditor
 */
@WebServlet("/ShowEditModuleForEditor")
public class ShowEditModuleForEditor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */



	public ShowEditModuleForEditor() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		ModuleAdministration mAdmin = new ModuleAdministration();
		UserAdministration uAdmin = new UserAdministration();
		LinkedList<Entry> entryList = new LinkedList<Entry>();
		LinkedList<Entry> entryListForTypeC = new LinkedList<Entry>();
		// speichert das ausgeählte Modul + Version
		String selectedModule = null;
		// ModulID steht an Stelle 0, Versionsnummer an Stelle 1
		String[] selectedModuleArray = new String[2];

		long moduleID = 0;
		int version = 0;
		// selectedModuleToEdit == null --> call from a button
		System.out.println("selectedModuletoApprove is null: "+(request.getParameter("selectedModuleToApprove") == null));
		if (request.getParameter("selectedModuleToApprove") == null) {
			moduleID = (long) session.getAttribute("selectedModuleIDToApprove");
			version = (int) session.getAttribute("selectedVersionToApprove");

		}
		// first call from showModulesForEditor
		else {
			selectedModule = request.getParameter("selectedModuleToApprove");
			selectedModuleArray = selectedModule.split(" ");
			moduleID = Long.parseLong(selectedModuleArray[0]);
			version = Integer.parseInt(selectedModuleArray[1]);
			System.out.println("ModuleID: "+moduleID+" version: "+version);
			session.setAttribute("selectedModuleIDToApprove", moduleID);
			session.setAttribute("selectedVersionToApprove", version);
		}

		Module approveModule = mAdmin.getModuleByID(moduleID, version);
		entryList = (LinkedList<Entry>) mAdmin
				.sortModuleEntryListByOrder(approveModule);
		entryListForTypeC.addAll(entryList);

		String instituteID = approveModule.getInstituteID();

		// TypeA --> predefined mandatory fields Feld
		// TypeB --> predefined mandatory fields Textarea
		// TypeC --> self defined fields Textarea
		// TypeD --> predefined mandatory field Aufwand
		ArrayList<String[]> fieldsTypeA = new ArrayList<>();
		ArrayList<String[]> fieldsTypeB = new ArrayList<>();
		ArrayList<String[]> fieldsTypeC = new ArrayList<>();
		ArrayList<String[]> fieldsTypeD = new ArrayList<>();

		// fill all fields with default values if the session attribute doesn't
		// exist yet
		// else copy the session attribute into the local attribute

		// For TypA

		if (session.getAttribute("fieldsTypeAApprove") == null) {
			// Type A
			for (Entry entry : entryList) {
				if (entry.getTitle().equals("Kürzel")) {
					fieldsTypeA
							.add(new String[] { "Kürzel", entry.getContent() });
					entryListForTypeC.remove(entry);
				} else if (entry.getTitle().equals("Titel")) {
					fieldsTypeA
							.add(new String[] { "Titel", entry.getContent() });
					entryListForTypeC.remove(entry);
				} else if (entry.getTitle().equals("Verantwortlicher")) {
					fieldsTypeA.add(new String[] { "Verantwortlicher",
							entry.getContent() });
					entryListForTypeC.remove(entry);
				} else if (entry.getTitle().equals("Turnus")) {
					fieldsTypeA
							.add(new String[] { "Turnus", entry.getContent() });
					entryListForTypeC.remove(entry);
				} else if (entry.getTitle().equals("Sprache")) {
					fieldsTypeA.add(new String[] { "Sprache",
							entry.getContent() });
					entryListForTypeC.remove(entry);
				}

			}

			System.out.println("fieldsTypeAApprove");

		} else {
			fieldsTypeA.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeAApprove"));
		}

		// For TypeD
		if (session.getAttribute("fieldsTypeDApprove") == null) {
			for (Entry entry : entryList) {
				if (entry.getClass() == EffortEntry.class
						&& entry.getTitle().equals("Zeitaufwand")) {
					EffortEntry effortEntry = (EffortEntry) entry;
					LinkedList<SelfStudy> selfStudyList = (LinkedList<SelfStudy>) effortEntry
							.getSelfStudyList();

					entryListForTypeC.remove(entry);
					int selfStudySize = selfStudyList.size();
					for (int i = 0; i < selfStudySize; i++) {
						if (selfStudyList.get(i).getTitle()
								.equals("Präsenzzeit")) {
							fieldsTypeD.add(new String[] { "Präsenzzeit",
									"" + selfStudyList.get(i).getTime() });
						} else if (selfStudyList.get(i).getTitle()
								.equals("Nacharbeitung")) {
							fieldsTypeD.add(new String[] { "Nacharbeitung",
									"" + selfStudyList.get(i).getTime() });
						} else if (selfStudyList.get(i).getTitle()
								.equals("Übungsaufgaben")) {
							fieldsTypeD.add(new String[] { "Übungsaufgaben",
									"" + selfStudyList.get(i).getTime() });
						} else if (selfStudyList.get(i).getTitle()
								.equals("Prüfung")) {
							fieldsTypeD.add(new String[] { "Prüfung",
									"" + selfStudyList.get(i).getTime() });
						}
					}
				}
			}

		} else {
			fieldsTypeD.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeDApprove"));
		}

		// For TypeB
		if (session.getAttribute("fieldsTypeBApprove") == null) {
			for (Entry entry : entryList) {
				if (entry.getTitle().equals("Inhalt")) {
					fieldsTypeB
							.add(new String[] { "Inhalt", entry.getContent() });
					entryListForTypeC.remove(entry);
				} else if (entry.getTitle().equals("Lernziele")) {
					fieldsTypeB.add(new String[] { "Lernziele",
							entry.getContent() });
					entryListForTypeC.remove(entry);
				} else if (entry.getTitle().equals("Literatur")) {
					fieldsTypeB.add(new String[] { "Literatur",
							entry.getContent() });
					entryListForTypeC.remove(entry);
				} else if (entry.getTitle().equals("Notenbildung")) {
					fieldsTypeB.add(new String[] { "Notenbildung",
							entry.getContent() });
					entryListForTypeC.remove(entry);
				} else if (entry.getTitle().equals("Prüfungsform")) {
					fieldsTypeB.add(new String[] { "Prüfungsform",
							entry.getContent() });
					entryListForTypeC.remove(entry);
				}
			}

			System.out.println("fieldsTypeBApprove");

		} else {
			fieldsTypeB.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeBApprove"));
		}

		// For TypeC
		if (session.getAttribute("fieldsTypeCApprove") == null) {
			for (Entry entry : entryListForTypeC) {
				fieldsTypeC.add(new String[] {entry.getTitle(), entry.getContent()});
			}
		}
		else{
			fieldsTypeC.addAll((ArrayList<String[]>) session
					.getAttribute("fieldsTypeCApprove"));
		}

		// get parameters from request and add them to their particular list
		// For TypeA
		for (int i = 0; i < fieldsTypeA.size(); i++) {
			String[] entry = fieldsTypeA.get(i);
			if (request.getParameter(i + "ContentA") != null) {
				entry[1] = request.getParameter(i + "ContentA").trim();
			}
		}
		// For TypeD
		for (int i = 0; i < fieldsTypeD.size(); i++) {
			String[] entry = fieldsTypeD.get(i);
			if (request.getParameter(i + "TitleD") != null) {
				entry[0] = request.getParameter(i + "TitleD").trim();
			}
			if (request.getParameter(i + "ContentD") != null) {
				entry[1] = request.getParameter(i + "ContentD").trim();
			}
		}

		// For TypeB
		for (int i = 0; i < fieldsTypeB.size(); i++) {
			String[] entry = fieldsTypeB.get(i);
			if (request.getParameter(i + "ContentB") != null) {
				entry[1] = request.getParameter(i + "ContentB").trim();
			}
		}
		// For TypeC
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
		
		
		if (request.getParameter("approveModule") != null) {
			// add row
			if (request.getParameter("approveModule").equals("addRow")) {
				fieldsTypeC.add(new String[] { "", "" });
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			}
			//save module for this session
			else if (request.getParameter("approveModule").equals("saveModule")) {
				System.out
						.println("(ShowEditModuleForEditor.java): Modul für Sitzung gespeichert");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			}
		}
			//approve the whole module
//			else if (request.getParameter("createModule").equals("sendModule")) {
//
//				LinkedList<Entry> entryListForNewModule = new LinkedList<>();
//				entryListForNewModule.addAll(entryList);
//
//				for (String[] strings : fieldsTypeA) {
//					TextualEntry entry = new TextualEntry(strings[0],
//							strings[1]);
//					entryListForNewModule.add(entry);
//				}
//
//				// save effort
//				int pt = Integer.parseInt(fieldsTypeD.get(0)[1]);
//				EffortEntry effort = new EffortEntry("Präsenzzeit", pt);
//				List<SelfStudy> selfStudyList = new ArrayList<>();
//
//				for (int i = 1; i < fieldsTypeD.size(); i++) {
//					String[] entry = fieldsTypeD.get(i);
//					if (!(entry[0].equals("") || entry[1].equals(""))) {
//						selfStudyList.add(new SelfStudy(entry[0], Integer
//								.parseInt(entry[1])));
//					}
//				}
//				effort.setSelfStudyList(selfStudyList);
//				entryListForNewModule.add(effort);
//
//				// Textfelder speichern
//				for (String[] strings : fieldsTypeB) {
//					TextualEntry entry = new TextualEntry(strings[0],
//							strings[1]);
//					entryListForNewModule.add(entry);
//				}
//				for (String[] strings : fieldsTypeC) {
//					TextualEntry entry = new TextualEntry(strings[0],
//							strings[1]);
//					entryListForNewModule.add(entry);
//				}
//				fieldsTypeA = fieldsTypeB = fieldsTypeC = fieldsTypeD = null;
//				// TODO leere selbsterstellte felder aussortieren
//
//				// Spezifische Felder für Turnus, LP, Aufwand, Studiengang
//				// Versionsnummer von Modul aktualisieren
//				java.sql.Date creationdate = (java.sql.Date) editModule
//						.getCreationDate();
//				int newVersion = editModule.getVersion() + 1;
//				mAdmin.createModuleByModuleManager(entryListForNewModule,
//						((User) session.getAttribute("user")).getLogin(),
//						institute, creationdate, newVersion, moduleID);
//				// TODO pruefen ob Pflichfelder befuellt sind

				// insert into History "Module approved"
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date currentTime = new Date();
				String date = formatter.format(currentTime);
				String title = null;
				for (Entry entry : entryList) {
					if (entry.getTitle().equals("Kürzel")) {
						title = entry.getContent();
					}
				}
				uAdmin.insertHistory(
						((User) session.getAttribute("user")).getLogin(), date,
						"Hat folgendes Modul freigegeben: " + title);

		session.setAttribute("fieldsTypeAApprove", fieldsTypeA);
		session.setAttribute("fieldsTypeBApprove", fieldsTypeB);
		session.setAttribute("fieldsTypeCApprove", fieldsTypeC);
		session.setAttribute("fieldsTypeDApprove", fieldsTypeD);
		session.setAttribute("instituteListForApproveModule", mAdmin.getInstituteName(instituteID));

		session.setAttribute("content", "showEditModuleForEditor");
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			
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
