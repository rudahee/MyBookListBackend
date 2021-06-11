package com.book.model.dto.books;

import java.time.LocalDateTime;
import java.util.List;

import com.book.model.enumerated.Genre;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/* This class represents a book. 
 * 
 * @author J. Rubén Daza
 */
@JsonInclude(Include.NON_NULL)
public class BookDTO {
	
	private Long id;
	private String name;
	private String description;
	private String imageUrl;
	private String isbn;
	private String publisher;
	private LocalDateTime publishDate;
	private Integer pages;
	private List<Genre> genres;
	private String sagaName;
	private Integer sagaTotalBooks;
	private Integer orderInSaga;
	
	public BookDTO() {
		super();
	}

	public BookDTO(Long id, String name, String description, String imageUrl, String isbn, String publisher,
			LocalDateTime publishDate, Integer pages) {
		super();
		this.id = id;
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

	public String getSagaName() {
		return sagaName;
	}

	public void setSagaName(String sagaName) {
		this.sagaName = sagaName;
	}

	public Integer getSagaTotalBooks() {
		return sagaTotalBooks;
	}

	public void setSagaTotalBooks(Integer sagaTotalBooks) {
		this.sagaTotalBooks = sagaTotalBooks;
	}

	public Integer getOrderInSaga() {
		return orderInSaga;
	}

	public void setOrderInSaga(Integer orderInSaga) {
		this.orderInSaga = orderInSaga;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	
	public void addGenre(Genre genre) {
		this.genres.add(genre);
	}
	
}
