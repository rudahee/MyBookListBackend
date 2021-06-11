package com.book.model.dto.books;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/* This class represents a saga
 * 
 * @author J. Rubén Daza
 */
@JsonInclude(Include.NON_NULL)
public class SagaDTO {

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	private List<String> booksName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getBooksName() {
		return booksName;
	}

	public void setBooksName(List<String> booksName) {
		this.booksName = booksName;
	}

	
}
