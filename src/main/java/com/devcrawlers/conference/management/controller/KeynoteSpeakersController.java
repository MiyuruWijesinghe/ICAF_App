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
import com.devcrawlers.conference.management.model.KeynoteSpeakers;
import com.devcrawlers.conference.management.resource.KeynoteSpeakersAddResource;
import com.devcrawlers.conference.management.resource.KeynoteSpeakersUpdateResource;
import com.devcrawlers.conference.management.resource.SuccessAndErrorDetailsResource;
import com.devcrawlers.conference.management.service.KeynoteSpeakersService;

@RestController
@RequestMapping(value = "/keynote-speakers")
@CrossOrigin(origins = "*")
public class KeynoteSpeakersController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private KeynoteSpeakersService keynoteSpeakersService;

	
	/**
	 * Gets the all keynote speakers.
	 *
	 * @return the all keynote speakers
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllKeynoteSpeakers() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<KeynoteSpeakers> keynoteSpeakers = keynoteSpeakersService.findAll();
		if (!keynoteSpeakers.isEmpty()) {
			return new ResponseEntity<>((Collection<KeynoteSpeakers>) keynoteSpeakers, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the keynote speaker by id.
	 *
	 * @param id - the id
	 * @return the keynote speaker by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getKeynoteSpeakerById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<KeynoteSpeakers> isPresentKeynoteSpeaker = keynoteSpeakersService.findById(id);
		if (isPresentKeynoteSpeaker.isPresent()) {
			return new ResponseEntity<>(isPresentKeynoteSpeaker, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the keynote speakers by status.
	 *
	 * @param status - the status
	 * @return the keynote speakers by status
	 */
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> getKeynoteSpeakersByStatus(@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<KeynoteSpeakers> keynoteSpeakers = keynoteSpeakersService.findByStatus(status);
		if (!keynoteSpeakers.isEmpty()) {
			return new ResponseEntity<>((Collection<KeynoteSpeakers>) keynoteSpeakers, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
		
	
	/**
	 * Gets the keynote speakers by name.
	 *
	 * @param name - the name
	 * @return the keynote speakers by name
	 */
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Object> getKeynoteSpeakersByName(@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<KeynoteSpeakers> keynoteSpeakers = keynoteSpeakersService.findByName(name);
		if (!keynoteSpeakers.isEmpty()) {
			return new ResponseEntity<>((Collection<KeynoteSpeakers>) keynoteSpeakers, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the keynote speaker.
	 *
	 * @param keynoteSpeakersAddResource - the keynote speakers add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addKeynoteSpeaker(@Valid @RequestBody KeynoteSpeakersAddResource keynoteSpeakersAddResource) {
		Integer keynoteSpeakersId = keynoteSpeakersService.saveKeynoteSpeaker(keynoteSpeakersAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), keynoteSpeakersId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Update keynote speaker.
	 *
	 * @param id - the id
	 * @param keynoteSpeakersUpdateResource - the keynote speakers update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateKeynoteSpeaker(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody KeynoteSpeakersUpdateResource keynoteSpeakersUpdateResource) {
		KeynoteSpeakers keynoteSpeakers = keynoteSpeakersService.updateKeynoteSpeaker(id, keynoteSpeakersUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), keynoteSpeakers);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Delete keynote speaker.
	 *
	 * @param id - the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteKeynoteSpeaker(@PathVariable(value = "id", required = true) int id) {
		String message = keynoteSpeakersService.deleteKeynoteSpeaker(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
}
