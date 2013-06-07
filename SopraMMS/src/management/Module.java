package management;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Module {
	private String name;
	private Date creationDate;
	private Date modificationDate;
	private boolean approved;
	private long moduleID;
	private int version;
	private String instituteID;
	private String  subject;
	private Random random;

	private String modificationauthor;

	private List<Entry> entryList = new LinkedList<Entry>();


	public Module(int moduleID, int version, String name, Date creationDate,
			Date modificationDate, boolean approved, String insituteID, String subject, String modificationauthor) {
		this.moduleID = moduleID;
		this.version = version;
		this.name = name;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.approved = approved;
		this.instituteID = insituteID;
		this.subject = subject;
		this.modificationauthor = modificationauthor;
		random = new Random();
	}


	public Module(int moduleID, int version, String name, Date creationDate,
			Date modificationDate, boolean approved, String insituteID,
			List<Entry> entryList, String subject, String modificationauthor) {
		this.moduleID = moduleID;
		this.version = version;
		this.name = name;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.approved = approved;
		this.instituteID = insituteID;
		this.entryList = entryList;
		this.subject = subject;
		this.modificationauthor = modificationauthor;
		random = new Random();
	}
	
	//updated version
	public Module(long moduleID, int version, String name, Date creationDate,
			Date modificationDate, boolean approved, String insituteID, String subject, String modificationauthor) {
		this.moduleID = moduleID;
		this.version = version;
		this.name = name;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.approved = approved;
		this.instituteID = insituteID;
		this.subject = subject;
		this.modificationauthor = modificationauthor;
		random = new Random();
	}


	public Module(long moduleID, int version, String name, Date creationDate,
			Date modificationDate, boolean approved, String insituteID,
			List<Entry> entryList, String subject, String modificationauthor) {
		this.moduleID = moduleID;
		this.version = version;
		this.name = name;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.approved = approved;
		this.instituteID = insituteID;
		this.entryList = entryList;
		this.subject = subject;
		this.modificationauthor = modificationauthor;
		random = new Random();
	}
	
	
	public Module(String name, Date creationDate,
			Date modificationDate, boolean approved, String insituteID, String subject, String modificationauthor) {
		random = new Random();
		this.moduleID = createModuleID();
		this.version = 1;
		this.name = name;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.approved = approved;
		this.instituteID = insituteID;
		this.subject = subject;
		this.modificationauthor = modificationauthor;
		
	}


	public Module(String name, Date creationDate,
			Date modificationDate, boolean approved, String insituteID,
			List<Entry> entryList, String subject, String modificationauthor) {
		random = new Random();
		this.moduleID = createModuleID();
		this.version = 1;
		this.name = name;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.approved = approved;
		this.instituteID = insituteID;
		this.entryList = entryList;
		this.subject = subject;
		this.modificationauthor = modificationauthor;
		
	}


	private long createModuleID() {
		return random.nextInt(1000)+100*Calendar.getInstance().getTimeInMillis();
		
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


	public long getModuleID() {
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


	public int getVersion() {
		return version;
	}


	public void setVersion(int version) {
		this.version = version;
	}


	@SuppressWarnings("unused")
	private Date getCurrentDate() {
		Date date = new Date();
		return date;
	}
	
	public void print()
	{
		System.out.println("############ Modul #############");
		System.out.println("Titel: "+name);
		System.out.println("Autor: "+modificationauthor);
		System.out.println("Institut: "+instituteID);
		System.out.println("Erstellungsdatum: "+creationDate.toString());
		
		for (Entry e : entryList) {
			System.out.println(e.toString());
		}
		System.out.println("################################");
	}

}
