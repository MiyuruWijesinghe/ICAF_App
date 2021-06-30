package com.devcrawlers.conference.management.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WorkshopsAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String conferenceTracksId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;
	
	private String description;
	
	@NotBlank(message = "{common.not-null}")
	private String documentURL;
	
	@NotBlank(message = "{common.not-null}")
	private String conductor;
	
	@NotBlank(message = "{common.not-null}")
	private String imageURL;
	
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
	
	public String getConferenceTracksId() {
		return conferenceTracksId;
	}

	public void setConferenceTracksId(String conferenceTracksId) {
		this.conferenceTracksId = conferenceTracksId;
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
	
}
