package com.book.model.dto.users;

import java.util.List;
import java.util.Set;

import com.book.model.dto.books.BookDTO;
import com.book.model.enumerated.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/* This class represents a author
 * 
 * @author J. Rubén Daza
 */
@JsonInclude(Include.NON_NULL)
public class AuthorPublicDTO {
	private Long id;
	private String name;
	private String surname;
	private Integer age;
	
	private String username;
	private String email;
	private Set<UserRole> roles;
	private List<BookDTO> books;
	private String biography;
	private String urlImage;
	
	
	public AuthorPublicDTO() {
		super();
	}

	public AuthorPublicDTO(String name, String surname, Integer age, String username, String email,
			Set<UserRole> roles, List<BookDTO> books, String biography, String urlImage) {
		super();
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.books = books;
		this.biography = biography;
		this.urlImage = urlImage;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public List<BookDTO> getBooks() {
		return books;
	}

	public void setBooks(List<BookDTO> books) {
		this.books = books;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
