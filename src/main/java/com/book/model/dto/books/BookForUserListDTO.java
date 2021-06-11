package com.book.model.dto.books;

import com.book.model.enumerated.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/* This class represents a book. 
 * 
 * @author J. Rubén Daza
 */
@JsonInclude(Include.NON_NULL)
public class BookForUserListDTO {
	
	private Long id;
	private String name;
	private String imageUrl;
	private String sagaName;
	private String comment;
	private Double score;
	private Status status;
	private Integer pagesReaded;
	private Integer totalPages;
	
	public BookForUserListDTO() {
		super();
	}

	public BookForUserListDTO(Long id, String name, String imageUrl, String sagaName, String comment, Double score,
			Status status, Integer pagesReaded) {
		super();
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.sagaName = sagaName;
		this.comment = comment;
		this.score = score;
		this.status = status;
		this.pagesReaded = pagesReaded;
	}

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSagaName() {
		return sagaName;
	}

	public void setSagaName(String sagaName) {
		this.sagaName = sagaName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getPagesReaded() {
		return pagesReaded;
	}

	public void setPagesReaded(Integer pagesReaded) {
		this.pagesReaded = pagesReaded;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	
	
}
