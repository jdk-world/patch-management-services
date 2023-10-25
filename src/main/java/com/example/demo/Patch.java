package com.example.demo;

class Patch {
    private int id;
    private String name;
    private String description;
    private String version;
    private String releaseDate;
    private String complianceDate;
    private String isActive;

    
    public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getComplianceDate() {
		return complianceDate;
	}

	public void setComplianceDate(String complianceDate) {
		this.complianceDate = complianceDate;
	}

	private String application;
    private String applicabilityStatus;
    private String compatibility;

    
    
    
    public String getCompatibility() {
		return compatibility;
	}

	public void setCompatibility(String compatibility) {
		this.compatibility = compatibility;
	}

	public String getApplicabilityStatus() {
		return applicabilityStatus;
	}

	public void setApplicabilityStatus(String applicabilityStatus) {
		this.applicabilityStatus = applicabilityStatus;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public PatchMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(PatchMetadata metadata) {
		this.metadata = metadata;
	}

	private PatchMetadata metadata;

    // Constructors, getters, and setters

    public void update(Patch newPatch) {
        this.name = newPatch.name;
        this.description = newPatch.description;
        this.version = newPatch.version;
        this.releaseDate = newPatch.releaseDate;
        this.metadata = newPatch.metadata;
    }
}

class PatchMetadata {
    private String system;
    private String applicabilityStatus;
    private String compatibility;
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getApplicabilityStatus() {
		return applicabilityStatus;
	}
	public void setApplicabilityStatus(String applicabilityStatus) {
		this.applicabilityStatus = applicabilityStatus;
	}
	public String getCompatibility() {
		return compatibility;
	}
	public void setCompatibility(String compatibility) {
		this.compatibility = compatibility;
	}

    // Constructors, getters, and setters
}