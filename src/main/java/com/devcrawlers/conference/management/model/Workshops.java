package com.devcrawlers.conference.management.model;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "Workshops")
public class Workshops {

	@Id
	private Integer id;
	
	@JsonIgnore
	private ConferenceTracks conferenceTrack;
	
	@Transient
    private Integer conferenceTracksId;
	
	@Transient
    private String conferenceTracksName;
	
	private String name;
	
	private String description;
	
	private String documentURL;
	
	private String conductor;
	
	private String imageURL;
	
	private String venue;
	
	private String date;
	
	private String startTime;
	
	private String endTime;
	
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

	public ConferenceTracks getConferenceTrack() {
		return conferenceTrack;
	}

	public void setConferenceTrack(ConferenceTracks conferenceTrack) {
		this.conferenceTrack = conferenceTrack;
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

	public String getDocumentURL() {
		return documentURL;
	}

	public void setDocumentURL(String documentURL) {
		this.documentURL = documentURL;
	}

	public String getConductor() {
		return conductor;
	}

	public void setConductor(String conductor) {
		this.conductor = conductor;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
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

	public Integer getconferenceTracksId() {
		if(conferenceTrack != null) {
			return conferenceTrack.getId();
		} else {
			return null;
		}
	}

	public String getconferenceTracksName() {
		if(conferenceTrack != null) {
			return conferenceTrack.getName();
		} else {
			return null;
		}
	}
	
}
