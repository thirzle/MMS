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

	private List<Entry> entryList = new LinkedList<Entry>();


	public Module(int moduleID, String name, Date creationDate,
			Date modificationDate, boolean approved, String insituteID) {
		this.moduleID = moduleID;
		this.name = name;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.approved = approved;
		this.instituteID = insituteID;
	}
	public Module(int moduleID, String name, Date creationDate,
			Date modificationDate, boolean approved, String insituteID, List<Entry> entryList) {
		this.moduleID = moduleID;
		this.name = name;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.approved = approved;
		this.instituteID = insituteID;
		this.entryList = entryList;
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


	public void addTextualEntry(TextualEntry textualentry) {
		entryList.add(textualentry);
	}


	public void addEffortEntry(EffortEntry effortentry) {
		entryList.add(effortentry);
	}


	public void addCourseEntry(CourseEntry courseentry) {
		entryList.add(courseentry);
	}


	@SuppressWarnings("unused")
	private Date getCurrentDate() {
		Date date = new Date();
		return date;
	}

}
