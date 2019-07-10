package com.hcl.ecommerce.exception;

public class UserAlreadyExists extends RuntimeException {

	public UserAlreadyExists(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
