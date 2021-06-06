package com.book.controller.relationships;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.exception.UserManagementException;
import com.book.model.enumerated.BodyErrorCode;
import com.book.security.common.SecurityConstants;
import com.book.security.jwt.JWTTokenProvider;
import com.book.service.converters.users.UserConverter;
import com.book.service.entity.users.UserService;
import com.book.service.utils.Checker;

@RestController
@RequestMapping(path = "/friend")
public class FriendController {
	@Autowired
	protected Checker checker;

	@Autowired
	protected UserService service;
	
	@Autowired
	protected UserConverter converter;
	
	@GetMapping("")
	public ResponseEntity<?> friendList(HttpServletRequest request) {
		Long requesterId = JWTTokenProvider.getIdFromToken(request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length()));

		return ResponseEntity.status(HttpStatus.OK).body(service.getFriendList(requesterId));
		
	}
	
	@PutMapping("/request/{receiverId}")
	public ResponseEntity<?> friendRequest(HttpServletRequest request, @PathVariable Long receiverId) {
		Long requesterId = JWTTokenProvider.getIdFromToken(request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length()));
		
		try {
			if (service.createFriendRequest(requesterId, receiverId)) {
				return ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);

			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
			}
		} catch (UserManagementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCode());
		}
	}
	
	@PutMapping("/accept/{requesterId}")
	public ResponseEntity<?> friendAccept(HttpServletRequest request, @PathVariable Long requesterId) {
		Long receiverId = JWTTokenProvider.getIdFromToken(request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length()));
		
		if (service.acceptFriendRequest(requesterId, receiverId)) {
			return ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
	@DeleteMapping("reject/{requesterId}")
	public ResponseEntity<?> friendReject(HttpServletRequest request, @PathVariable Long requesterId){
		Long receiverId = JWTTokenProvider.getIdFromToken(request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length()));

		if (service.rejectFriendRequest(requesterId, receiverId)) {
			return ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}


}
