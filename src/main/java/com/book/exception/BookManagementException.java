package com.book.exception;

import com.book.security.error.BodyErrorCode;

@SuppressWarnings("serial")
public class BookManagementException extends Exception {
	private final BodyErrorCode code;

	public BookManagementException(BodyErrorCode code) {
		super();
		this.code = code;
	}
	
	public BodyErrorCode getCode() {
		return this.code;
	}
}
