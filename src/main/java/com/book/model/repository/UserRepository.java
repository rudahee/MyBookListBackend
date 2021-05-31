package com.book.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	Optional<User> findByUsernameAndPassword(String username, String password);

	boolean existsUserByEmail(String email);
	
	boolean existsUserByUsername(String username);

}
