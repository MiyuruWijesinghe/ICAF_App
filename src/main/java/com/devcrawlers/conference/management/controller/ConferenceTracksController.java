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
import com.devcrawlers.conference.management.model.ConferenceTracks;
import com.devcrawlers.conference.management.resource.CommonApproveRejectResource;
import com.devcrawlers.conference.management.resource.ConferenceTracksAddResource;
import com.devcrawlers.conference.management.resource.ConferenceTracksUpdateResource;
import com.devcrawlers.conference.management.resource.SuccessAndErrorDetailsResource;
import com.devcrawlers.conference.management.service.ConferenceTracksService;

@RestController
@RequestMapping(value = "/tracks")
@CrossOrigin(origins = "*")
public class ConferenceTracksController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ConferenceTracksService conferenceTracksService;

	
	/**
	 * Gets the all conference tracks.
	 *
	 * @return the all conference tracks
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllConferenceTracks() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ConferenceTracks> conferenceTracks = conferenceTracksService.findAll();
		if (!conferenceTracks.isEmpty()) {
			return new ResponseEntity<>((Collection<ConferenceTracks>) conferenceTracks, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the conference track by id.
	 *
	 * @param id - the id
	 * @return the conference track by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getConferenceTrackById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<ConferenceTracks> isPresentConferenceTrack = conferenceTracksService.findById(id);
		if (isPresentConferenceTrack.isPresent()) {
			return new ResponseEntity<>(isPresentConferenceTrack, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the conference tracks by status.
	 *
	 * @param status - the status
	 * @return the conference tracks by status
	 */
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> getConferenceTracksByStatus(@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ConferenceTracks> conferenceTracks = conferenceTracksService.findByStatus(status);
		if (!conferenceTracks.isEmpty()) {
			return new ResponseEntity<>((Collection<ConferenceTracks>) conferenceTracks, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
		
	
	/**
	 * Gets the conference tracks by name.
	 *
	 * @param name - the name
	 * @return the conference tracks by name
	 */
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Object> getConferenceTracksByName(@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ConferenceTracks> conferenceTracks = conferenceTracksService.findByName(name);
		if (!conferenceTracks.isEmpty()) {
			return new ResponseEntity<>((Collection<ConferenceTracks>) conferenceTracks, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the conference track.
	 *
	 * @param conferenceTracksAddResource - the conference tracks add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addConferenceTrack(@Valid @RequestBody ConferenceTracksAddResource conferenceTracksAddResource) {
		Integer conferenceTracksId = conferenceTracksService.saveConferenceTrack(conferenceTracksAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), conferenceTracksId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Update conference track.
	 *
	 * @param id - the id
	 * @param conferenceTracksUpdateResource - the conference tracks update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateConferenceTrack(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody ConferenceTracksUpdateResource conferenceTracksUpdateResource) {
		ConferenceTracks conferenceTracks = conferenceTracksService.updateConferenceTrack(id, conferenceTracksUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), conferenceTracks);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Delete conference track.
	 *
	 * @param id - the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteConferenceTrack(@PathVariable(value = "id", required = true) int id) {
		String message = conferenceTracksService.deleteConferenceTrack(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Approve conference track.
	 *
	 * @param id the id
	 * @param commonApproveRejectResource the common approve reject resource
	 * @return the response entity
	 */
	@PutMapping(value = "/approve/{id}")
	public ResponseEntity<Object> approveConferenceTrack(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody CommonApproveRejectResource commonApproveRejectResource) {
		String message = conferenceTracksService.approveConferenceTrack(id, commonApproveRejectResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Reject conference track.
	 *
	 * @param id the id
	 * @param commonApproveRejectResource the common approve reject resource
	 * @return the response entity
	 */
	@PutMapping(value = "/reject/{id}")
	public ResponseEntity<Object> rejectConferenceTrack(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody CommonApproveRejectResource commonApproveRejectResource) {
		String message = conferenceTracksService.rejectConferenceTrack(id, commonApproveRejectResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
}
