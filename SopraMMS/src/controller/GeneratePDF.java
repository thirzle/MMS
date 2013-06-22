package controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.zip.DataFormatException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;

import com.ibm.icu.text.SimpleDateFormat;

import pdfcreator.SimplePdfCreator;

import management.Course;
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
		String semester;

		LinkedList<String> facListName = (LinkedList) uAdmin
				.getAllFacultiesByName();
		LinkedList<String> facListID = (LinkedList) uAdmin.getAllFacultiesID();
		LinkedList<Course> courses = (LinkedList<Course>) mAdmin.getCourses();

		// all courses of faculty in courseArray[]
		String[] courseArray = new String[2 * courses.size()];
		for (int i = 0; i < courses.size(); i++) {
			courseArray[i] = "Bachelor " + courses.get(i).getDescription();
		}
		for (int j = 0; j < courses.size(); j++) {
			courseArray[j + courses.size()] = "Master "
					+ courses.get(j).getDescription();
		}

		// get course from gui
		String fullCourse = request.getParameter("course");
		String course = courseArray[Integer.parseInt(fullCourse)];
		String[] splitCourse = course.split(" ");
		String courseName = splitCourse[1];
		String degree = splitCourse[0];
		String courseID = mAdmin.getCourseID(courseName);
		System.out.println("(GeneratePDF.java) courseID=" + courseID
				+ " degree=" + degree);

		// get current Date
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date currentTime = new Date();
		String date = formatter.format(currentTime);

		// load all modules of course
		LinkedList<Module> moduleList = (LinkedList) mAdmin.getModulesByCourse(
				courseID, degree);
		// generate latest version
		String latestVersion;
		if (currentTime.getMonth() >= 4 && currentTime.getMonth() <= 9) {
			semester = "sose";
		} else {
			semester = "wise";
		}
		latestVersion = courseID + "_" + degree + "_" + semester
				+ date.substring(0, 4);
		
		String latestAuthor = mAdmin
				.getLastModificationAuthor(courseID, degree);
		System.out.println("latest modification author: " + latestAuthor);
		String modificationDate = mAdmin.getLastModificationDateOfModuleManual(
				courseID, degree);
		System.out.println("latest modification date: " + modificationDate);
		String examRegulation = request.getParameter("examRegulation");
		LinkedList<String> instituteIDList = mAdmin
				.getInstituteListOfModuleManual(courseID, degree);
		LinkedList<String> instituteNameList = new LinkedList<String>();
		for (String instituteID : instituteIDList) {
			instituteNameList.add(mAdmin.getInstituteName(instituteID));
		}

		// create filename
		String fileName = latestVersion + ".pdf";

		// Create new ModuleManual
		mAdmin.createModuleManual(latestVersion, courseID, degree, date, date,
				false, Integer.parseInt(examRegulation));

		// Generate new PDF
		try {
			SimplePdfCreator pdfCreator = new SimplePdfCreator();
			// TODO institute ist eine Liste
			pdfCreator.createModulePdf("P:/Team7_12/TestPDF/" + fileName,
					moduleList, "institute", facListName.getFirst(), degree,
					examRegulation, modificationDate, latestAuthor, semester,
					latestVersion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("couldn't create PDF of course: "
					+ splitCourse[1]);
		}
		response.sendRedirect("/SopraMMS/fileExportServlet?filename="
				+ fileName);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
