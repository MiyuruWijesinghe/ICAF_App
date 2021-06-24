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

import com.devcrawlers.conference.management.model.User;
import com.devcrawlers.conference.management.resource.SuccessAndErrorDetailsResource;
import com.devcrawlers.conference.management.resource.UserAddResource;
import com.devcrawlers.conference.management.resource.UserUpdateResource;
import com.devcrawlers.conference.management.service.UserService;

/**
 * User Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Author    IT No.        Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-05-2021   MiyuruW   IT19020990     Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private UserService userService;

	
	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllUsers() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<User> user = userService.findAll();
		if (!user.isEmpty()) {
			return new ResponseEntity<>((Collection<User>) user, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the user by id.
	 *
	 * @param id - the id
	 * @return the user by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<User> isPresentUser = userService.findById(id);
		if (isPresentUser.isPresent()) {
			return new ResponseEntity<>(isPresentUser, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the user by user name.
	 *
	 * @param userName - the user name
	 * @return the user by user name
	 */
	@GetMapping(value = "/username/{userName}")
	public ResponseEntity<Object> getUserByUserName(@PathVariable(value = "userName", required = true) String userName) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<User> isPresentUser = userService.findByUserName(userName);
		if (isPresentUser.isPresent()) {
			return new ResponseEntity<>(isPresentUser, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Register user.
	 *
	 * @param userAddResource - the user add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/register")
	public ResponseEntity<Object> registerUser(@Valid @RequestBody UserAddResource userAddResource) {
		Integer userId = userService.saveUser(userAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("user.registered"), userId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Update user.
	 *
	 * @param id - the id
	 * @param userUpdateResource - the user update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody UserUpdateResource userUpdateResource) {
		User user = userService.updateUser(id, userUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), user);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Delete user.
	 *
	 * @param id - the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable(value = "id", required = true) int id) {
		String message = userService.deleteUser(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
}
