package management;


import java.sql.Date;

public class Deadline {
	private static Date deadline;
	private Date beginremember;
	private int tolerance;
	private String facultyID;

	
	public Deadline(int dday, int dmonth, int dyear, int bday, int bmonth, int byear,
			int tolerance, String facultyID) {
		deadline = calcDate(dday, dmonth, dyear);
		beginremember = calcDate(bday, bmonth, byear);
		this.tolerance = tolerance;
		this.facultyID = facultyID;
	}
	
	
	public Deadline(Date deadline, Date beginremember, int tolerance, String facultyID){
		this.deadline = deadline;
		this.beginremember = beginremember;
		this.tolerance = tolerance;
		this.facultyID = facultyID;
	}
	
	
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
	
	
	public static Date getDeadline() {
		return deadline;
	}


	public static void setDeadline(Date deadline) {
		Deadline.deadline = deadline;
	}


	public Date getBeginremember() {
		return beginremember;
	}


	public void setBeginremember(Date beginremember) {
		this.beginremember = beginremember;
	}


	public int getTolerance() {
		return tolerance;
	}


	public void setTolerance(int tolerance) {
		this.tolerance = tolerance;
	}


	public String getFacultyID() {
		return facultyID;
	}


	public void setFacultyID(String facultyID) {
		this.facultyID = facultyID;
	}
}
