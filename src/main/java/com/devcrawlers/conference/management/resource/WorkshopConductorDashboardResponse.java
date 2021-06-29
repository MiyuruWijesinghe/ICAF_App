package com.devcrawlers.conference.management.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WorkshopConductorDashboardResponse {

	private String totalApprovedWorkshops;
	
	private String totalRejectedWorkshops;

	public String getTotalApprovedWorkshops() {
		return totalApprovedWorkshops;
	}

	public void setTotalApprovedWorkshops(String totalApprovedWorkshops) {
		this.totalApprovedWorkshops = totalApprovedWorkshops;
	}

	public String getTotalRejectedWorkshops() {
		return totalRejectedWorkshops;
	}

	public void setTotalRejectedWorkshops(String totalRejectedWorkshops) {
		this.totalRejectedWorkshops = totalRejectedWorkshops;
	}
	
}
