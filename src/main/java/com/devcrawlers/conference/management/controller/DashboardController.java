package com.devcrawlers.conference.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devcrawlers.conference.management.resource.AdminDashboardResponse;
import com.devcrawlers.conference.management.resource.ResearcherDashboardResponse;
import com.devcrawlers.conference.management.resource.ReviewerDashboardResponse;
import com.devcrawlers.conference.management.resource.SuccessAndErrorDetailsResource;
import com.devcrawlers.conference.management.resource.WorkshopConductorDashboardResponse;
import com.devcrawlers.conference.management.service.DashboardService;


@RestController
@RequestMapping(value = "/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private DashboardService dashboardService;
	
	
	/**
	 * Gets the admin dashboard details.
	 *
	 * @return the admin dashboard details
	 */
	@GetMapping("/admin")
	public ResponseEntity<Object> getAdminDashboardDetails() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		AdminDashboardResponse adminDashboardResponse = dashboardService.getAdminDashboardDetails();
		if (adminDashboardResponse != null) {
			return new ResponseEntity<>(adminDashboardResponse, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the reviewer dashboard details.
	 *
	 * @return the reviewer dashboard details
	 */
	@GetMapping("/reviewer")
	public ResponseEntity<Object> getReviewerDashboardDetails() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		ReviewerDashboardResponse reviewerDashboardResponse = dashboardService.getReviewerDashboardDetails();
		if (reviewerDashboardResponse != null) {
			return new ResponseEntity<>(reviewerDashboardResponse, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the researcher dashboard details.
	 *
	 * @param username - the username
	 * @return the researcher dashboard details
	 */
	@GetMapping("/researcher/{username}")
	public ResponseEntity<Object> getResearcherDashboardDetails(@PathVariable(value = "username", required = true) String username) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		ResearcherDashboardResponse researcherDashboardResponse = dashboardService.getResearcherDashboardDetails(username);
		if (researcherDashboardResponse != null) {
			return new ResponseEntity<>(researcherDashboardResponse, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the workshop conductor dashboard details.
	 *
	 * @param username - the username
	 * @return the workshop conductor dashboard details
	 */
	@GetMapping("/workshop-conductor/{username}")
	public ResponseEntity<Object> getWorkshopConductorDashboardDetails(@PathVariable(value = "username", required = true) String username) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		WorkshopConductorDashboardResponse workshopConductorDashboardResponse = dashboardService.getWorkshopConductorDashboardDetails(username);
		if (workshopConductorDashboardResponse != null) {
			return new ResponseEntity<>(workshopConductorDashboardResponse, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
}
