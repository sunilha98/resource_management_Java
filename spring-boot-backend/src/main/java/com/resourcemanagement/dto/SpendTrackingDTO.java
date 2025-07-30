package com.resourcemanagement.dto;

public class SpendTrackingDTO {

	private String clientName;
	private String projectName;
	private double plannedSpend;
	private double actualSpend;
	private double variance;
	
	
	
	public SpendTrackingDTO() {
	}
	public SpendTrackingDTO(String clientName, String projectName, double plannedSpend, double actualSpend,
			double variance) {
		super();
		this.clientName = clientName;
		this.projectName = projectName;
		this.plannedSpend = plannedSpend;
		this.actualSpend = actualSpend;
		this.variance = variance;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public double getPlannedSpend() {
		return plannedSpend;
	}
	public void setPlannedSpend(double plannedSpend) {
		this.plannedSpend = plannedSpend;
	}
	public double getActualSpend() {
		return actualSpend;
	}
	public void setActualSpend(double actualSpend) {
		this.actualSpend = actualSpend;
	}
	public double getVariance() {
		return variance;
	}
	public void setVariance(double variance) {
		this.variance = variance;
	}

}
