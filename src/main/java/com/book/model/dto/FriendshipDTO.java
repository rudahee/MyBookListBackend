package com.book.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/* This class represents a relationship between users
 * 
 * @author J. Rubén Daza
 */
@JsonInclude(Include.NON_NULL)
public class FriendshipDTO {
	
	private Long id;
	private Long requesterId;
	private String requesterName;
	private Long friendId;
	private String friendName;
	private Boolean accepted;
		
	public FriendshipDTO() {
		super();
	}

	public FriendshipDTO(Long id, Long requesterId, String requesterName, Long friendId, String friendName,
			Boolean accepted) {
		super();
		this.id = id;
		this.requesterId = requesterId;
		this.requesterName = requesterName;
		this.friendId = friendId;
		this.friendName = friendName;
		this.accepted = accepted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(Long requesterId) {
		this.requesterId = requesterId;
	}

	public String getRequesterName() {
		return requesterName;
	}

	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}
	
	
}
