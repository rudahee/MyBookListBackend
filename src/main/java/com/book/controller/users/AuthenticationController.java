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
