package com.devcrawlers.conference.management.model;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "Research")
public class Research {

	@Id
	private Integer id;
	
	@JsonIgnore
	private ConferenceTracks conferenceTrack;
	
	@Transient
    private Integer conferenceTracksId;
	
	@Transient
    private String conferenceTracksName;
	
	private String name;
	
	private String publishedDate;
	
	private String description;
	
	private String documentURL;
	
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

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
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
