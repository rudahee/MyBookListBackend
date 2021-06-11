package com.book.model.entity.book;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.book.model.entity.Saga;
import com.book.model.entity.customer.AuthorCustomer;
import com.book.model.entity.relationship.BookUserCustomer;
import com.book.model.enumerated.Genre;

/* This class represents a Book table in database
 * 
 * @author J. Rubén Daza
 */
@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	@Column(nullable=false)
	private String name;
	
	@Lob //Large object. The length is greater than the maximum size of a java string 
	@Column(columnDefinition = "text", length=4250, nullable=false)
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

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private List<Genre> genres;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "books")
	private List<AuthorCustomer> author;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="saga_id", nullable=true)
	private Saga saga;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
	private List<BookUserCustomer> users;
	
	
	public Book() {
		super();
		users = new ArrayList<BookUserCustomer>();
		author = new ArrayList<AuthorCustomer>();
		genres = new ArrayList<Genre>();
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
		users = new ArrayList<BookUserCustomer>();
		author = new ArrayList<AuthorCustomer>();
		genres = new ArrayList<Genre>();
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
	
	public Saga getSaga() {
		return saga;
	}

	public void setSaga(Saga saga) {
		this.saga = saga;
	}

	public List<BookUserCustomer> getUsers() {
		return users;
	}

	public void setUsers(List<BookUserCustomer> users) {
		this.users = users;
	}
	
	public void addUser(BookUserCustomer user) {
		this.users.add(user);
	}
	
	public List<AuthorCustomer> getAuthor() {
		return author;
	}

	public void setAuthor(List<AuthorCustomer> author) {
		this.author = author;
	}
	
	public void addAuthor(AuthorCustomer author) {
		this.author.add(author);
	}
	
	public void AddAllAuthors(List<AuthorCustomer> authors) {
		this.author.addAll(authors);
	}
	
	public List<Genre> getGenre() {
		return genres;
	}
	
	public void setGenre(List<Genre> genre) {
		this.genres = genre;
	}
	
	public void setGenre(Genre genre) {
		this.genres.add(genre);
	}
}
