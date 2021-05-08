package com.devcrawlers.conference.management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.devcrawlers.conference.management.enums.CommonStatus;
import com.devcrawlers.conference.management.exception.NoRecordFoundException;
import com.devcrawlers.conference.management.model.User;
import com.devcrawlers.conference.management.repository.UserRepository;
import com.devcrawlers.conference.management.resource.UserAddResource;
import com.devcrawlers.conference.management.resource.UserUpdateResource;
import com.devcrawlers.conference.management.service.UserService;
import com.devcrawlers.conference.management.util.IdGenerator;
import com.devcrawlers.conference.management.util.MessageProperties;

@Component
@Transactional(rollbackFor=Exception.class)
public class UserServiceImpl extends MessageProperties implements UserService {

	@Autowired
	private UserRepository userRepository;

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
		
		return IdGenerator.generateUserIDs(userIdList);	
	}
	
	@Override
	public Integer saveUser(UserAddResource userAddResource) {
		User user = new User();
		user.setId(generateId());
		user.setName(userAddResource.getName());
		user.setStatus(CommonStatus.ACTIVE.toString());
		userRepository.save(user);
		return user.getId();
	}

	@Override
	public User updateUser(int id, UserUpdateResource userUpdateResource) {
		
		Optional<User> isPresentUser = userRepository.findById(id);
		if (!isPresentUser.isPresent()) {
			throw new NoRecordFoundException(RECORD_NOT_FOUND);
		}
		
		User user = isPresentUser.get();
		user.setName(userUpdateResource.getName());
		user.setStatus(userUpdateResource.getStatus());
		userRepository.save(user);
		return user;
	}
	
	@Override
	public String deleteUser(int id) {
		Optional<User> isPresentUser = userRepository.findById(id);
		if (!isPresentUser.isPresent()) {
			throw new NoRecordFoundException(RECORD_NOT_FOUND);
		}
		
		userRepository.deleteById(id);
		return COMMON_DELETED + id;
	}

}
