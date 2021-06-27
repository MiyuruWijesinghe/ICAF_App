package com.devcrawlers.conference.management.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devcrawlers.conference.management.model.Notifications;
import com.devcrawlers.conference.management.resource.SuccessAndErrorDetailsResource;
import com.devcrawlers.conference.management.service.NotificationsService;

@RestController
@RequestMapping(value = "/notifications")
@CrossOrigin(origins = "*")
public class NotificationsController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private NotificationsService notificationsService;
	
	
	/**
	 * Gets the notification by id.
	 *
	 * @param id - the id
	 * @return the notification by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getNotificationById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Notifications> isPresentNotification = notificationsService.findById(id);
		if (isPresentNotification.isPresent()) {
			return new ResponseEntity<>(isPresentNotification, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the notifications by user name.
	 *
	 * @param userName - the user name
	 * @return the notifications by user name
	 */
	@GetMapping(value = "/username/{userName}")
	public ResponseEntity<Object> getNotificationsByUserName(@PathVariable(value = "userName", required = true) String userName) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Notifications> notifications = notificationsService.findByUserName(userName);
		if (!notifications.isEmpty()) {
			return new ResponseEntity<>((Collection<Notifications>) notifications, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the notifications by user name and type.
	 *
	 * @param userName - the user name
	 * @param type - the type
	 * @return the notifications by user name and type
	 */
	@GetMapping(value = "/username/{userName}/type/{type}")
	public ResponseEntity<Object> getNotificationsByUserNameAndType(@PathVariable(value = "userName", required = true) String userName, 
			@PathVariable(value = "type", required = true) String type) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Notifications> notifications = notificationsService.findByUserNameAndType(userName, type);
		if (!notifications.isEmpty()) {
			return new ResponseEntity<>((Collection<Notifications>) notifications, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Delete notifications.
	 *
	 * @param id - the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteNotifications(@PathVariable(value = "id", required = true) int id) {
		String message = notificationsService.deleteNotifications(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
}
