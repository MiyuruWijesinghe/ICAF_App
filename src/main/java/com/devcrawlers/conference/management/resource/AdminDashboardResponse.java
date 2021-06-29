package com.devcrawlers.conference.management.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AdminDashboardResponse {

	private String totalConferences;
	
	private String totalConferenceDetails;
	
	private String totalTracks;
	
	private String totalKeynoteSpeakers;
	
	private String totalResearches;
	
	private String totalWorkshops;
	
	private String totalUsers;
	
	private String totalRoles;

	public String getTotalConferences() {
		return totalConferences;
	}

	public void setTotalConferences(String totalConferences) {
		this.totalConferences = totalConferences;
	}

	public String getTotalConferenceDetails() {
		return totalConferenceDetails;
	}

	public void setTotalConferenceDetails(String totalConferenceDetails) {
		this.totalConferenceDetails = totalConferenceDetails;
	}

	public String getTotalTracks() {
		return totalTracks;
	}

	public void setTotalTracks(String totalTracks) {
		this.totalTracks = totalTracks;
	}

	public String getTotalKeynoteSpeakers() {
		return totalKeynoteSpeakers;
	}

	public void setTotalKeynoteSpeakers(String totalKeynoteSpeakers) {
		this.totalKeynoteSpeakers = totalKeynoteSpeakers;
	}

	public String getTotalResearches() {
		return totalResearches;
	}

	public void setTotalResearches(String totalResearches) {
		this.totalResearches = totalResearches;
	}

	public String getTotalWorkshops() {
		return totalWorkshops;
	}

	public void setTotalWorkshops(String totalWorkshops) {
		this.totalWorkshops = totalWorkshops;
	}

	public String getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(String totalUsers) {
		this.totalUsers = totalUsers;
	}

	public String getTotalRoles() {
		return totalRoles;
	}

	public void setTotalRoles(String totalRoles) {
		this.totalRoles = totalRoles;
	}
	
}
