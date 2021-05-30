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
import com.devcrawlers.conference.management.model.Conference;
import com.devcrawlers.conference.management.model.Roles;
import com.devcrawlers.conference.management.model.User;
import com.devcrawlers.conference.management.repository.ConferenceRepository;
import com.devcrawlers.conference.management.repository.RolesRepository;
import com.devcrawlers.conference.management.repository.UserRepository;
import com.devcrawlers.conference.management.resource.UserAddResource;
import com.devcrawlers.conference.management.resource.UserUpdateResource;
import com.devcrawlers.conference.management.service.UserService;
import com.devcrawlers.conference.management.util.IdGenerator;

/**
 * User Service Implementation
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
public class UserServiceImpl implements UserService {

	@Autowired
	private Environment environment;
	
	//@Autowired
	//AuthenticationManager authenticationManager;
	
	//@Autowired
	//PasswordEncoder encoder;

	//@Autowired
	//JwtUtils jwtUtils;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RolesRepository rolesRepository;
	
	@Autowired
	private ConferenceRepository conferenceRepository;
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findById(int id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return Optional.ofNullable(user.get());
		} else {
			return Optional.empty();
		}
	}

	private int generateId() {
		List<User> userList = userRepository.findAll();
		List<Integer> userIdList = new ArrayList<>();
		
		for(User userObject : userList) {
			userIdList.add(userObject.getId());
		}
		
		return IdGenerator.generateIDs(userIdList);	
	}
	
	@Override
	public Integer saveUser(UserAddResource userAddResource) {
		User user = new User();
		
		Optional<User> isPresentUser = userRepository.findByUserName(userAddResource.getUserName());
        if (isPresentUser.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("user.duplicate"), "message");
		}
        
        Optional<Roles> roles = rolesRepository.findByIdAndStatus(Integer.parseInt(userAddResource.getRoleId()), CommonStatus.ACTIVE.toString());
		if (!roles.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("role.invalid-value"), "message");
		} else {
			user.setRoles(roles.get());
		}
		
		Optional<Conference> conference = conferenceRepository.findByIdAndStatus(Integer.parseInt(userAddResource.getConferenceId()), CommonStatus.ACTIVE.toString());
		if (!conference.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("conference.invalid-value"), "message");
		} else {
			user.setConferences(conference.get());
		}
		
		user.setId(generateId());
		user.setTitle(userAddResource.getTitle());
		user.setFirstName(userAddResource.getFirstName());
		user.setLastName(userAddResource.getLastName());
		user.setUserName(userAddResource.getUserName());
		user.setPassword(userAddResource.getPassword());
		user.setAddressLine1(userAddResource.getAddressLine1());
		user.setAddressLine2(userAddResource.getAddressLine2());
		user.setAddressLine3(userAddResource.getAddressLine3());
		user.setEmail(userAddResource.getEmail());
		user.setPhoneNumber(userAddResource.getPhoneNumber());
		user.setStatus(CommonStatus.ACTIVE.toString());
		userRepository.save(user);
		return user.getId();
	}

	@Override
	public User updateUser(int id, UserUpdateResource userUpdateResource) {
		
		Optional<User> isPresentUser = userRepository.findById(id);
		if (!isPresentUser.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		User user = isPresentUser.get();
		
		Optional<Roles> roles = rolesRepository.findByIdAndStatus(Integer.parseInt(userUpdateResource.getRoleId()), CommonStatus.ACTIVE.toString());
		if (!roles.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("role.invalid-value"), "message");
		} else {
			user.setRoles(roles.get());
		}
		
		if(userUpdateResource.getPassword() != null && !userUpdateResource.getPassword().isEmpty()) {
			user.setPassword(userUpdateResource.getPassword());
		}
		user.setAddressLine1(userUpdateResource.getAddressLine1());
		user.setAddressLine2(userUpdateResource.getAddressLine2());
		user.setAddressLine3(userUpdateResource.getAddressLine3());
		user.setEmail(userUpdateResource.getEmail());
		user.setPhoneNumber(userUpdateResource.getPhoneNumber());
		user.setStatus(userUpdateResource.getStatus());
		userRepository.save(user);
		return user;
	}
	
	@Override
	public String deleteUser(int id) {
		Optional<User> isPresentUser = userRepository.findById(id);
		if (!isPresentUser.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		userRepository.deleteById(id);
		return environment.getProperty("user.deleted");
	}

}
