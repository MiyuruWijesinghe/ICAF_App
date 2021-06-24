package com.devcrawlers.conference.management.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllNotifications() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Notifications> notifications = notificationsService.findAll();
		if (!notifications.isEmpty()) {
			return new ResponseEntity<>((Collection<Notifications>) notifications, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
}
