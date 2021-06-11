package com.book.model.entity.customer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.book.model.entity.relationship.BookUserCustomer;
import com.book.model.entity.relationship.Friendship;

/* This class represents a user_customer table in database
 * 
 * @author J. Rubén Daza
 */
@SuppressWarnings("serial")
@Entity	
public class UserCustomer extends Customer {
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "requester", cascade = CascadeType.ALL)
    private Set<Friendship> requests = new HashSet<Friendship>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "friend", cascade = CascadeType.ALL)
    private Set<Friendship> friends = new HashSet<Friendship>();
	
    private String urlImageProfile;
    
    @ManyToMany(mappedBy="followers")
	private List<AuthorCustomer> follows;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<BookUserCustomer> books;
	
	public UserCustomer() {
		super();
		follows = new ArrayList<AuthorCustomer>();
	}
	
	public void addAuthorFollow(AuthorCustomer author) {
		follows.add(author);
	}
	
	public List<AuthorCustomer> getFollows() {
		return this.follows;
	}

	public List<BookUserCustomer> getBooks() {
		return books;
	}

	public void setBooks(List<BookUserCustomer> books) {
		this.books = books;
	}
	
	public void addBook(BookUserCustomer book) {
		this.books.add(book);
	}

	public Set<Friendship> getRequests() {
		return requests;
	}

	public void setRequests(Set<Friendship> requests) {
		this.requests = requests;
	}

	public Set<Friendship> getFriends() {
		return friends;
	}

	public void setFriends(Set<Friendship> friends) {
		this.friends = friends;
	}

	public String getUrlImageProfile() {
		return urlImageProfile;
	}

	public void setUrlImageProfile(String urlImageProfile) {
		this.urlImageProfile = urlImageProfile;
	}
	
}
