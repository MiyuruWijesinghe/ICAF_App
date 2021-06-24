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

import com.devcrawlers.conference.management.model.Research;
import com.devcrawlers.conference.management.resource.CommonApproveRejectResource;
import com.devcrawlers.conference.management.resource.ResearchAddResource;
import com.devcrawlers.conference.management.resource.ResearchUpdateResource;
import com.devcrawlers.conference.management.resource.SuccessAndErrorDetailsResource;
import com.devcrawlers.conference.management.service.ResearchService;

@RestController
@RequestMapping(value = "/research")
@CrossOrigin(origins = "*")
public class ResearchController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ResearchService researchService;
	
	
	/**
	 * Gets the all research.
	 *
	 * @return the all research
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllResearch() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Research> research = researchService.findAll();
		if (!research.isEmpty()) {
			return new ResponseEntity<>((Collection<Research>) research, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the research by id.
	 *
	 * @param id - the id
	 * @return the research by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getResearchById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Research> isPresentResearch = researchService.findById(id);
		if (isPresentResearch.isPresent()) {
			return new ResponseEntity<>(isPresentResearch, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the research by status.
	 *
	 * @param status - the status
	 * @return the research by status
	 */
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> getResearchByStatus(@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Research> research = researchService.findByStatus(status);
		if (!research.isEmpty()) {
			return new ResponseEntity<>((Collection<Research>) research, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the research by name.
	 *
	 * @param name - the name
	 * @return the research by name
	 */
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Object> getResearchByName(@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Research> research = researchService.findByName(name);
		if (!research.isEmpty()) {
			return new ResponseEntity<>((Collection<Research>) research, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the research by conference details id.
	 *
	 * @param conferenceDetailsId - the conference details id
	 * @return the research by conference details id
	 */
	@GetMapping(value = "/conference-details/{conferenceDetailsId}")
	public ResponseEntity<Object> getResearchByConferenceDetailsId(@PathVariable(value = "conferenceDetailsId", required = true) int conferenceDetailsId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Research> research = researchService.findByConferenceDetailsId(conferenceDetailsId);
		if (!research.isEmpty()) {
			return new ResponseEntity<>((Collection<Research>) research, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the research by conference details topic.
	 *
	 * @param topic - the topic
	 * @return the research by conference details topic
	 */
	@GetMapping(value = "/conference-details/topic/{topic}")
	public ResponseEntity<Object> getResearchByConferenceDetailsTopic(@PathVariable(value = "topic", required = true) String topic) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Research> research = researchService.findByConferenceDetailsTopic(topic);
		if (!research.isEmpty()) {
			return new ResponseEntity<>((Collection<Research>) research, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the research by created user.
	 *
	 * @param createdUser - the created user
	 * @return the research by created user
	 */
	@GetMapping(value = "/created-user/{createdUser}")
	public ResponseEntity<Object> getResearchByCreatedUser(@PathVariable(value = "createdUser", required = true) String createdUser) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Research> research = researchService.findByCreatedUser(createdUser);
		if (!research.isEmpty()) {
			return new ResponseEntity<>((Collection<Research>) research, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the research.
	 *
	 * @param researchAddResource - the research add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addResearch(@Valid @RequestBody ResearchAddResource researchAddResource) {
		Integer researchId = researchService.saveResearch(researchAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), researchId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Update research.
	 *
	 * @param id - the id
	 * @param researchUpdateResource - the research update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateResearch(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody ResearchUpdateResource researchUpdateResource) {
		Research research = researchService.updateResearch(id, researchUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), research);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Delete research.
	 *
	 * @param id - the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteResearch(@PathVariable(value = "id", required = true) int id) {
		String message = researchService.deleteResearch(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Approve research.
	 *
	 * @param id - the id
	 * @param commonApproveRejectResource - the common approve reject resource
	 * @return the response entity
	 */
	@PutMapping(value = "/approve/{id}")
	public ResponseEntity<Object> approveResearch(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody CommonApproveRejectResource commonApproveRejectResource) {
		String message = researchService.approveResearch(id, commonApproveRejectResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Reject research.
	 *
	 * @param id - the id
	 * @param commonApproveRejectResource - the common approve reject resource
	 * @return the response entity
	 */
	@PutMapping(value = "/reject/{id}")
	public ResponseEntity<Object> rejectResearch(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody CommonApproveRejectResource commonApproveRejectResource) {
		String message = researchService.rejectResearch(id, commonApproveRejectResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
}
