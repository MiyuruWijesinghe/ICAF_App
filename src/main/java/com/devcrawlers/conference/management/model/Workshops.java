package com.devcrawlers.conference.management.model;

import java.util.Date;

import javax.persistence.Transient;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "Workshops")
public class Workshops {

	@Id
	private Integer id;
	
	@JsonIgnore
	private ConferenceDetails conferenceDetail;
	
	@Transient
    private Integer conferenceDetailsId;
	
	@Transient
    private String topic;
	
	private String name;
	
	private String description;
	
	private String documentURL;
	
	private String status;
	
	private String remarks;

	private String createdUser;
	
	private Date createdDate;
	
	private String approvedUser;
	
	private Date approvedDate;
	
	private String rejectedUser;
	
	private Date rejectedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ConferenceDetails getConferenceDetail() {
		return conferenceDetail;
	}

	public void setConferenceDetail(ConferenceDetails conferenceDetail) {
		this.conferenceDetail = conferenceDetail;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getApprovedUser() {
		return approvedUser;
	}

	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getRejectedUser() {
		return rejectedUser;
	}

	public void setRejectedUser(String rejectedUser) {
		this.rejectedUser = rejectedUser;
	}

	public Date getRejectedDate() {
		return rejectedDate;
	}

	public void setRejectedDate(Date rejectedDate) {
		this.rejectedDate = rejectedDate;
	}

	public Integer getConferenceDetailsId() {
		if(conferenceDetail != null) {
			return conferenceDetail.getId();
		} else {
			return null;
		}
	}

	public String getTopic() {
		if(conferenceDetail != null) {
			return conferenceDetail.getTopic();
		} else {
			return null;
		}
	}
	
}
