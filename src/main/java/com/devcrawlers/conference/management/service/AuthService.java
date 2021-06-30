package com.devcrawlers.conference.management.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.devcrawlers.conference.management.model.Users;
import com.devcrawlers.conference.management.resource.JwtResponseResource;
import com.devcrawlers.conference.management.resource.LoginRequestResource;
import com.devcrawlers.conference.management.resource.SignupRequestResource;


@Service
public interface AuthService {

	public JwtResponseResource authenticateUser(LoginRequestResource loginRequest);

	public Users registerUser(SignupRequestResource signUpRequest);

}
