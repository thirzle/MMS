package mail;

import org.apache.commons.mail.*;

/**
 * This class uses Apache's commons mail and is configured to send
 * mails via an external email account.
 * <p>
 * Set up the attributes like {@link #host_name}, {@link #port}, 
 * {@link #user_name}, {@link #user_password} and {@link #mail_address_from}
 * to meet the requirements of your account.
 * <p>
 * Current settings are already working with a google mail account.
 * Just change {@link #user_name}, {@link #user_password} and {@link #mail_address_from}.
 * <p>
 * Use the <br>
 * {@link #send_mail(String subject, String mail_address_to, String content)}<br>
 * function to send mails.
 * 
 * @author AJ
 *
 */
public class EmailApache {
    
    
    //smtp settings
    private static String host_name = "smtp.googlemail.com";
    private static int port = 465;
    
    //account / authentication settings
    private static String user_name = "benutzer_name";
    private static String user_password = "passwort";
    
    private static String mail_address_from = "adresse@gmail.com";
    
    private static String mail_footer = "\n\nThis message was sent by the MMS of the University of Ulm. It is not possible to reply.";
    
    /**
     * Sends a mail to the given address with subject and content.
     * 
     * @param subject		The subject that will appear in the mail.
     * @param mail_address_to	The address the mail is sent to.
     * @param content		The content of the mail.
     */
    public static void send_mail(String subject, String mail_address_to, String content) {
	Email email = new SimpleEmail();
	email.setHostName(host_name);
	email.setSmtpPort(port);
	email.setAuthenticator(new DefaultAuthenticator(user_name, user_password));
	email.setSSLOnConnect(true);
	try {
	    email.setFrom(mail_address_from);
	} catch (EmailException e) {
	    System.out.println("email.setFrom() error...");
	}
	email.setSubject(subject);
	try {
	    email.setMsg(content + mail_footer);
	} catch (EmailException e) {
	    System.out.println("email.setMsg() error...");
	}
	try {
	    email.addTo(mail_address_to);
	} catch (EmailException e) {
	    System.out.println("email.addto() error...");
	}
	try {
	    email.send();
	} catch (EmailException e) {
	    System.out.println("email.send() error...");
	}
    }
}
