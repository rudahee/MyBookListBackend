package com.book.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.book.model.dto.UserDTO;
import com.book.model.entity.User;
import com.book.model.repository.UserRepository;
import com.book.service.abstracts.BaseService;
import com.book.service.converters.UserConverter;

@Service
public class UserService extends BaseService<User, UserDTO, UserConverter, UserRepository, Long> implements UserDetailsService {
	
	@Autowired
	protected PasswordEncoder passEncoder;
	
	public Optional<User> findByUsername(String username) {
		return this.repository.findByUsername(username);
	}	
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		return repository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("Username not found"));
	}

	public UserDetails loadUserById(Long id) {
		return super.repository.findById(id).get();
	}
}
