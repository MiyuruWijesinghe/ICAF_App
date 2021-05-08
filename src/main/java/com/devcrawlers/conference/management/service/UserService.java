package com.devcrawlers.conference.management.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.devcrawlers.conference.management.model.User;
import com.devcrawlers.conference.management.resource.UserAddResource;
import com.devcrawlers.conference.management.resource.UserUpdateResource;

@Service
public interface UserService {

	public List<User> findAll();
	
	public Optional<User> findById(int id);
	
	public Integer saveUser(UserAddResource userAddResource);
	
	public User updateUser(int id, UserUpdateResource userUpdateResource);
	
	public String deleteUser(int id);
	
}
