package com.devcrawlers.conference.management.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ConferenceDetailsAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String conferenceId;
	
	@NotBlank(message = "{common.not-null}")
	private String topic;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 255, message = "{description.size}")
	private String description;
	
	@NotBlank(message = "{common.not-null}")
	private String conductor;
	
	@NotBlank(message = "{common.not-null}")
	private String venue;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|(\\d{4})-(\\d{2})-(\\d{2})$", message = "{common.invalid-date-pattern}")
	private String date;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|(\\d{2}):(\\d{2}):(\\d{2})$", message = "{common.invalid-time-pattern}")
	private String startTime;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|(\\d{2}):(\\d{2}):(\\d{2})$", message = "{common.invalid-time-pattern}")
	private String endTime;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String payment;

	public String getConferenceId() {
		return conferenceId;
	}

	public void setConferenceId(String conferenceId) {
		this.conferenceId = conferenceId;
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

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
	
}
