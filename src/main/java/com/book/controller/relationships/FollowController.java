package com.book.controller.relationships;

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
import com.book.model.enumerated.BodyErrorCode;
import com.book.security.common.SecurityConstants;
import com.book.security.jwt.JWTTokenProvider;
import com.book.service.converters.users.UserConverter;
import com.book.service.entity.users.UserService;
import com.book.service.utils.Checker;

/*
 * Controller for API Rest. 
 * 
 * Annotated by @RestController and @RequestMapping. its mapped in [url]:[port]/follow
 * 
 * This controller receives related to follow authors and the relationship between 
 * authors and users requests.
 * 
 * @author J. Rubén Daza
 */
@RestController
@RequestMapping(path = "/follow")
public class FollowController {
	
	@Autowired
	protected Checker checker;

	@Autowired
	protected UserService service;
	
	@Autowired
	protected UserConverter converter;
	
	/*
	 * This method receives an id of an author and gets the id of the user from the JWT token
	 * to create the relationship between the user and the author.
	 * 
	 * @param receiverId author id as Long
	 * @param request HttpServletRequest
	 * 
	 * @return ResponseEntity<BodyErrorCode>
	 */
	@PutMapping("{receiverId}")
	public ResponseEntity<BodyErrorCode> setFollower(HttpServletRequest request, @PathVariable Long receiverId) {
		Long requesterId = JWTTokenProvider.getIdFromToken(request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length()));
		
		try {
			service.followAuthor(requesterId, receiverId);
			return ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);
		} catch (UserManagementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCode());
		}
	}
	
	/*
	 * This method returns a list of the authors that a specific user follows through the JWT token.
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @return ResponseEntity<?> List<SimplifiedAuthorDTO> or BodyErrorCode.
	 */
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
