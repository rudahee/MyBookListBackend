package com.book.model.entity.Customer;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import com.book.model.entity.Book;

@SuppressWarnings("serial")
@Entity
public class AuthorCustomer extends Customer {

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="followers", joinColumns=@JoinColumn(name="author_customer_id"), inverseJoinColumns=@JoinColumn(name="user_customer_id"))
	private List<UserCustomer> followers;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="author_book", joinColumns=@JoinColumn(name="author_id"), inverseJoinColumns=@JoinColumn(name="book_id"))
	private List<Book> books;
	
	@Lob
	@Column(columnDefinition = "text", length=4218, nullable=false)
	private String biography;
	
	private String urlImage;

	public AuthorCustomer() {
		super();
	}

	public AuthorCustomer(List<UserCustomer> followers, List<Book> books) {
		super();
		this.followers = followers;
		this.books = books;
	}

	public List<UserCustomer> getFollowers() {
		return followers;
	}
	
	public void addFollower(UserCustomer follower) {
		this.followers.add(follower);
	}

	public void setFollowers(List<UserCustomer> followers) {
		this.followers = followers;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public void addBook(Book book) {
		this.books.add(book);
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
}

