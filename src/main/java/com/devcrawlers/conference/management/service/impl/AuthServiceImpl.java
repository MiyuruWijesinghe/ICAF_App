package com.devcrawlers.conference.management.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.devcrawlers.conference.management.enums.CommonStatus;
import com.devcrawlers.conference.management.exception.NoRecordFoundException;
import com.devcrawlers.conference.management.exception.ValidateRecordException;
import com.devcrawlers.conference.management.model.Conference;
import com.devcrawlers.conference.management.model.Research;
import com.devcrawlers.conference.management.model.Roles;
import com.devcrawlers.conference.management.model.User;
import com.devcrawlers.conference.management.model.Users;
import com.devcrawlers.conference.management.repository.ConferenceRepository;
import com.devcrawlers.conference.management.repository.RolesRepository;
import com.devcrawlers.conference.management.repository.UsersRepository;
import com.devcrawlers.conference.management.resource.JwtResponseResource;
import com.devcrawlers.conference.management.resource.LoginRequestResource;
import com.devcrawlers.conference.management.resource.SignupRequestResource;
import com.devcrawlers.conference.management.security.jwt.JwtUtils;
import com.devcrawlers.conference.management.service.AuthService;
import com.devcrawlers.conference.management.util.IdGenerator;


@Component
@Transactional(rollbackFor=Exception.class)
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsersRepository userRepository;

	@Autowired
	RolesRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private ConferenceRepository conferenceRepository;

	@Autowired
	JwtUtils jwtUtils;

	@Override
	public JwtResponseResource authenticateUser(LoginRequestResource loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		String role = roles.get(0);
		return new JwtResponseResource(jwt, 
				 userDetails.getId(), 
				 userDetails.getUsername(), 
				 userDetails.getEmail(), 
				 role);
	}

	@Override
	public Users registerUser(SignupRequestResource signUpRequest) {
		Set<Roles> roles = new HashSet<>();
		
		Users user = new Users();
		
		Optional<Users> isPresentUser = userRepository.findByUsername(signUpRequest.getUserName());
        if (isPresentUser.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("user.duplicate"), "message");
		}
        
        Optional<Roles> role = roleRepository.findByName(signUpRequest.getRoleName());
		if (!role.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("role.invalid-value"), "message");
		} else {
			roles.add(role.get());
		}
		
		Optional<Conference> conference = conferenceRepository.findByIdAndStatus(Integer.parseInt(signUpRequest.getConferenceId()), CommonStatus.ACTIVE.toString());
		if (!conference.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("conference.invalid-value"), "message");
		} else {
			user.setConferences(conference.get());
		}
		
		user.setId(generateId());
		user.setTitle(signUpRequest.getTitle());
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setUsername(signUpRequest.getUserName());
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setAddressLine1(signUpRequest.getAddressLine1());
		user.setAddressLine2(signUpRequest.getAddressLine2());
		user.setAddressLine3(signUpRequest.getAddressLine3());
		user.setEmail(signUpRequest.getEmail());
		user.setPhoneNumber(signUpRequest.getPhoneNumber());
		user.setStatus(CommonStatus.ACTIVE.toString());
		user.setRoles(roles);
		userRepository.save(user);
		return user;
		
	}
		
		
		
		
		
		
		
		
//		if (userRepository.existsByUsername(signUpRequest.getUsername()))
//			throw new ValidateRecordException(environment.getProperty("user.username-exists"), "message");
//
//		if (userRepository.existsByEmail(signUpRequest.getEmail()))
//			throw new ValidateRecordException(environment.getProperty("user.email-exists"), "message");
//
//		Users user = new Users(signUpRequest.getUsername(), 
//							 signUpRequest.getEmail(),
//							 encoder.encode(signUpRequest.getPassword()));
//
//		String strRoles = signUpRequest.getRoleName();
//		Set<Roles> roles = new HashSet<>();
//		
//		Roles userRole = roleRepository.findByName(signUpRequest.getRoleName())
//				.orElseThrow(() -> new NoRecordFoundException(environment.getProperty("role-not-found")));
//		roles.add(userRole);
//
////		if (strRoles == null) {
////			Roles userRole = roleRepository.findByName("ROLE_USER")
////					.orElseThrow(() -> new NoRecordFoundException(environment.getProperty("role-not-found")));
////			roles.add(userRole);
////		} else {
////			strRoles.forEach(role -> {
////				switch (role) {
////				case "admin":
////					Roles adminRole = roleRepository.findByName("ROLE_ADMIN")
////							.orElseThrow(() -> new NoRecordFoundException(environment.getProperty("role-not-found")));
////					roles.add(adminRole);
////
////					break;
////				default:
////					Roles userRole = roleRepository.findByName("ROLE_USER")
////							.orElseThrow(() -> new NoRecordFoundException(environment.getProperty("role-not-found")));
////					roles.add(userRole);
////				}
////			});
////		}
//		user.setId(generateId());
//		user.setRoles(roles);
//		return userRepository.save(user);
//	}
	
	private int generateId() {
		List<Users> userList = userRepository.findAll();
		List<Integer> userIdList = new ArrayList<>();
		
		for(Users userObject : userList) {
			userIdList.add(userObject.getId());
		}
		
		return IdGenerator.generateIDs(userIdList);	
	}
}
