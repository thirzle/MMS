package management;

import java.util.Date;

public class Module {
	private String name;
	private Date creationDate;
	private Date modificationDate;
	private boolean approved;
	private int moduleID;
	private String instituteID;


	public Module(int moduleID, String name, Date creationDate, Date modificationDate,
			boolean approved, String insituteID) {
		this.moduleID = moduleID;
		this.name = name;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.approved = approved;
		this.instituteID = insituteID;
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

}
