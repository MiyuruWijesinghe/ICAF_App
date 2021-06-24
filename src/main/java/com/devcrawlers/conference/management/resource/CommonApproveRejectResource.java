package com.devcrawlers.conference.management.resource;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CommonApproveRejectResource {

	@NotBlank(message = "{common.not-null}")
	private String userName;
	
	@NotBlank(message = "{common.not-null}")
	private String remarks;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
