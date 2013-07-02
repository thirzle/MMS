package mail;

import java.util.*;
import javax.activation.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * Works on the Apache Mercury mail server. Needs to have proper authentication
 * to ensure outgoing mails DO NOT GET BLOCKED at target mail server...
 * 
 * Needs to be set up properly on the server side...
 * http://www.derdualstudent.de/mercury-einrichten-xampp-mailserver.html
 * 
 * Use EmailApache class for fast setup on external mail server (e.g. gmail, gmx, etc.)
 * or EmailTelnet to send mails from university's mail server... (server needs to run in uni network)
 */

/**
 * This class sends mails via the Mercury mail server.
 * <p>
 * The Mercury mail server therefore needs to be set up properly on the server to NOT fail
 * the authentication on other mail servers. Otherwise no mails will be accepted on other mail servers.
 * <p>
 * Set up {@link #mail_address_from}.
 * <p>
 * Use the<br>
 * {@link #send_mail(String subject, String mail_address_to, String content)},<br>
 * function to send mails.
 * @author AJ
 *
 */
@SuppressWarnings("unused")
public class EmailMercury {

    // smtp settings
    private static String host_name = "mail.smtp.host";

    // account / authentication settings
    //private static String user_name = "benutzer_name";
    //private static String user_password = "passwort";

    private static String mail_address_from = "adresse@gmail.com";

    private static String mail_footer = "\n\nThis message was sent by the MMS of the University of Ulm. It is not possible to reply.";
    

    /**
     * Sends a mail to the given address.
     * 
     * @param subject		The subject of the mail.
     * @param mail_address_to	The address this mail is going to.
     * @param content		The content of this mail.
     */
    public static void send_mail(String subject, String mail_address_to, String content) {
	// Assuming you are sending email from localhost
	String host = "localhost";

	// Get system properties
	Properties properties = System.getProperties();

	// Setup mail server
	properties.setProperty(host_name, host);

	// Debug mode ON
	//properties.put("mail.debug", "true");

	// Get the default Session object.
	Session session = Session.getDefaultInstance(properties);

	try {
	    // Create a default MimeMessage object.
	    MimeMessage message = new MimeMessage(session);

	    // Set From: header field of the header.
	    message.setFrom(new InternetAddress(mail_address_from));

	    // Set To: header field of the header.
	    message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail_address_to));

	    // Set Subject: header field
	    message.setSubject(subject);

	    // Now set the actual message
	    message.setText(content + mail_footer);

	    // Send message
	    Transport.send(message);
	    System.out.println("Sent message successfully....");
	} catch (MessagingException mex) {
	    mex.printStackTrace();
	}
    }
}