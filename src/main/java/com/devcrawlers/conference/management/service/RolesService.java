package com.devcrawlers.conference.management.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.devcrawlers.conference.management.model.Roles;
import com.devcrawlers.conference.management.resource.RolesAddResource;
import com.devcrawlers.conference.management.resource.RolesUpdateResource;

@Service
public interface RolesService {

	public List<Roles> findAll();
	
	public Optional<Roles> findById(int id);
	
	public Integer saveRole(RolesAddResource rolesAddResource);
	
	public Roles updateRole(int id, RolesUpdateResource rolesUpdateResource);
	
	public String deleteRole(int id);

}
