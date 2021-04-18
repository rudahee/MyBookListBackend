package com.book.model.entity.Customer;

public class AuthorCustomer extends Customer {

		
	private UserCustomer followers;
	
	public AuthorCustomer() {
		super();
	}

	public AuthorCustomer(UserCustomer followers) {
		super();
		this.followers = followers;
	}

	public UserCustomer getFollowers() {
		return followers;
	}

	public void setFollowers(UserCustomer followers) {
		this.followers = followers;
	}
}
