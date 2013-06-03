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
 */
@WebServlet(urlPatterns = { "/fileExportServlet" }, initParams = { @WebInitParam(name = "exportFolder", value = "P:/Team7_12/TestPDF/", description = "Folder with files to export") })
public class FileExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileExportServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/pdf");
		String exportFolder = this.getInitParameter("exportFolder");
		String exportFile = request.getParameter("filename");
		FileInputStream fi = new FileInputStream(new File(exportFolder
				+ exportFile));
		int bytesRead = 0;
		byte[] buffer = new byte[1024];
		while ((bytesRead = (fi.read(buffer))) > 0) {
			response.getOutputStream().write(buffer, 0, bytesRead);
		}
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
