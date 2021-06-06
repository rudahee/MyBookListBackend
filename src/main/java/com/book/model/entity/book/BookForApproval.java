package com.book.model.entity.book;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class BookForApproval {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	@Column(nullable=false)
	private String name;
	@Lob
	@Column(columnDefinition = "text", length=8192, nullable=false)
	private String description;
	@Column(nullable=false)
	private Integer pages;
	@Column(nullable=false)
	private String imageUrl;
	@Column(nullable=false)
	private String isbn;
	@Column(nullable=false)
	private String publisher;
	@Column(nullable=true)
	private LocalDateTime publishDate;
	@Column(columnDefinition = "text", length=8192)
	private String notes;
	
	private String references1;
	private String references2;
	
	private String sagaName;
	private String authorName;
	
	public BookForApproval() {
		super();
	}
	
	public BookForApproval(Long id, String name, String description, Integer pages, String imageUrl, String isbn,
			String publisher, LocalDateTime publishDate, String notes, String references1, String references2,
			String sagaName, String authorName) {
		
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.pages = pages;
		this.imageUrl = imageUrl;
		this.isbn = isbn;
		this.publisher = publisher;
		this.publishDate = publishDate;
		this.notes = notes;
		this.references1 = references1;
		this.references2 = references2;
		this.sagaName = sagaName;
		this.authorName = authorName;
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
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getReferences1() {
		return references1;
	}
	public void setReferences1(String references1) {
		this.references1 = references1;
	}
	public String getReferences2() {
		return references2;
	}
	public void setReferences2(String references2) {
		this.references2 = references2;
	}
	public String getSagaName() {
		return sagaName;
	}
	public void setSagaName(String sagaName) {
		this.sagaName = sagaName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
}
