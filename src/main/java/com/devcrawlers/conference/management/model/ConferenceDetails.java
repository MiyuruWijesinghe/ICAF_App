package com.devcrawlers.conference.management.model;

import java.math.BigDecimal;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "ConferenceDetails")
public class ConferenceDetails {

	@Id
	private Integer id;
	
	@JsonIgnore
	private Conference conferences;
	
	@Transient
    private Integer conferenceId;
	
	@Transient
    private String conferenceName;
	
	@Transient
    private String conferenceYear;
	
	private String topic;
	
	private String description;
	
	private String conductor;
	
	private String venue;
	
	private String date;
	
	private String startTime;
	
	private String endTime;
	
	private BigDecimal payment;
	
	private String status;
	
	private String remarks;

	private String createdUser;
	
	private String createdDate;
	
	private String approvedUser;
	
	private String approvedDate;
	
	private String rejectedUser;
	
	private String rejectedDate;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Conference getConferences() {
		return conferences;
	}

	public void setConferences(Conference conferences) {
		this.conferences = conferences;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getConductor() {
		return conductor;
	}

	public void setConductor(String conductor) {
		this.conductor = conductor;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getApprovedUser() {
		return approvedUser;
	}

	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getRejectedUser() {
		return rejectedUser;
	}

	public void setRejectedUser(String rejectedUser) {
		this.rejectedUser = rejectedUser;
	}

	public String getRejectedDate() {
		return rejectedDate;
	}

	public void setRejectedDate(String rejectedDate) {
		this.rejectedDate = rejectedDate;
	}
	
	public Integer getConferenceId() {
		if(conferences != null) {
			return conferences.getId();
		} else {
			return null;
		}
	}

	public String getConferenceName() {
		if(conferences != null) {
			return conferences.getName();
		} else {
			return null;
		}
	}

	public String getConferenceYear() {
		if(conferences != null) {
			return conferences.getYear();
		} else {
			return null;
		}
	}
	
}
