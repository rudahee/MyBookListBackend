package com.book.exception;

import com.book.security.error.BodyErrorCode;

@SuppressWarnings("serial")
public class UserManagementException extends Exception {
	private final BodyErrorCode code;

	public UserManagementException(BodyErrorCode code) {
		super();
		this.code = code;
	}
	
	public BodyErrorCode getCode() {
		return this.code;
	}
}
