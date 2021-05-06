package com.devcrawlers.conference.management.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.devcrawlers.conference.management.model.User;
import com.devcrawlers.conference.management.repository.UserRepository;
import com.devcrawlers.conference.management.resource.UserAddResource;
import com.devcrawlers.conference.management.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findById(int id) {
		return userRepository.findById(id);
	}

	@Override
	public Integer saveUser(UserAddResource userAddResource) {
		User user = new User();
		user.setId(userAddResource.getId());
		user.setName(userAddResource.getName());
		user.setStatus(userAddResource.getStatus());
		userRepository.save(user);
		return user.getId();
	}

	@Override
	public String deleteUser(int id) {
		userRepository.deleteById(id);
		return "User deleted with id : " + id;
	}

}
