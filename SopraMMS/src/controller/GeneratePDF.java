package controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;

import pdfcreator.SimplePdfCreator;

import management.Entry;
import management.Module;
import management.ModuleAdministration;

import user.UserAdministration;

/**
 * Servlet implementation class GeneratePDF
 */
@WebServlet("/GeneratePDF")
public class GeneratePDF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GeneratePDF() {
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
		LinkedList<String> facListID = (LinkedList) uAdmin.getAllFacultiesID();
		LinkedList<String> courses = (LinkedList) uAdmin
				.getCoursesByFaculty(facListID.getFirst());

		// all courses of faculty in courseArray[]
		String[] courseArray = new String[2 * courses.size()];
		for (int i = 0; i < courses.size(); i++) {
			courseArray[i] = "Bachelor " + courses.get(i);
		}
		for (int j = 0; j < courses.size(); j++) {
			courseArray[j + courses.size()] = "Master " + courses.get(j);
		}

		String fullCourse = request.getParameter("course");
		String course = courseArray[Integer.parseInt(fullCourse)];
		String[] splitCourse = course.split(" ");

		// load all modules of course
		LinkedList<Module> moduleList = (LinkedList) mAdmin.getModulesByCourse(
				mAdmin.getCourseID(splitCourse[1]), splitCourse[0]);
		for (Module module : moduleList) {
			System.out.println(module.getModuleID() + " " + module.getName()
					+ " " + module.getCreationDate() + " "
					+ module.getModificationDate());
			for (Entry entry : module.getEntryList()) {
				System.out.println(entry.getTitle() + " " + entry.getVersion());
			}
		}
		try {
			SimplePdfCreator pdfCreator = new SimplePdfCreator();
			pdfCreator.createModulePdf("P:/Team7_12/TestPDF/module.pdf",
					moduleList);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("couldn't create PDF of course: "
					+ splitCourse[1]);
		}
		response.sendRedirect("/SopraMMS/fileExportServlet?filename=module.pdf");
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
