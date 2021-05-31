package com.book.security.error;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BodyErrorCode implements Serializable {
	
	/*
	 * Specials Errors
	 */
	NO_ERROR(0, "OK"),
	INDETERMINATE_ERROR(-1, "Unknown error. Contact an administrator"),
	
	
	/*
	 * General Errors
	 */
	FIELD_IS_MISSING(0001,"Some required field is missing"),
	
	
	/*
	 * User Management
	 */
	INCORRECT_LOGIN(1001, "Incorrect username or password"),
	USERNAME_ALREADY_EXISTS(1002, "Username already exist"),
	USER_NOT_EXIST(1002, "User does not exist"),
	EMAIL_ALREADY_EXISTS(1003, "Email already exists"),
	ACTIVATION_CODE_MALFORMED(1004, "Activation code is missing or malformed"),
	ACTIVATION_CODE_INVALID(1005, "Activation code is invalid or expired"),
	FRIENDS_ITSELF(1006, "You are trying to make a friend request to yourself"),
	INVALID_PUBLIC_USER(1007, "you are trying to access an invalid user page"),
	
	/*
	 * JWT Related 
	 */
	TOKEN_NOT_VALID(2001, "TOKEN_NOT_VALID"),
	TOKEN_EXPIRED(2002, "TOKEN_EXPIRED"),
	
	/*
	 * Book Related
	 */
	BOOK_NOT_FOUND(3001, "The book or books have not been found"),
	
	FILE_TOO_BIG(9,"Maximun file size is 510Mb");
	
	private final Integer code;
	private final String message;

	private BodyErrorCode(Integer code, String message) {
		this.message = message;
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Integer getCode() {
		return code;
	}
	
}
