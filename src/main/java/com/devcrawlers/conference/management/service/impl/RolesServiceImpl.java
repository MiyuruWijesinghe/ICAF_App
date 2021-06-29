package com.devcrawlers.conference.management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.devcrawlers.conference.management.enums.CommonStatus;
import com.devcrawlers.conference.management.exception.NoRecordFoundException;
import com.devcrawlers.conference.management.exception.ValidateRecordException;
import com.devcrawlers.conference.management.model.Roles;
import com.devcrawlers.conference.management.repository.RolesRepository;
import com.devcrawlers.conference.management.resource.RolesAddResource;
import com.devcrawlers.conference.management.resource.RolesUpdateResource;
import com.devcrawlers.conference.management.service.RolesService;
import com.devcrawlers.conference.management.util.IdGenerator;

/**
 * Roles Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Author    IT No.        Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-05-2021   MiyuruW   IT19020990     Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class RolesServiceImpl implements RolesService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	private int generateId() {
		List<Roles> rolesList = rolesRepository.findAll();
		List<Integer> rolesIdList = new ArrayList<>();
		
		for(Roles rolesObject : rolesList) {
			rolesIdList.add(rolesObject.getId());
		}
		
		return IdGenerator.generateIDs(rolesIdList);	
	}

	@Override
	public List<Roles> findAll() {
		return rolesRepository.findAll();
	}

	@Override
	public Optional<Roles> findById(int id) {
		Optional<Roles> roles = rolesRepository.findById(id);
		if (roles.isPresent()) {
			return Optional.ofNullable(roles.get());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<Roles> findByName(String name) {
		Optional<Roles> roles = rolesRepository.findByName(name);
		if (roles.isPresent()) {
			return Optional.ofNullable(roles.get());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public Integer saveRole(RolesAddResource rolesAddResource) {
		Roles roles = new Roles();
		
		Optional<Roles> isPresentRole = rolesRepository.findByName(rolesAddResource.getName());
        if (isPresentRole.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("role.duplicate"), "message");
		}
		
		roles.setId(generateId());
		roles.setName("ROLE_" + rolesAddResource.getName());
		roles.setStatus(CommonStatus.ACTIVE.toString());
		rolesRepository.save(roles);
		return roles.getId();
	}

	@Override
	public Roles updateRole(int id, RolesUpdateResource rolesUpdateResource) {
		
		Optional<Roles> isPresentRoles = rolesRepository.findById(id);
		if (!isPresentRoles.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Roles roles = isPresentRoles.get();
		roles.setStatus(rolesUpdateResource.getStatus());
		rolesRepository.save(roles);
		return roles;
	}
	
	@Override
	public String deleteRole(int id) {
		Optional<Roles> isPresentRoles = rolesRepository.findById(id);
		if (!isPresentRoles.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		rolesRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}

}