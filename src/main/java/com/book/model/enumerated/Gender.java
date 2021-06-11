package com.book.model.enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;

/* This enumeration is used to define gender of user.
 * 
 * @author J. Rubén Daza
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Gender {
	MALE, FEMALE, NONE
}
