package com.devcrawlers.conference.management.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AdminDashboardResponse {

	private String totalRoles;
	
	private String totalEditors;
	
	private String totalReviewers;

	public String getTotalRoles() {
		return totalRoles;
	}

	public void setTotalRoles(String totalRoles) {
		this.totalRoles = totalRoles;
	}

	public String getTotalEditors() {
		return totalEditors;
	}

	public void setTotalEditors(String totalEditors) {
		this.totalEditors = totalEditors;
	}

	public String getTotalReviewers() {
		return totalReviewers;
	}

	public void setTotalReviewers(String totalReviewers) {
		this.totalReviewers = totalReviewers;
	}
	
}
