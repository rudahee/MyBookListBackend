package com.book.model.dto.minimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/* This class represents a minimal data from author
 * 
 * @author J. Rubén Daza
 */
@JsonInclude(Include.NON_NULL)
public class AuthorPersonalDataDTO {

	private String urlImage;
	private String biography;
	
	public AuthorPersonalDataDTO() {
		super();
	}
	
	public AuthorPersonalDataDTO(String urlImage, String biography) {
		super();
		this.urlImage = urlImage;
		this.biography = biography;
	}
	
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	
	
	
}
