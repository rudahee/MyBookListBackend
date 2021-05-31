package com.book.model.entity.Relationship;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.book.model.entity.Book;
import com.book.model.entity.Customer.UserCustomer;
import com.book.model.enumerated.Status;

@Entity
public class BookUserCustomer {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "user_customer_id")
	private UserCustomer user;
	
	@Column(nullable = true)
	private String comment;
	
	@Column(precision = 2, nullable = true)
	private Double score;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	private Integer pagesReaded;
	
	private LocalDateTime date;
	
	public BookUserCustomer() {
		super();
	}

	public BookUserCustomer(Long id, Book book, UserCustomer user, String comment, Double score,
			Status status, Integer pagesReaded) {
		super();
		this.id = id;
		this.book = book;
		this.user = user;
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

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public UserCustomer getUser() {
		return user;
	}

	public void setUser(UserCustomer user) {
		this.user = user;
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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
