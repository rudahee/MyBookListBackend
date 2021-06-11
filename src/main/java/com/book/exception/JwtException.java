package com.book.exception;

import com.book.model.enumerated.BodyErrorCode;

/* This class is a custom exception to return errors related to JWT code. custom error codes are obtained from the BodyErrorCode enumeration.
 * 
 * @see BodyErrorCode
 * 
 * @author J. Rubén Daza
 */
@SuppressWarnings("serial")
public class JwtException extends Exception {
	private final BodyErrorCode code;

	public JwtException(BodyErrorCode code) {
		super();
		this.code = code;
	}
	
	public BodyErrorCode getCode() {
		return this.code;
	}

}
