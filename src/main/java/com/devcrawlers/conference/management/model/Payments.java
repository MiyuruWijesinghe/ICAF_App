package com.devcrawlers.conference.management.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "Payments")
public class Payments {

	@Id
	private Integer id;
	
	@JsonIgnore
	private User users;
	
	@Transient
    private Integer userId;
	
	@Transient
    private String userName;
	
	private String type;
	
	private String description;
	
	private Date transactionDate;
	
	private BigDecimal amount;
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
