package com.devcrawlers.conference.management.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devcrawlers.conference.management.model.ConferenceDetails;
import com.devcrawlers.conference.management.resource.ConferenceDetailsAddResource;
import com.devcrawlers.conference.management.resource.ConferenceDetailsApproveRejectResource;
import com.devcrawlers.conference.management.resource.ConferenceDetailsUpdateResource;
import com.devcrawlers.conference.management.resource.SuccessAndErrorDetailsResource;
import com.devcrawlers.conference.management.service.ConferenceDetailsService;

@RestController
@RequestMapping(value = "/conference-details")
@CrossOrigin(origins = "*")
public class ConferenceDetailsController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ConferenceDetailsService conferenceDetailsService;
	
	
	/**
	 * Gets the all conference details.
	 *
	 * @return the all conference details
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllConferenceDetails() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ConferenceDetails> conferenceDetails = conferenceDetailsService.findAll();
		if (!conferenceDetails.isEmpty()) {
			return new ResponseEntity<>((Collection<ConferenceDetails>) conferenceDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the conference details by id.
	 *
	 * @param id - the id
	 * @return the conference details by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getConferenceDetailsById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<ConferenceDetails> isPresentConferenceDetails = conferenceDetailsService.findById(id);
		if (isPresentConferenceDetails.isPresent()) {
			return new ResponseEntity<>(isPresentConferenceDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the conference details by status.
	 *
	 * @param status - the status
	 * @return the conference details by status
	 */
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> getConferenceDetailsByStatus(@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ConferenceDetails> conferenceDetails = conferenceDetailsService.findByStatus(status);
		if (!conferenceDetails.isEmpty()) {
			return new ResponseEntity<>((Collection<ConferenceDetails>) conferenceDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the conference details by conference id.
	 *
	 * @param conferenceId - the conference id
	 * @return the conference details by conference id
	 */
	@GetMapping(value = "/conference/{conferenceId}")
	public ResponseEntity<Object> getConferenceDetailsByConferenceId(@PathVariable(value = "conferenceId", required = true) int conferenceId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ConferenceDetails> conferenceDetails = conferenceDetailsService.findByConferenceId(conferenceId);
		if (!conferenceDetails.isEmpty()) {
			return new ResponseEntity<>((Collection<ConferenceDetails>) conferenceDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the conference details by conference name.
	 *
	 * @param conferenceName - the conference name
	 * @return the conference details by conference name
	 */
	@GetMapping(value = "/conference/name/{conferenceName}")
	public ResponseEntity<Object> getConferenceDetailsByConferenceName(@PathVariable(value = "conferenceName", required = true) String conferenceName) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ConferenceDetails> conferenceDetails = conferenceDetailsService.findByConferenceName(conferenceName);
		if (!conferenceDetails.isEmpty()) {
			return new ResponseEntity<>((Collection<ConferenceDetails>) conferenceDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the conference details by conference year.
	 *
	 * @param conferenceYear - the conference year
	 * @return the conference details by conference year
	 */
	@GetMapping(value = "/conference/year/{conferenceYear}")
	public ResponseEntity<Object> getConferenceDetailsByConferenceYear(@PathVariable(value = "conferenceYear", required = true) String conferenceYear) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ConferenceDetails> conferenceDetails = conferenceDetailsService.findByConferenceYear(conferenceYear);
		if (!conferenceDetails.isEmpty()) {
			return new ResponseEntity<>((Collection<ConferenceDetails>) conferenceDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the conference details.
	 *
	 * @param conferenceDetailsAddResource - the conference details add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addConferenceDetails(@Valid @RequestBody ConferenceDetailsAddResource conferenceDetailsAddResource) {
		Integer conferenceDetailsId = conferenceDetailsService.saveConferenceDetails(conferenceDetailsAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), conferenceDetailsId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Update conference details.
	 *
	 * @param id - the id
	 * @param conferenceDetailsUpdateResource - the conference details update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateConferenceDetails(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody ConferenceDetailsUpdateResource conferenceDetailsUpdateResource) {
		ConferenceDetails conferenceDetails = conferenceDetailsService.updateConferenceDetails(id, conferenceDetailsUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), conferenceDetails);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Delete conference details.
	 *
	 * @param id - the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteConferenceDetails(@PathVariable(value = "id", required = true) int id) {
		String message = conferenceDetailsService.deleteConferenceDetails(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Approve conference details.
	 *
	 * @param id - the id
	 * @param conferenceDetailsApproveRejectResource - the conference details approve reject resource
	 * @return the response entity
	 */
	@PutMapping(value = "/approve/{id}")
	public ResponseEntity<Object> approveConferenceDetails(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody ConferenceDetailsApproveRejectResource conferenceDetailsApproveRejectResource) {
		String message = conferenceDetailsService.approveConferenceDetails(id, conferenceDetailsApproveRejectResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Reject conference details.
	 *
	 * @param id - the id
	 * @param conferenceDetailsApproveRejectResource - the conference details approve reject resource
	 * @return the response entity
	 */
	@PutMapping(value = "/reject/{id}")
	public ResponseEntity<Object> rejectConferenceDetails(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody ConferenceDetailsApproveRejectResource conferenceDetailsApproveRejectResource) {
		String message = conferenceDetailsService.rejectConferenceDetails(id, conferenceDetailsApproveRejectResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
}
