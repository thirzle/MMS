package management;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Deadline {
    private Date deadline;
    private Date beginremember;
    private String facultyID;

    public Deadline(int dday, int dmonth, int dyear, int bday, int bmonth, int byear, String facultyID) {
	deadline = calcDate(dday, dmonth, dyear);
	beginremember = calcDate(bday, bmonth, byear);
	this.facultyID = facultyID;
    }

    public Deadline(Date deadline, Date beginremember, String facultyID) {
	this.deadline = deadline;
	this.beginremember = beginremember;
	this.facultyID = facultyID;
    }

    // TODO
    /*
     * Beispiel um den veralteten Konstruktor zu umgehen....
     * 
     * Calendar cal = Calendar.getInstance();
     * cal.set(Calendar.YEAR, 1988);
     * cal.set(Calendar.MONTH, 1);
     * cal.set(Calendar.DAY_OF_MONTH, 1);
     * Date dateRepresentation = cal.getTime();
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

    public Date getDeadline() {
	return deadline;
    }

    public void setDeadline(Date deadline) {
	this.deadline = deadline;
    }

    public Date getBeginremember() {
	return beginremember;
    }

    public void setBeginremember(Date beginremember) {
	this.beginremember = beginremember;
    }

    public String getFacultyID() {
	return facultyID;
    }

    public void setFacultyID(String facultyID) {
	this.facultyID = facultyID;
    }
}
