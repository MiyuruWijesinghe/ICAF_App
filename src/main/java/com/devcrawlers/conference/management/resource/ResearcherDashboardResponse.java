package com.devcrawlers.conference.management.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResearcherDashboardResponse {

	private String totalApprovedResearches;
	
	private String totalRejectedResearches;

	public String getTotalApprovedResearches() {
		return totalApprovedResearches;
	}

	public void setTotalApprovedResearches(String totalApprovedResearches) {
		this.totalApprovedResearches = totalApprovedResearches;
	}

	public String getTotalRejectedResearches() {
		return totalRejectedResearches;
	}

	public void setTotalRejectedResearches(String totalRejectedResearches) {
		this.totalRejectedResearches = totalRejectedResearches;
	}
	
}
