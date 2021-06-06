package com.book.model.dto.books;

import java.util.Date;

import com.book.model.enumerated.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BookUserCustomerDTO {

	private Long id;
	private Long bookId;
	private Long userId;
	private String comment;
	private Double score;
	private Status status;
	private Integer pagesReaded;
	private Date date;
	
	public BookUserCustomerDTO() {
		super();
	}

	public BookUserCustomerDTO(Long id, Long bookId, Long userId, String comment, Double score, Status status,
			Integer pagesReaded) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.userId = userId;
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

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
