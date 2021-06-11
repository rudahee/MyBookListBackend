package com.book.exception;

import com.book.model.enumerated.BodyErrorCode;

/* This class is a custom exception to return errors related to User or account management. custom error codes are obtained from the BodyErrorCode enumeration.
 * 
 * @see BodyErrorCode
 * 
 * @author J. Rubén Daza
 */
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
