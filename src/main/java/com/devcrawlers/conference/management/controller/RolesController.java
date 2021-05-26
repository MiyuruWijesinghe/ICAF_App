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
import com.devcrawlers.conference.management.model.Roles;
import com.devcrawlers.conference.management.resource.RolesAddResource;
import com.devcrawlers.conference.management.resource.RolesUpdateResource;
import com.devcrawlers.conference.management.resource.SuccessAndErrorDetailsResource;
import com.devcrawlers.conference.management.service.RolesService;


@RestController
@RequestMapping(value = "/roles")
@CrossOrigin(origins = "*")
public class RolesController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private RolesService rolesService;

	
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllRoles() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Roles> roles = rolesService.findAll();
		if (!roles.isEmpty()) {
			return new ResponseEntity<>((Collection<Roles>) roles, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getRoleById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Roles> isPresentRoles = rolesService.findById(id);
		if (isPresentRoles.isPresent()) {
			return new ResponseEntity<>(isPresentRoles, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addRole(@Valid @RequestBody RolesAddResource rolesAddResource) {
		Integer rolesId = rolesService.saveRole(rolesAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), rolesId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateRole(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody RolesUpdateResource rolesUpdateResource) {
		Roles roles = rolesService.updateRole(id, rolesUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), roles);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteRole(@PathVariable(value = "id", required = true) int id) {
		String message = rolesService.deleteRole(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
}