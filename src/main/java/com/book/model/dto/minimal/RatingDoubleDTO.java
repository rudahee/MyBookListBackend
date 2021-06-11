package com.book.model.dto.minimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/* This class represents a rating of a book
 * 
 * @author J. Rubén Daza
 */
@JsonInclude(Include.NON_NULL)
public class RatingDoubleDTO {

	private Double score;

	public RatingDoubleDTO(Double score) {
		super();
		this.score = score;
	}
	
	public RatingDoubleDTO() {
		super();
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
	
	
}
