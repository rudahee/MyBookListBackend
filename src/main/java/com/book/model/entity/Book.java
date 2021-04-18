package com.book.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@Column(nullable=false)
	private String name;
	@Column(nullable=false)
	private String description;
	
	@Column(nullable=false)
	private String imageUrl;
	
	@Column(nullable=false, unique=true)
	private String isbn;
	@Column(nullable=false)
	private String publisher;
	@Column(nullable=true)
	private LocalDateTime publishDate;
	
	private Integer pages;
	
	//private List<AuthorCustomer> author;

	//private Saga saga;
	
	public Book() {
		super();
	}

	public Book(String name, String description, String imageUrl, String isbn, String publisher,
			LocalDateTime publishDate, Integer pages) {
		super();
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.isbn = isbn;
		this.publisher = publisher;
		this.publishDate = publishDate;
		this.pages = pages;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public LocalDateTime getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDateTime publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}
	
}
