package management;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Module {
	private String name;
	private Date creationDate;
	private Date modificationDate;
	private boolean approved;
	private int moduleID;
	private String instituteID;
	private String  subject;

	private String modificationauthor;

	private List<Entry> entryList = new LinkedList<Entry>();


	public Module(int moduleID, String name, Date creationDate,
			Date modificationDate, boolean approved, String insituteID, String subject, String modificationauthor) {
		this.moduleID = moduleID;
		this.name = name;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.approved = approved;
		this.instituteID = insituteID;
		this.subject = subject;
		this.modificationauthor = modificationauthor;
	}


	public Module(int moduleID, String name, Date creationDate,
			Date modificationDate, boolean approved, String insituteID,
			List<Entry> entryList, String subject, String modificationauthor) {
		this.moduleID = moduleID;
		this.name = name;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.approved = approved;
		this.instituteID = insituteID;
		this.entryList = entryList;
		this.subject = subject;
		this.modificationauthor = modificationauthor;
	}


	public String getName() {
		return name;
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public Date getModificationDate() {
		return modificationDate;
	}


	public boolean isApproved() {
		return approved;
	}


	public int getModuleID() {
		return moduleID;
	}


	public String getInstituteID() {
		return instituteID;
	}


	public List<Entry> getEntryList() {
		return entryList;
	}


	public void setEntryList(List<Entry> entryList) {
		this.entryList = entryList;
	}


	public void addTextualEntry(TextualEntry textualentry) {
		entryList.add(textualentry);
	}


	public void addEffortEntry(EffortEntry effortentry) {
		entryList.add(effortentry);
	}


	public void addCourseEntry(CourseEntry courseentry) {
		entryList.add(courseentry);
	}
	
	public void addEntryList(List<Entry> entries){
		entryList.addAll(entries);
	}

	
	public void addTextualEntryList(List<TextualEntry> entries){
		entryList.addAll(entries);
	}
	
	public String getSubject() {
	    return subject;
	}


	public String getModificationauthor() {
	    return modificationauthor;
	}


	@SuppressWarnings("unused")
	private Date getCurrentDate() {
		Date date = new Date();
		return date;
	}

}
