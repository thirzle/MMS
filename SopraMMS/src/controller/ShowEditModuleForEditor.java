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
		boolean allEntriesApproved = true;
		// speichert das ausgeählte Modul + Version
		String selectedModule = null;
		// ModulID steht an Stelle 0, Versionsnummer an Stelle 1
		String[] selectedModuleArray = new String[2];

		long moduleID = 0;
		int version = 0;
		// selectedModuleToEdit == null --> call from a button
		System.out.println("selectedModuletoApprove is null: "
				+ (request.getParameter("selectedModuleToApprove") == null));
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
			System.out
					.println("ModuleID: " + moduleID + " version: " + version);
			session.setAttribute("selectedModuleIDToApprove", moduleID);
			session.setAttribute("selectedVersionToApprove", version);
		}

		Module approveModule = mAdmin.getModuleByID(moduleID, version);
		entryList = (LinkedList<Entry>) mAdmin
				.sortModuleEntryListByOrder(approveModule);
		
		System.out.println("ShowEditModuleForEditor################################");
		for (Entry entry : entryList) {
			System.out.println(entry.getTitle()+" approved: "+entry.isApproved());
		}

		String instituteID = approveModule.getInstituteID();
		
		if (request.getParameter("approveModule") != null) {
			// save module for this session
			if (request.getParameter("approveModule").equals("saveModule")) {
				System.out
						.println("(ShowEditModuleForEditor.java): Modul für Sitzung gespeichert");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			}

			// approve the whole module
			else if (request.getParameter("approveModule").equals("sendModule")) {

				LinkedList<Entry> entryListForNewModule = new LinkedList<>();
				entryListForNewModule.addAll(entryList);

				//set new approvalstatus
				for (Entry entry : entryList) {
					// if the value of the button is true, set the status of
					// this entry as approved
					if (request.getParameter("radioEffort" + entry.getTitle()) != null) {
						if (request.getParameter(
								"radioEffort" + entry.getTitle())
								.equals("true")) {
							entry.setApprovalstatus(true);
							entry.setRejectionstatus(false);
						} else {
							entry.setApprovalstatus(false);
							entry.setRejectionstatus(true);
							allEntriesApproved = false;
						}

					}
					if (request.getParameter("radioZeitaufwand") != null) {
						if (request.getParameter("radioZeitaufwand").equals(
								"true")) {
							entry.setApprovalstatus(true);
							entry.setRejectionstatus(false);
						} else {
							entry.setApprovalstatus(false);
							entry.setRejectionstatus(true);
							allEntriesApproved = false;
						}

					}
					if (request.getParameter("radioInstitute") != null) {
						if (request.getParameter("radioInstitute").equals(
								"true")) {
							entry.setApprovalstatus(true);
							entry.setRejectionstatus(false);
						} else {
							entry.setApprovalstatus(false);
							entry.setRejectionstatus(true);
							allEntriesApproved = false;
						}

					}
					if (request.getParameter("radioEntryB") != null) {
						if (request.getParameter(
								"radioEntryB" + entry.getTitle())
								.equals("true")) {
							entry.setApprovalstatus(true);
							entry.setRejectionstatus(false);
						} else {
							entry.setApprovalstatus(false);
							entry.setRejectionstatus(true);
							allEntriesApproved = false;
						}

					}
					if (request.getParameter("radioEntryC") != null) {
						if (request.getParameter(
								"radioEntryC" + entry.getTitle())
								.equals("true")) {
							entry.setApprovalstatus(true);
							entry.setRejectionstatus(false);
						} else {
							entry.setApprovalstatus(false);
							entry.setRejectionstatus(true);
							allEntriesApproved = false;
						}

					}
				}
			}
			// change entries of module
			mAdmin.changeEntryListOfModule(approveModule);

			// insert into History "Module approved"
			if (allEntriesApproved) {
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
			}
		}
		session.setAttribute("entryListForEditor", entryList);
		session.setAttribute("instituteApproveModule",
				mAdmin.getInstituteName(instituteID));

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
