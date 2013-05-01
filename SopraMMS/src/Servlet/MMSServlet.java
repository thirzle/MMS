package Servlet;

import java.io.IOException;
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
import org.xml.sax.SAXException;

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
		String[] Loginname = {"admin","kopetri","herbert12","Tombat"};
		String[] Firstname = {"Peter","Sebastian","Herbert","Kevin"};
		String[] Lastname = {"Griffin","Hartwig","Pan","Mantz"};
		String[] Email = {"peter.griffin@uni-ulm.de","sebastian.hartwig@uni-ulm.de","herbert.pan@uni-ulm.de","kevin.mantz@uni-ulm.de"};
		String[] Rights = {"Administrator","Administrator","Redakteur","Modulverantwortlicher"};
		String[] Institute = {"Eingebettete Systeme","Compilerbau","Neuroinformatik","Theoretische Informatik"};
		String[] Supervisor = {"keinen","keinen","Jochen Wittmann","Berndt Kappler"};
		ServletOutputStream os = response.getOutputStream();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element root = document.createElement("User");
			document.appendChild(root);
			for (int i = 0; i < 4; i++) {
			Element loginname = document.createElement("loginname");
			loginname.appendChild(document.createTextNode(Loginname[i]));
			
			root.appendChild(loginname);
			
			Element firstname = document.createElement("firstname");
			firstname.appendChild(document.createTextNode(Firstname[i]));
			root.appendChild(firstname);
			
			Element lastname = document.createElement("lastname");
			lastname.appendChild(document.createTextNode(Lastname[i]));
			root.appendChild(lastname);
			
			Element email = document.createElement("email");
			email.appendChild(document.createTextNode(Email[i]));
			root.appendChild(email);
			
			Element rights = document.createElement("rights");
			rights.appendChild(document.createTextNode(Rights[i]));
			root.appendChild(rights);
			
			Element institute = document.createElement("institute");
			institute.appendChild(document.createTextNode(Institute[i]));
			root.appendChild(institute);
			
			Element supervisor = document.createElement("supervisor");
			supervisor.appendChild(document.createTextNode(Supervisor[i]));
			root.appendChild(supervisor);
			}
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
