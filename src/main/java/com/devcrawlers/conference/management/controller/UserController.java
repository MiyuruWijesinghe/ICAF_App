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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.devcrawlers.conference.management.model.User;
import com.devcrawlers.conference.management.resource.SuccessAndErrorDetailsResource;
import com.devcrawlers.conference.management.resource.UserAddResource;
import com.devcrawlers.conference.management.service.UserService;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private UserService userService;

	
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllUsers() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<User> user = userService.findAll();
		if (!user.isEmpty()) {
			return new ResponseEntity<>((Collection<User>) user, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("Records not found."));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<User> isPresentUser = userService.findById(id);
		if (isPresentUser.isPresent()) {
			return new ResponseEntity<>(isPresentUser, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("Record not found."));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addUser(@RequestBody UserAddResource userAddResource) {
		Integer userId = userService.saveUser(userAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource("Successfully Created.", userId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable(value = "id", required = true) int id) {
		String message = userService.deleteUser(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
}
