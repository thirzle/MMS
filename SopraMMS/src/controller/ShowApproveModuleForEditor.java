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

import mail.EmailTelnet;
import management.EffortEntry;
import management.Entry;
import management.Module;
import management.ModuleAdministration;
import management.SelfStudy;
import management.TextualEntry;
import user.User;
import user.UserAdministration;

/**
 * Servlet implementation class ShowApproveModuleForEditor
 */
@WebServlet("/ShowApproveModuleForEditor")
public class ShowApproveModuleForEditor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public ShowApproveModuleForEditor() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		ModuleAdministration mAdmin = new ModuleAdministration();
		UserAdministration uAdmin = new UserAdministration();
		LinkedList<Entry> entryList = new LinkedList<Entry>();
		String infotext = "";
		String moduleTitle = null;
		String author = null;
		String mailContent = "";
		boolean allEntriesApproved = true;
		// speichert das ausgeählte Modul + Version
		String selectedModule = null;
		// ModulID steht an Stelle 0, Versionsnummer an Stelle 1
		String[] selectedModuleArray = new String[2];

		long moduleID = 0;
		int version = 0;
		// selectedModuleToEdit == null --> call from a button
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
			session.setAttribute("selectedModuleIDToApprove", moduleID);
			session.setAttribute("selectedVersionToApprove", version);
		}

		Module approveModule = mAdmin.getModuleByID(moduleID, version);
		entryList = (LinkedList<Entry>) mAdmin
				.sortModuleEntryListByOrder(approveModule);

		String instituteID = approveModule.getInstituteID();

		if (request.getParameter("approveModule") != null) {
			// save module for this session
			if (request.getParameter("approveModule").equals("saveModule")) {
				response.sendRedirect("/SopraMMS/guiElements/home.jsp");
			}

			// approve the whole module
			else if (request.getParameter("approveModule").equals("sendModule")) {

				// set new approvalstatus
				for (Entry entry : entryList) {
					// if the value of the button is true, set the status of
					// this entry as approved
					if (request.getParameter("radioEntry" + entry.getTitle()) != null) {
						if (request.getParameter(
								"radioEntry" + entry.getTitle()).equals("true")) {
							entry.setApprovalstatus(true);
							entry.setRejectionstatus(false);
						} else {
							entry.setApprovalstatus(false);
							entry.setRejectionstatus(true);
							allEntriesApproved = false;
						}
					} else {
						entry.setApprovalstatus(false);
						entry.setRejectionstatus(false);
					}
				}
				// change entries of module
				mAdmin.changeEntryListOfModule(approveModule);

				// get title of module
				for (Entry entry : entryList) {
					if (entry.getTitle().equals("Kürzel")) {
						moduleTitle = entry.getContent();
					}
				}
				//get author and mail
				author = approveModule.getModificationauthor();
				String mailAuthor = new UserAdministration().getEmailOfUser(author);
				
				if (allEntriesApproved) {
					// insert into History "Module approved"
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd");
					Date currentTime = new Date();
					String date = formatter.format(currentTime);
					
					uAdmin.insertHistory(
							((User) session.getAttribute("user")).getLogin(),
							date, "Hat folgendes Modul freigegeben: " + moduleTitle);
					//
					infotext = "Das Modul "+moduleTitle+" wurde freigegeben!";
					
					// send mail to modulemanager
					mailContent = "Ihr Modul "+approveModule.getName()+" wurde komplett freigegeben.";
					EmailTelnet mail = new EmailTelnet();
					mail.send_mail("Ihr Modul wurde freigegeben", mailAuthor, mailContent);
				}
				else{
					infotext = "Die Freigabestatus der Einträge des Moduls "+moduleTitle+" wurden gespeichert.";
					//send mail to modulemanager
					mailContent = "";
					//TODO send mail
				}

				session.setAttribute("content", "home");
				response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="
						+ infotext);
			}
		} else {

			session.setAttribute("entryListForEditor", entryList);
			session.setAttribute("subjectApproveModule",
					approveModule.getSubject());
			session.setAttribute("instituteApproveModule",
					mAdmin.getInstituteName(instituteID));

			session.setAttribute("content", "showApproveModuleForEditor");
			response.sendRedirect("/SopraMMS/guiElements/home.jsp");

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
