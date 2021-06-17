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
	private User users;
	
	@Transient
    private Integer userId;
	
	@Transient
    private String userName;
	
	private String name;
	
	private String description;
	
	private String documentURL;
	
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
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
	
	public Integer getUserId() {
		if(users != null) {
			return users.getId();
		} else {
			return null;
		}
	}

	public String getUserName() {
		if(users != null) {
			return users.getFirstName() + " " + users.getLastName();
		} else {
			return null;
		}
	}
	
}
