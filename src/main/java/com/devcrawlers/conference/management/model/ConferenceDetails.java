package com.devcrawlers.conference.management.model;

import java.math.BigDecimal;
import java.util.Date;
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
	
	private String topic;
	
	private String description;
	
	private String conductor;
	
	private String venue;
	
	private Date date;
	
	private BigDecimal payment;
	
	private String status;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
}
