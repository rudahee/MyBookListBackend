package com.book.model.dto.minimal;

import com.book.model.enumerated.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/* This class represents a comment from a relatonship between book and user
 * 
 * @author J. Rubén Daza
 */
@JsonInclude(Include.NON_NULL)
public class CommentsDTO {

	
	private String comment;
	private String username;
	private Double rating;
	private Status status;
	
	public CommentsDTO(String comment, String username, Double rating, Status status) {
		super();
		this.comment = comment;
		this.username = username;
		this.rating = rating;
		this.status = status;
	}

	public CommentsDTO() {
		super();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
