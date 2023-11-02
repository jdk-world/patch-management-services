package com.example.demo;
import java.util.ArrayList;
import java.util.List;


public class TagRequest {
    public TagRequest(List<String> patchIdsList, List<String> empIdsList) {
		super();
		this.patchIdsList = patchIdsList;
		this.empIdsList = empIdsList;
	}
	public List<String> getPatchIdsList() {
		return patchIdsList;
	}
	public void setPatchIdsList(List<String> patchIdsList) {
		this.patchIdsList = patchIdsList;
	}
	public List<String> getEmpIdsList() {
		return empIdsList;
	}
	public void setEmpIdsList(List<String> empIdsList) {
		this.empIdsList = empIdsList;
	}
	private List<String> patchIdsList;
    private List<String> empIdsList;

    // Constructors, getters, and setters
}
