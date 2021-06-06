package com.book.exception;

import com.book.model.enumerated.BodyErrorCode;

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
