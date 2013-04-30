package Servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Servlet implementation class MMSServlet
 */
@WebServlet("/MMSServlet")
public class MMSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MMSServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletOutputStream os = response.getOutputStream();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element root = document.createElement("User");
			document.appendChild(root);
			
			Element loginname = document.createElement("loginname");
			loginname.appendChild(document.createTextNode("admin"));
			root.appendChild(loginname);
			
			Element firstname = document.createElement("firstname");
			firstname.appendChild(document.createTextNode("Peter"));
			root.appendChild(firstname);
			
			Element lastname = document.createElement("lastname");
			lastname.appendChild(document.createTextNode("Griffin"));
			root.appendChild(lastname);
			
			Element email = document.createElement("email");
			email.appendChild(document.createTextNode("peter.griffin@uni-ulm.de"));
			root.appendChild(email);
			
			Element rights = document.createElement("rights");
			rights.appendChild(document.createTextNode("Administrator"));
			root.appendChild(rights);
			
			Element institute = document.createElement("institute");
			institute.appendChild(document.createTextNode("Eingebettete Systeme"));
			root.appendChild(institute);
			
			Element supervisor = document.createElement("supervisor");
			supervisor.appendChild(document.createTextNode("keinen"));
			root.appendChild(supervisor);
			
			TransformerFactory transFac = TransformerFactory.newInstance();
			Transformer transformer = transFac.newTransformer();
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(os);
			transformer.transform(source, result);
			os.flush();
			os.close();
	    } catch (ParserConfigurationException e) {
			e.printStackTrace();
	    } catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
