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

import com.devcrawlers.conference.management.model.Conference;
import com.devcrawlers.conference.management.resource.ConferenceAddResource;
import com.devcrawlers.conference.management.resource.ConferenceUpdateResource;
import com.devcrawlers.conference.management.resource.SuccessAndErrorDetailsResource;
import com.devcrawlers.conference.management.service.ConferenceService;

/**
 * Conference Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Author    IT No.        Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-05-2021   MiyuruW   IT19020990     Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/conference")
@CrossOrigin(origins = "*")
public class ConferenceController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ConferenceService conferenceService;

	
	/**
	 * Gets the all conferences.
	 *
	 * @return the all conferences
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllConferences() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Conference> conference = conferenceService.findAll();
		if (!conference.isEmpty()) {
			return new ResponseEntity<>((Collection<Conference>) conference, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the conference by id.
	 *
	 * @param id - the id
	 * @return the conference by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getConferenceById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Conference> isPresentConference = conferenceService.findById(id);
		if (isPresentConference.isPresent()) {
			return new ResponseEntity<>(isPresentConference, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the conferences by status.
	 *
	 * @param status - the status
	 * @return the conferences by status
	 */
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> getConferencesByStatus(@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Conference> conference = conferenceService.findByStatus(status);
		if (!conference.isEmpty()) {
			return new ResponseEntity<>((Collection<Conference>) conference, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
		
	
	/**
	 * Gets the conferences by name.
	 *
	 * @param name - the name
	 * @return the conferences by name
	 */
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Object> getConferencesByName(@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Conference> conference = conferenceService.findByName(name);
		if (!conference.isEmpty()) {
			return new ResponseEntity<>((Collection<Conference>) conference, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the conference.
	 *
	 * @param conferenceAddResource - the conference add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addConference(@Valid @RequestBody ConferenceAddResource conferenceAddResource) {
		Integer conferenceId = conferenceService.saveConference(conferenceAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), conferenceId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Update conference.
	 *
	 * @param id - the id
	 * @param conferenceUpdateResource - the conference update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateConference(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody ConferenceUpdateResource conferenceUpdateResource) {
		Conference conference = conferenceService.updateConference(id, conferenceUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), conference);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Delete conference.
	 *
	 * @param id - the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteConference(@PathVariable(value = "id", required = true) int id) {
		String message = conferenceService.deleteConference(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
}
