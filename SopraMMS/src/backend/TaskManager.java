package backend;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import user.User;
import user.UserAdministration;

import mail.EmailTelnet;
import management.Deadline;
import management.ModuleAdministration;

/**
 * @author ...
 *
 */
public class TaskManager {
	
	/**
	 * 
	 * @param deadline			{@link Deadline#getDeadline()}
	 */
	public TaskManager (final Deadline deadline) {
		final ModuleAdministration mAdmin = new ModuleAdministration();
		final UserAdministration uAdmin = new UserAdministration();
		Date now = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(deadline.getDeadline());
		cal.set(cal.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+14);
		Date delay = cal.getTime();
		Timer caretaker = new Timer();
		TimerTask clearModules = new TimerTask() {
			public void run() {
				mAdmin.clearDatabase();	
			}
		};
		
		TimerTask clearDeadline = new TimerTask() {
			public void run() {
				uAdmin.deleteDeadlinebyFaculty(deadline.getFacultyID());
			}
		};
		
		TimerTask notifyUsers = new TimerTask() {
			public void run() {
				String date = new SimpleDateFormat("dd.MM.yyyy").format(deadline.getDeadline());
				EmailTelnet mail = new EmailTelnet();
				for (User user : uAdmin.getAllUsers()) {
					StringBuilder text = new StringBuilder();
					text.append("Sehr geehrte/geehrter Frau/Herr " + user.getLastName()
						+ ",");
					text.append("\n\n");
					text.append("Bis zum "+date+" müssen Änderungen oder neue Module im MMS eingereicht werden,"
						+" im kommenden Semester wirksam zu sein.");
					text.append("\n\n");
					text.append("Falls Sie diesen Termin nicht einhalten können, treten Sie bitte"
						+" mit dem Administrator in Kontakt.");
					text.append("\n\n");
					text.append("Mit freundlichen Grüßen");
					text.append("\n");
					text.append("MMS-Team");
					try {
							mail.send_mail("MMS - Stichtag", user.getMail(), text.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		if(now.after(deadline.getBeginremember())){
			//caretaker.schedule(notifyUsers, 0);
		} else {
			//caretaker.schedule(notifyUsers, deadline.getBeginremember());
		}
		caretaker.schedule(clearModules, delay);
		caretaker.schedule(clearDeadline, delay);	
	}
}
