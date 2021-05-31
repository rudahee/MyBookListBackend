package com.book.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.model.entity.Customer.UserCustomer;
import com.book.model.entity.Relationship.Friendship;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

	
	Optional<Friendship> findByFriendAndRequester(UserCustomer friend, UserCustomer requester);
	
	List<Friendship> findAllByfriend(UserCustomer friend);

}
