package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import javax.servlet.annotation.WebInitParam;

/**
 * Servlet implementation class FileExportServlet
 * 
 * @author Teresa Hirzle, Johann Albach
 */
//@WebServlet(urlPatterns = { "/FileExportServlet" }, initParams = { @WebInitParam(name = "exportFolder", value = "P:/Team7_12/TestPDF/", description = "Folder with files to export") })
@WebServlet(urlPatterns = { "/FileExportServlet" })
public class FileExportServlet extends SessionCheck {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileExportServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request, response)) {
			response.setContentType("application/pdf");
			//AJ
			//String exportFolder = this.getInitParameter("exportFolder");
			String exportFolder = sysconfig.Config.system_path.getValue() + sysconfig.Config.system_pdf_path.getValue();
			String exportFile = request.getParameter("filename");
			FileInputStream fi = new FileInputStream(new File(exportFolder
					+ exportFile));
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = (fi.read(buffer))) > 0) {
				response.getOutputStream().write(buffer, 0, bytesRead);
			}
			fi.close();
		} else {
			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
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
