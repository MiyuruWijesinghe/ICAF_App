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

import com.devcrawlers.conference.management.model.Workshops;
import com.devcrawlers.conference.management.resource.CommonApproveRejectResource;
import com.devcrawlers.conference.management.resource.SuccessAndErrorDetailsResource;
import com.devcrawlers.conference.management.resource.WorkshopsAddResource;
import com.devcrawlers.conference.management.resource.WorkshopsUpdateResource;
import com.devcrawlers.conference.management.service.WorkshopsService;

@RestController
@RequestMapping(value = "/workshops")
@CrossOrigin(origins = "*")
public class WorkshopsController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private WorkshopsService workshopsService;
	
	
	/**
	 * Gets the all workshops.
	 *
	 * @return the all workshops
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllWorkshops() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Workshops> workshops = workshopsService.findAll();
		if (!workshops.isEmpty()) {
			return new ResponseEntity<>((Collection<Workshops>) workshops, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the workshop by id.
	 *
	 * @param id - the id
	 * @return the workshop by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getWorkshopById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Workshops> isPresentWorkshop = workshopsService.findById(id);
		if (isPresentWorkshop.isPresent()) {
			return new ResponseEntity<>(isPresentWorkshop, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the workshops by status.
	 *
	 * @param status - the status
	 * @return the workshops by status
	 */
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> getWorkshopsByStatus(@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Workshops> workshops = workshopsService.findByStatus(status);
		if (!workshops.isEmpty()) {
			return new ResponseEntity<>((Collection<Workshops>) workshops, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the workshops by name.
	 *
	 * @param name - the name
	 * @return the workshops by name
	 */
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Object> getWorkshopsByName(@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Workshops> workshops = workshopsService.findByName(name);
		if (!workshops.isEmpty()) {
			return new ResponseEntity<>((Collection<Workshops>) workshops, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the workshops by conference tracks id.
	 *
	 * @param conferenceTracksId - the conference tracks id
	 * @param status - the status
	 * @return the workshops by conference tracks id
	 */
	@GetMapping(value = "/conference-tracks/{conferenceTracksId}/status/{status}")
	public ResponseEntity<Object> getWorkshopsByConferenceTracksId(@PathVariable(value = "conferenceTracksId", required = true) int conferenceTracksId, 
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Workshops> workshops = workshopsService.findByConferenceTracksIdAndStatus(conferenceTracksId, status);
		if (!workshops.isEmpty()) {
			return new ResponseEntity<>((Collection<Workshops>) workshops, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the workshops by conference tracks name.
	 *
	 * @param name - the name
	 * @return the workshops by conference tracks name
	 */
	@GetMapping(value = "/conference-tracks/name/{name}")
	public ResponseEntity<Object> getWorkshopsByConferenceTracksName(@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Workshops> workshops = workshopsService.findByConferenceTracksName(name);
		if (!workshops.isEmpty()) {
			return new ResponseEntity<>((Collection<Workshops>) workshops, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the workshops by created user.
	 *
	 * @param createdUser - the created user
	 * @return the workshops by created user
	 */
	@GetMapping(value = "/created-user/{createdUser}")
	public ResponseEntity<Object> getWorkshopsByCreatedUser(@PathVariable(value = "createdUser", required = true) String createdUser) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Workshops> workshops = workshopsService.findByCreatedUser(createdUser);
		if (!workshops.isEmpty()) {
			return new ResponseEntity<>((Collection<Workshops>) workshops, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the workshop.
	 *
	 * @param workshopsAddResource - the workshops add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addWorkshop(@Valid @RequestBody WorkshopsAddResource workshopsAddResource) {
		Integer workshopsId = workshopsService.saveWorkshop(workshopsAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), workshopsId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Update workshop.
	 *
	 * @param id - the id
	 * @param workshopsUpdateResource - the workshops update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateWorkshop(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody WorkshopsUpdateResource workshopsUpdateResource) {
		Workshops workshops = workshopsService.updateWorkshop(id, workshopsUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), workshops);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Delete workshop.
	 *
	 * @param id - the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteWorkshop(@PathVariable(value = "id", required = true) int id) {
		String message = workshopsService.deleteWorkshop(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Approve workshop.
	 *
	 * @param id - the id
	 * @param commonApproveRejectResource - the common approve reject resource
	 * @return the response entity
	 */
	@PutMapping(value = "/approve/{id}")
	public ResponseEntity<Object> approveWorkshop(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody CommonApproveRejectResource commonApproveRejectResource) {
		String message = workshopsService.approveWorkshop(id, commonApproveRejectResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Reject workshop.
	 *
	 * @param id - the id
	 * @param commonApproveRejectResource - the common approve reject resource
	 * @return the response entity
	 */
	@PutMapping(value = "/reject/{id}")
	public ResponseEntity<Object> rejectWorkshop(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody CommonApproveRejectResource commonApproveRejectResource) {
		String message = workshopsService.rejectWorkshop(id, commonApproveRejectResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
}
