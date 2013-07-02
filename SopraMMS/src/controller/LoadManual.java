package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoadManual
 * 
 * @author Teresa Hirzle, Johann Albach
 */
@WebServlet("/LoadManual")
public class LoadManual extends SessionCheck {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadManual() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/pdf");
		String exportFolder = sysconfig.Config.system_path.getValue() + sysconfig.Config.system_manual.getValue();
		FileInputStream fi = new FileInputStream(new File(exportFolder));
		int bytesRead = 0;	
		byte[] buffer = new byte[1024];
		while ((bytesRead = (fi.read(buffer))) > 0) {
			response.getOutputStream().write(buffer, 0, bytesRead);
		}
		fi.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
