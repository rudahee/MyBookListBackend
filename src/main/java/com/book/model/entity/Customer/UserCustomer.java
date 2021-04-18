package com.book.model.entity.Customer;


public class UserCustomer extends Customer {

	
	private UserCustomer friends;
	
	private AuthorCustomer follows;

	
	
	public UserCustomer() {
		super();
	}

	public UserCustomer(UserCustomer friends, AuthorCustomer follows) {
		super();
		this.friends = friends;
		this.follows = follows;
	}

	public UserCustomer getFriends() {
		return friends;
	}

	public void setFriends(UserCustomer friends) {
		this.friends = friends;
	}

	public AuthorCustomer getFollows() {
		return follows;
	}

	public void setFollows(AuthorCustomer follows) {
		this.follows = follows;
	}	
	
	
	
}
