package com.book.model.enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;

/* This enumeration is used to define type of recommendation.
 * 
 * @author J. Rubén Daza
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TypeRecommendation {
	FAVORITEGENRE(0), 
	FRIENDSBOOK(1), 
	AUTHORSBOOK(2), 
	RANDOMAUTHOR(3), 
	RANDOMBOOK(4);
	
	private final Integer code;

	private TypeRecommendation(Integer code) {
		this.code = code;
	}
	
	public Integer getCode() {
		return code;
	}
	
}
