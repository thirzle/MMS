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
import java.util.GregorianCalendar;
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
import java.util.Calendar;

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
public class GeneratePDF extends SessionCheck {
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
		if(isLoggedIn(request, response)) {
			HttpSession session = request.getSession();
			String semester;
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			Calendar cal = new GregorianCalendar();
			
			// get course from gui
			String fullCourse = request.getParameter("course");
			System.out.println("fullCourse: "+fullCourse);
			String[] splitCourse = fullCourse.split(":");
			String courseName = splitCourse[0];
			String degree = splitCourse[1];
			String courseID = mAdmin.getCourseID(courseName);
			System.out.println("(GeneratePDF.java) courseID=" + courseID
					+ " degree=" + degree);
	
			// get current Date
			Date currentTime = cal.getTime();
	
			// load all modules of course
			LinkedList<Module> moduleList = (LinkedList) mAdmin.getModulesByCourse(
					courseID, degree);
			// generate latest version
			String version;
			if (cal.get(cal.MONTH) >= 4 && cal.get(cal.MONTH) <= 9) {
				semester = "sose";
			} else {
				semester = "wise";
			}
			version = courseID + "_" + degree + "_" + semester
					+ cal.get(cal.YEAR);
			
			//get latest modificatoinauthor and latest modificationdate
			String latestAuthor = moduleList.getFirst().getModificationauthor();
			Date latestModificationDate = moduleList.getFirst().getModificationDate();
			for(Module module : moduleList){
				if(module.getModificationDate().after(latestModificationDate)){
					latestModificationDate = module.getModificationDate();
					latestAuthor = module.getModificationauthor();
				}
			}
			//get examRegulation from gui
			String examRegulation = request.getParameter("examRegulation");
			
			// create filename
			String fileName = version + ".pdf";
	
			//Create new ModuleManual in database
			mAdmin.createModuleManual(version, fileName, courseID, degree, new java.sql.Date(currentTime.getTime()), new java.sql.Date(latestModificationDate.getTime()),
					semester, Integer.parseInt(examRegulation));
	
			// Generate new PDF
			try {
				SimplePdfCreator pdfCreator = new SimplePdfCreator();
				pdfCreator.createModulePdf("P:/Team7_12/TestPDF/" + fileName,
						moduleList, "institut kommt noch raus", "Fakult�t Ingenieurwissenschaften und Informatik", degree,
						examRegulation, latestModificationDate.toString(), latestAuthor, semester,
						version);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("couldn't create PDF of course: "
						+ splitCourse[1]);
			}
			response.sendRedirect("/SopraMMS/FileExportServlet?filename="+fileName);
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
