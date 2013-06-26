package controller;

import sysconfig.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import backend.TaskManager;

import management.Deadline;

import user.UserAdministration;

/**
 * Application Lifecycle Listener implementation class ApplicationListener
 *
 */
@WebListener
public class ApplicationListener implements ServletContextListener, HttpSessionListener {

    /**
     * Default constructor. 
     */
    public ApplicationListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
	//AJ's system startup...
        System.out.println("MMS starting...");
        
        
        
        Config conf = new Config();
        conf.load_settings();
        
        
        
        
	Timer caretaker = new Timer();
	final UserAdministration uAdmin = new UserAdministration();
	List<Deadline> deadlines = new LinkedList<Deadline>();
	List<TaskManager> deadlineTasks = new LinkedList<TaskManager>();
	for (String facultyID : uAdmin.getAllFacultiesID()) {
	    deadlines.add(uAdmin.getDeadlinebyFaculty(facultyID));
	}
	for (Deadline deadline : deadlines) {
	    deadlineTasks.add(new TaskManager(deadline));
	}
	TimerTask clearHistroy = new TimerTask() {
	    public void run() {
		uAdmin.clearHistory();
	    }
	};
	caretaker.schedule(clearHistroy, 0, 60 * 24 * 60 * 60 * 1000);
	System.out.println("MMS started.");
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
