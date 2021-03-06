package com.book.model.dto.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/* This class represents a author
 * 
 * @author J. Rubén Daza
 */
@JsonInclude(Include.NON_NULL)
public class SimplifiedAuthorDTO {
	
	private long id;
	private String name;
	private String surname;
	private String username;
	private String completeName;
	private String imageUrl;
		
	public SimplifiedAuthorDTO() {
		super();
	}

	public SimplifiedAuthorDTO(long id, String name, String surname, String username) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
