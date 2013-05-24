package mail;

import org.apache.commons.mail.*;

public class EmailApache {
    
    
    //smtp settings
    private static String host_name = "smtp.googlemail.com";
    private static int port = 465;
    
    //account / authentication settings
    private static String user_name = "benutzer_name";
    private static String user_password = "passwort";
    
    private static String mail_address_from = "adresse@gmail.com";
    
    private static String mail_footer = "\n\nDo not reply...";
    
    
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
