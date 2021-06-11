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

/*
* Controller for API Rest. 
* 
* Annotated by @RestController and @RequestMapping. its mapped in [url]:[port]/friend
* 
* This controller receives related to friends relationship between users requests.
* 
* @author J. Rubén Daza
*/
@RestController
@RequestMapping(path = "/friend")
public class FriendController {
	@Autowired
	protected Checker checker;

	@Autowired
	protected UserService service;
	
	@Autowired
	protected UserConverter converter;
	
	/* HTTP/GET
	 * 
	 * This method returns a list of the friends that a specific user through the JWT token.
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @return ResponseEntity<?> List<SimplifiedAuthorDTO> or BodyErrorCode.
	 */
	@GetMapping("")
	public ResponseEntity<?> friendList(HttpServletRequest request) {
		Long requesterId = JWTTokenProvider.getIdFromToken(request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length()));

		return ResponseEntity.status(HttpStatus.OK).body(service.getFriendList(requesterId));
	}
	
	/* HTTP/PUT
	 * This method receives an id of an another user and gets the id of the user from the JWT token
	 * to create the relationship between the users.
	 * 
	 * @param receiverId user id as Long
	 * @param request HttpServletRequest
	 * 
	 * @return ResponseEntity<BodyErrorCode>
	 */
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
	
	/* HTTP/PUT
	 * This method receives an id of an user and gets the id of the user from the JWT token
	 * to accept a friendship.
	 * 
	 * @param receiverId user id as Long
	 * @param request HttpServletRequest
	 * 
	 * @return ResponseEntity<BodyErrorCode>
	 */	
	@PutMapping("/accept/{requesterId}")
	public ResponseEntity<?> friendAccept(HttpServletRequest request, @PathVariable Long requesterId) {
		Long receiverId = JWTTokenProvider.getIdFromToken(request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length()));
		
		if (service.acceptFriendRequest(requesterId, receiverId)) {
			return ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
	/* HTTP/DELETE
	 * This method receives an id of an user and gets the id of the user from the JWT token
	 * to reject a friendship.
	 * 
	 * @param receiverId user id as Long
	 * @param request HttpServletRequest
	 * 
	 * @return ResponseEntity<BodyErrorCode>
	 */	
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
