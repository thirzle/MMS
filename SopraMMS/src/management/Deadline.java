package management;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class is used for Deadlines of specific faculties.
 * <p>
 * Some useful functions for {@link Date}s are provided.
 * It creates Date objects, and can check if dates expired.
 * 
 * @see Date
 * 
 * @author Max
 *
 */
public class Deadline {
    /**
     * The Date of the deadline.
     */
    private Date deadline;
    /**
     * The Date on which users should be notified.
     */
    private Date beginremember;
    /**
     * The faculty ID.
     */
    private String facultyID;

    /**
     * Constructs the Deadline object from given values.
     * <p>
     * Invoke this constructor if you do not have created or want to create Date objects.
     * Instead provide values for the dates.
     * 
     * 
     * 
     * @param dday		The day of the deadline date.
     * @param dmonth		The month of the deadline date.
     * @param dyear		The year of the deadline date.
     * @param bday		The day of the remember/notification date.
     * @param bmonth		The month of the remember/notification date.
     * @param byear		The year of the remember/notification date.
     * @param facultyID		The faculty's ID, this Deadline object belongs to.
     */
    public Deadline(int dday, int dmonth, int dyear, int bday, int bmonth, int byear, String facultyID) {
	deadline = calcDate(dday, dmonth, dyear);
	beginremember = calcDate(bday, bmonth, byear);
	this.facultyID = facultyID;
    }

    /**
     * Constructs the Deadline object from given {@link Date}s.
     * <p>
     * Invoke this constructor if you have already the {@link #deadline} and {@link #beginremember} {@link Date} objects.
     * 
     * @see #deadline
     * @see #beginremember
     * @see Date
     * 
     * @param deadline		The {@link #deadline}  {@link Date} object.
     * @param beginremember	The {@link #beginremember} {@link Date} object.
     * @param facultyID		The faculty's ID.
     */
    public Deadline(Date deadline, Date beginremember, String facultyID) {
	this.deadline = deadline;
	this.beginremember = beginremember;
	this.facultyID = facultyID;
    }

    /*
     * Beispiel um den veralteten Konstruktor zu umgehen....
     * 
     * Calendar cal = Calendar.getInstance();
     * cal.set(Calendar.YEAR, 1988);
     * cal.set(Calendar.MONTH, 1);
     * cal.set(Calendar.DAY_OF_MONTH, 1);
     * Date dateRepresentation = cal.getTime();
     */

    /**
     * Creates a Date object from day, month and year values.
     * 
     * @param day	The day.
     * @param month	The month.
     * @param year	The year.
     * @return		The Date object.
     */
    private Date calcDate(int day, int month, int year) {
	//Date constructor deprecation fix (AJ)
	
	//old
	/*
	 * return new Date(year-1900, month-1, day);
	 */
	
	//TODO: sollte noch jemand durch checken ob das so stimmt...
	//new
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.YEAR, year);
	cal.set(Calendar.MONTH, month);
	cal.set(Calendar.DAY_OF_MONTH, day);
	return new java.sql.Date(cal.getTime().getTime());
	
    }

    /**
     * Checks if the remember functions need to be called.
     *  
     * @param current	The current date.
     * @return		True or False.
     */
    public boolean checkRemember(Date current) {
	//Date function's deprecation fix (AJ)
	
	//old
	/*
	if (current.getYear() >= beginremember.getYear()) {
	    if (current.getMonth() >= beginremember.getMonth()) {
		if (current.getDay() >= beginremember.getDay()) {
		    return true;
		}
	    }
	}
	return false;
	*/
	
	//TODO: sollte noch jemand durch checken ob das so stimmt...
	//new
	Calendar calcurrent = new GregorianCalendar();
	calcurrent.setTime(beginremember);

	Calendar calbeginremember = new GregorianCalendar();
	calbeginremember.setTime(current);

	int yearcurrent = calcurrent.get(Calendar.YEAR);
	int monthcurrent = calcurrent.get(Calendar.MONTH);
	int daycurrent = calcurrent.get(Calendar.DAY_OF_YEAR);

	int yearbeginremember = calbeginremember.get(Calendar.YEAR);
	int monthbeginremember = calbeginremember.get(Calendar.MONTH);
	int daybeginremember = calbeginremember.get(Calendar.DAY_OF_YEAR);

	return yearcurrent >= yearbeginremember && monthcurrent >= monthbeginremember && daycurrent >= daybeginremember;
    }

    /**
     * Returns the {@link #deadline} Date object.
     * @return	The {@link #deadline} Date object.
     */
    public Date getDeadline() {
	return deadline;
    }

    /**
     * Sets the {@link #deadline} Date object.
     * @param deadline	The {@link #deadline} Date object.
     */
    public void setDeadline(Date deadline) {
	this.deadline = deadline;
    }

    /**
     * Returns the {@link #beginremember} Date object.
     * @return 	The {@link #beginremember} Date object.
     */
    public Date getBeginremember() {
	return beginremember;
    }

    /**
     * Sets the {@link #beginremember} Date object.
     * @param beginremember	The {@link #beginremember} Date object.
     */
    public void setBeginremember(Date beginremember) {
	this.beginremember = beginremember;
    }

    /**
     * Returns the faculty's ID.
     * @return	The faculty's ID.
     */
    public String getFacultyID() {
	return facultyID;
    }

    /**
     * Sets the faculty's ID.
     * @param facultyID	The faculty's ID.
     */
    public void setFacultyID(String facultyID) {
	this.facultyID = facultyID;
    }
}
