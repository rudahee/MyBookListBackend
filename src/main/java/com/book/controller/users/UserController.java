package com.book.controller.users;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.exception.UserManagementException;
import com.book.model.dto.users.UserDTO;
import com.book.model.dto.users.UserPersonalDataDTO;
import com.book.model.enumerated.BodyErrorCode;
import com.book.model.enumerated.UserRole;
import com.book.security.common.SecurityConstants;
import com.book.security.jwt.JWTTokenProvider;
import com.book.service.converters.users.UserConverter;
import com.book.service.entity.users.UserService;
import com.book.service.utils.Checker;
import com.book.service.utils.RecommendationService;


/*
* Controller for API Rest. 
* 
* Annotated by @RestController and @RequestMapping. its mapped in [url]:[port]/user
* 
* This controller is in charge of managing all the requests that have to do with the user. 
* 
* @author J. Rubén Daza
*/
@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	protected Checker checker;

	@Autowired
	protected UserService service;
	
	@Autowired
	protected UserConverter converter;
	
	@Autowired
	protected RecommendationService recomendationService;
	
	/* HTTP/GET
	 * 
	 * This method returns all user personal data for himself.
	 * 
	 * @param request HttpServletRequest, used to extract JWT Token
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or UserDTO.
	 */
	@GetMapping("/me")
	public ResponseEntity<?> get(HttpServletRequest request) {
		Long id = JWTTokenProvider.getIdFromToken(
				request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length())
			);
		
		UserDTO dto = service.findById(id);
		dto.setImageProfile(service.getPersonalInfoForChange(id).getUrlImage());
		
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	/* HTTP/GET
	 * 
	 * This method returns user personal data for execute changes.
	 * 
	 * @param request HttpServletRequest, used to extract JWT Token
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or UserPersonalDataDTO.
	 */
	@GetMapping("/change-personal-data")
	public ResponseEntity<?> getPersonalInfoForChange(HttpServletRequest request) {
		Long id = JWTTokenProvider.getIdFromToken(
				request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length())
			);
		
		return ResponseEntity.status(HttpStatus.OK).body(service.getPersonalInfoForChange(id));
	}
	
	/* HTTP/GET
	 * 
	 * Get public data from user by id.
	 * 
	 * @param id Long user id
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or UserDTO.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getPublicUser(@PathVariable Long id) {
		try {
			UserDTO dto = this.service.findById(id);			

			if (dto.getRoles().contains(UserRole.USER)) {

				// We need delete some data from dto
				dto.setActivationCode(null);
				dto.setEnableAccount(null);
				dto.setLastPasswordChange(null);
				dto.setNextPasswordChange(null);
				dto.setPassword(null);
				
				return ResponseEntity.status(HttpStatus.OK).body(dto);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BodyErrorCode.INVALID_PUBLIC_USER);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
	/* HTTP/GET
	 * 
	 * Get recommendations based in your application used.
	 * 
	 * @param request HttpServletRequest, used to extract JWT Token
	 * 
	 * @return ResponseEntity<RecommendationDTO>.
	 */
	@GetMapping("/recommendations")
	public ResponseEntity<?> recommendation(HttpServletRequest request) {
		Long idUser = JWTTokenProvider.getIdFromToken(
				request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length())
				);
		return ResponseEntity.status(HttpStatus.OK).body(recomendationService.getRecommendations(idUser));
	}
	
	/* HTTP/PUT
	 * 
	 * This method allow edit personal data of user and setup a photo.
	 * 
	 * @param request HttpServletRequest, used to extract JWT Token
	 * @param data UserPersonalDataDTO.
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or AuthorPublicDTO.
	 */
	@PutMapping("/change-personal-data")
	public ResponseEntity<?> changePersonalData(HttpServletRequest request, @RequestBody UserPersonalDataDTO data) {
		try {
			Long userId = JWTTokenProvider.getIdFromToken(
					request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length())
					);
			
			this.service.changePersonalData(userId, data);
			return ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);
		
		} catch (UserManagementException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getCode());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
	/* HTTP/DELETE
	 * 
	 * This method allow remove your personal account
	 * 
	 * @param request HttpServletRequest, used to extract JWT Token
	 * 
	 * @return ResponseEntity<?> BodyErrorCode -> NO ERROR
	 */
	@DeleteMapping("/remove-account")
	public ResponseEntity<?> deleteAccount(HttpServletRequest request) {
		Long userId = JWTTokenProvider.getIdFromToken(
				request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length())
				);
		
		this.service.deleteById(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);
	}
}
