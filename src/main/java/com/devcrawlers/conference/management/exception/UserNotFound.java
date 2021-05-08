package com.devcrawlers.conference.management.exception;

public class UserNotFound extends RuntimeException {
	
	private static final long serialVersionUID = 7452761686904563106L;

	public UserNotFound(String exception) {
		super(exception);
	}
}
