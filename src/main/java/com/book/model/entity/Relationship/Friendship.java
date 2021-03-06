package com.book.model.entity.relationship;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.book.model.entity.customer.UserCustomer;

/* This class represents a relationship between users, normally spring does the N: M relations automatically, 
 * but I needed to have an exhaustive control of this class, in addition to adding extra parameters.
 * 
 * @author J. Rubén Daza
 */
@SuppressWarnings("serial")
@Entity
public class Friendship implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
		
	@ManyToOne
	@JoinColumn(name = "requester_id")
	 private UserCustomer requester;
	
	@ManyToOne
	@JoinColumn(name = "friend_id")
	private UserCustomer friend;
	
	@Column(nullable = false)
	private Boolean accepted;
	
	public Friendship() {
		super();
	}

	public Friendship(UserCustomer requester, UserCustomer friend, Boolean accepted) {
		super();
		this.requester = requester;
		this.friend = friend;
		this.accepted = accepted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserCustomer getRequester() {
		return requester;
	}

	public void setRequester(UserCustomer requester) {
		this.requester = requester;
	}

	public UserCustomer getFriend() {
		return friend;
	}

	public void setFriend(UserCustomer friend) {
		this.friend = friend;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}
	
}
