package com.devcrawlers.conference.management.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ConferenceAddResource {

	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-year.size}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String year;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	@Pattern(regexp = "^$|^[a-zA-Z]+", message = "{common-name.pattern}")
	private String name;
	
	private String description;
	
	@NotBlank(message = "{common.not-null}")
	private String venue;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}
	
}
