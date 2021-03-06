package com.book.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.exception.UserManagementException;
import com.book.model.dto.users.UserDTO;
import com.book.model.enumerated.BodyErrorCode;
import com.book.service.converters.users.UserConverter;
import com.book.service.entity.users.UserService;
import com.book.service.utils.Checker;
import com.book.service.utils.EmailService;

/*
* Controller for API Rest. 
* 
* Annotated by @RestController and @RequestMapping. its mapped in [url]:[port]/auth
* 
* This controller is responsible for the creation and login of users in the system. It also allows account activation.
* 
* @author J. Rubén Daza
*/
@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

	@Autowired
	protected Checker checker;

	@Autowired
	protected UserService service;
	
	@Autowired
	protected UserConverter converter;
	
	@Autowired
	private EmailService emailService;	
	
	/* HTTP/POST
	 * 
	 * This method allows you to register a new user in the system.
	 * 
	 * @param user UserDTO 
	 * 
	 * @return ResponseEntity<BodyErrorCode>.
	 */
	@PostMapping("/sign-up")
	public ResponseEntity<?> signUpUser(@RequestBody UserDTO user) {
		ResponseEntity<?> response = null;
		
		try {
			if (checker.checkRegister(user)) {
				user = service.signUpUser(user);
				response = ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);				
			}
		} catch (UserManagementException ex) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getCode());
		}
		
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			emailService.sendActivationMail(user.getId(), user.getUsername(), user.getEmail(), user.getActivationCode());
		}
		
		return response;
	}
	
	/* HTTP/PUT
	 * 
	 * This driver allows you to activate a user account
	 * 
	 * @param user UserDTO 
	 * 
	 * @return ResponseEntity<BodyErrorCode>.
	 */
	@PutMapping("/activate")
	public ResponseEntity<?> activateAccount(@RequestBody UserDTO dataReceived) {
		UserDTO user = null;
		
		try {
			
			if (checker.checkActivationCode(dataReceived)) {
				
				
				if (!service.existsById(dataReceived.getId())) {
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(BodyErrorCode.ACTIVATION_CODE_INVALID);
				} else {
					user = service.findById(dataReceived.getId());					
				}
				
				if (user.getActivationCode().equals(dataReceived.getActivationCode()) && !user.getEnableAccount()) {
					user.setActivationCode(null);
					user.setEnableAccount(true);
					
					user = service.edit(user);
				} else {
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(BodyErrorCode.ACTIVATION_CODE_INVALID);
				}
			}
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
			
		} catch (UserManagementException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getCode());
		}
	}
	
	/* HTTP/POST
	 * 
	 * This method allows an admin to register a new author in the system.
	 * 
	 * @param user UserDTO 
	 * 
	 * @return ResponseEntity<BodyErrorCode>.
	 */
	@PostMapping("/sign-up/author")
	public ResponseEntity<?> signUpAuthor(@RequestBody UserDTO user) {
		ResponseEntity<?> response = null;
			
		try {
			if (checker.checkRegisterSpecialAccount(user)) {
				user = service.signUpAuthor(user);
				response = ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);	
			}
		} catch (UserManagementException ex) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getCode());
		}		
		return response;
	}
	
	/* HTTP/POST
	 * 
	 * This method allows an admin to register a new admin in the system.
	 * 
	 * @param user UserDTO 
	 * 
	 * @return ResponseEntity<BodyErrorCode>.
	 */
	@PostMapping("/sign-up/admin")
	public ResponseEntity<?> signUpAdmin(@RequestBody UserDTO user) {
		ResponseEntity<?> response = null;
		
		try {
			if (checker.checkRegisterSpecialAccount(user)) {
				user = service.signUpAdmin(user);
				response = ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);
				emailService.sendSignUpComplete(user.getUsername(), user.getEmail());
			}
		} catch (UserManagementException ex) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getCode());
		}
		
		return response;
	}

	/* HTTP/POST
	 * 
	 * This controller allows logging an account in the system, 
	 * in the response the JWT token will be returned.
	 * 
	 * @param user UserDTO 
	 * 
	 * @return ResponseEntity<BodyErrorCode>.
	 */
	@PostMapping("/sign-in")
	public ResponseEntity<?> signIn(@RequestBody UserDTO user) {
		ResponseEntity<?> response = null;
		try {
			if (checker.checkLogin(user)) {
				response = ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);
			}
		} catch (UserManagementException ex) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getCode());
		}		
		return response;
	}
	
}
