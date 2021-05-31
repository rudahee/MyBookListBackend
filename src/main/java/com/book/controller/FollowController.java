package com.book.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.exception.UserManagementException;
import com.book.security.common.SecurityConstants;
import com.book.security.error.BodyErrorCode;
import com.book.security.jwt.JWTTokenProvider;
import com.book.service.UserService;
import com.book.service.converters.UserConverter;
import com.book.service.utils.Checker;

@RestController
@RequestMapping(path = "/follow")
public class FollowController {
	
	@Autowired
	protected Checker checker;

	@Autowired
	protected UserService service;
	
	@Autowired
	protected UserConverter converter;
	
	@PutMapping("{receiverId}")
	public ResponseEntity<?> setFollower(HttpServletRequest request, @PathVariable Long receiverId) {
		Long requesterId = JWTTokenProvider.getIdFromToken(request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length()));
		
		try {
			service.followAuthor(requesterId, receiverId);
			return ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);
		} catch (UserManagementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCode());
		}
	}
	
	@GetMapping("all")
	public ResponseEntity<?> getFollowers(HttpServletRequest request) {
		Long userId = JWTTokenProvider.getIdFromToken(request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length()));
	
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.getFollows(userId));
		} catch (UserManagementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCode());
		}
	}
		
}
