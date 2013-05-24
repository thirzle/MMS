package mail;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class EmailTelnet extends HttpServlet {

    private static BufferedOutputStream os = null;
    private static BufferedReader is = null;

    // smtp settings
    private static String host_name = "smtp.uni-ulm.de";
    private static int port = 25;

    // account / authentication settings
    
    //braucht man nicht, man ist im uni netzwerk, also gehts ohne...
    //private static String user_name = "benutzer_name";
    //private static String user_password = "passwort";

    private static String mail_address_from = "adresse@gmail.com";

    private static String mail_footer = "\n\nDo not reply...";

    public static void send_mail(String subject, String mail_address_to, String content) throws IOException {
	mail_transfer(mail_address_from, mail_address_from, mail_address_to, mail_address_to, subject, content + mail_footer);

    }

    private synchronized final static void mail_transfer(String mail_address_from, String mail_from, String mail_address_to, String mail_to, String subject, String content) throws IOException {
	Socket so = null;
	try {
	    if (null == mail_address_from || 0 >= mail_address_from.length() || null == mail_address_to || 0 >= mail_address_to.length() || ((null == subject || 0 >= subject.length()) && (null == content || 0 >= content.length())))
		System.out.println("mail error......");
	    if (null == mail_from || 0 >= mail_from.length())
		mail_from = mail_address_from;
	    if (null == mail_to || 0 >= mail_to.length())
		mail_to = mail_address_to;
	    so = new Socket(host_name, port);
	    os = new BufferedOutputStream(so.getOutputStream());
	    is = new BufferedReader(new InputStreamReader(so.getInputStream()));
	    so.setSoTimeout(10000);
	    stream(true, "220", null);
	    stream(true, "250", "HELO " + host_name + "\r\n");
	    stream(true, "250", "RSET\r\n");
	    stream(true, "250", "MAIL FROM:<" + mail_address_from + ">\r\n");
	    stream(true, "250", "RCPT TO:<" + mail_address_to + ">\r\n");
	    stream(true, "354", "DATA\r\n");
	    stream(false, null, "To: " + mail_to + " <" + mail_address_to + ">\r\n");
	    stream(false, null, "From: " + mail_from + " <" + mail_address_from + ">\r\n");
	    stream(false, null, "Subject: " + subject + "\r\n");
	    stream(false, null, "Mime-Version: 1.0\r\n");
	    stream(false, null, "Content-Type: text/plain; charset=\"UTF-8\"\r\n");
	    // Content-Transfer-Encoding: quoted-printable DARF AUF KEINEN FALL
	    // VERWENDET WERDEN!!!
	    stream(false, null, content + "\r\n");
	    stream(true, "250", ".\r\n");
	    stream(true, "221", "QUIT\r\n");
	} finally {
	    if (is != null)
		try {
		    is.close();
		} catch (Exception ex) {
		    System.out.println("mail error......");
		}
	    if (os != null)
		try {
		    os.close();
		} catch (Exception ex) {
		    System.out.println("mail error......");
		}
	    if (so != null)
		try {
		    so.close();
		} catch (Exception ex) {
		    System.out.println("mail error......");
		}
	    is = null;
	    os = null;
	}
    }

    private final static void stream(boolean read_answer, String answer_beginns_with, String write) throws IOException {
	if (null != write && 0 < write.length()) {
	    System.out.println(new String(write.getBytes(Charset.forName("UTF-8"))));
	    os.write(write.getBytes(Charset.forName("UTF-8")));
	    os.flush();
	}

	if (read_answer) {
	    String sRd = is.readLine() + "\n";
	    if (null != answer_beginns_with && 0 < answer_beginns_with.length() && !sRd.startsWith(answer_beginns_with))
		System.out.println("mail error......");
	}

    }
}
