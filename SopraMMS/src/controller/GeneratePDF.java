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
		
		LinkedList<String> facListName = (LinkedList) uAdmin
				.getAllFacultiesByName();
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

//		get course from gui
		String fullCourse = request.getParameter("course");
		String course = courseArray[Integer.parseInt(fullCourse)];
		String[] splitCourse = course.split(" ");
		String courseName = splitCourse[1]; 
		String degree = splitCourse[0];
		String courseID = mAdmin.getCourseID(courseName);
		System.out.println("(GeneratePDF.java) courseID="+courseID +" degree="+degree);

		// load all modules of course
		LinkedList<Module> moduleList = (LinkedList) mAdmin.getModulesByCourse(
				courseID, degree);
		
		String latestVersion = mAdmin.getLatestVersionOfModuleManual(courseID, degree);
		String latestAuthor = mAdmin.getLastModificationAuthor(courseID, degree);
		String modificationDate = mAdmin.getLastModificationDateOfModuleManual(courseID, degree);
		String examRegulation = request.getParameter("examRegulation");
		LinkedList<String> instituteIDList = mAdmin.getInstituteListOfModuleManual(courseID, degree);
		LinkedList<String> instituteNameList = new LinkedList<String>();
		for (String instituteID : instituteIDList) {
			instituteNameList.add(mAdmin.getInstituteName(instituteID));
		}
		String semester;
		if(latestVersion.charAt(0) == 's'){
			semester = latestVersion.substring(0, 8);
		}
		else{
			semester = latestVersion.substring(0, 12);
		}
		String fileName = courseID + "_" + degree + "_"+semester+".pdf";
//		Create new ModuleManual
//		get current Date
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date currentTime = new Date();
		String date = formatter.format(currentTime);
		
		mAdmin.createModuleManual(latestVersion, courseID, degree, date, date, false, Integer.parseInt(examRegulation));
		
//		Generate new PDF
		try {
			SimplePdfCreator pdfCreator = new SimplePdfCreator();
// TODO institute ist eine Liste
			pdfCreator.createModulePdf("P:/Team7_12/TestPDF/" + fileName,
					moduleList, instituteNameList.getFirst(), facListName.getFirst(), degree, examRegulation,
					modificationDate,
					latestAuthor, semester,
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
		// TODO Auto-generated method stub
	}

}
