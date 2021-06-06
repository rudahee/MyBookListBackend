package com.book.controller.users;

import java.util.List;

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
import com.book.model.dto.users.AuthorPublicDTO;
import com.book.model.dto.users.SimplifiedAuthorDTO;
import com.book.model.dto.users.UserDTO;
import com.book.model.enumerated.BodyErrorCode;
import com.book.security.common.SecurityConstants;
import com.book.security.jwt.JWTTokenProvider;
import com.book.service.entity.users.AuthorService;

@RestController
@RequestMapping(path = "/author")
public class AuthorController {
	
	@Autowired
	protected AuthorService service;

	
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllAuthorSimplified() {
		List<UserDTO> dtos = service.getAllAuthors();
		
		if (dtos != null && !dtos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(dtos);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.USER_NOT_EXIST);			
		}
	}
	
	@GetMapping("/public/{id}")
	public ResponseEntity<?> getPublicAuthor(@PathVariable Long id) {
		try {
			AuthorPublicDTO authorDTO = this.service.getPublicAuthor(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(authorDTO);
		} catch (UserManagementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCode());
		}
	}
	
	@GetMapping("/followed")
	public ResponseEntity<?> getFollowedAuthor(HttpServletRequest request) {		
		try {
			Long id = JWTTokenProvider.getIdFromToken(
					request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length())
					);
			
			List<SimplifiedAuthorDTO> dtos = this.service.getFollowedAuthor(id);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(dtos);
			
		} catch (UserManagementException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
		}		
	}
	
	@PutMapping("/follow/{authorId}")
	public ResponseEntity<?> followAuthor(HttpServletRequest request, @PathVariable Long authorId) {
		try {
			Long userId = JWTTokenProvider.getIdFromToken(
					request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length())
					);
			
			if (this.service.followAuthor(userId, authorId)) {
				return ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
			}
		} catch (UserManagementException e) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(e.getCode());
		}
	}
	
}
