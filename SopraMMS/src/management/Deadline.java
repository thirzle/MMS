package management;


import java.sql.Date;
import java.text.SimpleDateFormat;

public class Deadline {
	private Date deadline;
	private Date beginremember;
	private String facultyID;

	
	public Deadline(int dday, int dmonth, int dyear, int bday, int bmonth, int byear,
			 String facultyID) {
		deadline = calcDate(dday, dmonth, dyear);
		beginremember = calcDate(bday, bmonth, byear);
		this.facultyID = facultyID;
	}
	
	
	public Deadline(Date deadline, Date beginremember, String facultyID){
		this.deadline = deadline;
		this.beginremember = beginremember;
		this.facultyID = facultyID;
	}
	
	
	//TODO
	/*
	 * 	Beispiel um den veralteten Konstruktor zu umgehen....
	 * 
	 * 	Calendar cal = Calendar.getInstance();
    	 *	cal.set(Calendar.YEAR, 1988);
    	 *	cal.set(Calendar.MONTH, 1);
    	 *	cal.set(Calendar.DAY_OF_MONTH, 1);
    	 *	Date dateRepresentation = cal.getTime();
	 */
	
	private Date calcDate(int day, int month, int year){
		return new Date(year-1900, month-1, day);
	}

	
	public boolean checkRemember(Date current){
		if(current.getYear()>=beginremember.getYear()){
			if(current.getMonth()>=beginremember.getMonth()){
				if(current.getDay()>=beginremember.getDay()){
					return true;
				}
			}
		}
		return false;
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
