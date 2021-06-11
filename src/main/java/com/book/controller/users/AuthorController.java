package com.book.controller.users;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.exception.UserManagementException;
import com.book.model.dto.minimal.AuthorPersonalDataDTO;
import com.book.model.dto.users.AuthorPublicDTO;
import com.book.model.dto.users.SimplifiedAuthorDTO;
import com.book.model.dto.users.UserDTO;
import com.book.model.enumerated.BodyErrorCode;
import com.book.security.common.SecurityConstants;
import com.book.security.jwt.JWTTokenProvider;
import com.book.service.entity.users.AuthorService;


/*
* Controller for API Rest. 
* 
* Annotated by @RestController and @RequestMapping. its mapped in [url]:[port]/author
* 
* This controller is in charge of managing all the requests that have to do with the authors. 
* 
* @author J. Rubén Daza
*/
@RestController
@RequestMapping(path = "/author")
public class AuthorController {
	
	@Autowired
	protected AuthorService service;

	/* HTTP/GET
	 * 
	 * This method returns all the authors that exist in the system. 
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or List<UserDTO>.
	 */
	@GetMapping("/all")
	public ResponseEntity<?> getAllAuthorSimplified() {
		List<UserDTO> dtos = service.getAllAuthors();
		
		if (dtos != null && !dtos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(dtos);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.USER_NOT_EXIST);			
		}
	}
	
	/* HTTP/GET
	 * 
	 * This method returns all info from one authors that exist in the system. 
	 * 
	 * @param id Long for author id
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or AuthorPublicDTO.
	 */
	@GetMapping("/public/{id}")
	public ResponseEntity<?> getPublicAuthor(@PathVariable Long id) {
		try {
			AuthorPublicDTO authorDTO = this.service.getPublicAuthor(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(authorDTO);
		} catch (UserManagementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCode());
		}
	}
	
	/* HTTP/GET
	 * 
	 * This method returns all the authors that a user follows. 
	 * 
	 * @param request HttpServletRequest, used to extract JWT Token
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or AuthorPublicDTO.
	 */
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

	/* HTTP/PUT
	 * 
	 * This method creates a relationship between a user and an author, this relationship is "following" an author. 
	 * 
	 * @param request HttpServletRequest, used to extract JWT Token
	 * @param authorId Long
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or AuthorPublicDTO.
	 */
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
	
	
	/* HTTP/PUT
	 * 
	 * This method allow edit biography and setup a photo.
	 * 
	 * @param request HttpServletRequest, used to extract JWT Token
	 * @param data AuthorPersonalDataDTO.
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or AuthorPublicDTO.
	 */
	@PutMapping("/change-personal-data")
	public ResponseEntity<?> changePersonalData(HttpServletRequest request, @RequestBody AuthorPersonalDataDTO data) {
		try {
			Long userId = JWTTokenProvider.getIdFromToken(
					request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length())
					);
			
			this.service.changePersonalData(userId, data);
			return ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
}
